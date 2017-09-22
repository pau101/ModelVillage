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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;
import static net.minecraftforge.common.util.Constants.NBT.TAG_INT;

public class InventoryHandler <K extends IContainerType> extends ItemStackHandler {

    private TileEntity tile;

    public InventoryHandler(TileEntity tile, K type) {
        setSize(type.getTotalSlots());
        this.tile = tile;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger("slot", i);
                stacks.get(i).writeToNBT(item);
                items.appendTag(item);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("items", items);
        nbt.setInteger("size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        setSize(nbt.hasKey("size", TAG_INT)
                ? nbt.getInteger("size")
                : stacks.size());
        NBTTagList items = nbt.getTagList("items", TAG_COMPOUND);
        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound item = items.getCompoundTagAt(i);
            int slot = item.getInteger("slot");
            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, new ItemStack(item));
            }
        }
        onLoad();
    }

    @Override
    protected void onContentsChanged(int slot) {
        tile.markDirty();
    }

}
