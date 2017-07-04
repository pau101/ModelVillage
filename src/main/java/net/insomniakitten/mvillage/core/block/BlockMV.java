package net.insomniakitten.mvillage.core.block;

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
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMV extends Block {

    private EnumBlockType blockType;
    private AxisAlignedBB axisAlignedBB;

    public BlockMV(
            String name,
            Material material,
            SoundType sound,
            float resistance,
            float hardness) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(ModelVillage.MOD_ID + "." + name);
        setSoundType(sound);
        setResistance(resistance);
        setHardness(hardness);
        setCreativeTab(ModelVillage.CTAB);
        blockType = EnumBlockType.CUBE;
        axisAlignedBB = FULL_BLOCK_AABB;
    }

    public BlockMV(
            String name,
            Material material,
            SoundType sound,
            float resistance,
            float hardness,
            AxisAlignedBB aabb) {
        this(name, material, sound, resistance, hardness);
        blockType = EnumBlockType.MODEL;
        axisAlignedBB = aabb;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return blockType.equals(EnumBlockType.CUBE);
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return blockType.equals(EnumBlockType.CUBE);
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return blockType.equals(EnumBlockType.CUBE) ? 255 : 0;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return blockType.equals(EnumBlockType.CUBE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return blockType.equals(EnumBlockType.CUBE);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return axisAlignedBB;
    }

    public enum EnumBlockType {CUBE, MODEL}

}
