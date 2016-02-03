package com.arideus.skill;

import java.util.Random;

import com.arideus.skill.items.ItemArmorLegs;
import com.arideus.skill.items.ItemGun;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {
	protected static final ResourceLocation scope = new ResourceLocation("skill:overlay/scope.png");
	private long field_175191_F = 0;
	private long updateCounter = 0;
	private int field_175194_C = 0;
	private long lastSystemTime = 0;
	private int field_175189_D = 0;

	@SubscribeEvent
	public void onEntityTick(LivingUpdateEvent event) {
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (event.source.damageType.contains("bullet") && event.source.getEntity() instanceof EntityPlayer) {
			if (((EntityPlayer) event.source.getEntity()).getHeldItem() != null) {
				ItemStack stack = ((EntityPlayer) event.source.getEntity()).getHeldItem();
				if (stack.getItem() instanceof ItemGun) {
					int e = stack.getTagCompound().getInteger("Exp");
					int l = stack.getTagCompound().getInteger("Level");
					if (l < 5) {
						if (e >= 8 * (l + 1) - 1) {
							stack.getTagCompound().setInteger("Exp", 0);
							stack.getTagCompound().setInteger("Level", l + 1);
						} else {
							stack.getTagCompound().setInteger("Exp", e + 1);
						}
					}
				}
			}
		}
		if (event.source.damageType.contains("dragonskin") && event.source.getEntity() instanceof EntityPlayer) {
			((EntityPlayer) event.source.getEntity())
					.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 60, 5));
			((EntityPlayer) event.source.getEntity())
					.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 60, 5));
		}
	}

	public void drawTexturedModalRect(double zLevel, int x, int y, int textureX, int textureY, int width, int height) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV((double) (x + 0), (double) (y + height), (double) zLevel,
				(double) ((float) (textureX + 0) * f), (double) ((float) (textureY + height) * f1));
		worldrenderer.addVertexWithUV((double) (x + width), (double) (y + height), (double) zLevel,
				(double) ((float) (textureX + width) * f), (double) ((float) (textureY + height) * f1));
		worldrenderer.addVertexWithUV((double) (x + width), (double) (y + 0), (double) zLevel,
				(double) ((float) (textureX + width) * f), (double) ((float) (textureY + 0) * f1));
		worldrenderer.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel,
				(double) ((float) (textureX + 0) * f), (double) ((float) (textureY + 0) * f1));
		tessellator.draw();
	}

	private static final ResourceLocation FORCEFIELD = new ResourceLocation("skill:textures/gui/icons.png");
	private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
	private static final ResourceLocation ICONS2 = new ResourceLocation("skill:textures/gui/icons2.png");

	@SubscribeEvent
	public void onOverlay(RenderGameOverlayEvent.Post event) {
		Random rand = new Random();
		GuiIngameForge.renderHealth = false;
		GuiIngameForge.renderArmor = false;
		if (event.isCancelable() || event.type != ElementType.BOSSHEALTH) {
			return;
		}

		GlStateManager.enableBlend();

		Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS2);
		EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().getRenderViewEntity();
		int health = MathHelper.ceiling_float_int(player.getHealth());
		boolean highlight = field_175191_F > (long) updateCounter
				&& (field_175191_F - (long) updateCounter) / 3L % 2L == 1L;
		updateCounter++;
		if (health < field_175194_C && player.hurtResistantTime > 0) {
			lastSystemTime = Minecraft.getSystemTime();
			field_175191_F = (long) (updateCounter + 20);
		} else if (health > field_175194_C && player.hurtResistantTime > 0) {
			lastSystemTime = Minecraft.getSystemTime();
			field_175191_F = (long) (updateCounter + 10);
		}

		if (Minecraft.getSystemTime() - lastSystemTime > 1000L) {
			field_175194_C = health;
			field_175189_D = health;
			lastSystemTime = Minecraft.getSystemTime();
		}

		field_175194_C = health;
		int healthLast = field_175189_D;

		IAttributeInstance attrMaxHealth = player.getEntityAttribute(SharedMonsterAttributes.maxHealth);
		float healthMax = (float) attrMaxHealth.getAttributeValue();
		float absorb = player.getAbsorptionAmount();

		int healthRows = MathHelper.ceiling_float_int((healthMax + absorb) / 2.0F / 10.0F);
		int rowHeight = Math.max(10 - (healthRows - 2), 3);

		rand.setSeed((long) (updateCounter * 312871));
		int width = event.resolution.getScaledWidth();
		int height = event.resolution.getScaledHeight();
		int left_height = 39;
		int left = width / 2 - 91;
		int top = height - left_height;
		left_height += (healthRows * rowHeight);
		if (rowHeight != 10)
			left_height += 10 - rowHeight;

		long regen = -1;
		if (player.isPotionActive(Potion.regeneration)) {
			regen = updateCounter % 25;
		}

		final int TOP = 9 * (Minecraft.getMinecraft().theWorld.getWorldInfo().isHardcoreModeEnabled() ? 5 : 0);
		final int BACKGROUND = (highlight ? 25 : 16);
		int MARGIN = 16;
		if (player.isPotionActive(Potion.poison))
			MARGIN += 36;
		else if (player.isPotionActive(Potion.wither))
			MARGIN += 72;
		float absorbRemaining = absorb;
		
		for (int i = MathHelper.ceiling_float_int((healthMax + absorb) / 2.0F)
				- 1; i >= 0; --i) {
			// int b0 = (highlight ? 1 : 0);
			int row = MathHelper.ceiling_float_int((float) (i + 1) / 10.0F) - 1;
			int x = left + i % 10 * 8;
			int y = top - row * rowHeight;

			if (health <= 4)
				y += rand.nextInt(2);
			if (i == regen)
				y -= 2;
			drawTexturedModalRect(-90, x, y, BACKGROUND, TOP, 9, 9);

			if (highlight) {
				if (i * 2 + 1 < healthLast)
					drawTexturedModalRect(-90, x, y, MARGIN + 54, TOP, 9, 9); // 6
				else if (i * 2 + 1 == healthLast)
					drawTexturedModalRect(-90, x, y, MARGIN + 63, TOP, 9, 9); // 7
			}
			if (absorbRemaining > 0.0F) {
				if (absorbRemaining == absorb && absorb % 2.0F == 1.0F)
					drawTexturedModalRect(-90, x, y, MARGIN + 153, TOP, 9, 9); // 17
				else
					drawTexturedModalRect(-90, x, y, MARGIN + 144, TOP, 9, 9); // 16
				absorbRemaining -= 2.0F;
			} else {
				if (i * 2 + 1 < health)
					drawTexturedModalRect(-90, x, y, MARGIN + 36, TOP, 9, 9); // 4
				else if (i * 2 + 1 == health)
					drawTexturedModalRect(-90, x, y, MARGIN + 45, TOP, 9, 9); // 5
			}

		}

		GlStateManager.disableBlend();
		Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
		GlStateManager.enableBlend();
		left = width / 2 - 91;
		top = height - left_height;

		int level = ForgeHooks.getTotalArmorValue(player);
		for (int i = 1; level > 0 && i < 20; i += 2) {
			if (i < level) {
				drawTexturedModalRect(-90, left, top, 34, 9, 9, 9);
			} else if (i == level) {
				drawTexturedModalRect(-90, left, top, 25, 9, 9, 9);
			} else if (i > level) {
				drawTexturedModalRect(-90, left, top, 16, 9, 9, 9);
			}
			left += 8;
		}
		if (level > 0)
			left_height += 10;

		GlStateManager.disableBlend();
		if (player.inventory.armorItemInSlot(1) != null
				&& player.inventory.armorItemInSlot(1).getItem() instanceof ItemArmorLegs
				&& player.inventory.armorItemInSlot(1).getTagCompound().getInteger("Absorb") > 0) {
			int flevel = ((ItemArmorLegs) player.inventory.armorItemInSlot(1).getItem())
					.getSpecDisplay(player.inventory.armorItemInSlot(1));
			GlStateManager.enableBlend();
			int lh = 0;
			{
				lh = 39;
				lh += (healthRows * rowHeight);
				if (rowHeight != 10)
					lh += 10 - rowHeight;
				if (level > 0)
					lh += 10;

			}
			left = width / 2 - 91;
			top = height - lh;
			Minecraft.getMinecraft().getTextureManager().bindTexture(FORCEFIELD);
			for (int i = 1; flevel > 0 && i < 20; i += 2) {
				if (i < flevel) {
					drawTexturedModalRect(-90.0F, left, top, 34,
							((ItemArmorLegs) player.inventory.armorItemInSlot(1).getItem())
									.getSpecSlot(player.inventory.armorItemInSlot(1)),
							9, 9);
				} else if (i == flevel) {
					drawTexturedModalRect(-90.0F, left, top, 25,
							((ItemArmorLegs) player.inventory.armorItemInSlot(1).getItem())
									.getSpecSlot(player.inventory.armorItemInSlot(1)),
							9, 9);
				} else if (i > flevel) {
					drawTexturedModalRect(-90.0F, left, top, 16,
							((ItemArmorLegs) player.inventory.armorItemInSlot(1).getItem())
									.getSpecSlot(player.inventory.armorItemInSlot(1)),
							9, 9);
				}
				left += 8;
			}
			Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
			GlStateManager.disableBlend();
		}
		GuiIngameForge.left_height = left_height;
	}
}
