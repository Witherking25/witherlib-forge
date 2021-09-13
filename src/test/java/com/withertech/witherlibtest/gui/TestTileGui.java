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

package com.withertech.witherlibtest.gui;

import com.withertech.witherlib.gui.TileGui;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.blocks.TestTileBlock;
import com.withertech.witherlibtest.containers.TestContainer;
import com.withertech.witherlibtest.screens.TestScreen;
import com.withertech.witherlibtest.tiles.TestTile;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class TestTileGui extends TileGui<TestTileBlock, TestTile, TestContainer, TestScreen>
{
    @Override
    protected RegistryObject<TestTileBlock> registerBlock()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_tile_block", TestTileBlock.class));
    }

    @Override
    protected RegistryObject<TileEntityType<TestTile>> registerTile()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_tile", TestTile.class));
    }

    @Override
    protected RegistryObject<ContainerType<TestContainer>> registerContainer()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_container", TestContainer.class));
    }

    @Override
    protected ScreenManager.IScreenFactory<TestContainer, TestScreen> registerScreen()
    {
        return TestScreen::new;
    }

}
