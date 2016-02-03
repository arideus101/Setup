package com.arideus.skill.items;

import java.util.List;
import java.util.Random;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorLegs extends ItemArmor implements ISpecialArmor {
	private int slots;
	private int toughness;
	private int regenrate;
	private String name;

	public ItemArmorLegs(String unlocalizedName, String ArmorName, int rate, ArmorMaterial material, int slot, int id,
			int toughness) {
		super(material, slot, slot);
		this.setUnlocalizedName(unlocalizedName);
		this.slots = id;
		this.toughness = toughness;
		this.name = ArmorName;
		this.regenrate = rate;
		this.setCreativeTab(CreativeTabs.tabCombat);

	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add("§eArmor Item");
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {

		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("owner", (((EntityPlayer) entity).getDisplayNameString()));
			stack.getTagCompound().setInteger("Reload", 0);
			stack.getTagCompound().setInteger("Absorb", toughness);

		}
	}

	public Multimap getAttributeModifiers(ItemStack stack) {

		Multimap multimap = HashMultimap.create();
		return multimap;
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("owner", (player.getDisplayNameString()));
			stack.getTagCompound().setInteger("Reload", 0);
			stack.getTagCompound().setInteger("Absorb", toughness);

		}
		if (stack.getMaxDamage() - 10 > stack.getTagCompound().getInteger("Reload"))
			stack.setItemDamage(stack.getTagCompound().getInteger("Reload"));
		else
			stack.setItemDamage(stack.getMaxDamage() - 10);
		if (stack.getTagCompound().getInteger("Reload") > 0) {
			stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") - 1);
		}
		if (stack.getTagCompound().getInteger("Reload") < 0) {
			stack.getTagCompound().setInteger("Reload", 0);
		}
		Random r = new Random();

		int regen = 1;
		if (regenrate == -1)
			regen = 50;
		if (player.getActivePotionEffect(Potion.regeneration) != null) {
			regen += player.getActivePotionEffect(Potion.regeneration).getAmplifier();
		}
		if (player.getActivePotionEffect(Potion.weakness) != null) {
			if (player.getActivePotionEffect(Potion.weakness).getAmplifier() == -1)
				return;
		}

		// System.out.println("Stage 1 - " +
		// stack.getTagCompound().getInteger("Reload"));
		for (int i = 0; i < regen; i++)
			if (stack.getTagCompound().getInteger("Reload") == 0) {
				// System.out.println("Stage 2");
				if (regenrate == -1) {
					// System.out.println("Stage 3");
					if (stack.getTagCompound().getInteger("Absorb") < toughness
							&& (world.getWorldTime() / 12000) % 2 == 0) {
						// System.out.println("Stage 4");
						stack.getTagCompound().setInteger("Absorb", stack.getTagCompound().getInteger("Absorb") + 1);
					}
				} else if (stack.getTagCompound().getInteger("Absorb") <= toughness
						&& world.getTotalWorldTime() % regenrate == 0)
					stack.getTagCompound().setInteger("Absorb", stack.getTagCompound().getInteger("Absorb") + 1);
			}
		if (stack.getTagCompound().getInteger("Absorb") >= toughness)
			player.addPotionEffect(new PotionEffect(Potion.heal.getId(), 1, 1, true, false));
	}

	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("owner", player.getDisplayNameString());
			stack.getTagCompound().setInteger("Absorb", toughness);
			stack.getTagCompound().setInteger("Reload", 0);
		}

	}

	public String getArmorTexture(ItemStack Stack, Entity Entity, int slot, String type) {
		return "skill:textures/models/armor/armor_" + name + "_layer_2.png";
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack stack, DamageSource source, double damage,
			int slot) {
		stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") + 30);
		boolean doit = false;
		if (stack.getTagCompound().getInteger("Absorb") > 0)
			doit = true;
		stack.getTagCompound().setInteger("Absorb", stack.getTagCompound().getInteger("Absorb") - (int) damage * 2);
		if (stack.getTagCompound().getInteger("Absorb") < 0) {
			int i = 2;
			if (regenrate == -1)
				i = 1;
			stack.getTagCompound().setInteger("Reload",
					stack.getTagCompound().getInteger("Reload") - 4 * stack.getTagCompound().getInteger("Absorb"));
			stack.getTagCompound().setInteger("Absorb", 0);
		}
		if (doit && regenrate != -1)
			return new ArmorProperties(1, 1, Integer.MAX_VALUE);
		if (doit && regenrate == -1)
			return new ArmorProperties(1, 0.99, Integer.MAX_VALUE);

		return new ArmorProperties(1, 0.2, 10);

	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack stack, int slot) {
		return 6;
	}

	public int getSpecDisplay(ItemStack stack) {
		return (int) ((double) stack.getTagCompound().getInteger("Absorb") / (double) toughness * 20d);
	}

	public int getSpecSlot(ItemStack stack) {

		return slots;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
	}
}
