package com.rubyboat.daily.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.HashMap;

public class Manneken extends Item {
    public Manneken(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        ItemStack helm = user.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = user.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = user.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = user.getEquippedStack(EquipmentSlot.FEET);

        var cur = getCurrentNBTStack(stack);

        HashMap<EquipmentSlot, ItemStack> toBeStowed = new HashMap<>();
        toBeStowed.put(EquipmentSlot.HEAD, helm);
        toBeStowed.put(EquipmentSlot.CHEST, chest);
        toBeStowed.put(EquipmentSlot.LEGS, legs);
        toBeStowed.put(EquipmentSlot.FEET, boots);

        setCurrentNBTStack(stack, toBeStowed);

        user.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
        user.equipStack(EquipmentSlot.CHEST, ItemStack.EMPTY);
        user.equipStack(EquipmentSlot.LEGS, ItemStack.EMPTY);
        user.equipStack(EquipmentSlot.FEET, ItemStack.EMPTY);

        cur.forEach(user::equipStack);

        return super.use(world, user, hand);
    }

    public HashMap<EquipmentSlot, ItemStack> getCurrentNBTStack(ItemStack stack) {
        var nbt = stack.getOrCreateNbt();

        HashMap<EquipmentSlot, ItemStack> set = new HashMap<>();

        set.put(EquipmentSlot.HEAD, ItemStack.fromNbt(nbt.getCompound(EquipmentSlot.HEAD.getName())));
        set.put(EquipmentSlot.CHEST, ItemStack.fromNbt(nbt.getCompound(EquipmentSlot.CHEST.getName())));
        set.put(EquipmentSlot.LEGS, ItemStack.fromNbt(nbt.getCompound(EquipmentSlot.LEGS.getName())));
        set.put(EquipmentSlot.FEET, ItemStack.fromNbt(nbt.getCompound(EquipmentSlot.FEET.getName())));

        return set;
    }

    public void setCurrentNBTStack(ItemStack stack, HashMap<EquipmentSlot, ItemStack> map) {
        var nbt = stack.getOrCreateNbt();

        map.forEach((e, i) -> {
            nbt.put(e.getName(), i.writeNbt(new NbtCompound()));
        });
    }
}
