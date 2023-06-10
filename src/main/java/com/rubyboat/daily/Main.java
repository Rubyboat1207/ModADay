package com.rubyboat.daily;

import com.rubyboat.daily.items.Manneken;
import com.rubyboat.daily.items.MeshPannerItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
    public static String MODID = "rubydaily";
    public MeshPannerItem MESH_PANNER_ITEM = new MeshPannerItem(new FabricItemSettings().maxCount(1));
    public Manneken MANNEKEN = new Manneken(new FabricItemSettings().maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MODID, "mesh_panner"), MESH_PANNER_ITEM);
        Registry.register(Registries.ITEM, new Identifier(MODID, "manneken"), MANNEKEN);
    }
}
