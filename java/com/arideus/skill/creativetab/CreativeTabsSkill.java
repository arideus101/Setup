package com.arideus.skill.creativetab;

import com.arideus.skill.items.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabsSkill {
	public static final CreativeTabs TOOL = new CreativeTabs("skilltool") {
		@Override
		public Item getTabIconItem() {
			return Items.golden_pickaxe;
		}
	};
}
