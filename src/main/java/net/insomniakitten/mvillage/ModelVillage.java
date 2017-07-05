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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(
        modid = ModelVillage.MOD_ID,
        name = ModelVillage.MOD_NAME,
        version = ModelVillage.MOD_VERSION,
        acceptedMinecraftVersions = ModelVillage.MC_VERSION
)
public class ModelVillage {

    private static final boolean DEOBF = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static final String MOD_ID = "mvillage";
    public static final String MOD_NAME = "Model Village";
    public static final String MOD_VERSION = "%mod_version";
    public static final String MC_VERSION = "%mc_version%";
    public static final TabMV CTAB = new TabMV();

    @Mod.Instance
    public static ModelVillage instance;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {}

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {}

    static class TabMV extends CreativeTabs {
        TabMV() { super(CreativeTabs.getNextID(), ModelVillage.MOD_ID); }
        @Override @Nonnull
        public ItemStack getTabIconItem() { return new ItemStack(Items.CAKE); }
    }

    public static class LogMV {

        private static final Logger LOGGER = LogManager.getLogger(ModelVillage.MOD_NAME);

        public static void log(boolean global, String msg, Object... vars) {
            if (global || DEOBF)
                LOGGER.info(msg, vars);
        }

        public static void warn(boolean global, String msg, Object... vars) {
            if (global || DEOBF)
                LOGGER.warn(msg, vars);
        }

    }

}
