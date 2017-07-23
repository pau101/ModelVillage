package net.insomniakitten.mvillage.base.inventory;

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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerInventory extends Container {

    private InventoryType type;

    public ContainerInventory(TileInventory tile, EntityPlayer player) {
        Capability<IItemHandler> items = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        IItemHandler inventory = tile.hasCapability(items, null) ? tile.getCapability(items, null) : null;
        this.type = tile.getInventoryType();
        createContainerSlots(inventory);
        createPlayerSlots(player);
    }

    private void createContainerSlots(IItemHandler inventory) {
        if (inventory == null) return;
        for (int row = 0; row < type.getRows(); ++row) {
            for (int column = 0; column < type.getColumns(); ++column) {
                int index = column + (row * type.getColumns());
                int x = type.getSlotsX() + (column * 18);
                int y = type.getSlotsY() + (row * 18);
                addSlotToContainer(new SlotItemHandler(inventory, index, x, y));
            }
        }
    }

    private void createPlayerSlots(EntityPlayer player) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                int index = column + row * 9 + 9;
                int x = type.getPlayerX() + (column * 18);
                int y = type.getPlayerY() + (row * 18);
                addSlotToContainer(new Slot(player.inventory, index, x, y));
            }
        }
        for (int column = 0; column < 9; ++column) {
            int x = type.getPlayerX() + (column * 18);
            int y = type.getPlayerY() + 58;
            addSlotToContainer(new Slot(player.inventory, column, x, y));
        }
    }

    @Override @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        ItemStack original = slot.getStack();
        ItemStack stack = original.copy();

        if (slot.getHasStack()) {

            int containerSlots = type.getTotalSlots();

            if (index >= containerSlots) {
                if (!mergeItemStack(stack, 0, containerSlots, false))
                    return ItemStack.EMPTY; // Inventory -> Slot
            } else if (!mergeItemStack(stack, containerSlots, containerSlots + 36, true))
                return ItemStack.EMPTY; // Slot -> Inventory

            slot.onSlotChanged();

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }

            slot.onTake(player, stack);

            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return true;
    }

}
