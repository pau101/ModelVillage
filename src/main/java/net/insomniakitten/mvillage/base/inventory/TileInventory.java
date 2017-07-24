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

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class TileInventory extends TileEntity {

    @CapabilityInject(IItemHandler.class)
    private static final Capability<IItemHandler> CAPABILITY = null;

    private InventoryType inventoryType;
    private ItemStackHandler itemStackHandler;

    public TileInventory() {
        // no-op
    }

    public TileInventory(InventoryType type) {
        inventoryType = type;
        itemStackHandler = new ItemStackHandler(type.getTotalSlots()) {
            @Override protected void onContentsChanged(int slot) { markDirty(); } };
    }

    public static Capability<IItemHandler> getCapability() {
        return CAPABILITY;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing facing) {
        if (cap == CAPABILITY)
            return CAPABILITY.cast(itemStackHandler);
        else return super.getCapability(cap, facing);
    }

    @Override
    public final void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        inventoryType = InventoryType.getType(nbt.getInteger("type"));
        itemStackHandler = new ItemStackHandler(inventoryType.getTotalSlots()) {
            @Override protected void onContentsChanged(int slot) { markDirty(); } };
        itemStackHandler.deserializeNBT(nbt.getCompoundTag("contents"));
    }

    @Override @Nonnull
    public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("type", inventoryType.getID());
        nbt.setTag("contents", itemStackHandler.serializeNBT());
        return nbt;
    }

    @Override @SuppressWarnings("NullableProblems")
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 0, this.getUpdateTag());
    }

    @Override @SideOnly(Side.CLIENT)
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public final void handleUpdateTag(@Nonnull NBTTagCompound tag) {
        readFromNBT(tag);
    }

}
