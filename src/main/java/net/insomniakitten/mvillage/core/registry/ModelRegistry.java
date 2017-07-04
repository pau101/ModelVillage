package net.insomniakitten.mvillage.core.registry;

/*
 * This file was created at 21:03 on 04 Jul 2017 by InsomniaKitten
 *
 * It is distributed as part of the ModelVillage mod.
 * Source code is visible at: https://github.com/InsomniaKitten/ModelVillage
 *
 * Copyright (c) InsomniaKitten 2017. All Rights Reserved.
 */

import net.insomniakitten.mvillage.ModelVillage;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModelVillage.MOD_ID)
public class ModelRegistry {

    @SubscribeEvent
    public void onRegisterModels(ModelRegistryEvent event) {
        for (Item item : ItemRegistry.ITEMS)
            registerModel(item, item.getRegistryName(), "inventory");
        for (ItemBlock item : BlockRegistry.ITEMBLOCKS)
            registerModel(item, item.getRegistryName(), "facing=north");
    }

    public static void registerModel(Item item, ResourceLocation rl, String variant) {
        ModelResourceLocation mrl = new ModelResourceLocation(rl, variant);
        ModelLoader.setCustomModelResourceLocation(item, 0, mrl);
    }

}
