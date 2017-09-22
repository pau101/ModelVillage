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

import net.insomniakitten.mvillage.ModelVillage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiDoorBell extends GuiContainer {

    private static final ResourceLocation GUI_DOOR_BELL = new ResourceLocation(
            ModelVillage.MOD_ID, "textures/gui/door_bell.png");

    public GuiDoorBell(EntityPlayer player) {
        super(new ContainerDoorBell(player));
    }

    @Override
    public void initGui() {
        super.initGui();
        xSize = 176;
        ySize = 166;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String container = I18n.format("container.mvillage.doorBell");
        String player = I18n.format("container.inventory");
        int offsetY = fontRenderer.FONT_HEIGHT + 2;
        fontRenderer.drawString(container, 8, 18 - offsetY, 0x404040);
        fontRenderer.drawString(player, 8, 84 - offsetY, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1f, 1f, 1f);
        mc.getTextureManager().bindTexture(GUI_DOOR_BELL);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

}
