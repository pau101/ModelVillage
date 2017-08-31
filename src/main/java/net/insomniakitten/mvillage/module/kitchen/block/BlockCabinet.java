package net.insomniakitten.mvillage.module.kitchen.block;

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

import net.insomniakitten.mvillage.common.block.BlockCardinalBase;
import net.insomniakitten.mvillage.common.inventory.IContainer;
import net.insomniakitten.mvillage.common.inventory.InventoryType;
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

@SuppressWarnings("deprecation")
public class BlockCabinet extends BlockCardinalBase<BlockCabinet.CabinetType> implements IContainer {

    public BlockCabinet() {
        super("cabinet", CabinetType.class);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.LARGE;
    }

    public enum CabinetType implements IStatePropertyHolder<CabinetType> {

        DOORS, DRAWERS, CRAFTING, SINK;

        @Override
        public CabinetType getEnum() {
            return this;
        }

        @Override
        public float getHardness() {
            return 5.0f;
        }

        @Override
        public float getResistance() {
            return 30.0f;
        }

        @Override
        public Material getMaterial() {
            return Material.IRON;
        }

        @Override
        public SoundType getSoundType() {
            return SoundType.METAL;
        }

        @Override
        public BlockRenderLayer getRenderLayer() {
            return BlockRenderLayer.CUTOUT;
        }

        @Override
        public String getOrePrefix() {
            return "cabinet";
        }

        @Override
        public String getEffectiveTool() {
            return "pickaxe";
        }

    }

}
