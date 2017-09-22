package net.insomniakitten.mvillage;

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

import net.insomniakitten.mvillage.common.util.GuiManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = ModelVillage.MOD_ID,
     name = ModelVillage.MOD_NAME,
     version = ModelVillage.MOD_VERSION,
     dependencies = ModelVillage.DEPENDENCIES,
     acceptedMinecraftVersions = ModelVillage.MC_VERSION)

public class ModelVillage {

    @Mod.Instance
    public static ModelVillage instance;

    public static final String MOD_ID = "mvillage";
    public static final String MOD_NAME = "Model Village";
    public static final String MOD_VERSION = "%MODVERSION%";
    public static final String DEPENDENCIES = "required-after:ctm@[%CTMVERSION%,);";
    public static final String MC_VERSION = "[1.12,1.13)";

    public static final CreativeTabs CTAB = new CreativeTabs(ModelVillage.MOD_ID) {

        @Override
        public ItemStack getTabIconItem() {
            return ItemStack.EMPTY;
        }

    };

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        GuiManager.register();
    }

}
