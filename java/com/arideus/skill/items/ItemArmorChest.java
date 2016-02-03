package com.arideus.skill.items;

import com.arideus.skill.Main;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public class ItemArmorChest extends ItemArmor implements ISpecialArmor {
	private static int type;

	public ItemArmorChest(ArmorMaterial material, int armorType, int type) {
		super(material, armorType, armorType);
		this.type = type;
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
	}

	public String getArmorTexture(ItemStack Stack, Entity Entity, int slot, String type) {
		return "skill:textures/models/armor/armor_7_layer_1.png";
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack stack, DamageSource source, double damage,
			int slot) {
		if (type == 2)
			return new ArmorProperties(1, 0.25, 10);
		return new ArmorProperties(1, 0, 10);

	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 20;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

	}

}
