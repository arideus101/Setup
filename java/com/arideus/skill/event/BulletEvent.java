package com.arideus.skill.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
@Cancelable
public class BulletEvent extends PlayerEvent {
	
	
	    public final ItemStack bow;
	    public int charge;
	    public Item ammo;
	    public BulletEvent(EntityPlayer player, ItemStack bow, int charge, Item ammo)
	    {
	        super(player);
	        this.bow = bow;
	        this.charge = charge;
	        this.ammo = ammo;
	    }
	
}
