package net.insomniakitten.mvillage.common.util;

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

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface ITileHolder {

    @Nullable
    default TileEntity getTileEntity(IBlockState state) {
        return null;
    }

    /**
     * Called in the base block class when the tile's block is right clicked by a player
     * All parameters are provided by Block#onBlockActivated
     */
    default boolean onTileInteract(IBlockState state, World world, BlockPos pos, EntityPlayer player) {
        return false;
    }

    /**
     * Called in the base block class when the tile's block is broken
     * All parameters are provided by Block#breakBlock
     */
    default void onTileRemove(IBlockState state, World world, BlockPos pos) {
        // no-op
    }

}
