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

import net.insomniakitten.mvillage.base.util.GuiManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;

@Mod(   modid = ModelVillage.MOD_ID,
        name = ModelVillage.MOD_NAME,
        version = ModelVillage.MOD_VERSION,
        acceptedMinecraftVersions = ModelVillage.MC_VERSION,
        dependencies = ModelVillage.DEPENDENCIES)

public class ModelVillage {

    private static final boolean DEOBF = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

    public static final String MOD_ID = "mvillage";
    public static final String MOD_NAME = "Model Village";
    public static final String MOD_VERSION = "%mod_version";
    public static final String MC_VERSION = "%mc_version%";
    public static final String DEPENDENCIES = "required-after:ctm@[%chisel_version%,);";
    public static final CreativeTabMV CTAB = new CreativeTabMV();

    @Mod.Instance
    private static ModelVillage instance;

    public static ModelVillage getInstance() {
        return instance;
    }

    static class CreativeTabMV extends CreativeTabs {
        CreativeTabMV() { super(CreativeTabs.getNextID(), ModelVillage.MOD_ID); }
        @Override @Nonnull // TODO: Use mod object for creative tab icon
        public ItemStack getTabIconItem() { return new ItemStack(Items.CAKE); }
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ModelVillage.MOD_ID, new GuiManager());
    }

    public static class Logger {

        private static final org.apache.logging.log4j.Logger LOGGER
                = LogManager.getLogger(ModelVillage.MOD_NAME);

        public static void info(boolean global, String msg, Object... vars) {
            if (global || DEOBF) LOGGER.info(msg, vars);
        }

        public static void warn(boolean global, String msg, Object... vars) {
            if (global || DEOBF) LOGGER.warn(msg, vars);
        }

    }

}
