package me.jerrysmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils {
	
	public static void drawOutlinedBoundingBox(AxisAlignedBB aabb, int colourInt, float partialTicks) {
		Entity render = Minecraft.getMinecraft().getRenderViewEntity();
		Color colour = new Color(colourInt);
		
		double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
		double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
		double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;
		
		GlStateManager.disableCull();
		GlStateManager.disableDepth();
		GlStateManager.pushMatrix();
		GlStateManager.translate(-realX, -realY, -realZ);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glLineWidth(2);
		
		RenderGlobal.drawOutlinedBoundingBox(aabb, colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha());
		
		GlStateManager.translate(realX, realY, realZ);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
		GlStateManager.enableDepth();
		GlStateManager.enableCull();
	}
	
	public static void outlineBlock(BlockPos pos, Color color, float partialTicks) {
		drawOutlinedBoundingBox(new AxisAlignedBB(
				pos.getX(),
				pos.getY(),
				pos.getZ(),
				pos.getX() + 1,
				pos.getY() + 1,
				pos.getZ() + 1
		), color.getRGB(), partialTicks);
	}
	
	public static void drawFilledBoundingBox(AxisAlignedBB aabb, Color c, float alphaMultiplier) {
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.disableTexture2D();
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
		GlStateManager.color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f * alphaMultiplier);
		
		//vertical
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		tessellator.draw();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		tessellator.draw();
		
		
		GlStateManager.color(c.getRed() / 255f * 0.8f, c.getGreen() / 255f * 0.8f, c.getBlue() / 255f * 0.8f, c.getAlpha() / 255f * alphaMultiplier);
		
		//x
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		tessellator.draw();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		tessellator.draw();
		
		
		GlStateManager.color(c.getRed() / 255f * 0.9f, c.getGreen() / 255f * 0.9f, c.getBlue() / 255f * 0.9f, c.getAlpha() / 255f * alphaMultiplier);
		//z
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		tessellator.draw();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
}
