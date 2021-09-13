package com.withertech.witherlibtest.registration;

import net.minecraftforge.registries.ForgeRegistryEntry;

public class TestRegistryEntry extends ForgeRegistryEntry<TestRegistryEntry>
{
    public final Boolean test;

    public TestRegistryEntry(Boolean test)
    {
        this.test = test;
    }
}
