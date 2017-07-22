package net.insomniakitten.mvillage.base.item;

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

import net.insomniakitten.mvillage.base.util.IPropertySerializable;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockMV<T extends Enum<T> & IPropertySerializable> extends ItemBlock {

    private T[] objects;

    public ItemBlockMV(Block block) {
        super(block);
        assert block.getRegistryName() != null;
        setRegistryName(block.getRegistryName());
        setHasSubtypes(false);
    }

    public ItemBlockMV(Block block, T[] objects) {
        this(block);
        this.objects = objects;
        setHasSubtypes(true);
    }

    public T[] getVariants() {
        return objects;
    }

    @Override
    public void getSubItems(
            @Nonnull CreativeTabs tab,
            @Nonnull NonNullList<ItemStack> items) {
        if (!this.isInCreativeTab(tab)) return;
        if (objects == null) super.getSubItems(tab, items);
        else for (int i = 0; i < objects.length; ++i) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override @Nonnull
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        if (objects == null) return super.getUnlocalizedName(stack);
        int meta = stack.getMetadata() % objects.length;
        String type = objects[meta].getName();
        return this.getBlock().getUnlocalizedName() + "." + type;
    }

    @Override @SideOnly(Side.CLIENT)
    public void addInformation(
            @Nonnull ItemStack stack, @Nullable World world,
            @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flag) {
        String key = this.getUnlocalizedName(stack) + ".tooltip";
        if (I18n.hasKey(key)) tooltip.add(I18n.format(key));
    }

    @Override
    public int getMetadata(int damage) { return damage; }

}
