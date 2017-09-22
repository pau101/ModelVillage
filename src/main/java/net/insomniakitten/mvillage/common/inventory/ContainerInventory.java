package net.insomniakitten.mvillage.common.inventory;

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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerInventory extends Container {

    private TileInventory tileInventory;
    private InventoryType inventoryType;

    public ContainerInventory(TileInventory tile, EntityPlayer player) {
        IItemHandler inventory = tile.getCapability(TileInventory.getCapabilityType(), null);
        tileInventory = tile;
        inventoryType = tile.getInventoryType();
        createContainerSlots(inventory);
        createPlayerSlots(player);
    }

    private void createContainerSlots(IItemHandler inventory) {
        for (int row = 0; row < inventoryType.getRows(); ++row) {
            for (int column = 0; column < inventoryType.getColumns(); ++column) {
                int index = column + (row * inventoryType.getColumns());
                int x = inventoryType.getSlotsX() + (column * 18);
                int y = inventoryType.getSlotsY() + (row * 18);
                addSlotToContainer(new SlotItemHandler(inventory, index, x, y));
            }
        }
    }

    private void createPlayerSlots(EntityPlayer player) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                int index = column + row * 9 + 9;
                int x = inventoryType.getPlayerX() + (column * 18);
                int y = inventoryType.getPlayerY() + (row * 18);
                addSlotToContainer(new Slot(player.inventory, index, x, y));
            }
        }
        for (int column = 0; column < 9; ++column) {
            int x = inventoryType.getPlayerX() + (column * 18);
            int y = inventoryType.getPlayerY() + 58;
            addSlotToContainer(new Slot(player.inventory, column, x, y));
        }
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        ItemStack stack = slot.getStack().copy();

        if (slot.getHasStack()) {
            int containerSlots = inventoryType.getTotalSlots();
            if (index >= containerSlots) {
                if (!mergeItemStack(stack, 0, containerSlots, false)) return ItemStack.EMPTY; // Inventory -> Slot
            } else if (!mergeItemStack(stack, containerSlots, containerSlots + 36, true))
                return ItemStack.EMPTY; // Slot -> Inventory
            slot.onSlotChanged();
            if (stack.isEmpty()) slot.putStack(ItemStack.EMPTY);
            slot.onTake(player, stack);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        IContainer block = ((IContainer) tileInventory.getBlockType());
        block.playSound(true, player.world, tileInventory.getPos());
        super.onContainerClosed(player);
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return true;
    }

}
