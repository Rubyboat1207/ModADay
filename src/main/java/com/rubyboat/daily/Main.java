package com.rubyboat.daily;

import com.rubyboat.daily.armor.CamoArmor;
import com.rubyboat.daily.items.Manneken;
import com.rubyboat.daily.items.MeshPannerItem;
import com.rubyboat.daily.items.MoonstoneItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {


    public static String MODID = "rubydaily";
    // Day 1
    public MeshPannerItem MESH_PANNER_ITEM = new MeshPannerItem(new FabricItemSettings().maxCount(1));
    // Day 2
    public Manneken MANNEKEN = new Manneken(new FabricItemSettings().maxCount(1));
    // Day 3
    public CamoArmor CAMO_BOOTS = new CamoArmor(ArmorItem.Type.BOOTS, new FabricItemSettings().maxCount(1));
    public CamoArmor CAMO_LEGGINGS = new CamoArmor(ArmorItem.Type.LEGGINGS, new FabricItemSettings().maxCount(1));
    public CamoArmor CAMO_CHESTPLATE = new CamoArmor(ArmorItem.Type.CHESTPLATE, new FabricItemSettings().maxCount(1));
    public CamoArmor CAMO_HELMET = new CamoArmor(ArmorItem.Type.HELMET, new FabricItemSettings().maxCount(1));
    // Day 4
    public static final BooleanProperty MOONSTONE_APPLIED = BooleanProperty.of("moonstone_applied");
    public Item MOONSTONE = new MoonstoneItem(new FabricItemSettings());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MODID, "mesh_panner"), MESH_PANNER_ITEM);
        Registry.register(Registries.ITEM, new Identifier(MODID, "manneken"), MANNEKEN);

        Registry.register(Registries.ITEM, new Identifier(MODID, "camo_boots"), CAMO_BOOTS);
        Registry.register(Registries.ITEM, new Identifier(MODID, "camo_leggings"), CAMO_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(MODID, "camo_chestplate"), CAMO_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(MODID, "camo_helmet"), CAMO_HELMET);

        Registry.register(Registries.ITEM, new Identifier(MODID, "moonstone"), MOONSTONE);
    }
}
