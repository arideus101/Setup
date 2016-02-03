package com.arideus.skill.items;

import java.util.List;

import com.arideus.skill.Main;
import com.arideus.skill.entity.EntityJetpack;
import com.arideus.skill.entity.EntityShieldMagic;
import com.arideus.skill.entity.EntityShieldMagicSpike;
import com.arideus.skill.entity.EntityShieldOrbit;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Heart extends Item {
	public boolean hasCustomEntity(ItemStack stack) {
		if (stack.getItem() == ModItems.magicshield)
			return true;
		return false;
	}

	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
		if (stack.getItem() == ModItems.magicshield||stack.getItem() == ModItems.jetpack) {
			return new ModelResourceLocation("skill:blankshield", "inventory");
		}
		return new ModelResourceLocation(Main.MODID + ":" + stack.getUnlocalizedName().substring(5), "inventory");
	}

	public Heart(String unlocalizedName) {
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		if (stack.getItem() == ModItems.magicshield)
			list.add("\u00A74Rare Item");
		if (stack.getItem() == ModItems.magicshieldspike)
			list.add("\u00A74Rare Item");
	}

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps
	 * to check if is on a player hand and update it's contents.
	 */
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().hasKey("entity"))
			stack.getTagCompound().setInteger("entity", -1);
		if (!stack.getTagCompound().hasKey("show"))
			stack.getTagCompound().setBoolean("show", true);
		for (int i = 0; i < stack.stackSize; i++) {
			if (stack.getItem() == ModItems.ammopouch) {
				if (!((EntityPlayer) entityIn).inventory.hasItem(ModItems.Ammo31)) {
					((EntityPlayer) entityIn).inventory.addItemStackToInventory(new ItemStack(ModItems.Ammo31, 9));

				}
			}
			if (stack.getItem() == ModItems.magicshield) {
				if (worldIn.getEntityByID(stack.getTagCompound().getInteger("entity")) == null) {
					List list = worldIn.getEntitiesWithinAABB(EntityShieldMagic.class,
							entityIn.getEntityBoundingBox().expand(60, 60, 60));
					int g = -1;
					for (int l = 0; l < list.size(); l++) {
						if (((EntityShieldMagic) list.get(l)).getPlayer() == entityIn) {
							g = l;
						}
					}
					if (g == -1) {
						Entity shield = new EntityShieldMagic(worldIn, ((EntityPlayer) entityIn));
						worldIn.spawnEntityInWorld(shield);
						stack.getTagCompound().setInteger("entity", shield.getEntityId());
					}
				}
			}
			if (stack.getItem() == ModItems.magicshieldspike) {
				List l1 = worldIn.getEntitiesWithinAABB(EntityShieldMagicSpike.class,
						((EntityPlayer) entityIn).getEntityBoundingBox().expand(5, 5, 5));
				if (l1.size() < stack.stackSize)
					worldIn.spawnEntityInWorld(new EntityShieldMagicSpike(worldIn, ((EntityPlayer) entityIn)));
			}
			if (stack.getItem() == ModItems.jetpack) {
				List l1 = worldIn.getEntitiesWithinAABB(EntityShieldMagicSpike.class,
						((EntityPlayer) entityIn).getEntityBoundingBox().expand(5, 5, 5));
				if (l1.size() < stack.stackSize)
					worldIn.spawnEntityInWorld(new EntityJetpack(worldIn, ((EntityPlayer) entityIn)));
			}
			if (stack.getItem() == ModItems.orbitshield) {
				List l1 = worldIn.getEntitiesWithinAABB(EntityShieldOrbit.class,
						((EntityPlayer) entityIn).getEntityBoundingBox().expand(5, 5, 5));
				if (l1.size() < stack.stackSize)
					worldIn.spawnEntityInWorld(new EntityShieldOrbit(worldIn, ((EntityPlayer) entityIn)));
			}
		}
	}

	public ItemStack onItemRightClick(ItemStack stack, World p_77659_2_, EntityPlayer player) {
		if (stack.getItem() == ModItems.magicshield) {
			stack.stackSize--;
		}
		return stack;
	}
}
