package com.arideus.skill.entity;

import java.util.List;

import com.arideus.skill.items.ModItems;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityShieldOrbit extends EntityOrbital {
	public EntityShieldOrbit(World worldIn) {
		super(worldIn);
		this.setSize(0.3f, 0.5f);
	}

	public EntityShieldOrbit(World worldIn, EntityPlayer entityPlayer) {
		super(worldIn, entityPlayer);
		this.setSize(0.3f, 0.5f);
	}

	public void onUpdate() {
		super.onUpdate();
		if (this.isDead)
			return;
		if (!player.inventory.hasItem(ModItems.orbitshield))
			this.setDead();
		if (this.isDead)
			return;
		int count = 0;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (player.inventory.mainInventory[i] != null
					&& player.inventory.mainInventory[i].getItem() == ModItems.orbitshield) {
				count = player.inventory.mainInventory[i].stackSize;
			}
		}
		List l1 = worldObj.getEntitiesWithinAABB(EntityShieldOrbit.class,
				player.getEntityBoundingBox().expand(6, 6, 6));
		if (l1.size() > count && !this.isDead) {
			for (int i = 0; i < l1.size(); i++) {
				if (l1.get(i) != this)
					((EntityShieldOrbit) l1.get(i)).setDead();
			}
		}
		if (player.isSneaking()) {
			worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, player.posX, player.posY + 2, player.posZ,
					posX - player.posX, posY - player.posY - 2, posZ - player.posZ, new int[0]);
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 2, 7));
			player.motionY = -1;
			l1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.3, 0.3, 0.3));
			for (int i = 0; i < l1.size(); i++) {
				if ((l1.get(i) instanceof EntityLiving || l1.get(i) instanceof EntityPlayer)
						&& l1.get(i) != this.player) {
					((EntityLivingBase) l1.get(i)).attackEntityFrom(DamageSource.causePlayerDamage(player), 4);
					((EntityLivingBase) l1.get(i)).knockBack(player, 1f,
							player.posX - ((EntityLivingBase) l1.get(i)).posX,
							player.posZ - ((EntityLivingBase) l1.get(i)).posZ);
				}
			}
		}
	}
}
