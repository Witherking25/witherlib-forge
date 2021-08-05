package com.withertech.witherlib;

import com.withertech.witherlib.registration.BuilderRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WitherLib.MODID)
public class WitherLib
{
    public static final String MODID = "witherlib";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();


    public static ItemGroup TEST_TAB;
    public static BuilderRegistry<Block> BLOCKS;
    public static BuilderRegistry<Item> ITEMS;


    public WitherLib()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        if (System.getenv().containsKey("witherlib.dev"))
        {
            TEST_TAB = new ItemGroup("witherlib")
            {
                @Nonnull
                @Override
                public ItemStack createIcon()
                {
                    return new ItemStack(ITEMS.get("test_item").get());
                }
            };
            BLOCKS = BuilderRegistry.Builder.create(WitherLib.MODID, Block.class)
                    .add("test_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK)))
                    .build(FMLJavaModLoadingContext.get().getModEventBus());
            ITEMS = BuilderRegistry.Builder.create(WitherLib.MODID, Item.class)
                    .add("test_item", () -> new Item(new Item.Properties().group(TEST_TAB)))
                    .add("test_block_item", () -> new BlockItem(BLOCKS.get("test_block").get(), new Item.Properties().group(TEST_TAB)))
                    .build(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
//        InterModComms.sendTo("witherlib", "helloworld", () ->
//        {
//            return "Hello world";
//        });
    }

    private void processIMC(final InterModProcessEvent event)
    {
//        // some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m -> m.getMessageSupplier().get()).
//                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {

    }
}
