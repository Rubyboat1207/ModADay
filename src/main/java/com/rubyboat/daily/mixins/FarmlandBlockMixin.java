package com.rubyboat.daily.mixins;

import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.rubyboat.daily.Main.MOONSTONE_APPLIED;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends Block {

    public FarmlandBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendProperties", at = @At("HEAD"))
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(MOONSTONE_APPLIED);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var blockAbove = world.getBlockState(pos.up());

        if(state.get(MOONSTONE_APPLIED) && world.isNight() && blockAbove.isIn(BlockTags.MAINTAINS_FARMLAND)) {
            CropBlock cropBlock = (CropBlock) blockAbove.getBlock();
            cropBlock.applyGrowth(world, pos.up(), blockAbove);
        }
    }

    @ModifyArg(method = "<init>", at = @At(value="INVOKE", target = "Lnet/minecraft/block/FarmlandBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"))
    public BlockState modifyDefaultState(BlockState par1) {
        return par1.with(MOONSTONE_APPLIED, false);
    }

    @ModifyArg(method = "<init>", at = @At(value="INVOKE", target = "Lnet/minecraft/block/Block;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static Settings modifyBlockProperties(Settings settings) {
        settings.luminance((b) -> b.get(MOONSTONE_APPLIED) ? 6 : 0);
        return settings;
    }
}
