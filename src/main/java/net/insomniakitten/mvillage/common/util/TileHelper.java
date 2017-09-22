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

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileHelper {

    private static boolean isTileHolder(Block block) {
        return block instanceof ITileHolder;
    }

    public static boolean hasTileEntity(IBlockState state) {
        return getTileEntity(state) != null;
    }

    public static TileEntity getTileEntity(IBlockState state) {
        return state.getBlock() instanceof ITileHolder
               ? ((ITileHolder) state.getBlock()).getTileEntity(state)
               : null;
    }

    public static boolean onTileInteract(IBlockState state, World world, BlockPos pos, EntityPlayer player) {
        return isTileHolder(state.getBlock()) && ((ITileHolder) state.getBlock())
                .onTileInteract(state, world, pos, player);
    }

    public static void onTileRemove(IBlockState state, World world, BlockPos pos) {
        if (isTileHolder(state.getBlock())) {
            ((ITileHolder) state.getBlock()).onTileRemove(state, world, pos);
            world.removeTileEntity(pos);
        }
    }

}
