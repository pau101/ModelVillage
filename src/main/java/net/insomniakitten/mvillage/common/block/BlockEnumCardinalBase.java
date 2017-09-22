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

import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnumCardinalBase <E extends Enum<E> & IStatePropertyHolder<E>> extends BlockEnumBase<E> {

    private static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockEnumCardinalBase(String name, Class<E> clazz) {
        super(name, clazz);
    }

    public static PropertyDirection getFacingProperty() {
        return FACING;
    }

    public EnumFacing getFacing(IBlockState state) {
        return state.getValue(FACING);
    }

    @Override
    public IBlockState getStateForPlacement(
            World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta,
            EntityLivingBase placer, EnumHand hand) {
        EnumFacing facing = placer.getHorizontalFacing().getOpposite();
        return super.getStateFromMeta(meta).withProperty(FACING, facing);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta >> 2);
        return super.getStateFromMeta(meta).withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = getFacing(state).getHorizontalIndex() << 2;
        return facing | super.getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer.Builder createStateContainer() {
        return super.createStateContainer().add(FACING);
    }

}
