package com.rubyboat.daily.items;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.LootTable;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class MeshPannerItem extends Item {
    public MeshPannerItem(Settings settings) {
        super(settings);
    }

    LootTable lootTable;


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        context.getPlayer().sendMessage(context.getWorld().getBlockState(context.getBlockPos()).getBlock().getName(), true);
        return super.useOnBlock(context);
    }

}
