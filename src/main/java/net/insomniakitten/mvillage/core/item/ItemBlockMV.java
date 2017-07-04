package net.insomniakitten.mvillage.core.item;

/*
 * This file was created at 21:28 on 04 Jul 2017 by InsomniaKitten
 *
 * It is distributed as part of the ModelVillage mod.
 * Source code is visible at: https://github.com/InsomniaKitten/ModelVillage
 *
 * Copyright (c) InsomniaKitten 2017. All Rights Reserved.
 */

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockMV extends ItemBlock {

    public ItemBlockMV(Block block) {
        super(block);
        assert block.getRegistryName() != null;
        setRegistryName(block.getRegistryName());
        setUnlocalizedName(block.getUnlocalizedName());
    }

}
