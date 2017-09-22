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
import net.insomniakitten.mvillage.common.item.ItemBlockCardinalBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCardinalBase extends BlockBase {

    private static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCardinalBase(String name, float hardness, float resistance, Material material, SoundType sound) {
        super(name, hardness, resistance, material, sound);
    }

    @Override
    public void registerItemBlock() {
        RegistryManager.registerItemBlock(new ItemBlockCardinalBase(this));
    }

    public static PropertyDirection getFacingProperty() {
        return FACING;
    }

    public EnumFacing getFacing(IBlockState state) {
        return state.getValue(FACING);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta);
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getFacing(state).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateForPlacement(
            World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta,
            EntityLivingBase placer, EnumHand hand) {
        EnumFacing facing = placer.getHorizontalFacing().getOpposite();
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    protected BlockStateContainer.Builder createStateContainer() {
        return super.createStateContainer().add(FACING);
    }

}
