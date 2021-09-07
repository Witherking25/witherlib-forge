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
import com.withertech.witherlib.config.BaseConfig;
import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.gui.TileEntityBaseContainerScreen;
import com.withertech.witherlib.gui.TileGui;
import com.withertech.witherlib.tile.BaseTileEntity;
import com.withertech.witherlib.util.ClassUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

public class TypedRegKey<X>
{
	private final Class<X> type;
	private final String id;

	private TypedRegKey(String id, Class<X> type)
	{
		this.id = id;
		this.type = type;
	}

	public static <X extends IForgeRegistryEntry<X>> TypedRegKey<X> registry(String id, Class<X> type)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends IForgeRegistryEntry<X>> TypedRegKey<RegistryObject<X>> custom(String id, Class<X> type)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends Block> TypedRegKey<RegistryObject<X>> block(String id, Class<X> type)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends Item> TypedRegKey<RegistryObject<X>> item(String id, Class<X> type)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends Fluid> TypedRegKey<RegistryObject<X>> fluid(String id, Class<X> type)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends Entity> TypedRegKey<RegistryObject<EntityType<X>>> entity(String id, Class<X> type)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends BaseTileEntity<X>> TypedRegKey<RegistryObject<TileEntityType<X>>> tile(
			String id,
			Class<X> type
	)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends TileEntityBaseContainer<X, ?>> TypedRegKey<RegistryObject<ContainerType<X>>> container(
			String id,
			Class<X> type
	)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends BaseConfig> TypedRegKey<X> config(String id, Class<X> type)
	{
		return new TypedRegKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public static <X extends TileGui<B, T, C, S>, B extends BaseTileBlock<T>, T extends BaseTileEntity<T>,
			C extends TileEntityBaseContainer<C, T>, S extends TileEntityBaseContainerScreen<T, C>> TypedGuiKey<X, B,
			T, C, S> gui(
			String id,
			Class<X> type
	)
	{
		return new TypedGuiKey<>(id, ClassUtil.castClass(TypedRegKey.class));
	}

	public Class<X> getType()
	{
		return type;
	}

	public String getId()
	{
		return id;
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

	public static class TypedGuiKey<X extends TileGui<B, T, C, S>, B extends BaseTileBlock<T>,
			T extends BaseTileEntity<T>, C extends TileEntityBaseContainer<C, T>,
			S extends TileEntityBaseContainerScreen<T, C>>
			extends TypedRegKey<X>
	{
		private TypedGuiKey(String id, Class<X> type)
		{
			super(id, type);
		}
	}
}
