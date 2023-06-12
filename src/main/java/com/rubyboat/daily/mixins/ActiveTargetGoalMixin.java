package com.rubyboat.daily.mixins;

import com.rubyboat.daily.armor.CamoItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Predicate;

@Debug(export = true)
@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin extends TrackTargetGoal {
    public ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @ModifyArg(
            method = "<init>(Lnet/minecraft/entity/mob/MobEntity;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/TargetPredicate;setPredicate(Ljava/util/function/Predicate;)Lnet/minecraft/entity/ai/TargetPredicate;")
    )
    public Predicate<LivingEntity> setPredicate(@Nullable Predicate<LivingEntity> predicate) {
        Predicate<LivingEntity> mypred = (e) -> {
            EquipmentSlot[] slots = {
                    EquipmentSlot.HEAD,
                    EquipmentSlot.CHEST,
                    EquipmentSlot.LEGS,
                    EquipmentSlot.FEET,
            };

            double newMax = this.getFollowRange();
            double max = this.getFollowRange();

            for(var eq : slots) {
                var stack = e.getEquippedStack(eq);
                if(stack.getItem() instanceof CamoItem camo) {
                    newMax -= max * camo.getCamoPercent(stack);
                }
            }
            if(newMax < 1) {
                newMax = 1;
            }

            return e.distanceTo(this.mob) < newMax;
        };
        if(predicate == null) {
            return mypred;
        }
        return predicate.and(mypred);
    }
}
