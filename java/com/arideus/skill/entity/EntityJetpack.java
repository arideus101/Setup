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

public class EntityJetpack extends EntityLivingBase {
	public EntityPlayer player = null;

	public boolean isEntityInvulnerable(DamageSource source) {
		return source != DamageSource.inWall;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.isDead && source != DamageSource.inWall) {
			if (!worldObj.isRemote) {
				setDead();
				worldObj.createExplosion(source.getEntity(), posX, posY, posZ, (float) 5.0, true);
				return player.inventory.consumeInventoryItem(ModItems.jetpack);
			}
		}
		return false;
	}

	public double getRot1() {
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		return Math.abs(((player.renderYawOffset - 180) % 360) - 360);
	}

	public double getRot2() {
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 4);
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		int r = 180;
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
		if (player == null || !player.inventory.hasItem(ModItems.jetpack)) {
			this.setDead();
			return;
		}
		List l1 = worldObj.getEntitiesWithinAABB(EntityJetpack.class, player.getEntityBoundingBox().expand(5, 5, 5));
		if (!this.isDead && l1.size() >= 1)
			for (i = 1; i < l1.size(); i++) {
				((Entity) l1.get(i)).setDead();
			}
		{
			player.motionY += 0.05;
			player.fallDistance = 0;
			player.jumpMovementFactor = 0.5f;

		}
		double off = 5;
		if (player.isSneaking())
			off = 3;
		double x = player.posX;
		double y = player.posY + (2.0 - this.height) / 2;
		double z = player.posZ;
		double a = this.getRot2();
		this.setPosition(x + Math.sin(a) / off, y, z + Math.cos(a) / off);
	}

	public void moveEntity(double x, double y, double z) {
	}

	public EntityJetpack(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 0.5f);
	}

	public EntityJetpack(World worldIn, EntityPlayer player) {
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

	}

	@Override
	public ItemStack[] getInventory() {
		// TODO Auto-generated method stub
		return new ItemStack[] {};
	}

}
