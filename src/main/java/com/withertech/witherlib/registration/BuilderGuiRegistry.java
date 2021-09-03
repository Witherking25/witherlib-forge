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

import java.util.HashMap;
import java.util.Map;

public class BuilderGuiRegistry
{
    private final Map<TypedGuiKey<? extends TileGui<? extends BaseTileBlock<?>, ?, ?, ?>, ?, ?, ?, ?>, TileGui<?, ?, ?, ?>> GUIS;

    private BuilderGuiRegistry(Builder builder)
    {
        GUIS = builder.GUIS;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public boolean containsKey(TypedGuiKey<?, ?, ?, ?, ?> key)
    {
        return GUIS.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <B extends BaseTileBlock<T>, T extends BaseTileEntity<T>, C extends TileEntityBaseContainer<C, T>, S extends TileEntityBaseContainerScreen<T, C>> TileGui<B, T, C, S> get(TypedGuiKey<TileGui<B, T, C, S>, B, T, C, S> key)
    {
        return (TileGui<B, T, C, S>) GUIS.get(key);
    }

    public Map<TypedGuiKey<? extends TileGui<? extends BaseTileBlock<?>, ?, ?, ?>, ?, ?, ?, ?>, TileGui<?, ?, ?, ?>> getGUIS()
    {
        return GUIS;
    }

    public static class Builder
    {
        private final Map<TypedGuiKey<? extends TileGui<?, ?, ?, ?>, ?, ?, ?, ?>, TileGui<?, ?, ?, ?>> GUIS = new HashMap<>();

        public <B extends BaseTileBlock<T>, T extends BaseTileEntity<T>, C extends TileEntityBaseContainer<C, T>, S extends TileEntityBaseContainerScreen<T, C>>
        Builder add(
                TypedGuiKey<? extends TileGui<B, T, C, S>, B, T, C, S> id,
                TileGui<B, T, C, S> tileGui
        )
        {
            GUIS.put(id, tileGui);
            return this;
        }

        public BuilderGuiRegistry build()
        {
            return new BuilderGuiRegistry(this);
        }


    }
}
