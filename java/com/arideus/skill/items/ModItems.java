package com.arideus.skill.items;

import com.arideus.skill.creativetab.CreativeTabsSkill;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {

	public static final Item legs7 = new ItemArmorLegs("legs6", "7", 10, ArmorMaterial.DIAMOND, 2, 9, 100)
			.setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item Ammo11 = new ItemAmmo("ammo01", 1).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item Ammo21 = new ItemAmmo("ammo02", 1).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item Ammo31 = new ItemAmmo("ammo03", 1).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item Shotgun = new ItemGun("shotgun", 6579300, 100, 2d, 0.02d, 0.25d, 4f, 1, 18, 0,
			ModItems.Ammo11, EnumParticleTypes.CRIT, 0, false, 0d, 0, 2).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item Machinegun = new ItemGun("machinegun", 6579300, 0, 2d, 0.05d, 0.0625d, 2f, 1, 3, 0,
			ModItems.Ammo11, EnumParticleTypes.CRIT, 0, false, 0d, 0, 2).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item Hook = new ItemGun("hook", 6579300, 20, 1d, 1d, 0d, 2f, 0, 1, 0, null,
			EnumParticleTypes.CRIT, 2, false, 2d, 0, 3).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item SpecHook = new ItemGun("spechook", 6599935, 5, 5d, 1d, 0d, 2f, 0, 1, 0, null,
			EnumParticleTypes.CRIT_MAGIC, 3, false, 2d, 0, 3).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item crossbow = new ItemGun("skillcrossbow", 8873511, 180, 20d, 0.2, 0.025, 3, 2, 1, 0,
			Items.arrow, EnumParticleTypes.CRIT, 9, false, 0, 0, 0).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item Sniperrifle = new ItemGun("sniper", 6579300, 400, 40d, 0.08d, 0d, 8f, 3, 1, 0,
			ModItems.Ammo11, EnumParticleTypes.CRIT, 0, false, 0d, 0, 3).setCreativeTab(CreativeTabsSkill.TOOL);
	public static final ToolMaterial surgematerial = EnumHelper.addToolMaterial("SkillSurgeMaterial", 0, 5012, 15.0f,
			2.5f, 11);
	public static final Item SurgeSword = new ItemSwordChaining(2, "surgesword", surgematerial)
			.setCreativeTab(CreativeTabsSkill.TOOL);
	public static final Item ammopouch = new Heart("ammopouch");
	public static final Item jetpack = new Heart("jetpack").setMaxStackSize(4);
	public static final Item orbitshield = new Heart("orbitshield").setMaxStackSize(64);
	public static final Item magicshield = new Heart("magicshield").setMaxStackSize(1);
	public static final Item magicshieldspike = new Heart("magicshield2").setMaxStackSize(1);

	public static void init() {
		GameRegistry.registerItem(jetpack, "jetpack");
		GameRegistry.registerItem(crossbow, "skillcrossbow");
		GameRegistry.registerItem(SurgeSword, "surgesword");
		GameRegistry.registerItem(ammopouch, "ammopouch");
		GameRegistry.registerItem(orbitshield, "orbitshield");
		GameRegistry.registerItem(magicshield, "magicshield");
		GameRegistry.registerItem(magicshieldspike, "magicshield2");
		GameRegistry.registerItem(Shotgun, "shotgun");
		GameRegistry.registerItem(Machinegun, "machinegun");
		GameRegistry.registerItem(ammopouch, "ammopouch");
		GameRegistry.registerItem(Ammo11, "ammo01");
		GameRegistry.registerItem(Ammo21, "ammo02");
		GameRegistry.registerItem(Ammo31, "ammo03");
		GameRegistry.registerItem(legs7, "legs6");
		GameRegistry.registerItem(Sniperrifle, "sniper");
		GameRegistry.registerItem(SpecHook, "spechook");
		GameRegistry.registerItem(Hook, "hook");
		}

}
