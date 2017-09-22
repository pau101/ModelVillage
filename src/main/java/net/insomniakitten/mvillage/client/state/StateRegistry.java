package net.insomniakitten.mvillage.client.state;

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
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ModelVillage.MOD_ID, value = Side.CLIENT)
public class StateRegistry {

    private static final Map<Block, IProperty<?>[]> STATE_MAP_BLACKLISTS = new HashMap<>();
    private static final Map<Block, PropertyEnum<?>> STATE_MAP_REDIRECTS = new HashMap<>();

    public static void registerPropertyBlacklist(Block block, IProperty<?>... ignores) {
        STATE_MAP_BLACKLISTS.putIfAbsent(block, ignores);
    }

    public static <E extends Enum<E> & IStatePropertyHolder<E>> void registerVariantRedirect(
            Block block, PropertyEnum<E> property) {
        STATE_MAP_REDIRECTS.putIfAbsent(block, property);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        STATE_MAP_BLACKLISTS.forEach((block, ignores) -> {
            StateMap.Builder builder = new StateMap.Builder().ignore(ignores);
            ModelLoader.setCustomStateMapper(block, builder.build());
        });
        STATE_MAP_REDIRECTS.forEach((block, property) -> {
            //noinspection unchecked
            StateRemapper remapper = new StateRemapper(property);
            ModelLoader.setCustomStateMapper(block, remapper);
        });
    }

    private static final class StateRemapper <E extends Enum<E> & IStatePropertyHolder<E>> extends StateMapperBase {

        private final PropertyEnum<E> property;

        private StateRemapper(PropertyEnum<E> property) {
            this.property = property;
        }

        private static <T extends Comparable<T>> void appendProperty(
                StringBuilder bob, IBlockState state, IProperty<T> property) {
            bob.append(property.getName()).append('=').append(property.getName(state.getValue(property)));
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            //noinspection ConstantConditions
            String name = state.getBlock().getRegistryName().toString();
            String type = state.getValue(property).getName();
            StringBuilder builder = new StringBuilder(name).append('_').append(type).append("#");
            boolean needsComma = false;
            for (IProperty<?> property : state.getPropertyKeys()) {
                if (!this.property.equals(property)) {
                    if (needsComma) builder.append(',');
                    needsComma = true;
                    appendProperty(builder, state, property);
                }
            }
            return new ModelResourceLocation(builder.toString());
        }

    }

}
