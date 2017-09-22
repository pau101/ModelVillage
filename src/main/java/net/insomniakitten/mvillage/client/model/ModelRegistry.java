package net.insomniakitten.mvillage.client.model;

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
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = ModelVillage.MOD_ID, value = Side.CLIENT)
public class ModelRegistry {

    private static final Set<WrappedModel> MODELS = new HashSet<>();
    private static final Map<Item, PropertyEnum<?>> MODEL_REMAPS = new HashMap<>();

    public static void registerModel(WrappedModel model) {
        MODELS.add(model);
    }

    public static <E extends Enum<E> & IStatePropertyHolder<E>> void registerVariantRedirect(
            Item item, PropertyEnum<E> property) {
        MODEL_REMAPS.putIfAbsent(item, property);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        MODELS.forEach(model -> ModelLoader.setCustomModelResourceLocation(
                model.getItem(), model.getMetadata(), model.getMRL()));
        MODEL_REMAPS.forEach((item, property) -> {
            //noinspection unchecked
            ItemMeshRemapper remapper = new ItemMeshRemapper(property);
            ModelLoader.setCustomMeshDefinition(item, remapper);
        });
    }

    private static class ItemMeshRemapper <E extends Enum<E> & IStatePropertyHolder<E>> implements ItemMeshDefinition {

        private final PropertyEnum<E> property;
        private final String variants;

        private ItemMeshRemapper(PropertyEnum<E> property, String variants) {
            this.property = property;
            this.variants = variants;
        }

        private ItemMeshRemapper(PropertyEnum<E> property) {
            this(property, "inventory");
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            //noinspection ConstantConditions
            String name = stack.getItem().getRegistryName().toString();
            for (E value : property.getAllowedValues()) {
                if (value.getMetadata() == stack.getMetadata()) {
                    ResourceLocation remapped = new ResourceLocation(name + "_" + value.getName());
                    return new ModelResourceLocation(remapped, variants);
                }
            }
            return new ModelResourceLocation(name, variants);
        }

    }

}
