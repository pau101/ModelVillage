package net.insomniakitten.mvillage.module.kitchen.block;

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

import net.insomniakitten.mvillage.client.state.StateRegistry;
import net.insomniakitten.mvillage.common.RegistryManager;
import net.insomniakitten.mvillage.common.block.BlockEnumCardinalBase;
import net.insomniakitten.mvillage.common.inventory.IContainer;
import net.insomniakitten.mvillage.common.inventory.InventoryType;
import net.insomniakitten.mvillage.common.item.ItemBlockEnumBase;
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Locale;

public class BlockCabinet extends BlockEnumCardinalBase<BlockCabinet.CabinetType> implements IContainer {

    private static final PropertyEnum<CabinetAttach> ATTACH = PropertyEnum.create("attach", CabinetAttach.class);

    public BlockCabinet() {
        super("cabinet", CabinetType.class);
        setLightOpacity(0);
        StateRegistry.registerVariantRedirect(this, propertyEnum);
    }

    @Override
    public void registerItemBlock() {
        RegistryManager.registerItemBlock(new ItemBlockEnumBase<>(this, true));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return getType(state).hasTileEntity();
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.LARGE;
    }

    @Override
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(ATTACH, CabinetAttach.getValidState(world, pos));
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer.Builder createStateContainer() {
        return super.createStateContainer().add(ATTACH);
    }

    private enum CabinetAttach implements IStringSerializable {

        DOWN,
        UP;

        public static CabinetAttach getValidState(IBlockAccess world, BlockPos pos) {
            IBlockState stateAt = world.getBlockState(pos.down());
            boolean isTopSolid = stateAt.isSideSolid(world, pos, EnumFacing.UP);
            boolean isCabinet = stateAt.getBlock() instanceof BlockCabinet;
            return isTopSolid && !isCabinet ? DOWN : UP;
        }

        private int getLightOpacity() {
            return equals(DOWN) ? 255 : 0;
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }

    }

    public enum CabinetType implements IStatePropertyHolder<CabinetType> {

        DOORS,
        DRAWERS,
        CRAFTING,
        SINK;

        @Override
        public CabinetType getEnum() {
            return this;
        }

        @Override
        public float getHardness() {
            return 5.0f;
        }

        @Override
        public float getResistance() {
            return 30.0f;
        }

        @Override
        public Material getMaterial() {
            return Material.IRON;
        }

        @Override
        public SoundType getSoundType() {
            return SoundType.METAL;
        }

        @Override
        public BlockRenderLayer getRenderLayer() {
            return BlockRenderLayer.CUTOUT;
        }

        @Override
        public String getOrePrefix() {
            return "blockCabinet";
        }

        @Override
        public String getEffectiveTool() {
            return "pickaxe";
        }

        @Override
        public boolean isFullCube() {
            return false;
        }

        @Override
        public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
            return EnumFacing.UP.equals(side);
        }

        public boolean hasTileEntity() {
            return equals(DOORS) || equals(DRAWERS);
        }

    }

}
