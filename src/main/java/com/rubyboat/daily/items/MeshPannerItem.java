package com.rubyboat.daily.items;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.LootTable;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class MeshPannerItem extends Item {
    public MeshPannerItem(Settings settings) {
        super(settings);
    }

    LootTable lootTable;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if(hitResult.getType() == HitResult.Type.BLOCK) {
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            user.sendMessage(block.getName(), true);
        }

        return super.use(world, user, hand);
    }
}
