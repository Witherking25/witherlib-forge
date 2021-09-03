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

package com.withertech.witherlib.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.OptionalDouble;

/**
 * Created 6/12/2021 by SuperMartijn642
 */
public class RenderUtils
{

    private static final RenderState.LayerState VIEW_OFFSET_Z_LAYERING = new RenderState.LayerState("view_offset_z_layering", () ->
    {
        RenderSystem.pushMatrix();
        RenderSystem.scalef(0.99975586F, 0.99975586F, 0.99975586F);
    }, RenderSystem::popMatrix);
    private static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY = new RenderState.TransparencyState("translucent_transparency", () ->
    {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () ->
    {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    private static final RenderType LINES_NO_DEPTH = RenderType.create(
            "witherlib:highlight",
            DefaultVertexFormats.POSITION_COLOR,
            GL11.GL_LINES,
            128,
            RenderType.State.builder().setLineState(new RenderState.LineState(OptionalDouble.of(1))).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLayeringState(VIEW_OFFSET_Z_LAYERING).setWriteMaskState(new RenderState.WriteMaskState(true, false)).setDepthTestState(new RenderState.DepthTestState("no_depth", GL11.GL_ALWAYS)).createCompositeState(false));
    private static final IRenderTypeBuffer.Impl LINE_BUFFER = IRenderTypeBuffer.immediate(new BufferBuilder(128));

    public static void enableDepthTest()
    {
        GlStateManager._enableDepthTest();
    }

    public static void disableDepthTest()
    {
        GlStateManager._disableDepthTest();
    }

    /**
     * @return the current interpolated camera position.
     */
    public static Vector3d getCameraPosition()
    {
        return ClientUtils.getMinecraft().getEntityRenderDispatcher().camera.getPosition();
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, BlockShape shape, double x, double y, double z, float red, float green, float blue, float alpha)
    {
        matrixStack.pushPose();

        Vector3d camera = getCameraPosition();
        matrixStack.translate(x - camera.x, y - camera.y, z - camera.z);

        IVertexBuilder builder = LINE_BUFFER.getBuffer(LINES_NO_DEPTH);
        Matrix4f matrix4f = matrixStack.last().pose();
        shape.forEachEdge((x1, y1, z1, x2, y2, z2) ->
        {
            builder.vertex(matrix4f, (float) x1, (float) y1, (float) z1).color(red, green, blue, alpha).endVertex();
            builder.vertex(matrix4f, (float) x2, (float) y2, (float) z2).color(red, green, blue, alpha).endVertex();
        });
        LINE_BUFFER.endBatch();

        matrixStack.popPose();
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, VoxelShape shape, double x, double y, double z, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, BlockShape.create(shape), x, y, z, red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given box.
     */
    public static void renderBox(MatrixStack matrixStack, AxisAlignedBB box, double x, double y, double z, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, BlockShape.create(box), x, y, z, red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, BlockShape shape, BlockPos pos, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, shape, pos.getX(), pos.getY(), pos.getZ(), red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, VoxelShape shape, BlockPos pos, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, BlockShape.create(shape), pos.getX(), pos.getY(), pos.getZ(), red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given box.
     */
    public static void renderBox(MatrixStack matrixStack, AxisAlignedBB box, BlockPos pos, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, BlockShape.create(box), pos.getX(), pos.getY(), pos.getZ(), red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, BlockShape shape, double x, double y, double z, float red, float green, float blue)
    {
        renderShape(matrixStack, shape, x, y, z, red, green, blue, 1);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, VoxelShape shape, double x, double y, double z, float red, float green, float blue)
    {
        renderShape(matrixStack, BlockShape.create(shape), x, y, z, red, green, blue, 1);
    }

    /**
     * Draws an outline for the given box.
     */
    public static void renderBox(MatrixStack matrixStack, AxisAlignedBB box, double x, double y, double z, float red, float green, float blue)
    {
        renderShape(matrixStack, BlockShape.create(box), x, y, z, red, green, blue, 1);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, BlockShape shape, BlockPos pos, float red, float green, float blue)
    {
        renderShape(matrixStack, shape, pos.getX(), pos.getY(), pos.getZ(), red, green, blue, 1);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, VoxelShape shape, BlockPos pos, float red, float green, float blue)
    {
        renderShape(matrixStack, BlockShape.create(shape), pos.getX(), pos.getY(), pos.getZ(), red, green, blue);
    }

    /**
     * Draws an outline for the given box.
     */
    public static void renderBox(MatrixStack matrixStack, AxisAlignedBB box, BlockPos pos, float red, float green, float blue)
    {
        renderShape(matrixStack, BlockShape.create(box), pos.getX(), pos.getY(), pos.getZ(), red, green, blue);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, BlockShape shape, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, shape, 0, 0, 0, red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, VoxelShape shape, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, BlockShape.create(shape), 0, 0, 0, red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given box.
     */
    public static void renderBox(MatrixStack matrixStack, AxisAlignedBB box, float red, float green, float blue, float alpha)
    {
        renderShape(matrixStack, BlockShape.create(box), 0, 0, 0, red, green, blue, alpha);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, BlockShape shape, float red, float green, float blue)
    {
        renderShape(matrixStack, shape, 0, 0, 0, red, green, blue, 1);
    }

    /**
     * Draws an outline for the given shape.
     */
    public static void renderShape(MatrixStack matrixStack, VoxelShape shape, float red, float green, float blue)
    {
        renderShape(matrixStack, BlockShape.create(shape), 0, 0, 0, red, green, blue, 1);
    }

    /**
     * Draws an outline for the given box.
     */
    public static void renderBox(MatrixStack matrixStack, AxisAlignedBB box, float red, float green, float blue)
    {
        renderShape(matrixStack, BlockShape.create(box), 0, 0, 0, red, green, blue, 1);
    }

}
