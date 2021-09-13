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

package com.withertech.witherlibtest.client.entity.renderer;


import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.client.entity.model.TestEntityModel;
import com.withertech.witherlibtest.entities.TestEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TestEntityRenderer extends MobRenderer<TestEntity, TestEntityModel<TestEntity>>
{
    protected static final ResourceLocation TEXTURE = WitherLibTest.INSTANCE.MOD.modLocation("textures/entity/test_entity.png");

    public TestEntityRenderer(EntityRendererManager entityRendererManager)
    {
        super(entityRendererManager, new TestEntityModel<>(), 0.5f);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull TestEntity entity)
    {
        return TEXTURE;
    }
}
