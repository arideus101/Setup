package com.arideus.skill.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;

public class EnumRarity2 {
	public static final EnumRarity URARE = EnumHelper.addRarity("ultraRare", EnumChatFormatting.BLUE, "Ultra Rare");
	public static final EnumRarity MRARE = EnumHelper.addRarity("mythicRare", EnumChatFormatting.DARK_RED, "Mythic Rare");
	public static final EnumRarity LEGEND = EnumHelper.addRarity("legendary", EnumChatFormatting.GREEN, "Legendary");
	public static final EnumRarity GODLY = EnumHelper.addRarity("godly", EnumChatFormatting.GOLD, "Godlike");
	public static final EnumRarity BOLD = EnumHelper.addRarity("custom", EnumChatFormatting.UNDERLINE, "Customized");
	public static final EnumRarity BEYOND = EnumHelper.addRarity("beyond", EnumChatFormatting.DARK_PURPLE, "Immortal");
}
