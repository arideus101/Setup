package com.arideus.skill.entity;

import java.util.List;

import com.arideus.skill.items.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityOrbital extends EntityLivingBase {
	private static final int count = 16;
	protected EntityPlayer player = null;
	protected int posnumber = 0;
	protected int totalnumber = 0;

	public int getPos() {
		return posnumber;
	}

	public void setPos(int i) {
		posnumber = i;
	}

	public boolean isEntityInvulnerable(DamageSource p_180431_1_) {
		return true && p_180431_1_ != DamageSource.outOfWorld && !p_180431_1_.isCreativePlayer();
	}

	public double getRot1() {
		int c = count;
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 6);
		List l1 = worldObj.getEntitiesWithinAABB(EntityOrbital.class, player.getEntityBoundingBox().expand(5, 5, 5));
		if (l1.size() != totalnumber && !this.isDead) {
			for (int i = 0; i < l1.size(); i++)
				if (((EntityOrbital) l1.get(i)).getPos() != i)
					((EntityOrbital) l1.get(i)).setPos(i);
			totalnumber = l1.size();
		}
		if (player.isSneaking())
			c *= 2;
		int maxpos = l1.size();
		if (maxpos > c)
			maxpos = c;
		double x = player.posX;
		double y = player.posY + (2.0 - this.height) / 2;
		double z = player.posZ;
		long time = worldObj.getTotalWorldTime();
		if (maxpos > 1)
			time += (90f / ((float)maxpos)) * (posnumber % c);
		double a = (double) (time % 90);
		return a;
	}

	public double getRot2() {
		int c = count;
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 4);
		List l1 = worldObj.getEntitiesWithinAABB(EntityOrbital.class, player.getEntityBoundingBox().expand(5, 5, 5));
		if (l1.size() != totalnumber && !this.isDead) {
			for (int i = 0; i < l1.size(); i++) {
				if (((EntityOrbital) l1.get(i)).getPos() != i)
					((EntityOrbital) l1.get(i)).setPos(i);
			}
			totalnumber = l1.size();
		}
		if (player.isSneaking())
			c *= 2;
		int maxpos = l1.size();
		if (maxpos > c)
			maxpos = c;
		double x = player.posX;
		double y = player.posY + (2.0 - this.height) / 2;
		double z = player.posZ;
		long time = worldObj.getTotalWorldTime();
		if (maxpos > 1)
			time += (90f / ((float)maxpos)) * (posnumber % c);
		double a = (double) (time % 90);
		a /= 90;
		a *= 2 * 3.141592653589793;
		return a;
	}

	public void onUpdate() {
		int c = count;
		if (player == null)
			player = worldObj.getClosestPlayer(posX, posY, posZ, 4);
		if (player == null) {
			this.setDead();
			return;
		}
		List l1 = worldObj.getEntitiesWithinAABB(EntityOrbital.class, player.getEntityBoundingBox().expand(5, 5, 5));
		if (l1.size() != totalnumber && !this.isDead) {
			for (int i = 0; i < l1.size(); i++) {
				if (((EntityOrbital) l1.get(i)).getPos() != i)
					((EntityOrbital) l1.get(i)).setPos(i);
			}
			totalnumber = l1.size();
		}
		if (player.isSneaking())
			c *= 2;
		int maxpos = l1.size();
		if (maxpos > c)
			maxpos = c;
		double x = player.posX;
		double y = player.posY + (2.0 - this.height) / 2;
		double z = player.posZ;
		long time = worldObj.getTotalWorldTime();
		if (maxpos > 1) {
			time += (90f / ((float)maxpos)) * (posnumber % c);
		}
		double a = (double) (time % 90);
		a /= 90;
		a *= 2 * 3.1415;
		
		int displace = maxpos + 1;
		
		double b = (double) (time % (int) (22));
		b /= 90;
		b *= 2 * 3.1415;
		b += (22 / displace) * (posnumber % c);
		float off = 1;
		if (player.isSneaking()) {
			off = 2f;
		}
		off += ((float) ((int) ((posnumber / c) - 2))) / 10;
		if (posnumber / c == 0) {
			setPosition(x + Math.sin(a) * off, y + (Math.sin(b * 4) / 4) * off, z + Math.cos(a) * off);
		} else if (posnumber / c == 1) {
			setPosition(x + Math.sin(a) * off, y + (Math.cos(b * 4) / 4) * off, z + Math.cos(a) * off);
		} else if (posnumber / c == 2) {
			setPosition(x + Math.sin(a) * off, y, z + Math.cos(a) * off);
		} else if (posnumber / c == 3) {
			setPosition(x + Math.sin(a) * off, y + (Math.cos(b * 4) / 4) * off, z + Math.cos(a) * off);
		} else if (posnumber / c == 4) {
			setPosition(x + Math.sin(a) * off, y + (Math.sin(b * 4) / 4) * off, z + Math.cos(a) * off);
		}
	}

	public void moveEntity(double x, double y, double z) {
	}

	public EntityOrbital(World worldIn) {
		super(worldIn);
		this.setSize(0.3f, 2.0f);
		// this.player = null;
	}

	public EntityOrbital(World worldIn, EntityPlayer player) {
		this(worldIn);
		this.player = player;
		this.setPosition(player.posX, player.posY + 1, player.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("playerbase", this.player.getEntityId());
		tagCompound.setInteger("totalnumber", this.totalnumber);
		tagCompound.setInteger("posnumber", this.posnumber);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		this.player = (EntityPlayer) worldObj.getEntityByID(tagCompund.getInteger("playerbase"));
		this.totalnumber = tagCompund.getInteger("totalnumber");
		this.posnumber = tagCompund.getInteger("posnumber");
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
