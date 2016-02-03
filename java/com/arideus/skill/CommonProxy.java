package com.arideus.skill;

import com.arideus.skill.client.render.entity.ModelSlimer;
import com.arideus.skill.client.render.entity.RenderHeart;
import com.arideus.skill.client.render.entity.RenderJetpack;
import com.arideus.skill.client.render.entity.RenderShield;
import com.arideus.skill.client.render.entity.RenderShield2;
import com.arideus.skill.client.render.entity.RenderSlimer;
import com.arideus.skill.client.render.items.ItemRenderRegister;
import com.arideus.skill.client.render.items.RenderBullet;
import com.arideus.skill.client.render.items.RenderThingy;
import com.arideus.skill.entity.EntityArrowLight;
import com.arideus.skill.entity.EntityBullet;
import com.arideus.skill.entity.EntityHook;
import com.arideus.skill.entity.EntityHookSpec;
import com.arideus.skill.entity.EntityJetpack;
import com.arideus.skill.entity.EntityLaserHeatSeak;
import com.arideus.skill.entity.EntityShieldMagic;
import com.arideus.skill.entity.EntityShieldMagicSpike;
import com.arideus.skill.entity.EntityShieldOrbit;
import com.arideus.skill.entity.EntitySlimer;
import com.arideus.skill.items.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		ModItems.init();
		MinecraftForge.EVENT_BUS.register(new ItemRenderRegister());
	}

	public void init(FMLInitializationEvent e) {
		Main.network = NetworkRegistry.INSTANCE.newSimpleChannel("SkillChannel");
		int packetId = 0;
		//Main.network.registerMessage(MessagePower.Handler.class, MessagePower.class, packetId++, Side.SERVER);
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimer.class,
				new RenderSlimer(Minecraft.getMinecraft().getRenderManager(), new ModelSlimer(16), 0.25F));

		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class,
				new RenderBullet(ModItems.Ammo11, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityHook.class, new RenderThingy());
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserHeatSeak.class, new RenderThingy());
		RenderingRegistry.registerEntityRenderingHandler(EntityHookSpec.class, new RenderThingy());
		RenderingRegistry.registerEntityRenderingHandler(EntityArrowLight.class,
				new RenderArrow(Minecraft.getMinecraft().getRenderManager()));
		ModelBakery.addVariantName(ModItems.SurgeSword, new String[] { Main.MODID + ":surgesword",
				Main.MODID + ":surgeswordcyan", Main.MODID + ":surgeswordred", Main.MODID + ":surgeswordomega" });
		ModelBakery.addVariantName(ModItems.SpecHook,
				new String[] { Main.MODID + ":spechook", Main.MODID + ":spechookspec" });
		ModelBakery.addVariantName(ModItems.Hook, new String[] { Main.MODID + ":hook", Main.MODID + ":hookspec" });
		ModelBakery.addVariantName(ModItems.Shotgun,
				new String[] { Main.MODID + ":shotgun", Main.MODID + ":shotgun_0", Main.MODID + ":shotgun_1",
						Main.MODID + ":shotgun_2", Main.MODID + ":shotgun_3", Main.MODID + ":shotgun_4",
						Main.MODID + ":shotgun_5", Main.MODID + ":shotgun_6", Main.MODID + ":shotgun_7",
						Main.MODID + ":shotgun_8" });
		ModelBakery.addVariantName(ModItems.crossbow,
				new String[] { Main.MODID + ":skillcrossbow", Main.MODID + ":skillcrossbow_pulling_0",
						Main.MODID + ":skillcrossbow_pulling_1", Main.MODID + ":skillcrossbow_pulling_2",
						Main.MODID + ":steelcrossbow", Main.MODID + ":steelcrossbow_pulling_0",
						Main.MODID + ":steelcrossbow_pulling_1", Main.MODID + ":steelcrossbow_pulling_2",
						Main.MODID + ":aureycrossbow", Main.MODID + ":aureycrossbow_pulling_0",
						Main.MODID + ":aureycrossbow_pulling_1", Main.MODID + ":aureycrossbow_pulling_2",
						Main.MODID + ":jadedcrossbow", Main.MODID + ":jadedcrossbow_pulling_0",
						Main.MODID + ":jadedcrossbow_pulling_1", Main.MODID + ":jadedcrossbow_pulling_2",
						Main.MODID + ":blankcrossbow", Main.MODID + ":blankcrossbow_pulling_0",
						Main.MODID + ":blankcrossbow_pulling_1", Main.MODID + ":blankcrossbow_pulling_2" });
		String[] dyename = new String[16];
		EntityRegistry.registerModEntity(EntitySlimer.class, "spideromega", 120, Main.instance, 40, 3, false);
		RenderingRegistry.registerEntityRenderingHandler(EntityShieldOrbit.class,
				new RenderHeart(ModItems.orbitshield, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityShieldMagic.class,
				new RenderShield(ModItems.magicshield, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityShieldMagicSpike.class,
				new RenderShield2(ModItems.magicshieldspike, Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityJetpack.class,
				new RenderJetpack(ModItems.jetpack, Minecraft.getMinecraft().getRenderItem()));
		}

	public void postInit(FMLPostInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
}
