/*
 * witherlib-forge
 * Copyright (C) 2021 WitherTech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.withertech.witherlib;

import com.withertech.witherlib.block.*;
import com.withertech.witherlib.gui.TestGuiTile;
import com.withertech.witherlib.registration.*;
import com.withertech.witherlib.util.SyncVariable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WitherLib.MODID)
public class WitherLib extends BuilderMod
{
    public static final String MODID = "witherlib";
    public static final Logger LOGGER = LogManager.getLogger();
    public static WitherLib INSTANCE;


    public WitherLib()
    {
        super(new ModData(MODID, FMLJavaModLoadingContext.get().getModEventBus()));
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
        SyncVariable.Helper.registerSerializer(ItemStackHandler.class, compoundNBT ->
        {
            ItemStackHandler itemStackHandler = new ItemStackHandler();
            itemStackHandler.deserializeNBT(compoundNBT);
            return itemStackHandler;
        }, (compoundNBT, itemStackHandler) -> itemStackHandler.deserializeNBT(compoundNBT));
    }

    @Override
    protected BuilderForgeRegistry<Block> registerBlocks()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.BLOCKS)
                .add(TypedRegKey.tileBlock("test", TestBlock.class), () -> new TestBlock(true, AbstractBlock.Properties.of(Material.STONE)))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<Item> registerItems()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ITEMS)
                .add(TypedRegKey.item("test", BlockItem.class), () -> new BlockItem(INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test", TestBlock.class)).get(), new Item.Properties()))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<TileEntityType<?>> registerTiles()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.TILE_ENTITIES)
                .add(TypedRegKey.tile("test", TestTileEntity.class), () -> TileEntityType.Builder.of(TestTileEntity::new, INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test", BaseTileBlock.class)).get()).build(null))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<ContainerType<?>> registerContainers()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.CONTAINERS)
                .add(TypedRegKey.container("test", TestContainer.class), () -> IForgeContainerType.create((windowId, inv, data) -> new TestContainer(windowId, inv.player, data.readBlockPos())))
                .build();
    }

    @Override
    protected BuilderGuiTileRegistry registerGuis()
    {
        return BuilderGuiTileRegistry.builder()
                .add(
                        TypedRegKey.gui("test", TestGuiTile.class),
                        new TestGuiTile()
                )
                .build();
    }
}
