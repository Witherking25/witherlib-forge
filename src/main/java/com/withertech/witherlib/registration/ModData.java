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

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModData
{
    public final String MODID;
    public final IEventBus MOD_EVENT_BUS;

    public ModData(String modid, IEventBus mod_event_bus)
    {
        MODID = modid;
        MOD_EVENT_BUS = mod_event_bus;
    }

    public ResourceLocation modLocation(String path)
    {
        return new ResourceLocation(MODID, path);
    }
}
