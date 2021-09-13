package com.withertech.witherlibtest.configs;

import com.withertech.witherlib.config.BaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class CommonConfig extends BaseConfig
{
    public final ForgeConfigSpec.BooleanValue testBoolean;
    public CommonConfig(ForgeConfigSpec.Builder builder)
    {
        super(builder);
        builder.push("common");
        testBoolean = builder.define("testBoolean", true);
        builder.pop();
    }

    @Override
    public ModConfig.Type getType()
    {
        return ModConfig.Type.COMMON;
    }
}
