package com.rubyboat.daily.items;

import com.rubyboat.daily.Main;
import com.rubyboat.madman.MadMan;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MeshPannerItem extends Item {
    static final Identifier WATER_LOOT_ID = new Identifier(Main.MODID, "panning/water");
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
            if(remainingUseTicks > 1) {
                if(raycastToFluid(world, player).isEmpty()) {
                    player.stopUsingItem();
                }
                return;
            }

            raycastToFluid(world, player).ifPresent((p) -> {
                var state = world.getBlockState(p.getRight().getBlockPos());
                if (p.getLeft().getFluidState(state).isIn(FluidTags.WATER)) {

                    LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder(serverWorld)
                            .add(LootContextParameters.ORIGIN, p.getRight().getPos())
                            .add(LootContextParameters.THIS_ENTITY, player);


                    var lootManager = serverWorld.getServer().getLootManager();
                    var table = lootManager.getLootTable(MeshPannerItem.WATER_LOOT_ID);
                    var loot = table.generateLoot(builder.build(LootContextTypes.CHEST), new Random().nextLong());

                    for (ItemStack item : loot) {
                        player.getInventory().insertStack(item);
                    }
                    player.stopUsingItem();
                }
            });
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(raycastToFluid(world, user).isPresent()) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(raycastToFluid(context.getWorld(), context.getPlayer()).isPresent()) {
            return ActionResult.CONSUME;
        }
        return ActionResult.FAIL;
    }

    public Optional<Pair<FluidBlock, BlockHitResult>> raycastToFluid(World world, PlayerEntity user) {
        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if(hitResult.getType() != HitResult.Type.BLOCK) {
            return Optional.empty();
        }

        BlockState state = world.getBlockState(hitResult.getBlockPos());
        Block block = state.getBlock();

        if(block instanceof FluidBlock fluid) {
            return Optional.of(new Pair<>(fluid, hitResult));
        }
        return Optional.empty();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BRUSH;
    }
}
