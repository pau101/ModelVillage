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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventory extends GuiContainer {

    private TileInventory tileInventory;
    private InventoryType inventoryType;

    public GuiInventory(TileInventory tile, EntityPlayer player) {
        super(new ContainerInventory(tile, player));
        tileInventory = tile;
        inventoryType = tile.getInventoryType();
    }

    @Override
    public void initGui() {
        super.initGui();
        xSize = inventoryType.getTextureWidth();
        ySize = inventoryType.getTextureHeight();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String container = tileInventory.getDisplayName().getUnformattedText();
        String player = I18n.format("container.inventory");
        int containerLabelY = inventoryType.getSlotsY() - (fontRenderer.FONT_HEIGHT + 2);
        int playerLabelY = inventoryType.getPlayerY() - (fontRenderer.FONT_HEIGHT + 2);
        fontRenderer.drawString(container, inventoryType.getSlotsX(), containerLabelY, 4210752);
        fontRenderer.drawString(player, inventoryType.getPlayerX(), playerLabelY, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1f, 1f, 1f);
        mc.getTextureManager().bindTexture(inventoryType.getGuiTexture());
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

}
