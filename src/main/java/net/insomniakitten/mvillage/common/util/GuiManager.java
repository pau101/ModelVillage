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

import net.insomniakitten.mvillage.ModelVillage;
import net.insomniakitten.mvillage.common.inventory.ContainerInventory;
import net.insomniakitten.mvillage.common.inventory.GuiInventory;
import net.insomniakitten.mvillage.common.inventory.TileInventory;
import net.insomniakitten.mvillage.module.technical.gui.ContainerDoorBell;
import net.insomniakitten.mvillage.module.technical.gui.GuiDoorBell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GuiManager implements IGuiHandler {

    public static final GuiManager INSTANCE = new GuiManager();

    public static void register() {
        NetworkRegistry.INSTANCE.registerGuiHandler(ModelVillage.MOD_ID, INSTANCE);
    }

    @Override
    @Nullable
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        switch (GuiType.get(ID)) {
            case INVENTORY:
                return new ContainerInventory((TileInventory) tile, player);
            case DOOR_BELL:
                return new ContainerDoorBell(player);
        }
        return null;
    }

    @Override
    @Nullable
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        switch (GuiType.get(ID)) {
            case INVENTORY:
                return new GuiInventory((TileInventory) tile, player);
            case DOOR_BELL:
                return new GuiDoorBell(player);
        }
        return null;
    }

    public enum GuiType {

        INVENTORY, DOOR_BELL;

        public static GuiType get(int id) {
            return values()[ id % values().length ];
        }

        public int getId() {
            return ordinal();
        }

    }

}
