package com.arideus.skill.items;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public enum EnumItemColor {

	       apple(16712960,Items.apple)
	    ,   coal(657930,Items.coal)
	    ,   diamond(6593535,Items.diamond)
	    ,   iron_ingot(13158600,Items.iron_ingot)
	    ,   gold_ingot(16777010,Items.gold_ingot)
	    ,   stick(8873511,Items.stick)
	    ,   string(13816530,Items.string)
	    ,   feather(13816520,Items.feather)
	    ,   gunpowder(8553090,Items.gunpowder)
	    ,   wheat(13150730,Items.wheat)
	    ,   bread(8873513,Items.bread)
	    ,   flint(6579300,Items.flint)
	    ,   porkchop(0,Items.porkchop)
	    ,   cooked_porkchop(0,Items.cooked_porkchop)
	    ,   golden_apple(0,Items.golden_apple)
	    ,   sign(0,Items.sign)
	    ,   bucket(0,Items.bucket)
	    ,   water_bucket(0,Items.water_bucket)
	    ,   lava_bucket(0,Items.lava_bucket)
	    ,   minecart(0,Items.minecart)
	    ,   saddle(0,Items.saddle)
	    ,   redstone(0,Items.redstone)
	    ,   snowball(0,Items.snowball)
	    ,   leather(0,Items.leather)
	    ,   milk_bucket(0,Items.milk_bucket)
	    ,   brick(0,Items.brick)
	    ,   clay_ball(0,Items.clay_ball)
	    ,   reeds(0,Items.reeds)
	    ,   paper(0,Items.paper)
	    ,   book(0,Items.book)
	    ,   slime_ball(0,Items.slime_ball)
	    ,   egg(0,Items.egg)
	    ,   compass(0,Items.compass)
	    ,   fishing_rod(0,Items.fishing_rod)
	    ,   clock(0,Items.clock)
	    ,   glowstone_dust(0,Items.glowstone_dust)
	    ,   fish(0,Items.fish)
	    ,   cooked_fish(0,Items.cooked_fish)
	    ,   dye(0,Items.dye)
	    ,   bone(0,Items.bone)
	    ,   sugar(0,Items.sugar)
	    ,   cake(0,Items.cake)
	    ,   bed(0,Items.bed)
	    ,   repeater(0,Items.repeater)//Special
	    ,   cookie(0,Items.cookie)
	    ,   shears(0,Items.shears)
	    ,   melon(0,Items.melon)
	    ,   beef(0,Items.beef)
	    ,   cooked_beef(0,Items.cooked_beef)
	    ,   chicken(0,Items.chicken)
	    ,   cooked_chicken(0,Items.cooked_chicken)
	    ,   mutton(0,Items.mutton)
	    ,   cooked_mutton(0,Items.cooked_mutton)
	    ,   rabbit(0,Items.rabbit)
	    ,   cooked_rabbit(0,Items.cooked_rabbit)
	    ,   rabbit_foot(0,Items.rabbit_foot)
	    ,   rabbit_hide(0,Items.rabbit_hide)
	    ,   rotten_flesh(0,Items.rotten_flesh)
	    ,   ender_pearl(0,Items.ender_pearl)
	    ,   blaze_rod(0,Items.blaze_rod)
	    ,   ghast_tear(0,Items.ghast_tear)
	    ,   gold_nugget(0,Items.gold_nugget)
	    ,   nether_wart(0,Items.nether_wart)
	    ,   potion(0,Items.potionitem)
	    ,   glass_bottle(0,Items.glass_bottle)
	    ,   spider_eye(0,Items.spider_eye)
	    ,   fermented_spider_eye(0,Items.fermented_spider_eye)
	    ,   blaze_powder(16737280,Items.blaze_powder)
	    ,   magma_cream(0,Items.magma_cream)
	    ,   ender_eye(0,Items.ender_eye)
	    ,   speckled_melon(0,Items.speckled_melon)
	    ,   spawn_egg(0,Items.spawn_egg)
	    ,   experience_bottle(0,Items.experience_bottle)//Special
	    ,   fire_charge(0,Items.fire_charge)
	    ,   emerald(0,Items.emerald)
	    ,   item_frame(0,Items.item_frame)
	    ,   flower_pot(0,Items.flower_pot)
	    ,   carrot(0,Items.carrot)
	    ,   potato(0,Items.potato)
	    ,   baked_potato(0,Items.baked_potato)
	    ,   poisonous_potato(0,Items.poisonous_potato)
	    ,   skull(0,Items.skull)
	    ,   nether_star(0,Items.nether_star)//Very Special
	    ,   pumpkin_pie(0,Items.pumpkin_pie)
	    ,   fireworks(0,Items.fireworks)
	    ,   firework_charge(0,Items.firework_charge)
	    ,   enchanted_book(0,Items.enchanted_book)//Special
	    ,   comparator(0,Items.comparator)
	    ,   netherbrick(0,Items.netherbrick)
	    ,   quartz(0,Items.quartz)
	    ,   lead(0,Items.lead)
	    ,   name_tag(0,Items.name_tag)
	    ,   prismarine_shard(0,Items.prismarine_shard)
	    ,   prismarine_crystals(0, Items.prismarine_crystals);
	    private EnumItemColor(int color, Item item){
	    	this.item=item;this.color=color;
	    }
	    private  Item item;public Item getItem(){return item;}
	    private int color;public int getColor(){return color;}
	    
}
