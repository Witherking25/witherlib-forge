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

package com.withertech.witherlibtest.entities;

import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.items.TestItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestEntity extends Animal
{
    private EatBlockGoal eatGrassGoal;
    private int eatAnimationTick;

    public TestEntity(EntityType<? extends Animal> type, Level world)
    {
        super(type, world);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@Nonnull ServerLevel world, @Nonnull AgeableMob ageable)
    {
        TestEntity entity = WitherLibTest.INSTANCE.REGISTRY.getEntity(TypedRegKey.entity("test_entity", TestEntity.class)).get().create(this.level);
        assert entity != null;
        entity.finalizeSpawn((ServerLevelAccessor) this.level, this.level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.eatGrassGoal = new EatBlockGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, Ingredient.of(WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_item", TestItem.class)).get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
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
            return ((float) Math.PI / 5F) + 0.21991149F * Mth.sin(f * 28.7F);
        }
        else
        {
            return this.eatAnimationTick > 0 ? ((float) Math.PI / 5F) : this.getXRot() * ((float) Math.PI / 180F);
        }
    }
}
