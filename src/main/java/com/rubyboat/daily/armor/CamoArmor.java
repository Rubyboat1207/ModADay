package com.rubyboat.daily.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;

public class CamoArmor extends ArmorItem implements CamoItem {
    public CamoArmor(Type type, Settings settings) {
        super(new CamoMaterial(), type, settings);
    }

    @Override
    public double getCamoPercent(ItemStack stack) {
        CamoArmor armor = (CamoArmor) stack.getItem();
        switch(armor.type.getEquipmentSlot()) {
            case FEET -> {
                return 0.1;
            }
            case LEGS -> {
                return 0.2;
            }
            case HEAD -> {
                return 0.3;
            }
            case CHEST -> {
                return 0.4;
            }
            default -> {
                return 0;
            }
        }
    }
}
