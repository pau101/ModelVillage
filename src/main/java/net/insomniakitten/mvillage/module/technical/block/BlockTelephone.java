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
import net.insomniakitten.mvillage.common.block.BlockCardinalBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Arrays;
import java.util.function.Function;

public class BlockTelephone extends BlockCardinalBase {

    private static final ImmutableMap<EnumFacing, AxisAlignedBB> AABB_TELEPHONE = Arrays.stream(EnumFacing.values())
            .collect(ImmutableMap.toImmutableMap(Function.identity(), BlockTelephone::computeAABBForFacing));

    public BlockTelephone() {
        super("telephone", 0.5f, 2.5f, Material.IRON, SoundType.METAL);
    }

    private static AxisAlignedBB computeAABBForFacing(EnumFacing facing) {
        AxisAlignedBB aabb = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.5, 0.75);
        double minX = aabb.minX, minY = aabb.minY, minZ = aabb.minZ;
        double maxX = aabb.maxX, maxY = aabb.maxY, maxZ = aabb.maxZ;
        switch (facing) {
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
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB_TELEPHONE.get(getFacing(state));
    }

}
