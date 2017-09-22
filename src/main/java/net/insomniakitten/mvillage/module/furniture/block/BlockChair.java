package net.insomniakitten.mvillage.module.furniture.block;

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

import net.insomniakitten.mvillage.common.block.BlockEnumCardinalBase;
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockChair extends BlockEnumCardinalBase<BlockChair.ChairType> {

    private static final PropertyBool HAS_TABLE = PropertyBool.create("has_table");

    public BlockChair() {
        super("chair", ChairType.class);
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.withProperty(HAS_TABLE, BlockTable.isTable(world, pos.offset(getFacing(state))));
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer.Builder createStateContainer() {
        return super.createStateContainer().add(HAS_TABLE);
    }

    public enum ChairType implements IStatePropertyHolder<ChairType> {

        WOODEN(2.0f, 15.0f, Material.WOOD, SoundType.WOOD),
        MODERN(5.0f, 30.0f, Material.IRON, SoundType.METAL),
        ANTIQUE(2.0f, 15.0f, Material.WOOD, SoundType.WOOD),
        FANTASY(5.0f, 30.0f, Material.IRON, SoundType.GLASS);

        private final float hardness;
        private final float resistance;
        private final Material material;
        private final SoundType sound;

        ChairType(float hardness, float resistance, Material material, SoundType sound) {
            this.hardness = hardness;
            this.resistance = resistance;
            this.material = material;
            this.sound = sound;
        }

        @Override
        public ChairType getEnum() {
            return this;
        }

        @Override
        public float getHardness() {
            return hardness;
        }

        @Override
        public float getResistance() {
            return resistance;
        }

        @Override
        public Material getMaterial() {
            return material;
        }

        @Override
        public SoundType getSoundType() {
            return sound;
        }

        @Override
        public BlockRenderLayer getRenderLayer() {
            return BlockRenderLayer.SOLID;
        }

        @Override
        public String getOrePrefix() {
            return "blockChair";
        }

        public AxisAlignedBB getBoundingBox() {
            return new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D);
        }

    }

}
