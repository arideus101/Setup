package com.arideus.skill.items;

import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorBoot extends ItemArmor implements ISpecialArmor {
	private double gravpercent;
	private int speed;
	private double speedfactor;
	private int jump;
	private double jumpfactor;
	private int tier;

	private ArmorMaterial material;

	public ItemArmorBoot(String unlocalizedName, ArmorMaterial material, int render, int type, double gravpercent,
			int speed, double speedfactor, int jump, double jumpfactor, int tier) {
		super(material, render, type);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.gravpercent = gravpercent;
		this.speed = speed;
		this.speedfactor = speedfactor;
		this.jump = jump;
		this.jumpfactor = jumpfactor;
		this.material = material;
		this.tier = tier;
	}

	public String getArmorTexture(ItemStack Stack, Entity Entity, int slot, String type) {

		return "skill:textures/models/armor/armor_7_layer_1.png";
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add("§eArmor Item");
	}

	@SubscribeEvent
	public void updatePlayerStepStatus(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack boot = player.getCurrentArmor(3);
			Item item = null;
			if (boot != null) {
				item = boot.getItem();
			}
			if (item == this)
				player.stepHeight = jump;
			if (item != this)
				player.stepHeight = 0.5f;

		}

	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (!player.isSneaking()) {
			player.jumpMovementFactor = (float) this.jumpfactor;
			if (speed >= 0)
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 5, this.speed));
			if (jump >= 0)
				player.addPotionEffect(new PotionEffect(Potion.jump.getId(), 5, this.jump));
			if (player.motionY >= 0)
				player.addVelocity(0, this.gravpercent * 0.075, 0);
			if (speedfactor != 0) {
				double ax = Math.abs(player.motionX);
				double az = Math.abs(player.motionZ);
				if (Math.sqrt(Math.pow(ax, 2) + Math.pow(az, 2)) <= 4) {
					player.addVelocity(player.motionX * (this.speedfactor - 1), 0,
							player.motionZ * (this.speedfactor - 1));
				}
			}
			// player.stepHeight = jump;
			player.fallDistance = 0;
			if (false) {
				float pitch = player.rotationPitch;
				double percent = 0;
				percent = -pitch / 90;
				if (Math.abs(percent) > 0.15)
					player.motionY += percent * 0.15;
				if (percent > -0.25)
					player.motionY += 0.075;
				
			}
		}
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,
			int slot) {
		return new ArmorProperties(1, 0.25, 100);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
	}
}
