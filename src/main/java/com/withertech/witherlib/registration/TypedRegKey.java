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
import com.withertech.witherlib.block.BaseTileEntity;
import com.withertech.witherlib.gui.GuiTile;
import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.gui.TileEntityBaseContainerScreen;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.Objects;

public class TypedRegKey<X>
{
    protected final Class<X> type;
    protected final String id;

    private TypedRegKey(String id, Class<X> type)
    {
        this.id = id;
        this.type = type;
    }

    public Class<X> getType()
    {
        return type;
    }

    public String getId()
    {
        return id;
    }

    @SuppressWarnings("unchecked")
    public static <X> Class<X> castClass(Class<?> clazz)
    {
        return (Class<X>) clazz;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof TypedRegKey))
        {
            return false;
        }
        TypedRegKey<?> that = (TypedRegKey<?>) o;
        return getType().equals(that.getType()) && getId().equals(that.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(type, id);
    }

    public static <X extends Block> TypedRegKey<RegistryObject<X>> block(String id, Class<X> clazz)
    {
        return new TypedRegKey<>(id, castClass(TypedRegKey.class));
    }

    public static <X extends Item> TypedRegKey<RegistryObject<X>> item(String id, Class<X> clazz)
    {
        return new TypedRegKey<>(id, castClass(TypedRegKey.class));
    }

    public static <X extends Fluid> TypedRegKey<RegistryObject<X>> fluid(String id, Class<X> clazz)
    {
        return new TypedRegKey<>(id, castClass(TypedRegKey.class));
    }

    public static <X extends BaseTileBlock> TypedRegKey<RegistryObject<X>> tileBlock(String id, Class<X> clazz)
    {
        return new TypedRegKey<>(id, castClass(TypedRegKey.class));
    }

    public static <X extends BaseTileEntity> TypedRegKey<RegistryObject<TileEntityType<X>>> tile(String id, Class<X> clazz)
    {
        return new TypedRegKey<>(id, castClass(TypedRegKey.class));
    }

    public static <X extends TileEntityBaseContainer<?>> TypedRegKey<RegistryObject<ContainerType<X>>> container(String id, Class<X> clazz)
    {
        return new TypedRegKey<>(id, castClass(TypedRegKey.class));
    }

    public static <X extends GuiTile<B, T, C, S, R>, B extends BaseTileBlock, T extends BaseTileEntity, C extends TileEntityBaseContainer<T>, S extends TileEntityBaseContainerScreen<T, C>, R extends TileEntityRenderer<T>> TypedGuiKey<X, B, T, C, S, R> gui(String id, Class<X> clazz)
    {
        return new TypedGuiKey<>(id, castClass(TypedRegKey.class));
    }

    public static class TypedGuiKey<X extends GuiTile<B, T, C, S, R>, B extends BaseTileBlock, T extends BaseTileEntity, C extends TileEntityBaseContainer<T>, S extends TileEntityBaseContainerScreen<T, C>, R extends TileEntityRenderer<T>> extends TypedRegKey<X>
    {
        private TypedGuiKey(String id, Class<X> type)
        {
            super(id, type);
        }
    }
}
