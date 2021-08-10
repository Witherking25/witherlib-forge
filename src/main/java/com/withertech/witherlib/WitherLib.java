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

import com.withertech.witherlib.data.BuilderDataGenerator;
import com.withertech.witherlib.registration.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WitherLib.MODID)
public class WitherLib extends BuilderMod
{
    public static final String MODID = "witherlib";
    public static final Logger LOGGER = LogManager.getLogger();
    public static WitherLib INSTANCE;


    public WitherLib()
    {
        super(new ModData(MODID, FMLJavaModLoadingContext.get().getModEventBus()));
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
    }


    @Override
    protected BuilderForgeRegistry<Block> registerBlocks()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.BLOCKS).build();
    }

    @Override
    protected BuilderForgeRegistry<Item> registerItems()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.ITEMS).build();
    }

    @Override
    protected BuilderForgeRegistry<Fluid> registerFluids()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.FLUIDS).build();
    }

    @Override
    protected BuilderForgeRegistry<EntityType<?>> registerEntities()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.ENTITIES).build();
    }

    @Override
    protected BuilderEntityAttributeRegistry registerEntityAttributes()
    {
        return BuilderEntityAttributeRegistry.Builder.create(MOD).build();
    }

    @Override
    protected BuilderEntityRendererRegistry registerEntityRenderers()
    {
        return BuilderEntityRendererRegistry.Builder.create(MOD).build();
    }

    @Override
    protected BuilderDataGenerator registerDataGenerators()
    {
        return BuilderDataGenerator.Builder.create(MOD).build();
    }

    @Override
    protected BuilderTagRegistry registerTags()
    {
        return BuilderTagRegistry.Builder.create(MOD).build();
    }

    @Override
    protected BuilderTabRegistry registerTabs()
    {
        return BuilderTabRegistry.Builder.create(MOD).build();
    }
}
