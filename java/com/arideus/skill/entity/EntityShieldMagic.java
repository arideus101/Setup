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

public class EntityShieldMagic extends EntityLivingBase {
	protected EntityPlayer player = null;
	protected int throwing = 0;
	protected int thrown = 0;
	protected int launching = 0;
	protected double[] coords = new double[3];// Record for throwing
	protected float angle = 0f;

	public boolean isEntityInvulnerable(DamageSource p_180431_1_) {
		return true && p_180431_1_ != DamageSource.outOfWorld && !p_180431_1_.isCreativePlayer();
	}

	public double getThrowing() {
		return throwing;
	}

	public int getLaunching() {
		return launching;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public double getRot1() {
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		int r = 60;
		if (player.isSneaking() && Math.abs(throwing) < 510)
			r = 105;
		return Math.abs(((player.renderYawOffset - r) % 360) - 360);
	}

	public double getRot2() {
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 4);
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		int r = 60;
		if (player.isSneaking() && Math.abs(throwing) < 510)
			r = 105;
		double a = (player.renderYawOffset - r) % 360;
		a = Math.abs(a - 360);
		a /= 360;
		a *= 2 * 3.141592653589793;
		return a;
	}

	public double getRot2(float fl) {
		double a = fl;
		a = Math.abs(a - 360);
		a /= 360;
		a *= 2 * 3.141592653589793;
		return a;
	}

	public void onUpdate() {
		super.onUpdate();
		// DEBUG: SUPERSLOW
		// if(worldObj.getTotalWorldTime() % 10 != 0) return;
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		double x2 = 0;
		double y2 = 0;
		double z2 = 0;
		if (player == null) {
			player = worldObj.getClosestPlayer(posX, posY, posZ, 4);
		}
		if (player == null || !player.inventory.hasItem(ModItems.magicshield)
				|| player.inventory.hasItem(ModItems.magicshieldspike)) {
			this.setDead();
			return;
		}
		List l1 = worldObj.getEntitiesWithinAABB(EntityShieldMagic.class,
				player.getEntityBoundingBox().expand(5, 5, 5));
		if (!this.isDead && l1.size() >= 1)
			for (int i = 1; i < l1.size(); i++) {
				((Entity) l1.get(i)).setDead();
			}
		double trw = 1;
		double snk = 0;
		double off = 1.6;
		if (player.isSneaking()) {
			if (throwing < 510) {
				snk = -0.2;
				off = 1.3;
				if (throwing != 0) {
					throwing /= 1.3;
					if (throwing > 5)
						throwing -= 5;
					else if (throwing < -5)
						throwing += 5;
					else
						throwing = 0;
					thrown = 0;
					launching = 0;
				}
			} else {
				launching = 1;
			}
		} else if (player.isSprinting()) {
		} else {
			if (throwing == 666)
				throwing = 0;
			thrown = 0;
			if (player.prevRenderYawOffset != player.renderYawOffset) {
			} else if (throwing != 0) {
				throwing /= 1.3;
				if (throwing > 5)
					throwing -= 5;
				else if (throwing < -5)
					throwing += 5;
				else
					throwing = 0;
			}
			launching = 0;
		}
		y += (2.0 - this.height) / 2 + snk;
		double a = this.getRot2();
		double a2 = this.getRot2(angle);
		if ((launching == 0))
			this.setPosition(x + Math.sin(a) / off * trw, y, z + Math.cos(a) / off * trw);

	}

	public void moveEntity(double x, double y, double z) {
	}

	public EntityShieldMagic(World worldIn) {
		super(worldIn);
		this.setSize(0.9f, 0.9f);
		// this.player = null;
	}

	public EntityShieldMagic(World worldIn, EntityPlayer player) {
		this(worldIn);
		this.player = player;
		this.setPosition(player.posX, player.posY + 1, player.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("playerbase", player.getEntityId());
		tagCompound.setInteger("throw", throwing);
		tagCompound.setInteger("thrown", thrown);
		tagCompound.setInteger("flying", launching);
		tagCompound.setInteger("eid", getEntityId());
		tagCompound.setDouble("coords1", coords[0]);
		tagCompound.setDouble("coords2", coords[1]);
		tagCompound.setDouble("coords3", coords[2]);
		tagCompound.setFloat("angle", angle);

	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		this.player = (EntityPlayer) worldObj.getEntityByID(tagCompund.getInteger("playerbase"));
		this.throwing = tagCompund.getInteger("throw");
		this.thrown = tagCompund.getInteger("thrown");
		this.launching = tagCompund.getInteger("flying");
		this.coords[0] = tagCompund.getDouble("coords1");
		this.coords[1] = tagCompund.getDouble("coords2");
		this.coords[2] = tagCompund.getDouble("coords3");
		this.angle = tagCompund.getFloat("angle");
		setEntityId(tagCompund.getInteger("eid"));
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
