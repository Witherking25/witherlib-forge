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

import net.minecraft.entity.ai.attributes.AttributeModifierMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderEntityAttributeRegistry
{
    private final Map<String, Supplier<AttributeModifierMap.MutableAttribute>> ENTITIES;

    public BuilderEntityAttributeRegistry(Builder builder)
    {
        ENTITIES = builder.ENTITIES;
    }

    public AttributeModifierMap.MutableAttribute getEntity(String key)
    {
        return ENTITIES.get(key).get();
    }

    public boolean containsKey(String key)
    {
        return ENTITIES.containsKey(key);
    }

    public static class Builder
    {
        private final String MODID;

        private final Map<String, Supplier<AttributeModifierMap.MutableAttribute>> ENTITIES = new HashMap<>();

        private Builder(ModData mod)
        {
            MODID = mod.MODID;
        }

        public static Builder create(ModData mod)
        {
            return new Builder(mod);
        }

        public Builder addEntity(String name, Supplier<AttributeModifierMap.MutableAttribute> attributes)
        {
            ENTITIES.put(name, attributes);
            return this;
        }

        public BuilderEntityAttributeRegistry build()
        {
            return new BuilderEntityAttributeRegistry(this);
        }

    }
}
