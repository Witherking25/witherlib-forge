package com.withertech.witherlib.registration;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderRegistry<T extends IForgeRegistryEntry<T>>
{
    protected final String MODID;
    protected final DeferredRegister<T> REGISTRY;
    protected final Map<String, RegistryObject<T>> ENTRIES;

    protected BuilderRegistry(Builder<T> builder, IEventBus bus)
    {
        MODID = builder.modid;
        REGISTRY = builder.REGISTRY;
        ENTRIES = builder.ENTRIES;
        REGISTRY.register(bus);
    }

    public RegistryObject<T> get(String key)
    {
        return ENTRIES.get(key);
    }

    public static class Builder<T extends IForgeRegistryEntry<T>>
    {
        private final String modid;
        private final DeferredRegister<T> REGISTRY;
        private final Map<String, RegistryObject<T>> ENTRIES = new HashMap<>();

        private Builder(String modid, Class<T> registry)
        {
            this.modid = modid;
            REGISTRY = DeferredRegister.create(registry, modid);
        }

        public static <T extends IForgeRegistryEntry<T>> Builder<T> create(String modid, Class<T> registry)
        {
            return new Builder<>(modid, registry);
        }

        public Builder<T> add(String name, Supplier<T> supplier)
        {
            ENTRIES.put(name, REGISTRY.register(name, supplier));
            return this;
        }

        public BuilderRegistry<T> build(IEventBus bus)
        {
            return new BuilderRegistry<>(this, bus);
        }
    }
}
