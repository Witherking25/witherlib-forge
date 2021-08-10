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

import net.minecraft.item.ItemGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderTabRegistry
{
    private final Map<String, Supplier<ItemGroup>> ITEM_GROUPS;

    public BuilderTabRegistry(Builder builder)
    {
        ITEM_GROUPS = builder.ITEM_GROUPS;
    }

    public ItemGroup getTab(String key)
    {
        return ITEM_GROUPS.get(key).get();
    }

    public boolean containsKey(String key)
    {
        return ITEM_GROUPS.containsKey(key);
    }

    public static class Builder
    {
        private final String MODID;

        private final Map<String, Supplier<ItemGroup>> ITEM_GROUPS = new HashMap<>();

        private Builder(ModData mod)
        {
            MODID = mod.MODID;
        }

        public static Builder create(ModData mod)
        {
            return new Builder(mod);
        }

        public Builder addGroup(String name, Supplier<ItemGroup> group)
        {
            ITEM_GROUPS.put(name, group);
            return this;
        }

        public BuilderTabRegistry build()
        {
            return new BuilderTabRegistry(this);
        }

    }
}
