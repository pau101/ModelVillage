package net.insomniakitten.mvillage;

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

import com.google.common.base.Equivalence.Wrapper;
import net.insomniakitten.mvillage.base.block.BlockInventory;
import net.insomniakitten.mvillage.base.inventory.InventoryType;
import net.insomniakitten.mvillage.base.inventory.TileInventory;
import net.insomniakitten.mvillage.base.item.ItemBlockMV;
import net.insomniakitten.mvillage.base.util.DataHandler;
import net.insomniakitten.mvillage.base.util.IPropertySerializable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class RegistryManager<T extends Enum<T> & IPropertySerializable> {

    public static enum Blocks {
        TEST(new BlockInventory("test_inventory", InventoryType.MEDIUM));

        private final Block block;
        Blocks(Block block) { this.block = block; }
        public Block getBlock() { return block; }
    }

    public static enum Items {
        ;

        private final Item item;
        Items(Item item) { this.item = item; }
        public Item getItem() { return item; }
    }

    public static enum Tiles {
        INVENTORY(TileInventory.class, new ResourceLocation(ModelVillage.MOD_ID, "tile_inventory"));

        private final Class<? extends TileEntity> tile;
        private final ResourceLocation key;
        Tiles(Class<? extends TileEntity> tile, ResourceLocation key) {
            this.tile = tile;
            this.key = key;
        }
        public Class<? extends TileEntity> getTile() { return tile; }
        public ResourceLocation getKey() { return key; }
    }

    @EventBusSubscriber
    public static class ObjectRegistry {

        private static final List<ItemBlock> ITEM_BLOCK_CACHE = new ArrayList<>();

        public static void registerItemBlock(Block block, Enum... subitems) {
            if (subitems.length > 0)
                ITEM_BLOCK_CACHE.add(new ItemBlockMV(block, subitems));
            else ITEM_BLOCK_CACHE.add(new ItemBlockMV(block));
        }

        @SubscribeEvent
        public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
            for (Blocks entry : Blocks.values()) {
                event.getRegistry().register(entry.getBlock());
            }
        }

        @SubscribeEvent
        public static void onItemRegistry(RegistryEvent.Register<Item> event) {
            for (ItemBlock entry : ITEM_BLOCK_CACHE) {
                event.getRegistry().register(entry);
            }
            for (Items entry : Items.values()) {
                event.getRegistry().register(entry.getItem());
            }
        }

        @SubscribeEvent
        public static void onTileRegistry(RegistryEvent.Register<Block> event) {
            for (Tiles entry : Tiles.values()) {
                GameRegistry.registerTileEntity(entry.getTile(), entry.getKey().toString());
            }
        }

    }

    @EventBusSubscriber(Side.CLIENT)
    public static class ModelRegistry {

        public static final Map<Wrapper<ItemStack>, ModelResourceLocation> MODELS = new HashMap<>();
        private enum Default implements IPropertySerializable {INVENTORY}

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void onModelRegistry(ModelRegistryEvent event) {

            for (ItemBlock itemBlock : ObjectRegistry.ITEM_BLOCK_CACHE) {
                NonNullList<ItemStack> subitems = NonNullList.create();
                CreativeTabs tab = itemBlock.getCreativeTab();
                itemBlock.getSubItems(tab != null ? tab : ModelVillage.CTAB, subitems);
                Enum[] variants = ((ItemBlockMV) itemBlock).getVariants();
                if (variants == null) variants = Default.values();
                for (int i = 0; i < subitems.size(); ++i) {
                    String variant = ((IPropertySerializable) variants[i]).getName();
                    MODELS.put(DataHandler.STACK_EQV.wrap(subitems.get(i)), new ModelResourceLocation(
                            itemBlock.getRegistryName(), variant));
                }
            }

            MODELS.forEach((key, value) -> {
                ModelLoader.setCustomModelResourceLocation(
                        key.get().getItem(), key.get().getMetadata(), value);
            });

        }

    }

    @EventBusSubscriber
    public static class RecipeRegistry {

        private static final List<IRecipe> RECIPES = new ArrayList<IRecipe>();

        public static void registerRecipe(IRecipe recipe) {
            if (!RECIPES.contains(recipe)) {
                RECIPES.add(recipe);
            }
        }

        @SubscribeEvent
        public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
            if (RECIPES.isEmpty()) return;
            ModelVillage.Logger.info(true, "Registering {} recipes", RECIPES.size());
            event.getRegistry().registerAll(RECIPES.toArray(new IRecipe[0]));
        }

    }


}
