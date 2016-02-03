package com.arideus.skill.client.render.entity;

import com.arideus.skill.entity.EntityShieldMagic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShield extends Render {
	protected final Item field_177084_a;
	private final RenderItem field_177083_e;
	private static final String __OBFID = "CL_00001008";

	public RenderShield(Item p_i46137_2_, RenderItem p_i46137_3_) {
		super(Minecraft.getMinecraft().getRenderManager());
		this.field_177084_a = p_i46137_2_;
		this.field_177083_e = p_i46137_3_;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y + entity.height / 2, (float) z);
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		float rotX = (float) (((EntityShieldMagic) entity).getRot1());
		GlStateManager.rotate(rotX, 0.0F, 1.0F, 0.0F);
		float thr = (float) ((EntityShieldMagic) entity).getThrowing();
		if (4 * Math.sqrt(Math.abs(thr)) > 90)
			thr = 1012.5f;
		if (!((EntityShieldMagic) entity).getPlayer().isSprinting() || ((EntityShieldMagic) entity).getLaunching() != 0)
			GlStateManager.rotate(4 * (float) Math.sqrt(Math.abs(thr / 2)), -1.0F, 0.0F, 0.0F);
		if (((EntityShieldMagic) entity).getLaunching() != 0)
			GlStateManager.rotate((entity.worldObj.getTotalWorldTime() % 24) * 15, 0.0F, 0.0F, 1.0F);

		GlStateManager.scale(2, 2, 2);
		this.bindTexture(TextureMap.locationBlocksTexture);
		this.field_177083_e.renderItemModel(this.func_177082_d(entity));
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y + entity.height / 2, z, p_76986_8_, partialTicks);
	}

	public ItemStack func_177082_d(Entity p_177082_1_) {
		return new ItemStack(this.field_177084_a, 1, 0);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.locationBlocksTexture;
	}
}