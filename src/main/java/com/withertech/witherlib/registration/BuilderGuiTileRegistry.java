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

package com.withertech.witherlib.registration;

import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.gui.TileEntityBaseContainerScreen;
import com.withertech.witherlib.gui.TileGui;
import com.withertech.witherlib.registration.TypedRegKey.TypedGuiKey;
import com.withertech.witherlib.tile.BaseTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

import java.util.HashMap;
import java.util.Map;

public class BuilderGuiTileRegistry
{
    private final Map<TypedGuiKey<? extends TileGui<? extends BaseTileBlock<?>, ?, ?, ?, ?>, ?, ?, ?, ?, ?>, TileGui<?, ?, ?, ?, ?>> GUI_TILES;

    private BuilderGuiTileRegistry(Builder builder)
    {
        GUI_TILES = builder.GUI_TILES;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    @SuppressWarnings("unchecked")
    public <B extends BaseTileBlock<T>, T extends BaseTileEntity<T>, C extends TileEntityBaseContainer<C, T>, S extends TileEntityBaseContainerScreen<T, C>, R extends TileEntityRenderer<T>> TileGui<B, T, C, S, R> get(TypedGuiKey<TileGui<B, T, C, S, R>, B, T, C, S, R> key)
    {
        return (TileGui<B, T, C, S, R>) GUI_TILES.get(key);
    }

    public Map<TypedGuiKey<? extends TileGui<? extends BaseTileBlock<?>, ?, ?, ?, ?>, ?, ?, ?, ?, ?>, TileGui<?, ?, ?, ?, ?>> getGUIS()
    {
        return GUI_TILES;
    }

    public static class Builder
    {
        private final Map<TypedGuiKey<? extends TileGui<?, ?, ?, ?, ?>, ?, ?, ?, ?, ?>, TileGui<?, ?, ?, ?, ?>> GUI_TILES = new HashMap<>();

        public <B extends BaseTileBlock<T>, T extends BaseTileEntity<T>, C extends TileEntityBaseContainer<C, T>, S extends TileEntityBaseContainerScreen<T, C>, R extends TileEntityRenderer<T>>
        Builder add(
                TypedGuiKey<? extends TileGui<B, T, C, S, R>, B, T, C, S, R> id,
                TileGui<B, T, C, S, R> tileGui
        )
        {
            GUI_TILES.put(id, tileGui);
            return this;
        }

        public BuilderGuiTileRegistry build()
        {
            return new BuilderGuiTileRegistry(this);
        }


    }
}
