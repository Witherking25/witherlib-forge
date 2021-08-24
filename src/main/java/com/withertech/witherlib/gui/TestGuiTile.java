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

package com.withertech.witherlib.gui;

import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.block.*;
import com.withertech.witherlib.registration.TypedRegKey;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Function;

public class TestGuiTile extends GuiTile<TestBlock, TestTileEntity, TestContainer, TestScreen, TileEntityRenderer<TestTileEntity>>
{
    @Override
    protected RegistryObject<TestBlock> registerBlock()
    {
        return WitherLib.INSTANCE.REGISTRY.getBlock(TypedRegKey.tileBlock("test", TestBlock.class));
    }

    @Override
    protected RegistryObject<TileEntityType<TestTileEntity>> registerTile()
    {
        return WitherLib.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test", TestTileEntity.class));
    }

    @Override
    protected RegistryObject<ContainerType<TestContainer>> registerContainer()
    {
        return WitherLib.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test", TestContainer.class));
    }

    @Override
    protected ScreenManager.IScreenFactory<TestContainer, TestScreen> registerScreen()
    {
        return TestScreen::new;
    }

    @Override
    protected Function<? super TileEntityRendererDispatcher, TileEntityRenderer<TestTileEntity>> registerTer()
    {
        return null;
    }

}
