package net.insomniakitten.mvillage.core.registry;

/*
 *  Copyright 2017 InsomniaKitten
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
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