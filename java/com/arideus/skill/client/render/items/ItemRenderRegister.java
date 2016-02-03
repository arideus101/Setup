package com.arideus.skill.client.render.items;

import com.arideus.skill.Main;
import com.arideus.skill.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ItemRenderRegister {

	public static void registerItemRenderer() {

		
		reg(ModItems.ammopouch);
		reg(ModItems.orbitshield);
		reg(ModItems.magicshield);
		reg(ModItems.magicshieldspike);
		reg(ModItems.Hook);
		reg(ModItems.SpecHook);
		reg(ModItems.Machinegun);
		reg(ModItems.Shotgun);
		reg(ModItems.Ammo11);
		reg(ModItems.Ammo21);
		reg(ModItems.Ammo31);
		reg(ModItems.legs7);
		reg(ModItems.jetpack);
		reg(ModItems.Sniperrifle);
		reg(ModItems.SurgeSword);
		reg(ModItems.crossbow, 0, "blankcrossbow_pulling_2");
	}

	// ==========================================================================

	public static String modid = Main.MODID;

	public static void reg(Item item, int i, String nameadd) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i,
				new ModelResourceLocation(modid + ":" + nameadd, "inventory"));
	}

	public static void reg(Item item) {
		int i = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i,
				new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}

	public static void reg(Item item, String nameext, int i) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, i,
				new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5)+ nameext, "inventory"));
		//System.out.println(item.getUnlocalizedName().substring(5)+"_" + nameext);
	}

	public static void reg(Block block) {
		int i = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), i,
				new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
