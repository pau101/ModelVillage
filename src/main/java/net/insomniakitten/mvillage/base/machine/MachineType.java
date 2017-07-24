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

import net.insomniakitten.mvillage.ModelVillage;
import net.insomniakitten.mvillage.base.util.IContainerType;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public enum MachineType implements IContainerType {

    CRAFTING(new ArrayList<Pair<Integer, Integer>>(){{
        add(Pair.of(30, 17));
        add(Pair.of(48, 17));
        add(Pair.of(66, 17));
        add(Pair.of(30, 35));
        add(Pair.of(48, 35));
        add(Pair.of(66, 35));
        add(Pair.of(30, 53));
        add(Pair.of(48, 53));
        add(Pair.of(66, 53));
        add(Pair.of(120, 31));
    }}, new ResourceLocation(ModelVillage.MOD_ID, "textures/gui/crafting.png"), 176, 166),
    SMELTING(new ArrayList<Pair<Integer, Integer>>(){{
        add(Pair.of(56, 18));
        add(Pair.of(56, 54));
        add(Pair.of(112, 32));
    }}, new ResourceLocation(ModelVillage.MOD_ID, "textures/gui/smelting.png"), 176, 166)
    ;

    private final List<Pair<Integer, Integer>> itemSlots;
    private final ResourceLocation guiTexture;
    private final int textureWidth;
    private final int textureHeight;

    MachineType(List<Pair<Integer, Integer>> itemSlots,
                ResourceLocation guiTexture, int textureWidth, int textureHeight) {
        this.itemSlots = itemSlots;
        this.guiTexture = guiTexture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }


    public List<Pair<Integer, Integer>> getSlots() { return itemSlots; }
    public int getSlotX(int slot) { return itemSlots.get(slot).getLeft(); }
    public int getSlotY(int slot) { return itemSlots.get(slot).getRight(); }
    public int getPlayerX() { return 8; }
    public int getPlayerY() { return textureHeight - 82; }
    public ResourceLocation getGuiTexture() { return guiTexture; }
    public int getTextureWidth() { return textureWidth; }
    public int getTextureHeight() { return  textureHeight; }

    @Override
    public int getTotalSlots() { return itemSlots.size(); }

    @Override
    public int getID() {return ordinal(); }

    public MachineType getType(int id) { return values()[id % values().length]; }

}
