package com.arideus.skill.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAmmo extends Item {
	private int tier;
	public ItemAmmo(String name, int tier) {
		this.setUnlocalizedName(name);
		this.setMaxStackSize(9);
		this.tier=tier;
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	public String getName()
	{
	return this.getUnlocalizedName();
	}
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_,
			List list, boolean p_77624_4_) {
		list.add("§eAmmo Item");
		if(tier==1)list.add("§1Tier 1: Common");
		if(tier==2)list.add("§2Tier 2: Uncommon");
		if(tier==3)list.add("§3Tier 3: Rare");
		if(tier==4)list.add("§4Tier 4: Super Rare");
		if(tier==5)list.add("§5Tier 5: Ultra Rare");
		if(tier==6)list.add("§6Tier 6: Epic Rare");
		if(tier==7)list.add("§7Tier 7: Legendary");
	}

	public void onUpdate(ItemStack stack, World world, Entity player, int inte,
			boolean boole) {
		if (player instanceof EntityPlayer) {
			if (stack.getItem() == ModItems.Ammo11 && stack.stackSize == 9) {
				for (int i = 0; i < 9; i++)
					((EntityPlayer) player).inventory
							.consumeInventoryItem(ModItems.Ammo11);
				((EntityPlayer) player).inventory
						.addItemStackToInventory(new ItemStack(ModItems.Ammo21));
			}
			if (stack.getItem() == ModItems.Ammo21 && stack.stackSize == 9) {
				for (int i = 0; i < 9; i++)
					((EntityPlayer) player).inventory
							.consumeInventoryItem(ModItems.Ammo21);
				((EntityPlayer) player).inventory
						.addItemStackToInventory(new ItemStack(ModItems.Ammo31));
			}
		}
	}
}
