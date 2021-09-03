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

package com.withertech.witherlib;

import com.withertech.witherlib.registration.BuilderMod;
import com.withertech.witherlib.registration.ModData;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The WitherLib Mod class
 */
@Mod(WitherLib.MODID)
public class WitherLib extends BuilderMod
{
    /**
     * The MODID.
     */
    public static final String MODID = "witherlib";
    /**
     * The LOGGER.
     */
    public static final Logger LOGGER = LogManager.getLogger();
    /**
     * The Singleton Instance
     */
    public static WitherLib INSTANCE;

    /**
     * The Mod Constructor
     */
    public WitherLib()
    {
        super(new ModData(MODID, FMLJavaModLoadingContext.get().getModEventBus()));
        INSTANCE = this;
    }
}
