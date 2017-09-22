package net.insomniakitten.mvillage.module.technical.block;

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

import com.google.common.collect.ImmutableMap;
import net.insomniakitten.mvillage.ModelVillage;
import net.insomniakitten.mvillage.client.model.ModelRegistry;
import net.insomniakitten.mvillage.client.model.WrappedModel;
import net.insomniakitten.mvillage.common.RegistryManager;
import net.insomniakitten.mvillage.common.block.BlockAttachmentBase;
import net.insomniakitten.mvillage.common.item.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class BlockDoorBell extends BlockAttachmentBase {

    private static final SoundEvent BELL = new SoundDoorBell();

    private static final PropertyBool PRESSED = PropertyBool.create("pressed");

    private static final ImmutableMap<EnumFacing, AxisAlignedBB> AABB_BELL = Arrays.stream(EnumFacing.values()).collect(
            ImmutableMap.toImmutableMap(Function.identity(), BlockDoorBell::computeAABBForFacing));

    public BlockDoorBell() {
        super("door_bell", 0.5f, 2.5f, Material.IRON, SoundType.METAL);
        setDefaultState(getDefaultState().withProperty(PRESSED, false));
    }

    private static AxisAlignedBB computeAABBForFacing(EnumFacing facing) {
        AxisAlignedBB aabb = new AxisAlignedBB(0.4375, 0.375, 0.9375, 0.5625, 0.625, 1);
        double minX = aabb.minX, minY = aabb.minY, minZ = aabb.minZ;
        double maxX = aabb.maxX, maxY = aabb.maxY, maxZ = aabb.maxZ;
        switch (facing) {
            case DOWN:
                return new AxisAlignedBB(1 - maxX, minZ, 1 - maxY, 1 - minX, 1, 1 - minY);
            case UP:
                return new AxisAlignedBB(minX, 1 - maxZ, minY, maxX, 1 - minZ, maxY);
            case SOUTH:
                return new AxisAlignedBB(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
            case WEST:
                return new AxisAlignedBB(minZ, minY, minX, maxZ, maxY, maxX);
            case EAST:
                return new AxisAlignedBB(1 - maxZ, minY, 1 - maxX, 1 - minZ, maxY, 1 - minX);
        }
        return aabb;
    }

    @Override
    public void registerItemBlock() {
        RegistryManager.registerItemBlock(new ItemBlockBase(this) {
            @Override
            protected void registerModels() {
                ModelRegistry.registerModel(new WrappedModel.ModelBuilder(this)
                        .addVariant("attach=north")
                        .addVariant("pressed=false")
                        .build());
            }
        });
    }

    @Override
    protected BlockStateContainer.Builder createStateContainer() {
        return super.createStateContainer().add(PRESSED);
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_BELL.get(getAttachedFace(state));
    }

    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote && state.getValue(PRESSED)) {
            world.setBlockState(pos, state.withProperty(PRESSED, false));
            notifyNeighbors(world, pos, getAttachedFace(state));
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    @Deprecated
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (!canStay(state, world, pos)) {
            world.setBlockToAir(pos);
            dropBlockAsItem(world, pos, state, 0);
        }
    }

    @Override
    public int tickRate(World world) {
        return 40;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getValue(PRESSED)) {
            notifyNeighbors(worldIn, pos, getAttachedFace(state));
        }
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
        BlockPos posAt = pos.offset(side.getOpposite());
        IBlockState stateAt = world.getBlockState(posAt);
        BlockFaceShape shapeAt = stateAt.getBlockFaceShape(world, posAt, side);
        return shapeAt == BlockFaceShape.SOLID;
    }

    @Override
    public boolean onBlockActivated(
            World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
            float hitX, float hitY, float hitZ) {
        if (player.isSneaking() && player.getHeldItem(hand).isEmpty()) {
            // TODO Pitch configuration
        } else {
            if (!state.getValue(PRESSED) && !world.isRemote) {
                world.setBlockState(pos, state.withProperty(PRESSED, true));
                world.playSound(null, pos, BELL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.scheduleUpdate(pos, this, tickRate(world));
            }
        }
        return true;
    }

    protected boolean canStay(IBlockState state, World world, BlockPos pos) {
        EnumFacing side = getAttachedFace(state);
        BlockPos posAt = pos.offset(side.getOpposite());
        IBlockState stateAt = world.getBlockState(posAt);
        BlockFaceShape shapeAt = stateAt.getBlockFaceShape(world, posAt, side);
        return shapeAt == BlockFaceShape.SOLID;
    }

    private void notifyNeighbors(World world, BlockPos pos, EnumFacing facing) {
        world.notifyNeighborsOfStateChange(pos, this, false);
        world.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this, false);
    }

    private static final class SoundDoorBell extends SoundEvent {

        private static final ResourceLocation PATH = new ResourceLocation(ModelVillage.MOD_ID, "door_bell");

        public SoundDoorBell() {
            super(PATH);
            setRegistryName(PATH);
        }

    }

}
