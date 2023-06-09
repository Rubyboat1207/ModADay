package com.rubyboat.madman.mixins;

import com.rubyboat.madman.MadMan;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    int skipped = 0;
    @ModifyConstant(method = "tickTime", constant =  @Constant(longValue = 1L))
    public long tickTime(long constant) {
        if(skipped >= getMult()) {
            return constant;
        }
        return 0;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if(skipped < getMult()) {
            skipped++;
        }else {
            skipped = 0;
        }
    }

    public double getMult() {
        return ((World) ((Object) this)).getGameRules().get(MadMan.TIME_MULTIPLIER).get();
    }
}
