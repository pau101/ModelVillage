package net.insomniakitten.mvillage.base.block;

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
import net.insomniakitten.mvillage.RegistryManager.ObjectRegistry;
import net.insomniakitten.mvillage.base.inventory.TileInventory;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockMV extends Block {

    private EnumBlockType blockType;
    private AxisAlignedBB axisAlignedBB;

    public BlockMV(
            String name, Material material, SoundType sound,
            float hardness, float resistance) {
        super(material);
        blockType = EnumBlockType.CUBE;
        axisAlignedBB = FULL_BLOCK_AABB;
        setRegistryName(name);
        setUnlocalizedName(ModelVillage.MOD_ID + "." + name);
        setSoundType(sound);
        setHardness(hardness);
        setResistance(resistance);
        setCreativeTab(ModelVillage.CTAB);
        handleItemBlock();
    }

    public void setBlockType(EnumBlockType type) {
        this.blockType = type;
    }

    public void setAxisAlignedBB(AxisAlignedBB aabb) {
        this.axisAlignedBB = aabb;
    }

    public void handleItemBlock() {
        ObjectRegistry.registerItemBlock(this);
    }

    @Override @Nonnull
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return blockType == EnumBlockType.CUBE;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return blockType == EnumBlockType.CUBE;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return blockType == EnumBlockType.CUBE ? 255 : 0;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return blockType == EnumBlockType.CUBE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        if (blockType == null)
            return super.isOpaqueCube(state);
        return blockType == EnumBlockType.CUBE;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return axisAlignedBB;
    }

    @Override
    public boolean onBlockActivated(
            World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        if (this instanceof IContainer) {
            ((IContainer) this).handleContainerGui(world, pos, player);
            return true;
        }
        else return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (this instanceof IContainer) {
            ((IContainer) this).handleItemDrops(world, pos, state);
        }
        else super.breakBlock(world, pos, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return this instanceof IContainer;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        if (this instanceof IContainer) {
            return new TileInventory(((IContainer) this).getInventoryType());
        }
        else return null;
    }

    public enum EnumBlockType {CUBE, MODEL}

}
