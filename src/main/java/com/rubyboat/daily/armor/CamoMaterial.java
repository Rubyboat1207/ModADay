package com.rubyboat.daily.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class CamoMaterial implements ArmorMaterial {
    @Override
    public int getDurability(ArmorItem.Type type) {
        switch(type) {
            case BOOTS -> {
                return 100;
            }
            case CHESTPLATE -> {
                return 350;
            }
            default -> { // Helmet and Leggings
                return 150;
            }
        }
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return -2;
    }

    @Override
    public int getEnchantability() {
        return 17
                ;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BLOCK_CHERRY_LEAVES_STEP;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(
                Items.OAK_LEAVES,
                Items.BIRCH_LEAVES,
                Items.SPRUCE_LEAVES,
                Items.DARK_OAK_LEAVES,
                Items.AZALEA_LEAVES,
                Items.ACACIA_LEAVES,
                Items.JUNGLE_LEAVES,
                Items.CHERRY_LEAVES
        );
    }

    @Override
    public String getName() {
        return "camouflage";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
