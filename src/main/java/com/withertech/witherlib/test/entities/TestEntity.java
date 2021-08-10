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

package com.withertech.witherlib.test.entities;

import com.withertech.witherlib.WitherLib;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestEntity extends AnimalEntity
{
    private EatGrassGoal eatGrassGoal;
    private int eatAnimationTick;

    public TestEntity(EntityType<? extends AnimalEntity> type, World world)
    {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes()
    {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.23F);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(@Nonnull ServerWorld world, @Nonnull AgeableEntity ageable)
    {
        TestEntity entity = (TestEntity) WitherLib.INSTANCE.REGISTRY.getEntity("test_entity").get().create(this.level);
        assert entity != null;
        entity.finalizeSpawn((IServerWorld) this.level, this.level.getCurrentDifficultyAt(entity.blockPosition()), SpawnReason.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(WitherLib.INSTANCE.REGISTRY.getItem("test_item").get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    public void customServerAiStep()
    {
        this.eatAnimationTick = this.eatGrassGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep()
    {
        if (this.level.isClientSide)
        {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }

        super.aiStep();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_)
    {
        if (p_70103_1_ == 10)
        {
            this.eatAnimationTick = 40;
        }
        else
        {
            super.handleEntityEvent(p_70103_1_);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadEatPositionScale(float p_70894_1_)
    {
        if (this.eatAnimationTick <= 0)
        {
            return 0.0F;
        }
        else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36)
        {
            return 1.0F;
        }
        else
        {
            return this.eatAnimationTick < 4 ? ((float) this.eatAnimationTick - p_70894_1_) / 4.0F : -((float) (this.eatAnimationTick - 40) - p_70894_1_) / 4.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadEatAngleScale(float p_70890_1_)
    {
        if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36)
        {
            float f = ((float) (this.eatAnimationTick - 4) - p_70890_1_) / 32.0F;
            return ((float) Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.eatAnimationTick > 0 ? ((float) Math.PI / 5F) : this.xRot * ((float) Math.PI / 180F);
        }
    }
}
