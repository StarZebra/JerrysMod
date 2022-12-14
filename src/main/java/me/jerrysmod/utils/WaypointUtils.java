package me.jerrysmod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class WaypointUtils {
	
	private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
	
	private static void renderBeaconBeam(
			double x, double y, double z, int rgb, float alphaMult,
			float partialTicks, Boolean disableDepth
	) {
		int height = 300;
		int bottomOffset = 0;
		int topOffset = bottomOffset + height;
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
		if (disableDepth) {
			GlStateManager.disableDepth();
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(beaconBeam);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GlStateManager.disableLighting();
		GlStateManager.enableCull();
		GlStateManager.enableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		
		double time = Minecraft.getMinecraft().theWorld.getTotalWorldTime() + (double) partialTicks;
		double d1 = MathHelper.func_181162_h(-time * 0.2D - (double) MathHelper.floor_double(-time * 0.1D));
		
		float r = ((rgb >> 16) & 0xFF) / 255f;
		float g = ((rgb >> 8) & 0xFF) / 255f;
		float b = (rgb & 0xFF) / 255f;
		double d2 = time * 0.025D * -1.5D;
		double d4 = 0.5D + Math.cos(d2 + 2.356194490192345D) * 0.2D;
		double d5 = 0.5D + Math.sin(d2 + 2.356194490192345D) * 0.2D;
		double d6 = 0.5D + Math.cos(d2 + (Math.PI / 4D)) * 0.2D;
		double d7 = 0.5D + Math.sin(d2 + (Math.PI / 4D)) * 0.2D;
		double d8 = 0.5D + Math.cos(d2 + 3.9269908169872414D) * 0.2D;
		double d9 = 0.5D + Math.sin(d2 + 3.9269908169872414D) * 0.2D;
		double d10 = 0.5D + Math.cos(d2 + 5.497787143782138D) * 0.2D;
		double d11 = 0.5D + Math.sin(d2 + 5.497787143782138D) * 0.2D;
		double d14 = -1.0D + d1;
		double d15 = (double) (height) * 2.5D + d14;
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(1.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(0.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(1.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(0.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		worldrenderer.pos(x + d6, y + topOffset, z + d7).tex(1.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		worldrenderer.pos(x + d6, y + bottomOffset, z + d7).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d10, y + bottomOffset, z + d11).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d10, y + topOffset, z + d11).tex(0.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		worldrenderer.pos(x + d8, y + topOffset, z + d9).tex(1.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		worldrenderer.pos(x + d8, y + bottomOffset, z + d9).tex(1.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d4, y + bottomOffset, z + d5).tex(0.0D, d14).color(r, g, b, 1.0F).endVertex();
		worldrenderer.pos(x + d4, y + topOffset, z + d5).tex(0.0D, d15).color(r, g, b, 1.0F * alphaMult).endVertex();
		tessellator.draw();
		
		GlStateManager.disableCull();
		double d12 = -1.0D + d1;
		double d13 = height + d12;
		
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.2D).tex(1.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.2D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.2D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.2D).tex(0.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.8D).tex(1.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.8D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.8D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.8D).tex(0.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.2D).tex(1.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.2D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + bottomOffset, z + 0.8D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.8D, y + topOffset, z + 0.8D).tex(0.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.8D).tex(1.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.8D).tex(1.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + bottomOffset, z + 0.2D).tex(0.0D, d12).color(r, g, b, 0.25F).endVertex();
		worldrenderer.pos(x + 0.2D, y + topOffset, z + 0.2D).tex(0.0D, d13).color(r, g, b, 0.25F * alphaMult).endVertex();
		tessellator.draw();
		
		GlStateManager.disableLighting();
		GlStateManager.enableTexture2D();
		if (disableDepth) {
			GlStateManager.enableDepth();
		}
	}
	
	public static void renderBeaconBeamOrBoundingBox(BlockPos block, int rgb, float alphaMult, float partialTicks) {
		double viewerX;
		double viewerY;
		double viewerZ;
		
		Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
		viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
		viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
		viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;
		
		
		double x = block.getX() - viewerX;
		double y = block.getY() - viewerY;
		double z = block.getZ() - viewerZ;
		
		double distSq = x * x + y * y + z * z;
		
		if (distSq > 10 * 10) {
			WaypointUtils.renderBeaconBeam(x, y, z, rgb, 1.0f, partialTicks, true);
		}
	}
	
	public static void renderBeaconBeam(BlockPos block, int rgb, float alphaMult, float partialTicks) {
		double viewerX;
		double viewerY;
		double viewerZ;
		
		
		Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
		viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
		viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
		viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;
		
		double x = block.getX() - viewerX;
		double y = block.getY() - viewerY;
		double z = block.getZ() - viewerZ;
		
		double distSq = x * x + y * y + z * z;
		
		WaypointUtils.renderBeaconBeam(x, y, z, rgb, 1.0f, partialTicks, distSq > 10 * 10);
	}
	
	public static void renderWayPoint(String str, BlockPos loc, float partialTicks) {
		renderWayPoint(str, new Vector3f(loc.getX(), loc.getY(), loc.getZ()), partialTicks);
	}
	
	public static void renderWayPoint(String str, Vector3f loc, float partialTicks) {
		GlStateManager.alphaFunc(516, 0.1F);
		
		GlStateManager.pushMatrix();
		
		Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
		double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
		double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
		double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;
		
		double x = loc.x - viewerX + 0.5f;
		double y = loc.y - viewerY - viewer.getEyeHeight();
		double z = loc.z - viewerZ + 0.5f;
		
		double distSq = x * x + y * y + z * z;
		double dist = Math.sqrt(distSq);
		if (distSq > 144) {
			x *= 12 / dist;
			y *= 12 / dist;
			z *= 12 / dist;
		}
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(0, viewer.getEyeHeight(), 0);
		
		renderNametag(str);
		
		GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0, -0.25f, 0);
		GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		
		renderNametag(EnumChatFormatting.YELLOW.toString() + Math.round(dist) + "m");
		
		GlStateManager.popMatrix();
		
		GlStateManager.disableLighting();
	}
	
	public static void renderNametag(String str) {
		FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
		float f = 1.6F;
		float f1 = 0.016666668F * f;
		GlStateManager.pushMatrix();
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-f1, -f1, f1);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GlStateManager.disableDepth();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		int i = 0;
		
		int j = fontrenderer.getStringWidth(str) / 2;
		GlStateManager.disableTexture2D();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(-j - 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos(-j - 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos(j + 1, 8 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos(j + 1, -1 + i, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
		GlStateManager.depthMask(true);
		
		fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
		
		GlStateManager.enableDepth();
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
