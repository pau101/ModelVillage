package net.insomniakitten.mvillage.module.technical.gui;

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

public class ContainerDoorBell extends Container {

    private final int textureX = 176, textureY = 166;
    private final int playerInvX = 8, playerInvY = 84;

    public ContainerDoorBell(EntityPlayer player) {
        createPlayerSlots(player);
    }

    private void createPlayerSlots(EntityPlayer player) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                int index = column + row * 9 + 9;
                int x = playerInvX + (column * 18);
                int y = playerInvY + (row * 18);
                addSlotToContainer(new Slot(player.inventory, index, x, y));
            }
        }
        for (int column = 0; column < 9; ++column) {
            int x = playerInvX + (column * 18);
            int y = playerInvY + 58;
            addSlotToContainer(new Slot(player.inventory, column, x, y));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

}
