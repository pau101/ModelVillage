package net.insomniakitten.mvillage.common;

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
import net.insomniakitten.mvillage.common.inventory.TileInventory;
import net.insomniakitten.mvillage.module.furniture.block.BlockChair;
import net.insomniakitten.mvillage.module.furniture.block.BlockTable;
import net.insomniakitten.mvillage.module.kitchen.block.BlockCabinet;
import net.insomniakitten.mvillage.module.kitchen.block.BlockOven;
import net.insomniakitten.mvillage.module.lighting.block.BlockCeilingFan;
import net.insomniakitten.mvillage.module.lighting.tile.TileCeilingFan;
import net.insomniakitten.mvillage.module.technical.block.BlockDoorBell;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ModelVillage.MOD_ID)
public class RegistryManager {

    private static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

    public static final Block CABINET = new BlockCabinet();
    public static final Block CHAIR = new BlockChair();
    public static final Block TABLE = new BlockTable();
    public static final Block OVEN = new BlockOven();
    public static final Block FAN = new BlockCeilingFan();
    public static final Block BELL = new BlockDoorBell();

    public static void registerItemBlock(ItemBlock iblock) {
        ITEM_BLOCKS.add(iblock);
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        GameRegistry.registerTileEntity(TileInventory.class, TileInventory.getKey());
        GameRegistry.registerTileEntity(TileCeilingFan.class, TileCeilingFan.getKey());
        event.getRegistry().registerAll(CABINET, CHAIR, TABLE, OVEN, FAN, BELL);
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEM_BLOCKS.toArray(new ItemBlock[ 0 ]));
    }

}
