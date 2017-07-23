package net.insomniakitten.mvillage.base.util;

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

import net.insomniakitten.mvillage.ModelVillage;
import net.insomniakitten.mvillage.base.inventory.InventoryType;
import net.insomniakitten.mvillage.base.inventory.TileInventory;
import net.insomniakitten.mvillage.base.util.GuiManager.GuiType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.items.IItemHandler;

public interface IContainer extends ITileHolder {

    /**
     * This is what tells the base class which inventory type you want attached to your block.
     * @see InventoryType for more information on the available types.
     */
    InventoryType getInventoryType();

    @Override
    default TileEntity getTileEntity() {
        return new TileInventory(getInventoryType());
    }

    @Override
    default void onTileInteract(World world, BlockPos pos, EntityPlayer player) {
        FMLNetworkHandler.openGui(
                player, ModelVillage.getInstance(), GuiType.INVENTORY.getID(),
                world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    default void onTileRemove(World world, BlockPos pos, IBlockState state) {
        Capability<IItemHandler> items = TileInventory.getCapability();
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileInventory && tile.hasCapability(items, null)) {
            IItemHandler inventory = tile.getCapability(items, null);
            if (inventory != null && world.getGameRules().getBoolean("doTileDrops")) {
                for (int i = 0; i < inventory.getSlots(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (!stack.isEmpty()) InventoryHelper.spawnItemStack(
                            world, pos.getX(), pos.getY(), pos.getZ(), stack);
                }
            }
        }
    }

}
