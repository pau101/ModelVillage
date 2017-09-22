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

import net.insomniakitten.mvillage.ModelVillage;
import net.insomniakitten.mvillage.client.model.ModelRegistry;
import net.insomniakitten.mvillage.client.model.WrappedModel.ModelBuilder;
import net.insomniakitten.mvillage.common.block.BlockEnumBase;
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBlockEnumBase <E extends Enum<E> & IStatePropertyHolder<E>> extends ItemBlock {

    protected final boolean redirectVariants;
    protected final E[] values;

    public ItemBlockEnumBase(BlockEnumBase<E> block, ResourceLocation name, boolean redirectVariants) {
        super(block);
        this.redirectVariants = redirectVariants;
        this.values = block.getValues();
        setRegistryName(name);
        setHasSubtypes(true);
        registerModels();
    }

    public ItemBlockEnumBase(BlockEnumBase<E> block, ResourceLocation name) {
        this(block, name, false);
    }

    public ItemBlockEnumBase(BlockEnumBase<E> block, String name, boolean redirectVariants) {
        this(block, new ResourceLocation(ModelVillage.MOD_ID, name), redirectVariants);
    }

    public ItemBlockEnumBase(BlockEnumBase<E> block, String name) {
        this(block, new ResourceLocation(ModelVillage.MOD_ID, name), false);
    }

    public ItemBlockEnumBase(BlockEnumBase<E> block, boolean redirectVariants) {
        this(block, block.getRegistryName(), redirectVariants);
    }

    public ItemBlockEnumBase(BlockEnumBase<E> block) {
        this(block, block.getRegistryName(), false);
    }

    protected void registerModels() {
        for (E value : values) {
            ModelBuilder model = new ModelBuilder(this, value.getMetadata());
            if (redirectVariants) {
                model.setResourceLocation(new ResourceLocation(getRegistryName().toString() + "_" + value.getName()));
            } else {
                model.addVariant("type=" + value.getName());
            }
            ModelRegistry.registerModel(model.build());
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata() % values.length;
        return this.getUnlocalizedName() + "." + values[ meta ].getName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        String key = stack.getUnlocalizedName() + ".tooltip";
        if (I18n.hasKey(key)) {
            tooltip.add(I18n.format(key));
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

}
