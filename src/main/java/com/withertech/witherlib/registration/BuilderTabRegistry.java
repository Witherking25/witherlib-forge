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

public class BuilderTabRegistry
{
    private final Map<String, ItemGroup> ITEM_GROUPS;

    private BuilderTabRegistry(Builder builder)
    {
        ITEM_GROUPS = builder.ITEM_GROUPS;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public ItemGroup getTab(String key)
    {
        return ITEM_GROUPS.get(key);
    }

    public boolean containsKey(String key)
    {
        return ITEM_GROUPS.containsKey(key);
    }

    public static class Builder
    {
        private final Map<String, ItemGroup> ITEM_GROUPS = new HashMap<>();

        private Builder()
        {

        }

        public Builder add(String name, ItemGroup group)
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
