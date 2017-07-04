package net.insomniakitten.mvillage.core.util;

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

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
public interface IModelled {

    String getVariants();

    @SideOnly(Side.CLIENT)
    default void initModel(ModelRegistryEvent event) {
        Item item;

        if (this instanceof Item) item = (Item) this;
        else if (this instanceof Block) item = Item.getItemFromBlock((Block) this);
        else throw new IllegalArgumentException("Cannot apply model to " + this.getClass().getCanonicalName());

        ModelResourceLocation mrl = new ModelResourceLocation(item.getRegistryName(), getVariants());
        ModelLoader.setCustomModelResourceLocation(item, 0, mrl);

    }

}
