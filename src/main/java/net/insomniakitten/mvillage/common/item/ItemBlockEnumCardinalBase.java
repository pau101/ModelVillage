package net.insomniakitten.mvillage.common.item;

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

import net.insomniakitten.mvillage.client.model.ModelRegistry;
import net.insomniakitten.mvillage.client.model.WrappedModel;
import net.insomniakitten.mvillage.common.block.BlockEnumBase;
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.util.ResourceLocation;

public class ItemBlockEnumCardinalBase <E extends Enum<E> & IStatePropertyHolder<E>> extends ItemBlockEnumBase<E> {

    public ItemBlockEnumCardinalBase(BlockEnumBase<E> block) {
        super(block);
    }

    public ItemBlockEnumCardinalBase(BlockEnumBase<E> block, ResourceLocation name, boolean redirectVariants) {
        super(block, name, redirectVariants);
    }

    public ItemBlockEnumCardinalBase(BlockEnumBase<E> block, ResourceLocation name) {
        super(block, name);
    }

    public ItemBlockEnumCardinalBase(BlockEnumBase<E> block, String name, boolean redirectVariants) {
        super(block, name, redirectVariants);
    }

    public ItemBlockEnumCardinalBase(BlockEnumBase<E> block, String name) {
        super(block, name);
    }

    public ItemBlockEnumCardinalBase(BlockEnumBase<E> block, boolean redirectVariants) {
        super(block, redirectVariants);
    }

    @Override
    protected void registerModels() {
        for (E value : values) {
            WrappedModel.ModelBuilder model = new WrappedModel.ModelBuilder(this, value.getMetadata());
            if (redirectVariants) {
                model.setResourceLocation(new ResourceLocation(getRegistryName().toString() + "_" + value.getName()));
                model.addVariant("facing=north");
            } else {
                model.addVariant("facing=north");
                model.addVariant("type=" + value.getName());
            }
            ModelRegistry.registerModel(model.build());
        }
    }

}
