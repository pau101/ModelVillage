package net.insomniakitten.mvillage.module.furniture.block;

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

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import net.insomniakitten.mvillage.client.state.StateRegistry;
import net.insomniakitten.mvillage.common.RegistryManager;
import net.insomniakitten.mvillage.common.block.BlockEnumBase;
import net.insomniakitten.mvillage.common.item.ItemBlockEnumBase;
import net.insomniakitten.mvillage.common.util.IStatePropertyHolder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;

public class BlockTable extends BlockEnumBase<BlockTable.TableType> {

    private static final ImmutableMap<EnumFacing, IProperty<Boolean>> ADJACENT_CONNECTIONS = Arrays.stream(
            EnumFacing.HORIZONTALS).collect(ImmutableMap.toImmutableMap(Function.identity(),
            facing -> PropertyBool.create("connect_" + facing.getName().toLowerCase(Locale.ROOT))));

    private static final PropertyEnum<TableShape> SHAPE = PropertyEnum.create("shape", TableShape.class);

    public BlockTable() {
        super("table", TableType.class);
        StateRegistry.registerPropertyBlacklist(this, getAllProperties());
        StateRegistry.registerVariantRedirect(this, getPropertyEnum());
        setLightOpacity(0);
    }

    protected static boolean isTable(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof BlockTable;
    }

    @Override
    public void registerItemBlock() {
        RegistryManager.registerItemBlock(new ItemBlockEnumBase<>(this, true));
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        TableShape shape = TableShape.get(meta);
        TableType type = getType(meta >> 1);
        return getDefaultState().withProperty(SHAPE, shape).withProperty(getPropertyEnum(), type);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int shape = state.getValue(SHAPE).ordinal();
        int type = getType(state).getMetadata() << 1;
        return shape | type;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean onBlockActivated(
            World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
        TableShape newShape = state.getValue(SHAPE).getOpposite();
        return player.isSneaking() && player.getHeldItem(hand).isEmpty()
                && world.setBlockState(pos, state.withProperty(SHAPE, newShape));
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return BlockRenderLayer.CUTOUT.equals(layer);
        // Needed for multipart to respect the block render layer
    }

    @Override
    protected BlockStateContainer.Builder createStateContainer() {
        BlockStateContainer.Builder builder = super.createStateContainer();
        getAdjacentConnections().forEach(builder::add);
        builder.add(SHAPE);
        return builder;
    }

    private IProperty[] getAllProperties() {
        return ArrayUtils.addAll(getAdjacentConnections().toArray(new IProperty[ 0 ]), getPropertyEnum(), SHAPE);
    }

    private boolean isTable(IBlockState state, IBlockAccess world, BlockPos pos) {
        TableType type = state.getValue(getPropertyEnum());
        IBlockState stateAt = world.getBlockState(pos);
        return stateAt.getBlock() instanceof BlockTable && type.equals(stateAt.getValue(getPropertyEnum()));
    }

    private IProperty<Boolean> getConnectionForSide(EnumFacing side) {
        return BlockTable.ADJACENT_CONNECTIONS.get(side);
    }

    private ImmutableCollection<IProperty<Boolean>> getAdjacentConnections() {
        return BlockTable.ADJACENT_CONNECTIONS.values();
    }

    @Override
    @Deprecated
    public boolean isTopSolid(IBlockState state) {
        return state.getValue(SHAPE) == TableShape.NORMAL;
    }

    @Override
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        for (EnumFacing side : EnumFacing.HORIZONTALS) {
            boolean isConnected = isTable(state, world, pos.offset(side));
            state = state.withProperty(getConnectionForSide(side), isConnected);
        }
        return state;
    }

    @Override
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(
            IBlockState actualState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        BlockPos posAt = pos.offset(side);
        IBlockState state = world.getBlockState(pos);
        IBlockState stateAt = world.getBlockState(posAt);
        EnumFacing sideAt = side.getOpposite();
        return state == stateAt ? side == EnumFacing.UP : !stateAt.doesSideBlockRendering(world, posAt, sideAt);
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public IBlockState getStateForPlacement(
            World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
            EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(getPropertyEnum(), getType(meta));
    }

    public enum TableShape implements IStringSerializable {

        NORMAL,
        CORNER;

        public static TableShape get(int meta) {
            return values()[ meta % values().length ];
        }

        public TableShape getOpposite() {
            return values()[ ~ordinal() & 1 ];
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }

    }

    public enum TableType implements IStatePropertyHolder<TableType> {

        WOODEN(2.0f, 15.0f, Material.WOOD, SoundType.WOOD, "axe"),
        MODERN(5.0f, 30.0f, Material.IRON, SoundType.METAL, "pickaxe"),
        ANTIQUE(2.0f, 15.0f, Material.WOOD, SoundType.WOOD, "axe"),
        FANTASY(5.0f, 30.0f, Material.IRON, SoundType.GLASS, "pickaxe");

        private static final AxisAlignedBB AABB_TABLE = new AxisAlignedBB(0, 0.8125, 0, 1, 1, 1);

        private final float hardness;
        private final float resistance;
        private final Material material;
        private final SoundType sound;
        private final String tool;

        TableType(float hardness, float resistance, Material material, SoundType sound, String tool) {
            this.hardness = hardness;
            this.resistance = resistance;
            this.material = material;
            this.sound = sound;
            this.tool = tool;
        }

        @Override
        public TableType getEnum() {
            return this;
        }

        @Override
        public float getHardness() {
            return hardness;
        }

        @Override
        public float getResistance() {
            return resistance;
        }

        @Override
        public Material getMaterial() {
            return material;
        }

        @Override
        public SoundType getSoundType() {
            return sound;
        }

        @Override
        public BlockRenderLayer getRenderLayer() {
            return BlockRenderLayer.SOLID;
        }

        @Override
        public String getOrePrefix() {
            return "blockTable";
        }

        @Override
        public String getEffectiveTool() {
            return tool;
        }

        @Override
        public AxisAlignedBB getBoundingBox() {
            return AABB_TABLE;
        }

    }

}
