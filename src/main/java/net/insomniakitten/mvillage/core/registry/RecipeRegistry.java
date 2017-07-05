package net.insomniakitten.mvillage.core.registry;

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
import net.insomniakitten.mvillage.ModelVillage.LogMV;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModelVillage.MOD_ID)
public class RecipeRegistry {

    private static final List<IRecipe> RECIPES = new ArrayList<IRecipe>();

    @SubscribeEvent
    public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
        if (RECIPES.isEmpty()) return;
        LogMV.log(true, "Registering {} recipes", RECIPES.size());
        event.getRegistry().registerAll(RECIPES.toArray(new IRecipe[0]));
    }

    public static void registerRecipe(IRecipe recipe) {
        if (RECIPES.contains(recipe)) {
            LogMV.warn(false, "Recipe <{}> exists already! Skipping...", recipe.getRegistryName());
            return;
        }
        RECIPES.add(recipe);
    }

}
