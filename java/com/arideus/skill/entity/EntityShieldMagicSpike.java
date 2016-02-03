package com.arideus.skill.entity;

import java.util.List;

import com.arideus.skill.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityShieldMagicSpike extends EntityLivingBase {
	protected EntityPlayer player = null;

	public boolean isEntityInvulnerable(DamageSource p_180431_1_) {
		return true && p_180431_1_ != DamageSource.outOfWorld && !p_180431_1_.isCreativePlayer();
	}

	public double getRot1() {
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		return Math.abs(((player.renderYawOffset - 60) % 360) - 360);
	}

	public double getRot2() {
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 4);
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		int r = 60;
		double a = (player.renderYawOffset - r) % 360;
		a = Math.abs(a - 360);
		a /= 360;
		a *= 2 * 3.141592653589793;
		return a;
	}

	public void onUpdate() {
		int i = 0;
		super.onUpdate();
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 4);
		if (player == null || !player.inventory.hasItem(ModItems.magicshieldspike)) {
			this.setDead();
			return;
		}
		List l1 = worldObj.getEntitiesWithinAABB(EntityShieldMagicSpike.class,
				player.getEntityBoundingBox().expand(5, 5, 5));
		if (!this.isDead && l1.size() >= 1)
			for (i = 1; i < l1.size(); i++) {
				((Entity) l1.get(i)).setDead();
			}
		double off = 1.6;
		if (player.isSneaking()) {
			off = 1.2;
		}
		double x = player.posX;
		double y = player.posY + (2.0 - this.height) / 2;
		double z = player.posZ;
		double a = this.getRot2();
		this.setPosition(x + Math.sin(a) / off, y, z + Math.cos(a) / off);
		l1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.3, 0.3, 0.3));
		if (player.isSneaking())
			for (i = 0; i < l1.size(); i++) {
				if ((l1.get(i) instanceof EntityLiving||l1.get(i) instanceof EntityPlayer) && l1.get(i) != this.player) {
					((EntityLivingBase) l1.get(i)).attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
					((EntityLivingBase) l1.get(i)).knockBack(player, 1f,
							player.posX - ((EntityLivingBase) l1.get(i)).posX,
							player.posZ - ((EntityLivingBase) l1.get(i)).posZ);
				}
			}
	}

	public void moveEntity(double x, double y, double z) {
	}

	public EntityShieldMagicSpike(World worldIn) {
		super(worldIn);
		this.setSize(0.9f, 0.9f);
		// this.player = null;
	}

	public EntityShieldMagicSpike(World worldIn, EntityPlayer player) {
		this(worldIn);
		this.player = player;
		this.setPosition(player.posX, player.posY + 1, player.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("playerbase", this.player.getEntityId());

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		this.player = (EntityPlayer) worldObj.getEntityByID(tagCompund.getInteger("playerbase"));

	}

	@Override
	public ItemStack getHeldItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int slotIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getCurrentArmor(int slotIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack[] getInventory() {
		// TODO Auto-generated method stub
		return new ItemStack[] {};
	}

}
