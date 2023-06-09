package com.rubyboat.daily.items;

import com.rubyboat.daily.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

public class MeshPannerItem extends Item {
    static final Identifier WATER_LOOT_ID = new Identifier(Main.MODID, "panning/water.json");
    public MeshPannerItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 200;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(world.isClient()) {
            return;
        }

        ServerWorld serverWorld = (ServerWorld) world;
        if(user instanceof PlayerEntity player) {
            if(remainingUseTicks != 0) {
                if(raycastToFluid(world, player).isEmpty()) {
                    player.stopUsingItem();
                }
            }
            raycastToFluid(world, player).ifPresent((p) -> {
                var state = world.getBlockState(p.getRight().getBlockPos());
                if(p.getLeft().getFluidState(state).isIn(FluidTags.WATER)) {

                    LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(
                        serverWorld)
                        .add(LootContextParameters.ORIGIN, user.getPos())
                        .luck(player.getLuck())
                        .add(LootContextParameters.THIS_ENTITY, player)
                        .build(LootContextTypes.GENERIC);

                    var lootManager = serverWorld.getServer().getLootManager();
                    var table = lootManager.getLootTable(MeshPannerItem.WATER_LOOT_ID);
                    var loot = table.generateLoot(lootContextParameterSet, new Random().nextLong());

                    for(ItemStack item : loot) {
                        player.getInventory().insertStack(item);
                    }
                }
            });
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    public Optional<Pair<FluidBlock, BlockHitResult>> raycastToFluid(World world, PlayerEntity user) {
        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if(hitResult.getType() != HitResult.Type.BLOCK) {
            return Optional.empty();
        }

        BlockState state = world.getBlockState(hitResult.getBlockPos());
        Block block = state.getBlock();
        user.sendMessage(block.getName(), true);

        if(block instanceof FluidBlock fluid) {
            return Optional.of(new Pair<>(fluid, hitResult));
        }
        return Optional.empty();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(raycastToFluid(world, user).isPresent()) {
            return TypedActionResult.consume(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BRUSH;
    }
}
