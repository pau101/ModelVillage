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

import net.insomniakitten.mvillage.ModelVillage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileInventory extends TileEntity {

    @CapabilityInject(IItemHandler.class)
    private static final Capability<IItemHandler> CAPABILITY = null;

    private static final ResourceLocation KEY = new ResourceLocation(ModelVillage.MOD_ID, "tile_inventory");

    private InventoryType inventoryType;
    private InventoryHandler inventoryHandler;

    public TileInventory() {
        // no-op
    }

    public TileInventory(InventoryType type) {
        inventoryType = type;
        inventoryHandler = new InventoryHandler<>(this, type);
    }

    public static String getKey() {
        return KEY.toString();
    }

    public static Capability<IItemHandler> getCapabilityType() {
        return CAPABILITY;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    @Override
    public final void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        inventoryType = InventoryType.getType(nbt.getInteger("inventoryType"));
        inventoryHandler = new InventoryHandler<>(this, inventoryType);
        inventoryHandler.deserializeNBT(nbt.getCompoundTag("contents"));
    }

    @Override
    @Nonnull
    public final NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("inventoryType", inventoryType.getID());
        nbt.setTag("contents", inventoryHandler.serializeNBT());
        return nbt;
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        String name = getBlockType().getUnlocalizedName() + ".name";
        return new TextComponentTranslation(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final void handleUpdateTag(@Nonnull NBTTagCompound tag) {
        readFromNBT(tag);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> cap, @Nullable EnumFacing facing) {
        return cap.equals(CAPABILITY) || super.hasCapability(cap, facing);
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing facing) {
        return cap.equals(CAPABILITY)
               ? CAPABILITY.cast(inventoryHandler)
               : super.getCapability(cap, facing);
    }

}
