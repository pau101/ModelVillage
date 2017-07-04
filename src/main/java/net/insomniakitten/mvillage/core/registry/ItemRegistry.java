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
import net.insomniakitten.mvillage.core.util.LogMV;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModelVillage.MOD_ID)
public class ItemRegistry {

    protected static final List<Item> ITEMS = new ArrayList<>();

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        if (ITEMS.isEmpty()) return;
        LogMV.log(true, "Registering {} items", ITEMS.size() + BlockRegistry.ITEMBLOCKS.size());
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
        event.getRegistry().registerAll(BlockRegistry.ITEMBLOCKS.toArray(new ItemBlock[0]));
    }

    public static void registerItem(Item item) {
        if (ITEMS.contains(item)) {
            LogMV.warn(false, "Item <{}> exists already! Skipping...", item.getRegistryName());
            return;
        }
        ITEMS.add(item);
    }

}
