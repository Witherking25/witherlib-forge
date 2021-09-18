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
import com.withertech.witherlib.tile.BaseTileEntity;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;

public abstract class TileGui<B extends BaseTileBlock<T>, T extends BaseTileEntity<T>,
		C extends TileEntityBaseContainer<C, T>, S extends TileEntityBaseContainerScreen<T, C>>
{
	protected final RegistryObject<B> block = registerBlock();
	protected final RegistryObject<BlockEntityType<T>> tile = registerTile();
	protected final RegistryObject<MenuType<C>> container = registerContainer();
	protected final MenuScreens.ScreenConstructor<C, S> screen = registerScreen();

	protected abstract RegistryObject<B> registerBlock();

	public final RegistryObject<B> getBlock()
	{
		return block;
	}

	protected abstract RegistryObject<BlockEntityType<T>> registerTile();

	public final RegistryObject<BlockEntityType<T>> getTile()
	{
		return tile;
	}

	protected abstract RegistryObject<MenuType<C>> registerContainer();

	public final RegistryObject<MenuType<C>> getContainer()
	{
		return container;
	}

	protected abstract MenuScreens.ScreenConstructor<C, S> registerScreen();

	public final MenuScreens.ScreenConstructor<C, S> getScreen()
	{
		return screen;
	}
}
