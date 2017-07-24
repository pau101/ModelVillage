package net.insomniakitten.mvillage.base.machine;

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

import javafx.util.Pair;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public enum MachineType {
    ;

    // slot id, <x, y>
    private final Map<Integer, Pair<Integer, Integer>> slots;
    private final ResourceLocation guiTexture;
    private final int textureWidth;
    private final int textureHeight;

    MachineType(Map<Integer, Pair<Integer, Integer>> slots,
                ResourceLocation guiTexture, int textureWidth, int textureHeight) {
        this.slots = slots;
        this.guiTexture = guiTexture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public int getTotalSlots() { return slots.size(); }
    public int getSlotX(int slot) { return slots.get(slot).getKey(); }
    public int getSlotY(int slot) { return slots.get(slot).getValue(); }
    public int getPlayerX() { return 8; }
    public int getPlayerY() { return textureHeight - 82; }
    public ResourceLocation getGuiTexture() { return guiTexture; }
    public int getTextureWidth() { return textureWidth; }
    public int getTextureHeight() { return  textureHeight; }

    public int getID() {return ordinal(); }

    public static MachineType getType(int id) {
        return values()[id % values().length];
    }

}
