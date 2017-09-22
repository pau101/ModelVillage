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

import net.insomniakitten.mvillage.common.RegistryManager;
import net.insomniakitten.mvillage.common.item.ItemBlockEnumBase;
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.insomniakitten.mvillage.common.util.StringHelper;
import net.insomniakitten.mvillage.common.util.TileHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEnumBase <E extends Enum<E> & IStatePropertyHolder<E>> extends Block {

    protected final E[] values;
    protected final PropertyEnum<E> propertyEnum;
    protected final BlockStateContainer container;

    public BlockEnumBase(String name, Class<E> clazz) {
        super(Material.AIR);
        values = clazz.getEnumConstants();
        propertyEnum = PropertyEnum.create("type", clazz);
        container = createStateContainer().build();
        setRegistryName(name);
        setUnlocalizedName(StringHelper.formatToLangKey(name));
        setDefaultState(getBlockState().getBaseState());
        registerItemBlock();
    }

    public void registerItemBlock() {
        RegistryManager.registerItemBlock(new ItemBlockEnumBase<>(this));
    }

    public final E getType(IBlockState state) {
        return state.getValue(propertyEnum);
    }

    public final E getType(int meta) {
        return values[ meta % values.length ];
    }

    public final E[] getValues() {
        return values;
    }

    public final PropertyEnum<E> getPropertyEnum() {
        return propertyEnum;
    }

    @Override
    @Deprecated
    public Material getMaterial(IBlockState state) {
        return getType(state).getMaterial();
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(propertyEnum, getType(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getType(state).getMetadata();
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return getType(state).isFullCube();
    }

    @Override
    @Deprecated
    public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
        return getType(state).getHardness();
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return getType(state).getBoundingBox();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileHelper.onTileRemove(state, world, pos);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getType(state).getMetadata();
    }

    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
        return getType(state).isSideSolid(world, pos, side) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean onBlockActivated(
            World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        return TileHelper.onTileInteract(state, world, pos, player);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (E type : values) {
            items.add(new ItemStack(this, 1, type.getMetadata()));
        }
    }

    @Override
    protected final BlockStateContainer createBlockState() {
        return new BlockStateContainer.Builder(this).build();
    }

    @Override
    public final BlockStateContainer getBlockState() {
        return container;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getType(state).getLightLevel();
    }

    @Override
    @Deprecated
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return getType(state).isSideSolid(world, pos, side);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return TileHelper.hasTileEntity(state);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return TileHelper.getTileEntity(state);
    }

    @Override
    public void getDrops(
            NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (!getType(state).requiresSilkTouch()) {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return getType(state).requiresSilkTouch();
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity entity, Explosion explosion) {
        return getType(world.getBlockState(pos)).getResistance();
    }

    @Override
    public ItemStack getPickBlock(
            IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, getType(state).getMetadata());
    }

    @Override
    public boolean isToolEffective(String type, IBlockState state) {
        String tool = getType(state).getEffectiveTool();
        return tool != null && tool.equals(type);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer.equals(getType(state).getRenderLayer());
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
        return getType(state).getSoundType();
    }

    protected BlockStateContainer.Builder createStateContainer() {
        return new BlockStateContainer.Builder(this).add(propertyEnum);
    }

}
