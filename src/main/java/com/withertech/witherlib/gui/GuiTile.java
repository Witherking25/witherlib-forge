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

import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.block.BaseTileEntity;
import net.minecraft.client.gui.ScreenManager.IScreenFactory;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Function;

public abstract class GuiTile<B extends BaseTileBlock, T extends BaseTileEntity, C extends TileEntityBaseContainer<T>, S extends TileEntityBaseContainerScreen<T, C>, R extends TileEntityRenderer<? super T>>
{
    protected final RegistryObject<B> block = registerBlock();

    protected abstract RegistryObject<B> registerBlock();

    public final RegistryObject<B> getBlock()
    {
        return block;
    }

    protected final RegistryObject<TileEntityType<T>> tile = registerTile();

    protected abstract RegistryObject<TileEntityType<T>> registerTile();

    public final RegistryObject<TileEntityType<T>> getTile()
    {
        return tile;
    }

    protected final RegistryObject<ContainerType<C>> container = registerContainer();

    protected abstract RegistryObject<ContainerType<C>> registerContainer();

    public final RegistryObject<ContainerType<C>> getContainer()
    {
        return container;
    }

    protected final IScreenFactory<C, S> screen = registerScreen();

    protected abstract IScreenFactory<C, S> registerScreen();

    public final IScreenFactory<C, S> getScreen()
    {
        return screen;
    }

    protected final Function<? super TileEntityRendererDispatcher, R> ter = registerTer();

    protected abstract Function<? super TileEntityRendererDispatcher, R> registerTer();

    public final Function<? super TileEntityRendererDispatcher, R> getTer()
    {
        return ter;
    }
}
