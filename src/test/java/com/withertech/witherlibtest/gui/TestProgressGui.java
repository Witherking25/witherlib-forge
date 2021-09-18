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
import com.withertech.witherlibtest.blocks.TestProgressBlock;
import com.withertech.witherlibtest.containers.TestProgressContainer;
import com.withertech.witherlibtest.screens.TestProgressScreen;
import com.withertech.witherlibtest.tiles.TestProgressTile;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;

public class TestProgressGui extends TileGui<TestProgressBlock, TestProgressTile, TestProgressContainer, TestProgressScreen>
{
    @Override
    protected RegistryObject<TestProgressBlock> registerBlock()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_progress_block", TestProgressBlock.class));
    }

    @Override
    protected RegistryObject<BlockEntityType<TestProgressTile>> registerTile()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_progress_tile", TestProgressTile.class));
    }

    @Override
    protected RegistryObject<MenuType<TestProgressContainer>> registerContainer()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_progress_container", TestProgressContainer.class));
    }

    @Override
    protected MenuScreens.ScreenConstructor<TestProgressContainer, TestProgressScreen> registerScreen()
    {
        return TestProgressScreen::new;
    }
}
