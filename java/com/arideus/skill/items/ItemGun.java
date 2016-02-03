package com.arideus.skill.items;

import java.util.List;
import java.util.Random;

import com.arideus.skill.KeyBindings;
import com.arideus.skill.Main;
import com.arideus.skill.entity.EntityArrowDragonSkin;
import com.arideus.skill.entity.EntityArrowLight;
import com.arideus.skill.entity.EntityBullet;
import com.arideus.skill.entity.EntityBulletChisel;
import com.arideus.skill.entity.EntityHook;
import com.arideus.skill.entity.EntityHookSpec;
import com.arideus.skill.event.BulletEvent;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGun extends Item implements IColorable, INameable {
	private int FireRate; // ticks of confusion used as cooldown
	private double Damage; // damage of each bullet
	private double Recoil; // number of blocks launched back
	private double Accuracy; // 0 = dead on, 1 = 1 block off
	private float Power;
	private int Knockback;
	private int Number;
	private int FireTime;
	private Item Ammo;
	private EnumParticleTypes particle;
	private int Type;
	private int Color;
	private int BulletType;
	private boolean Gravity;
	public static final String[] NamePre = { "Ender", "Creepy", "Flame", "Inferno", "Hydro", "Beastly", "Active",
			"Ultra", "Super", "Supra", "Uber", "Smash", "Frost", "Earthly", "Heavenly", "Lethal", "Deadly", "Nostalgic",
			"Aerial", "Wind", "Dark", "Light", "Aqua", "Magma", "Rocket", "Plasma", "Neo Plasma", "Galactic",
			"Universe", "Celestial", "Inactive", "Spatial", "Composite", "Jupiter" };
	public static final String[] NameRoot = { "Smasher", "Blaster", "Destroyer", "Flame", "Hunter", "Rifle", "Gun",
			"Launcher", "Assassin", "Cannon", "Lighter", "Thrower", "Activator", "Catalyst", "Ender", "Starter",
			"Finisher", "Fatality", "Reactor", "Compounder", "" };
	public static final String[] NameSuf = { "SR", "MG", "SG", "CG", "GH", "CB", "BL" };

	

	

	public int getColorByItem(Item item) {
		for (int i = 0; i < EnumItemColor.values().length; i++) {
			if (EnumItemColor.values()[i].getItem() == item)
				return EnumItemColor.values()[i].getColor();
		}
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		setupStack(stack);

		return stack.getTagCompound().getInteger("Color" + renderPass);

	}

	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
		if (stack.getItem() == ModItems.Hook && getInteger(stack, "Reload") != 0) {
			return new ModelResourceLocation("skill:hookspec", "inventory");
		}
		if (stack.getItem() == ModItems.SpecHook && getInteger(stack, "Reload") != 0) {
			return new ModelResourceLocation("skill:spechookspec", "inventory");
		}
		if (stack.getItem() == ModItems.Shotgun) {
			return new ModelResourceLocation(
					"skill:shotgun_" + (int) (stack.getTagCompound().getInteger("Reload") % 16 / 2), "inventory");
		}
		if (stack.getItem() == ModItems.crossbow) {
			if (getString(stack, "Name").contains("Steel")) {
				if (getInteger(stack, "Reload") >= 121)
					return new ModelResourceLocation("skill:steelcrossbow", "inventory");
				if (getInteger(stack, "Reload") >= 61)
					return new ModelResourceLocation("skill:steelcrossbow_pulling_0", "inventory");
				if (getInteger(stack, "Reload") >= 1)
					return new ModelResourceLocation("skill:steelcrossbow_pulling_1", "inventory");
				return new ModelResourceLocation("skill:steelcrossbow_pulling_2", "inventory");
			} else if (getString(stack, "Name").contains("Aure")) {
				if (getInteger(stack, "Reload") >= 121)
					return new ModelResourceLocation("skill:aureycrossbow", "inventory");
				if (getInteger(stack, "Reload") >= 61)
					return new ModelResourceLocation("skill:aureycrossbow_pulling_0", "inventory");
				if (getInteger(stack, "Reload") >= 1)
					return new ModelResourceLocation("skill:aureycrossbow_pulling_1", "inventory");
				return new ModelResourceLocation("skill:aureycrossbow_pulling_2", "inventory");
			} else if (getString(stack, "Name").contains("Jaded")) {
				if (getInteger(stack, "Reload") >= 121)
					return new ModelResourceLocation("skill:jadedcrossbow", "inventory");
				if (getInteger(stack, "Reload") >= 61)
					return new ModelResourceLocation("skill:jadedcrossbow_pulling_0", "inventory");
				if (getInteger(stack, "Reload") >= 1)
					return new ModelResourceLocation("skill:jadedcrossbow_pulling_1", "inventory");
				return new ModelResourceLocation("skill:jadedcrossbow_pulling_2", "inventory");
			} else {
				if (getInteger(stack, "Reload") >= 121)
					return new ModelResourceLocation("skill:blankcrossbow", "inventory");
				if (getInteger(stack, "Reload") >= 61)
					return new ModelResourceLocation("skill:blankcrossbow_pulling_0", "inventory");
				if (getInteger(stack, "Reload") >= 1)
					return new ModelResourceLocation("skill:blankcrossbow_pulling_1", "inventory");
				return new ModelResourceLocation("skill:blankcrossbow_pulling_2", "inventory");
			}
		}
		return new ModelResourceLocation(Main.MODID + ":" + stack.getUnlocalizedName().substring(5), "inventory");
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack = setupStack(stack);

	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.BOW;
	}

	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	/**
	 * Basically any gun you canthink of that uses non-exploding bullets
	 * 
	 * @param S
	 *            - Ticks of cooldown
	 * @param D
	 *            - Damage dealt by each bullet
	 * @param R
	 *            - Number of blocks player is launched back per bullet
	 * @param A
	 *            - 0 = perfect accuracy , 1 = one block off
	 * @param P
	 *            - Power in each arrow
	 * @param K
	 *            - Knockback, duh
	 * @param N
	 *            - Number of Bullets per shot
	 * @param F
	 *            - How long the arrow is on fire (0 for not at all)
	 * @param M
	 *            - The Ammo (null if none)
	 * @param P
	 *            - Particle Effect shot
	 * @param T
	 *            - Type of bullet (Normal, Explosive, Hook)
	 */
	public ItemGun(String name, int color, int S, double D, double R, double A, float P, int K, int N, int F, Item M,
			EnumParticleTypes p, Integer E, Boolean xg, double xs, int BT, int tier) {
		super();

		this.setUnlocalizedName(name);
		// System.out.println("ItemGun created: " + this.getUnlocalizedName());
		Color = color;
		FireRate = S;
		Damage = D;
		Recoil = R;
		Accuracy = A;
		Power = P;
		Knockback = K;
		Number = N;
		FireTime = F;
		Ammo = M;
		particle = p;
		Type = E;
		Gravity = xg;
		BulletType = BT;
		this.tier = tier;
		this.setMaxDamage(FireRate + 1);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setMaxStackSize(1);
		this.setFull3D();
	}

	private int tier;

	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
		stack = setupStack(stack);
		if (((EntityPlayer) entity).capabilities.isCreativeMode && p_77663_5_) {
			stack.getTagCompound().setInteger("Level", 5);
		}
		if (stack.getTagCompound().getInteger("Reload") != 0 && (p_77663_5_ || getInteger(stack, "Level") > 2)
				&& (((EntityPlayer) entity).isSneaking() || getInteger(stack, "Level") > 1)) {
			stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") - 1);
			// stack.setItemDamage(stack.getTagCompound().getInteger("Reload"));
		}
		if (getInteger(stack, "Reload") > 0 && getInteger(stack, "Level") > 1 && ((EntityPlayer) entity).isSneaking()) {
			stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") - 1);
		}
		if (getInteger(stack, "Reload") > 0 && getInteger(stack, "Level") > 2 && p_77663_5_) {
			stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") - 1);
		}
		if (getString(stack, "Name").contains("Automatic") && stack.getItem() == ModItems.crossbow) {
			if (stack.getTagCompound().getInteger("Reload") != 0 && (p_77663_5_ || getInteger(stack, "Level") > 2)
					&& (((EntityPlayer) entity).isSneaking() || getInteger(stack, "Level") > 1)) {
				stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") - 1);
				// stack.setItemDamage(stack.getTagCompound().getInteger("Reload"));
			}
			if (getInteger(stack, "Reload") > 0 && getInteger(stack, "Level") > 1
					&& ((EntityPlayer) entity).isSneaking()) {
				stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") - 1);
			}
			if (getInteger(stack, "Reload") > 0 && getInteger(stack, "Level") > 2 && p_77663_5_) {
				stack.getTagCompound().setInteger("Reload", stack.getTagCompound().getInteger("Reload") - 1);
			}

		}
		if (getInteger(stack, "Level") > 5) {
			if (getInteger(stack, "Reload") > 0)
				;
			stack.getTagCompound().setInteger("Reload", 0);

		}
		// stack.setItemDamage(stack.getTagCompound().getInteger("Reload"));

	}

	private String getString(ItemStack stack, String key) {
		return stack.getTagCompound().getString(key);
	}

	private float getFloat(ItemStack stack, String key) {
		return stack.getTagCompound().getFloat(key);
	}

	private Integer getInteger(ItemStack stack, String key) {
		return stack.getTagCompound().getInteger(key);
	}

	private Double getDouble(ItemStack stack, String key) {
		return stack.getTagCompound().getDouble(key);
	}

	private boolean getBoolean(ItemStack stack, String key) {
		return stack.getTagCompound().getBoolean(key);
	}

	private Item getItem(ItemStack stack, String key) {
		return Item.getItemById(stack.getTagCompound().getInteger(key));
	}

	public double getDurabilityForDisplay(ItemStack stack) {
		stack = setupStack(stack);
		if (stack.getTagCompound().getInteger("FireRate") != 0)
			return (double) stack.getTagCompound().getInteger("Reload")
					/ (double) stack.getTagCompound().getInteger("FireRate");
		else
			return 1.0;

	}

	public ItemStack setupStack(ItemStack stack) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if (!stack.getTagCompound().hasKey("FireRate"))
			stack.getTagCompound().setInteger("FireRate", FireRate);
		if (!stack.getTagCompound().hasKey("Damage"))
			stack.getTagCompound().setDouble("Damage", Damage);
		if (!stack.getTagCompound().hasKey("Color0"))
			stack.getTagCompound().setInteger("Color0", Color);
		if (!stack.getTagCompound().hasKey("Color1"))
			stack.getTagCompound().setInteger("Color1", 6579300);
		if (!stack.getTagCompound().hasKey("Color2"))
			stack.getTagCompound().setInteger("Color2", 11842740);
		if (!stack.getTagCompound().hasKey("Color3"))
			stack.getTagCompound().setInteger("Color3", 16777215);
		if (!stack.getTagCompound().hasKey("Recoil"))
			stack.getTagCompound().setDouble("Recoil", Recoil);
		if (!stack.getTagCompound().hasKey("Accuracy"))
			stack.getTagCompound().setDouble("Accuracy", Accuracy);
		if (!stack.getTagCompound().hasKey("Power"))
			stack.getTagCompound().setFloat("Power", Power);
		if (!stack.getTagCompound().hasKey("Knockback"))
			stack.getTagCompound().setInteger("Knockback", Knockback);
		if (!stack.getTagCompound().hasKey("BulletCount"))
			stack.getTagCompound().setInteger("BulletCount", Number);
		if (!stack.getTagCompound().hasKey("FireTime"))
			stack.getTagCompound().setInteger("FireTime", FireTime);
		if (!stack.getTagCompound().hasKey("AmmoType"))
			stack.getTagCompound().setInteger("AmmoType", Item.getIdFromItem(Ammo));
		if (!stack.getTagCompound().hasKey("Type"))
			stack.getTagCompound().setInteger("Type", Type);
		if (!stack.getTagCompound().hasKey("Mode"))
			stack.getTagCompound().setInteger("Mode", 0);
		if (!stack.getTagCompound().hasKey("Gravity"))
			stack.getTagCompound().setBoolean("Gravity", Gravity);
		if (!stack.getTagCompound().hasKey("Burnup"))
			stack.getTagCompound().setDouble("Burnup", 0);
		if (!stack.getTagCompound().hasKey("Level"))
			stack.getTagCompound().setInteger("Level", 0);
		if (!stack.getTagCompound().hasKey("Exp"))
			stack.getTagCompound().setInteger("Exp", 0);
		if (!stack.getTagCompound().hasKey("Material"))
			stack.getTagCompound().setInteger("Material", 0);
		if (!stack.getTagCompound().hasKey("Name"))
			stack.getTagCompound().setString("Name", stack.getDisplayName());
		// stack.getTagCompound().setBoolean("Unbreakable", true);
		if (!stack.getTagCompound().hasKey("Bonus"))
			stack.getTagCompound().setTag("Bonus", new NBTTagCompound());
		return stack;
	}

	public ItemStack modStats(ItemStack stack, Random rand) {
		stack.getTagCompound().setInteger("FireRate", (int) (FireRate * (Math.abs(rand.nextGaussian() / 2 + 1))));
		stack.getTagCompound().setDouble("Damage", Damage * ((Math.abs(rand.nextGaussian() / 2 + 1))));
		stack.getTagCompound().setInteger("Color0", Color);
		stack.getTagCompound().setDouble("Recoil", Recoil * (Math.abs(rand.nextGaussian() / 2 + 1)));
		stack.getTagCompound().setDouble("Accuracy", Accuracy * (Math.abs(rand.nextGaussian() / 2 + 1)));
		stack.getTagCompound().setFloat("Power", (float) (Power * ((Math.abs(rand.nextGaussian() / 2 + 1) + 0.5))));
		stack.getTagCompound().setInteger("Knockback", (int) (Knockback * (Math.abs(rand.nextGaussian() / 2 + 1))));
		return stack;
	}

	public String getItemStackDisplayName(ItemStack stack) {
		if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Name")
				|| getString(stack, "Name").contains("Hidden"))
			return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		else
			return getString(stack, "Name");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		stack = setupStack(stack);
		list.add("§eRanged Item");
		list.add("§eUncommon Item");
		if (KeyBindings.shiftHeld()) {
			double Damage = (double) ((int) (getDouble(stack, "Damage") * 1000)) / 1000;
			double RateofFire = (double) ((int) (getInteger(stack, "FireRate") * 1000)) / 1000;
			double Recoil = (double) ((int) (getDouble(stack, "Recoil") * 1000)) / 1000;
			double Accuracy = (double) ((int) (getDouble(stack, "Accuracy") * 1000)) / 1000;
			double Velocity = (double) ((int) (getFloat(stack, "Power") * 1000));
			list.add("Damage: " + Damage + " Points per Bullet");
			list.add("Fire Rate: " + RateofFire + " Ticks Delay");
			list.add("Recoil: " + Recoil + " Blocks per bullet");
			list.add("Accuracy: " + Accuracy + " Meter Standard Deviation");
			list.add("Bullet Velocity: " + Velocity + " Millimeters per Second");
			list.add("Knockback: " + getInteger(stack, "Knockback") + " Power per Bullet");
			list.add("Bullets: " + getInteger(stack, "BulletCount") + " per Shot");
			if (getItem(stack, "AmmoType") != null)
				list.add("Ammo Type: " + getItem(stack, "AmmoType")
						.getItemStackDisplayName(new ItemStack(getItem(stack, "AmmoType"))));
			else
				list.add("Ammo Type: Does not consume Ammo");
		} else
			list.add("Press shift for detailed gun stats");
		if (KeyBindings.ctrlHeld()) {
			if (getString(stack, "Name") != "") {
				if (!getString(stack, "Name").contains("Hidden"))
					list.add(getString(stack, "Name"));
				else
					list.add("§o" + getString(stack, "Name").replace("Hidden ", ""));
			}
			NBTTagCompound bonus = ((NBTTagCompound) stack.getTagCompound().getTag("Bonus"));
			Object[] all = bonus.getKeySet().toArray();
			for (int i = 0; i < all.length; i++) {
				if (bonus.getInteger((String) all[i]) == 1) {
					list.add(all[i]);
				} else if (bonus.getInteger((String) all[i]) > 1) {
					list.add((String) all[i] + " " + bonus.getInteger((String) all[i]));
				}
			}
			int i = getInteger(stack, "Level");
			String t = "";
			for (int l = 0; l < i; l++) {
				t += "§6X";
			}
			list.add(t);
			i = getInteger(stack, "Exp");
			t = "";
			for (int l = 0; l < 8; l++) {
				if (i > l + 40) {
					t += "§6█";
				} else if (i > l + 32) {
					t += "§7█";
				} else if (i > l + 24) {
					t += "§5█";
				} else if (i > l + 16) {
					t += "§4█";
				} else if (i > l + 8) {
					t += "§3█";
				} else if (i > l) {
					t += "§2█";
				} else {
					t += "§4-";
				}
			}
			if (getInteger(stack, "Level") != 5)
				list.add(t);
		} else
			list.add("Press ctrl for gun bonuses and experience");
	}

	private NBTTagCompound bonus(ItemStack stack) {
		return ((NBTTagCompound) stack.getTagCompound().getTag("Bonus"));
	}

	public ItemStack onItemRightClick(ItemStack stack, World p_77659_2_, EntityPlayer player) {
		if (stack.getTagCompound().getInteger("Reload") == 0 && stack.getTagCompound().getDouble("Burnup") <= 10) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		} else if (player.isUsingItem())
			player.stopUsingItem();
		return stack;
	}

	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {

	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {

		{
			Random rand = new Random();

			BulletEvent event = new BulletEvent(player, stack, count, getItem(stack, "AmmoType"));
			MinecraftForge.EVENT_BUS.post(event);
			if (event.isCanceled()) {
				return;
			}
			stack = setupStack(stack);
			{
				if (stack.getTagCompound().getInteger("Reload") == 0) {
					if (getInteger(stack, "FireRate") == 0) {
						player.addPotionEffect(
								new PotionEffect(Potion.moveSlowdown.getId(), 5, 8 - getInteger(stack, "Level")));
					}
					for (int c = 1; c <= getInteger(stack, "BulletCount"); c++) {
						boolean flag = player.capabilities.isCreativeMode
								|| (getItem(stack, "AmmoType") != ModItems.Ammo11);
						int flag1 = 0;
						int flag2 = 0;
						int flag3 = 0;

						int BulletType = this.BulletType;

						if (getItem(stack, "AmmoType") == ModItems.Ammo11 && player.inventory.hasItem(ModItems.Ammo21)
								&& !player.inventory.hasItem(ModItems.Ammo11)) {
							flag1 = 1;
						}

						if (getItem(stack, "AmmoType") == ModItems.Ammo11 && player.inventory.hasItem(ModItems.Ammo31)
								&& !player.inventory.hasItem(ModItems.Ammo21)
								&& !player.inventory.hasItem(ModItems.Ammo11)) {
							flag2 = 1;
						}

						if (flag || player.inventory.hasItem(getItem(stack, "AmmoType")) || flag1 >= 1 || flag2 >= 1) {

							if (getInteger(stack, "Type") == 0) {

								if (flag1 == 1) {
									player.inventory.consumeInventoryItem(ModItems.Ammo21);
									player.inventory.addItemStackToInventory(new ItemStack(ModItems.Ammo11, 9));
								}
								if (flag2 == 1) {
									player.inventory.consumeInventoryItem(ModItems.Ammo31);
									player.inventory.addItemStackToInventory(new ItemStack(ModItems.Ammo21, 8));
									player.inventory.addItemStackToInventory(new ItemStack(ModItems.Ammo11, 9));
								}

								if (!flag) {
									if (getItem(stack, "AmmoType") == ModItems.Ammo11)
										player.inventory.consumeInventoryItem(ModItems.Ammo11);

								}
								EntityBullet entityarrow;
								Item ammo = getItem(stack, "AmmoType");
								if (player.capabilities.isCreativeMode)
									ammo = null;
								if (BulletType < 4) {
									entityarrow = new EntityBullet(player.getEntityWorld(), player,
											getFloat(stack, "Power"), ammo, BulletType);
								} else {
									entityarrow = new EntityBullet(player.getEntityWorld(), player,
											getFloat(stack, "Power"), ammo, 0);
								}
								if (flag) {
									entityarrow.canBePickedUp = 2;
								}
								if (getInteger(stack, "FireRate") != 0) {
									stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								}
								entityarrow.setPosition(entityarrow.posX + entityarrow.motionX / 3,
										entityarrow.posY + entityarrow.motionY / 3,
										entityarrow.posZ + entityarrow.motionZ / 3);
								entityarrow.setKnockbackStrength(getInteger(stack, "Knockback"));
								entityarrow.setInvisible(true);
								entityarrow.setFire(getInteger(stack, "FireTime"));
								if (getInteger(stack, "Level") > 4
										&& (((EntityPlayer) player).isSneaking())) {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									entityarrow.addVelocity(f1, f2, f3);
								} else {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									entityarrow.addVelocity(f1, f2, f3);
								}
								entityarrow.setDamage(getInteger(stack, "Damage"));
								player.worldObj.spawnParticle(particle, entityarrow.posX, entityarrow.posY,
										entityarrow.posZ, entityarrow.motionX, entityarrow.motionY,
										entityarrow.motionZ);
								stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								player.worldObj.spawnEntityInWorld(entityarrow);
								player.addVelocity(
										(double) -(-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"));
							} else if (getInteger(stack, "Type") == 2) {

								EntityHook entityarrow = new EntityHook(player.getEntityWorld(), player,
										getFloat(stack, "Power"), null, getBoolean(stack, "Gravity"));
								if (getInteger(stack, "FireRate") != 0) {
									stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								}
								entityarrow.setPosition(entityarrow.posX + entityarrow.motionX / 3,
										entityarrow.posY + entityarrow.motionY / 3,
										entityarrow.posZ + entityarrow.motionZ / 3);
								entityarrow.setKnockbackStrength(getInteger(stack, "Knockback"));
								// entityarrow.setInvisible(true);
								entityarrow.setIsCritical(true);
								entityarrow.setFire(getInteger(stack, "FireTime"));
								if (getInteger(stack, "Level") > 4
										&& (((EntityPlayer) player).isSneaking())) {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									entityarrow.addVelocity(f1, f2, f3);
								} else {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									entityarrow.addVelocity(f1, f2, f3);
								}
								for (int c1 = 0; c1 < 1; ++c1) {
									player.worldObj.spawnParticle(particle, entityarrow.posX, entityarrow.posY,
											entityarrow.posZ, entityarrow.motionX, entityarrow.motionY,
											entityarrow.motionZ);
								}
								stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								entityarrow.setDamage(getInteger(stack, "Damage"));
								player.worldObj.spawnEntityInWorld(entityarrow);
								player.addVelocity(
										(double) -(-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"));

							} else if (getInteger(stack, "Type") == 3) {

								EntityHookSpec entityarrow = new EntityHookSpec(player.getEntityWorld(), player,
										getFloat(stack, "Power"), null, getBoolean(stack, "Gravity"));
								if (getInteger(stack, "FireRate") != 0) {
									stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								}
								entityarrow.setPosition(entityarrow.posX + entityarrow.motionX / 3,
										entityarrow.posY + entityarrow.motionY / 3,
										entityarrow.posZ + entityarrow.motionZ / 3);
								entityarrow.setKnockbackStrength(getInteger(stack, "Knockback"));
								// entityarrow.setInvisible(true);
								entityarrow.setIsCritical(true);
								entityarrow.setFire(getInteger(stack, "FireTime"));
								if (getInteger(stack, "Level") > 4
										&& (((EntityPlayer) player).isSneaking())) {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									entityarrow.addVelocity(f1, f2, f3);
								} else {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									entityarrow.addVelocity(f1, f2, f3);
								}
								for (int c1 = 0; c1 < 1; ++c1) {
									player.worldObj.spawnParticle(particle, entityarrow.posX, entityarrow.posY,
											entityarrow.posZ, entityarrow.motionX, entityarrow.motionY,
											entityarrow.motionZ);
								}
								stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								entityarrow.setDamage(getInteger(stack, "Damage"));
								player.worldObj.spawnEntityInWorld(entityarrow);
								player.addVelocity(
										(double) -(-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"));

							} else if (getInteger(stack, "Type") == 33) {

								if (flag1 == 1) {
									player.inventory.consumeInventoryItem(ModItems.Ammo21);
									player.inventory.addItemStackToInventory(new ItemStack(ModItems.Ammo11, 9));
								}
								if (flag2 == 1) {
									player.inventory.consumeInventoryItem(ModItems.Ammo31);
									player.inventory.addItemStackToInventory(new ItemStack(ModItems.Ammo21, 8));
									player.inventory.addItemStackToInventory(new ItemStack(ModItems.Ammo11, 9));
								}

								if (!flag) {
									if (getItem(stack, "AmmoType") == ModItems.Ammo11)
										player.inventory.consumeInventoryItem(ModItems.Ammo11);

								}
								EntityBulletChisel entityarrow;
								Item ammo = getItem(stack, "AmmoType");
								if (player.capabilities.isCreativeMode)
									ammo = null;
								if (BulletType < 4) {
									entityarrow = new EntityBulletChisel(player.getEntityWorld(), player,
											getFloat(stack, "Power"), ammo, BulletType);
								} else {
									entityarrow = new EntityBulletChisel(player.getEntityWorld(), player,
											getFloat(stack, "Power"), ammo, 0);
								}
								if (flag) {
									entityarrow.canBePickedUp = 2;
								}
								if (getInteger(stack, "FireRate") != 0) {
									stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								}
								entityarrow.setPosition(entityarrow.posX + entityarrow.motionX / 3,
										entityarrow.posY + entityarrow.motionY / 3,
										entityarrow.posZ + entityarrow.motionZ / 3);
								entityarrow.setKnockbackStrength(getInteger(stack, "Knockback"));
								entityarrow.setInvisible(true);
								entityarrow.setFire(getInteger(stack, "FireTime"));
								if (getInteger(stack, "Level") > 4
										&& (((EntityPlayer) player).isSneaking() )) {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									entityarrow.addVelocity(f1, f2, f3);
								} else {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									entityarrow.addVelocity(f1, f2, f3);
								}
								entityarrow.setDamage(getInteger(stack, "Damage"));
								player.worldObj.spawnParticle(particle, entityarrow.posX, entityarrow.posY,
										entityarrow.posZ, entityarrow.motionX, entityarrow.motionY,
										entityarrow.motionZ);
								stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								player.worldObj.spawnEntityInWorld(entityarrow);
								player.addVelocity(
										(double) -(-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"));
							} else if (getInteger(stack, "Type") == 9 && !player.worldObj.isRemote) {
								// System.out.println("Final 1");
								player.inventory.consumeInventoryItem(getItem(stack, "Ammo"));
								if (getInteger(stack, "FireRate") != 0) {
									stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));
								}
								EntityArrow entityarrow = new EntityArrow(player.getEntityWorld(), player,
										getFloat(stack, "Power"));
								if (getString(stack, "Name").contains("Semi-Automatic")) {
									stack.getTagCompound().setInteger("Reload",
											(int) getInteger(stack, "FireRate") / 2);
								} else if (getString(stack, "Name").contains("Dragonskin"))
									entityarrow = new EntityArrowDragonSkin(player.getEntityWorld(), player,
											getFloat(stack, "Power"));
								else if (getString(stack, "Name").contains("Light"))
									entityarrow = new EntityArrowLight(player.getEntityWorld(), player,
											getFloat(stack, "Power"));

								if (player.capabilities.isCreativeMode)
									entityarrow.canBePickedUp = 2;
								entityarrow.setPosition(entityarrow.posX + entityarrow.motionX / 3,
										entityarrow.posY + entityarrow.motionY / 3,
										entityarrow.posZ + entityarrow.motionZ / 3);
								entityarrow.setDamage(getInteger(stack, "Damage"));
								entityarrow.setKnockbackStrength(getInteger(stack, "Knockback"));
								// entityarrow.setInvisible(true);
								entityarrow.setIsCritical(true);
								entityarrow.setFire(getInteger(stack, "FireTime"));
								if (getInteger(stack, "Level") > 4
										&& (((EntityPlayer) player).isSneaking())) {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy") / 2;
									entityarrow.addVelocity(f1, f2, f3);
								} else {
									double f1 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f2 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									double f3 = (rand.nextGaussian()) * getDouble(stack, "Accuracy");
									entityarrow.addVelocity(f1, f2, f3);
								}
								for (int c1 = 0; c1 < 0; ++c1) {
									player.worldObj.spawnParticle(particle, entityarrow.posX, entityarrow.posY,
											entityarrow.posZ, entityarrow.motionX, entityarrow.motionY,
											entityarrow.motionZ);
								}
								stack.getTagCompound().setInteger("Reload", (int) getInteger(stack, "FireRate"));

								player.worldObj.spawnEntityInWorld(entityarrow);
								player.addVelocity(
										(double) -(-MathHelper.sin(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(-MathHelper.sin(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"),
										(double) -(MathHelper.cos(player.rotationYaw / 180.0F * (float) Math.PI)
												* MathHelper.cos(player.rotationPitch / 180.0F * (float) Math.PI))
												* getDouble(stack, "Recoil"));

							}

						}
					}
				} else {
					player.stopUsingItem();
				}
			}
		}
	}
}
