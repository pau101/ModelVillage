package net.insomniakitten.mvillage.common.block;

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
import net.insomniakitten.mvillage.common.RegistryManager;
import net.insomniakitten.mvillage.common.item.ItemBlockBase;
import net.insomniakitten.mvillage.common.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;

public class BlockBase extends Block {

    protected final BlockStateContainer container;

    public BlockBase(String name, float hardness, float resistance, Material material, SoundType sound) {
        super(material);
        container = createStateContainer().build();
        setRegistryName(name);
        setUnlocalizedName(StringHelper.formatToLangKey(name));
        setHardness(hardness);
        setResistance(resistance);
        setSoundType(sound);
        setCreativeTab(ModelVillage.CTAB);
        setDefaultState(getBlockState().getBaseState());
        registerItemBlock();
    }

    public void registerItemBlock() {
        RegistryManager.registerItemBlock(new ItemBlockBase(this));
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).build();
    }

    @Override
    public final BlockStateContainer getBlockState() {
        return container;
    }

    protected BlockStateContainer.Builder createStateContainer() {
        return new BlockStateContainer.Builder(this);
    }

}
