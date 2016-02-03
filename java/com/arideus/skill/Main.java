package com.arideus.skill;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {

	public static final IAttribute demonHeart = (new RangedAttribute((IAttribute)null, "generic.demonHealth", 0.0D, 0.0D, Double.MAX_VALUE)).setDescription("Demon Health").setShouldWatch(true);
	public static final IAttribute spiritHeart = (new RangedAttribute((IAttribute)null, "generic.spiritHealth", 0.0D, 0.0D, Double.MAX_VALUE)).setDescription("Spirit Health").setShouldWatch(true);
    public static final String MODID = "skill";
	public static final String MODNAME = "Arideus' Mod";
	public static final String VERSION = "1.0.0";
	public static SimpleNetworkWrapper network;
	public static DamageSource piercing = (new DamageSource("pierce")).setDamageBypassesArmor();
	public static DamageSource causeBulletDamage(Entity shot, Entity shooter) {
		return (new EntityDamageSourceIndirect("bullet", shot, shooter)).setProjectile();
	}

	public static DamageSource causeBulletDamageHook(Entity shot, Entity shooter) {
		return (new EntityDamageSourceIndirect("bullet.hook", shot, shooter)).setProjectile();
	}

	public static DamageSource causeArrowDamageLight(Entity shot, Entity shooter) {
		return (new EntityDamageSourceIndirect("arrow.light", shot, shooter)).setProjectile();
	}

	public static DamageSource causeArrowDamageDragonSkin(Entity shot, Entity shooter) {
		return (new EntityDamageSourceIndirect("arrow.dragonskin", shot, shooter)).setProjectile();
	}

	public static DamageSource causeArrowDamageFlame(Entity shot, Entity shooter) {
		return (new EntityDamageSourceIndirect("arrow.flame", shot, shooter)).setProjectile();
	}

	public static DamageSource causeArrowDamageSpec(Entity shot, Entity shooter, String type) {
		return (new EntityDamageSourceIndirect("arrow."+type, shot, shooter)).setProjectile();
	}

	@Instance
	public static Main instance = new Main();

	@SidedProxy(clientSide = "com.arideus.skill.ClientProxy", serverSide = "com.arideus.skill.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
		}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
}