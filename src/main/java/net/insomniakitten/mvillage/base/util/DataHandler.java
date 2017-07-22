package net.insomniakitten.mvillage.base.util;

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

import com.google.common.base.Equivalence;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@SuppressWarnings("ConstantConditions")
public class DataHandler {

    public static final Equivalence<ItemStack> EQV = new Equivalence<ItemStack>() {
        @Override
        protected boolean doEquivalent(@Nonnull ItemStack a, @Nonnull ItemStack b) {
            return ItemStack.areItemStackShareTagsEqual(a, b);
        }

        @Override
        protected int doHash(@Nonnull ItemStack stack) {
            int result = stack.getItem().getRegistryName().hashCode();
            result = 31 * result + stack.getItemDamage();
            result = 31 * result + stack.getCount();
            result = 31 * result + (stack.hasTagCompound() ?
                    stack.getTagCompound().hashCode() : 0);
            return result;
        }
    };

}
