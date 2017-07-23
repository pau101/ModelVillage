package net.insomniakitten.mvillage.base.block;

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

import net.insomniakitten.mvillage.base.inventory.InventoryType;
import net.insomniakitten.mvillage.base.inventory.TileInventory;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockInventory extends BlockMV implements IContainer {

    private InventoryType type;

    public BlockInventory(String name, InventoryType type) {
        super(name, Material.WOOD, SoundType.WOOD, 2.0f, 15.0f);
        setBlockType(EnumBlockType.MODEL);
        this.type = type;
    }

    @Override
    public boolean onBlockActivated(
            World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        handleContainerGui(world, pos, player);
        return true;
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        handleItemDrops(world, pos, state);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TileInventory(type);
    }

}
