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

import net.insomniakitten.mvillage.ModelVillage;
import net.minecraft.util.ResourceLocation;

public enum InventoryType {

    GRID(3, 3, 62, 17, new ResourceLocation(ModelVillage.MOD_ID, "textures/gui/grid.png"), 176, 166),
    SMALL(1, 5, 44, 20, new ResourceLocation(ModelVillage.MOD_ID, "textures/gui/small.png"), 176, 133),
    MEDIUM(2, 9, 8, 27, new ResourceLocation(ModelVillage.MOD_ID, "textures/gui/medium.png"), 176, 166),
    LARGE(3, 9, 8, 18, new ResourceLocation(ModelVillage.MOD_ID, "textures/gui/large.png"), 176, 166);

    private final int rows;
    private final int columns;
    private final int x;
    private final int y;
    private final ResourceLocation texture;
    private final int textureWidth;
    private final int textureHeight;

    InventoryType(int rows, int columns, int x, int y,
                  ResourceLocation texture, int textureWidth, int textureHeight) {
        this.rows = rows;
        this.columns = columns;
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public int getTotalSlots() { return rows * columns; }
    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public int getSlotsX() { return x; }
    public int getSlotsY() { return y; }
    public int getPlayerX() { return 8; }
    public int getPlayerY() { return textureHeight - 82; }
    public ResourceLocation getTexture() { return texture; }
    public int getTextureWidth() { return textureWidth; }
    public int getTextureHeight() { return  textureHeight; }

    public int getID() { return ordinal(); }
    public static InventoryType getType(int id) { return values()[id % values().length]; }

}
