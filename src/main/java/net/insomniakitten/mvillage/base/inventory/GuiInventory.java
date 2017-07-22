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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class GuiInventory extends GuiContainer {

    private TileEntity tile;
    private InventoryType type;

    public GuiInventory(TileInventory tile, EntityPlayer player) {
        super(new ContainerInventory(tile, player));
        this.tile = tile;
        this.type = tile != null ? tile.getInventoryType() : InventoryType.NONE;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String container = I18n.format(tile.getBlockType().getUnlocalizedName()+ ".name");
        String player = I18n.format("container.inventory");
        this.fontRenderer.drawString(container, 8, 6, 4210752);
        int padding = fontRenderer.FONT_HEIGHT + 2;
        this.fontRenderer.drawString(player, 8, type.getPlayerY() - padding, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1f, 1f, 1f);
        this.mc.getTextureManager().bindTexture(type.getTexture());
        int x = (width - type.getTextureWidth()) / 2;
        int y = (height - type.getTextureHeight()) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, type.getTextureWidth(), type.getTextureHeight());
    }

}
