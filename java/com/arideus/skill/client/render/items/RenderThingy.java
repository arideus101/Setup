package com.arideus.skill.client.render.items;

import org.lwjgl.opengl.GL11;

import com.arideus.skill.entity.EntityHook;
import com.arideus.skill.entity.EntityHookSpec;
import com.arideus.skill.entity.EntityLaserHeatSeak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderThingy extends Render {
	private static final ResourceLocation Hook = new ResourceLocation("skill:textures/items/hookhead.png");
	private static final ResourceLocation HookSpec = new ResourceLocation("skill:textures/items/spechookhead.png");
	private static final ResourceLocation HeatLaser = new ResourceLocation("skill:textures/items/heatlaser.png");
	private static final String __OBFID = "CL_00000978";

	public RenderThingy() {
		super(Minecraft.getMinecraft().getRenderManager());
	}

	public void doRender(Entity p_180551_1_, double p_180551_2_, double p_180551_4_, double p_180551_6_,
			float p_180551_8_, float p_180551_9_) {
		this.bindEntityTexture(p_180551_1_);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) p_180551_2_, (float) p_180551_4_, (float) p_180551_6_);
		GlStateManager.rotate(p_180551_1_.prevRotationYaw
				+ (p_180551_1_.rotationYaw - p_180551_1_.prevRotationYaw) * p_180551_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(p_180551_1_.prevRotationPitch
				+ (p_180551_1_.rotationPitch - p_180551_1_.prevRotationPitch) * p_180551_9_, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		byte b0 = 0;
		float f2 = 0.0F;
		float f3 = 0.5F;
		float f4 = (float) (0 + b0 * 10) / 32.0F;
		float f5 = (float) (5 + b0 * 10) / 32.0F;
		float f6 = 0.0F;
		float f7 = 0.15625F;
		float f8 = (float) (5 + b0 * 10) / 32.0F;
		float f9 = (float) (10 + b0 * 10) / 32.0F;
		float f10 = 0.05625F;
		GlStateManager.enableRescaleNormal();

		GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(f10, f10, f10);
		GlStateManager.translate(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(f10, 0.0F, 0.0F);
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) f6, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) f7, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) f7, (double) f9);
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) f6, (double) f9);
		tessellator.draw();
		GL11.glNormal3f(-f10, 0.0F, 0.0F);
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) f6, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) f7, (double) f8);
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) f7, (double) f9);
		worldrenderer.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) f6, (double) f9);
		tessellator.draw();

		for (int i = 0; i < 4; ++i) {
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f10);
			worldrenderer.startDrawingQuads();
			worldrenderer.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double) f2, (double) f4);
			worldrenderer.addVertexWithUV(8.0D, -2.0D, 0.0D, (double) f3, (double) f4);
			worldrenderer.addVertexWithUV(8.0D, 2.0D, 0.0D, (double) f3, (double) f5);
			worldrenderer.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double) f2, (double) f5);
			tessellator.draw();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		if (p_180551_1_ instanceof EntityHook)
			this.renderLeash((EntityHook) p_180551_1_, p_180551_2_, p_180551_4_, p_180551_6_, p_180551_8_, p_180551_9_);
		super.doRender(p_180551_1_, p_180551_2_, p_180551_4_, p_180551_6_, p_180551_8_, p_180551_9_);
	}

	private double interpolateValue(double start, double end, double pct) {
		return start + (end - start) * pct;
	}

	protected void renderLeash(EntityHook p_110827_1_, double p_110827_2_, double p_110827_4_, double p_110827_6_,
			float p_110827_8_, float p_110827_9_) {
		Entity entity = p_110827_1_.shootingEntity;

		if (entity != null) {
			p_110827_4_ -= (1.6D - (double) p_110827_1_.height) * 0.5D;
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			double d3 = this.interpolateValue((double) entity.prevRotationYaw, (double) entity.rotationYaw,
					(double) (p_110827_9_ * 0.5F)) * 0.01745329238474369D;
			double d4 = this.interpolateValue((double) entity.prevRotationPitch, (double) entity.rotationPitch,
					(double) (p_110827_9_ * 0.5F)) * 0.01745329238474369D;
			double d5 = Math.cos(d3);
			double d6 = Math.sin(d3);
			double d7 = Math.sin(d4);

			if (entity instanceof EntityHanging) {
				d5 = 0.0D;
				d6 = 0.0D;
				d7 = -1.0D;
			}

			double d8 = Math.cos(d4);
			double d9 = this.interpolateValue(entity.prevPosX, entity.posX, (double) p_110827_9_) - d5 * 0.7D
					- d6 * 0.5D * d8;
			double d10 = this.interpolateValue(entity.prevPosY + (double) entity.getEyeHeight() * 0.7D,
					entity.posY + (double) entity.getEyeHeight() * 0.7D, (double) p_110827_9_) - d7 * 0.5D - 0.25D;
			double d11 = this.interpolateValue(entity.prevPosZ, entity.posZ, (double) p_110827_9_) - d6 * 0.7D
					+ d5 * 0.5D * d8;
			double d12 = this.interpolateValue((double) p_110827_1_.rotationYaw, (double) p_110827_1_.prevRotationYaw,
					(double) p_110827_9_) * 0.01745329238474369D + (Math.PI / 2D);
			d5 = Math.cos(d12) * (double) p_110827_1_.width * 0.4D;
			d6 = Math.sin(d12) * (double) p_110827_1_.width * 0.4D;
			double d13 = this.interpolateValue(p_110827_1_.prevPosX, p_110827_1_.posX, (double) p_110827_9_) + d5;
			double d14 = this.interpolateValue(p_110827_1_.prevPosY, p_110827_1_.posY, (double) p_110827_9_);
			double d15 = this.interpolateValue(p_110827_1_.prevPosZ, p_110827_1_.posZ, (double) p_110827_9_) + d6;
			p_110827_2_ += d5;
			p_110827_6_ += d6;
			double d16 = (double) ((float) (d9 - d13));
			double d17 = (double) ((float) (d10 - d14));
			double d18 = (double) ((float) (d11 - d15));
			GlStateManager.disableTexture2D();
			GlStateManager.disableLighting();
			GlStateManager.disableCull();
			boolean flag = true;
			double d19 = 0.025D;
			worldrenderer.startDrawing(5);
			int i;
			float f2;

			for (i = 0; i <= 24; ++i) {
				if (i % 2 == 0) {
					worldrenderer.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
				} else {
					worldrenderer.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
				}

				f2 = (float) i / 24.0F;
				worldrenderer
						.addVertex(p_110827_2_ + d16 * (double) f2 + 0.0D,
								p_110827_4_ + d17 * (double) (f2 * f2 + f2) * 0.5D
										+ (double) ((24.0F - (float) i) / 18.0F + 0.125F),
						p_110827_6_ + d18 * (double) f2);
				worldrenderer.addVertex(p_110827_2_ + d16 * (double) f2 + 0.025D,
						p_110827_4_ + d17 * (double) (f2 * f2 + f2) * 0.5D
								+ (double) ((24.0F - (float) i) / 18.0F + 0.125F) + 0.025D,
						p_110827_6_ + d18 * (double) f2);
			}

			tessellator.draw();
			worldrenderer.startDrawing(5);

			for (i = 0; i <= 24; ++i) {
				if (i % 2 == 0) {
					worldrenderer.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
				} else {
					worldrenderer.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
				}

				f2 = (float) i / 24.0F;
				worldrenderer.addVertex(p_110827_2_ + d16 * (double) f2 + 0.0D,
						p_110827_4_ + d17 * (double) (f2 * f2 + f2) * 0.5D
								+ (double) ((24.0F - (float) i) / 18.0F + 0.125F) + 0.025D,
						p_110827_6_ + d18 * (double) f2);
				worldrenderer.addVertex(p_110827_2_ + d16 * (double) f2 + 0.025D,
						p_110827_4_ + d17 * (double) (f2 * f2 + f2) * 0.5D
								+ (double) ((24.0F - (float) i) / 18.0F + 0.125F),
						p_110827_6_ + d18 * (double) f2 + 0.025D);
			}

			tessellator.draw();
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
			GlStateManager.enableCull();
		}
	}

	protected ResourceLocation getEntityTexture(Entity ER) {
		if (ER instanceof EntityHookSpec)
			return HookSpec;
		if (ER instanceof EntityLaserHeatSeak)
			return HeatLaser;
		return Hook;
	}
}
