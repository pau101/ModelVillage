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
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModelVillage.MOD_ID)
public class BlockRegistry {

    protected static final List<Block> BLOCKS = new ArrayList<>();
    protected static final List<ItemBlock> ITEMBLOCKS = new ArrayList<>();

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        if (BLOCKS.isEmpty()) return;
        LogMV.log(true, "Registering {} blocks", BLOCKS.size());
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }

    public static void registerBlock(Block block) {
        if (BLOCKS.contains(block)) {
            LogMV.warn(false, "Block <{}> exists already! Skipping...", block.getRegistryName());
            return;
        }
        BLOCKS.add(block);
    }

    public static void registerBlock(Block block, ItemBlock item) {
        registerBlock(block);
        registerItemBlock(item);
    }

    public static void registerItemBlock(ItemBlock itemblock) {
        if (ITEMBLOCKS.contains(itemblock)) {
            LogMV.warn(false, "ItemBlock <{}> exists already! Skipping...", itemblock.getRegistryName());
            return;
        }
        ITEMBLOCKS.add(itemblock);
    }

}
