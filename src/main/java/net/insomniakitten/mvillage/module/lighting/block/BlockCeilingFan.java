package net.insomniakitten.mvillage.module.lighting.block;

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

import net.insomniakitten.mvillage.common.block.BlockBase;
import net.insomniakitten.mvillage.module.lighting.tile.TileCeilingFan;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCeilingFan extends BlockBase {

    private static final AxisAlignedBB AABB_FAN = new AxisAlignedBB(0.3125D, 0.75D, 0.3125D, 0.6875D, 1.0D, 0.6875D);

    public BlockCeilingFan() {
        super("ceiling_fan", 2.0f, 15.0f, Material.WOOD, SoundType.WOOD);
        setLightOpacity(0);
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB_FAN;
    }

    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        checkRedstoneAndUpdateTile(world, pos, state);
    }

    @Override
    @Deprecated
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (canStay(world, pos)) {
            checkRedstoneAndUpdateTile(world, pos, state);
        } else {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        checkRedstoneAndUpdateTile(world, pos, state);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
        return canStay(world, pos);
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP;
    }

    @Override
    public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {
        return true;
    }

    protected boolean canStay(World world, BlockPos pos) {
        IBlockState stateAt = world.getBlockState(pos.up());
        BlockFaceShape faceAt = stateAt.getBlockFaceShape(world, pos.up(), EnumFacing.DOWN);
        return faceAt == BlockFaceShape.SOLID;
    }

    private void checkRedstoneAndUpdateTile(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            int redstonePower = 0;
            for (EnumFacing side : EnumFacing.values()) {
                int powerAt = world.getRedstonePower(pos.offset(side), side);
                if (powerAt <= redstonePower) continue;
                redstonePower = powerAt % 15;
            }
            TileCeilingFan tileFan = (TileCeilingFan) world.getTileEntity(pos);
            if (tileFan != null) tileFan.setMaxRpm(redstonePower);
        }
    }

    @Override
    protected BlockStateContainer.Builder createStateContainer() {
        return super.createStateContainer();
    }

}
