package com.rubyboat.daily.armor;

import net.minecraft.item.ItemStack;

public interface CamoItem {
    default double getCamoPercent(ItemStack stack) {
        return 1;
    }
}
