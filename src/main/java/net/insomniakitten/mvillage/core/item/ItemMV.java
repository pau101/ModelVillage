package net.insomniakitten.mvillage.core.item;

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
import net.insomniakitten.mvillage.core.util.IModelled;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMV extends Item implements IModelled {

    private int maxMeta;

    public ItemMV(String name) {
        setRegistryName(name);
        setUnlocalizedName(ModelVillage.MOD_ID + "." + name);
        setCreativeTab(ModelVillage.CTAB);
    }

    public ItemMV(String name, int maxMeta) {
        this(name);
        hasSubtypes = true;
        this.maxMeta = maxMeta;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!tab.equals(this.getCreativeTab())) return;
        if (hasSubtypes)
            for (int i = 0; i <= maxMeta; ++i)
                items.add(new ItemStack(this, 1, i));
        else items.add(new ItemStack(this));
    }

    @Override
    public String getVariants() {
        return "inventory";
    }

}
