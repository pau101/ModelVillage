package net.insomniakitten.mvillage.testing;

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

import net.insomniakitten.mvillage.base.block.BlockCardinal;
import net.insomniakitten.mvillage.base.inventory.InventoryType;
import net.insomniakitten.mvillage.base.util.IContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCabinet extends BlockCardinal implements IContainer {

    public BlockCabinet() {
        super("cabinet", Material.IRON, SoundType.METAL, 2.0f, 15.0f);
        setBlockType(EnumBlockType.MODEL);
    }

    @Override
    public boolean onBlockActivated(
            World world, BlockPos pos, IBlockState state,
            EntityPlayer player, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        if (facing == state.getValue(getProperty())) {
            // Open GUI if interacting with front of cabinet
            onTileInteract(world, pos, player);
            return true;
        }
        else return false;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.LARGE;
    }

}
