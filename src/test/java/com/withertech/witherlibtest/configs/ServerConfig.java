package com.withertech.witherlibtest.configs;

import com.withertech.witherlib.config.BaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ServerConfig extends BaseConfig
{
    public final ForgeConfigSpec.BooleanValue testBoolean;
    public ServerConfig(ForgeConfigSpec.Builder builder)
    {
        super(builder);
        builder.push("server");
        testBoolean = builder.define("testBoolean", true);
        builder.pop();
    }

    @Override
    public ModConfig.Type getType()
    {
        return ModConfig.Type.SERVER;
    }
}
