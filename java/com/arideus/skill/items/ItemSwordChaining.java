package com.arideus.skill.items;

import java.util.List;
import java.util.Random;

import com.arideus.skill.Main;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSwordChaining extends ItemSword {
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
		if (stack.getTagCompound().getString("Name").contains("Red")
				|| stack.getTagCompound().getString("Name").contains("Flam")) {
			return new ModelResourceLocation(
					Main.MODID + ":" + stack.getItem().getUnlocalizedName().substring(5) + "red", "inventory");
		}
		if (stack.getTagCompound().getString("Name").contains("Cyan")
				|| stack.getTagCompound().getString("Name").contains("Blue")) {
			return new ModelResourceLocation(
					Main.MODID + ":" + stack.getItem().getUnlocalizedName().substring(5) + "cyan", "inventory");
		}
		if (stack.getTagCompound().getString("Name").contains("Omega")) {
			return new ModelResourceLocation(
					Main.MODID + ":" + stack.getItem().getUnlocalizedName().substring(5) + "omega", "inventory");
		}
		return new ModelResourceLocation(Main.MODID + ":" + stack.getItem().getUnlocalizedName().substring(5),
				"inventory");
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().hasKey("Name"))
			stack.getTagCompound().setString("Name", stack.getDisplayName());
		if (!stack.getTagCompound().hasKey("Unbreakable"))
			stack.getTagCompound().setBoolean("Unbreakable", true);
	}

	public String getItemStackDisplayName(ItemStack stack) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Name")
				|| stack.getTagCompound().getString("Name").contains("Hidden"))
			return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		else
			return stack.getTagCompound().getString("Name");
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer attacker, Entity target) {
		List elist = target.worldObj.getEntitiesWithinAABBExcludingEntity(attacker,
				target.getEntityBoundingBox().expand(radius, radius, radius));
		for (int i = 0; i < elist.size(); i++) {
			if (elist.get(i) instanceof EntityLivingBase && ((EntityLivingBase) elist.get(i)).hurtResistantTime <= 0) {
				((EntityLivingBase) elist.get(i)).hurtResistantTime = 1;
				onLeftClickEntity(stack, attacker, ((EntityLivingBase) elist.get(i)));
				double x1 = ((EntityLivingBase) elist.get(i)).posX;
				double y1 = ((EntityLivingBase) elist.get(i)).posY;
				double z1 = ((EntityLivingBase) elist.get(i)).posZ;
				double x2 = target.posX;
				double y2 = target.posY;
				double z2 = target.posZ;
				double distance = (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2)));
				int c = (int) (distance * 5);
				Random rand = new Random();
				double motionX = 0;
				double motionY = 0;
				double motionZ = 0;
				for (int d = 0; d < c; d++) {
					if (stack.getTagCompound().getString("Name").contains("Red")
							|| stack.getTagCompound().getString("Name").contains("Flam")) {
						attacker.worldObj.spawnParticle(EnumParticleTypes.FLAME,
								target.posX + (x1 - x2) / distance * d / 5,
								1 + target.posY + (y1 - y2) / distance * d / 5,
								target.posZ + (z1 - z2) / distance * d / 5, motionX, motionY, motionZ, new int[0]);
					} else if (stack.getTagCompound().getString("Name").contains("Cyan")
							|| stack.getTagCompound().getString("Name").contains("Blue")) {
						attacker.worldObj.spawnParticle(EnumParticleTypes.CRIT_MAGIC,
								target.posX + (x1 - x2) / distance * d / 5,
								1 + target.posY + (y1 - y2) / distance * d / 5,
								target.posZ + (z1 - z2) / distance * d / 5, motionX, motionY, motionZ, new int[0]);
					} else if (stack.getTagCompound().getString("Name").contains("Omega")) {

						attacker.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH,
								target.posX + (x1 - x2) / distance * d / 5,
								1 + target.posY + (y1 - y2) / distance * d / 5,
								target.posZ + (z1 - z2) / distance * d / 5, motionX, motionY, motionZ, new int[0]);
					} else {
						attacker.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
								target.posX + (x1 - x2) / distance * d / 5,
								1 + target.posY + (y1 - y2) / distance * d / 5,
								target.posZ + (z1 - z2) / distance * d / 5, motionX, motionY, motionZ, new int[0]);
					}
				}
				// stack.getItem().onLeftClickEntity(stack, attacker,
				// ((EntityLivingBase) elist.get(i)));
			}
		}
		return false;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		List elist = target.worldObj.getEntitiesWithinAABBExcludingEntity(attacker,
				target.getEntityBoundingBox().expand(radius, radius, radius));
		for (int i = 0; i < elist.size(); i++) {
			if (elist.get(i) instanceof EntityLivingBase) {
				((EntityPlayer) attacker).attackTargetEntityWithCurrentItem(((EntityLivingBase) elist.get(i)));
			}
		}
		return true;
	}

	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos,
			EntityLivingBase playerIn) {

		return true;
	}

	public int radius;

	public ItemSwordChaining(int r, String string, ToolMaterial material) {
		super(material);
		this.setUnlocalizedName(string);
		radius = r;
	}

	public EnumRarity getRarity(ItemStack stack) {
		return stack.isItemEnchanted() ? EnumRarity.EPIC : EnumRarity.RARE;
	}

}
