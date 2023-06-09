package com.rubyboat.daily;

import com.rubyboat.daily.items.MeshPannerItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
    public static String MODID = "rubydaily";
    public MeshPannerItem MESH_PANNER_ITEM = new MeshPannerItem(new FabricItemSettings());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM,  new Identifier(MODID, "mesh_panner"), MESH_PANNER_ITEM);
    }
}
