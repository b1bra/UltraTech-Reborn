/*     */ package com.brandon3055.draconicevolution.common.handler;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.achievements.Achievements;
/*     */ import com.brandon3055.draconicevolution.common.entity.ExtendedPlayer;
/*     */ import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.MountUpdatePacket;
/*     */ import com.brandon3055.draconicevolution.common.network.SpeedRequestPacket;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileGrinder;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import cpw.mods.fml.common.eventhandler.EventPriority;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.loliland.mctags.api.Tags;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.entity.EntityEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDeathEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingDropsEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*     */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ 
/*     */ 
/*     */ public class MinecraftForgeEventHandler
/*     */ {
/*  58 */   Random random = new Random();
/*     */   
/*     */   private static Method becomeAngryAt;
/*  61 */   public static double maxSpeed = 10.0D;
/*  62 */   public static int ticksSinceRequest = 0;
/*     */   
/*     */   public static boolean speedNeedsUpdating = true;
/*  65 */   private Field persistenceRequired = null;
/*     */   
/*     */   public MinecraftForgeEventHandler() {
/*     */     try {
/*  69 */       this.persistenceRequired = ReflectionHelper.findField(EntityLiving.class, new String[] { "field_82179_bU", "persistenceRequired" });
/*  70 */     } catch (Exception e) {
/*  71 */       LogHelper.error("Unable to find field \"persistenceRequired\"");
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
/*  77 */     EntityLivingBase entity = event.entityLiving;
/*     */     
/*  79 */     if (entity.getEntityData().func_74764_b("SpawnedByDESpawner")) {
/*  80 */       long spawnTime = entity.getEntityData().func_74763_f("SpawnedByDESpawner");
/*  81 */       long livedFor = entity.field_70170_p.func_82737_E() - spawnTime;
/*     */       
/*  83 */       if (livedFor > 600L && this.persistenceRequired != null) {
/*     */         try {
/*  85 */           this.persistenceRequired.setBoolean(entity, false);
/*  86 */           entity.getEntityData().func_82580_o("SpawnedByDESpawner");
/*  87 */         } catch (Exception e) {
/*  88 */           LogHelper.warn("Error occured while resetting entity persistence: " + e);
/*  89 */           entity.getEntityData().func_82580_o("SpawnedByDESpawner");
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  95 */     if (!event.entityLiving.field_70170_p.field_72995_K || !(event.entityLiving instanceof EntityPlayerSP))
/*  96 */       return;  EntityPlayerSP player = (EntityPlayerSP)entity;
/*     */     
/*  98 */     double motionX = player.field_70159_w;
/*  99 */     double motionZ = player.field_70179_y;
/* 100 */     double motion = Math.sqrt(motionX * motionX + motionZ * motionZ);
/* 101 */     double reduction = motion - maxSpeed;
/*     */     
/* 103 */     if (motion > maxSpeed && (player.field_70122_E || player.field_71075_bZ.field_75100_b)) {
/* 104 */       player.field_70159_w -= motionX * reduction;
/* 105 */       player.field_70179_y -= motionZ * reduction;
/*     */     } 
/*     */     
/* 108 */     if (speedNeedsUpdating) {
/* 109 */       if (ticksSinceRequest == 0) {
/* 110 */         DraconicEvolution.network.sendToServer((IMessage)new SpeedRequestPacket());
/* 111 */         LogHelper.info("Requesting speed packet from server");
/*     */       } 
/* 113 */       ticksSinceRequest++;
/* 114 */       if (ticksSinceRequest > 500) ticksSinceRequest = 0; 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingHurt(LivingHurtEvent event) {
/* 120 */     if (event.entityLiving instanceof EntityPlayer) {
/* 121 */       CustomArmorHandler.onPlayerHurt(event);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onLivingDeath(LivingDeathEvent event) {
/* 127 */     if (event.entityLiving instanceof EntityPlayer) {
/* 128 */       CustomArmorHandler.onPlayerDeath(event);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
/* 134 */     if (!(event.entityLiving instanceof EntityPlayer))
/* 135 */       return;  EntityPlayer player = (EntityPlayer)event.entityLiving;
/* 136 */     CustomArmorHandler.ArmorSummery summery = (new CustomArmorHandler.ArmorSummery()).getSummery(player);
/*     */     
/* 138 */     if (summery != null && summery.jumpModifier > 0.0F) {
/* 139 */       player.field_70181_x += (summery.jumpModifier * 0.1F);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onLivingAttack(LivingAttackEvent event) {
/* 145 */     if (!(event.entityLiving instanceof EntityPlayer))
/*     */       return; 
/* 147 */     CustomArmorHandler.onPlayerAttacked(event);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onDropEvent(LivingDropsEvent event) {
/* 152 */     if (event.entity.field_70170_p.field_72995_K || (!event.source.field_76373_n.equals("player") && !event.source.field_76373_n.equals("arrow")) || !isValidEntity(event.entityLiving)) {
/*     */       return;
/*     */     }
/*     */     
/* 156 */     EntityLivingBase entity = event.entityLiving;
/* 157 */     Entity attacker = event.source.func_76346_g();
/*     */     
/* 159 */     if (!(attacker instanceof EntityPlayer)) {
/*     */       return;
/*     */     }
/*     */     
/* 163 */     int dropChanceModifier = getDropChanceFromItem(((EntityPlayer)attacker).func_70694_bm());
/*     */     
/* 165 */     if (dropChanceModifier == 0)
/*     */       return; 
/* 167 */     World world = entity.field_70170_p;
/* 168 */     int rand = this.random.nextInt(Math.max(ConfigHandler.soulDropChance / dropChanceModifier, 1));
/* 169 */     int rand2 = this.random.nextInt(Math.max(ConfigHandler.passiveSoulDropChance / dropChanceModifier, 1));
/* 170 */     boolean isAnimal = entity instanceof net.minecraft.entity.passive.EntityAnimal;
/*     */     
/* 172 */     if ((rand == 0 && !isAnimal) || (rand2 == 0 && isAnimal)) {
/* 173 */       ItemStack soul = new ItemStack((Item)ModItems.mobSoul);
/* 174 */       String name = EntityList.func_75621_b((Entity)entity);
/* 175 */       ItemNBTHelper.setString(soul, "Name", name);
/* 176 */       if (entity instanceof EntitySkeleton) {
/* 177 */         ItemNBTHelper.setInteger(soul, "SkeletonType", ((EntitySkeleton)entity).func_82202_m());
/*     */       }
/* 179 */       world.func_72838_d((Entity)new EntityItem(world, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, soul));
/* 180 */       Achievements.triggerAchievement((EntityPlayer)attacker, "draconicevolution.soul");
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getDropChanceFromItem(ItemStack stack) {
/* 185 */     int chance = 0;
/* 186 */     if (stack == null) return 0; 
/* 187 */     if (stack.func_77973_b().equals(ModItems.wyvernBow) || stack.func_77973_b().equals(ModItems.wyvernSword)) chance++; 
/* 188 */     if (stack.func_77973_b().equals(ModItems.draconicSword) || stack.func_77973_b().equals(ModItems.draconicBow)) chance += 2; 
/* 189 */     if (stack.func_77973_b().equals(ModItems.draconicDestructionStaff)) chance += 3;
/*     */     
/* 191 */     chance += EnchantmentHelper.func_77506_a(ConfigHandler.reaperEnchantID, stack);
/* 192 */     return chance;
/*     */   }
/*     */   
/*     */   private boolean isValidEntity(EntityLivingBase entity) {
/* 196 */     if (entity instanceof net.minecraft.entity.boss.IBossDisplayData) {
/* 197 */       return false;
/*     */     }
/* 199 */     for (int i = 0; i < ConfigHandler.spawnerList.length; i++) {
/* 200 */       if (ConfigHandler.spawnerList[i].equals(entity.func_70005_c_()) && ConfigHandler.spawnerListType)
/* 201 */         return true; 
/* 202 */       if (ConfigHandler.spawnerList[i].equals(entity.func_70005_c_()) && !ConfigHandler.spawnerListType) {
/* 203 */         return false;
/*     */       }
/*     */     } 
/* 206 */     return !ConfigHandler.spawnerListType;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityConstructing(EntityEvent.EntityConstructing event) {
/* 211 */     if (event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer)event.entity) == null)
/* 212 */       ExtendedPlayer.register((EntityPlayer)event.entity); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void itemTooltipEvent(ItemTooltipEvent event) {
/* 217 */     if (ConfigHandler.showUnlocalizedNames) event.toolTip.add(event.itemStack.func_77977_a()); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void stopUsingEvent(PlayerUseItemEvent.Start event) {
/* 222 */     if (!ConfigHandler.pigmenBloodRage || event.item == null || event.item.func_77973_b() == null)
/* 223 */       return;  if (Tags.Items.PORKCHOP.is(event.item) || Tags.Items.COOKED_PORKCHOP.is(event.item)) {
/* 224 */       World world = event.entityPlayer.field_70170_p;
/* 225 */       if (world.field_72995_K)
/* 226 */         return;  EntityPlayer player = event.entityPlayer;
/* 227 */       List list = world.func_72872_a(EntityPigZombie.class, AxisAlignedBB.func_72330_a(player.field_70165_t - 32.0D, player.field_70163_u - 32.0D, player.field_70161_v - 32.0D, player.field_70165_t + 32.0D, player.field_70163_u + 32.0D, player.field_70161_v + 32.0D));
/*     */       
/* 229 */       EntityPigZombie entityPigZombie = new EntityPigZombie(world);
/* 230 */       entityPigZombie.func_70107_b(player.field_70165_t, player.field_70163_u, player.field_70161_v);
/*     */       
/* 232 */       boolean flag = false;
/*     */       
/* 234 */       for (Object o : list) {
/* 235 */         if (o instanceof EntityPigZombie) {
/* 236 */           EntityPigZombie zombie = (EntityPigZombie)o;
/* 237 */           if (becomeAngryAt == null) {
/* 238 */             becomeAngryAt = ReflectionHelper.findMethod(EntityPigZombie.class, zombie, new String[] { "becomeAngryAt", "func_70835_c" }, new Class[] { Entity.class });
/* 239 */             becomeAngryAt.setAccessible(true);
/*     */           } 
/*     */           
/*     */           try {
/* 243 */             becomeAngryAt.invoke(zombie, new Object[] { player });
/* 244 */           } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 245 */             e.printStackTrace();
/*     */           } 
/*     */           
/* 248 */           if (Math.abs(zombie.field_70165_t - player.field_70165_t) < 14.0D && Math.abs(zombie.field_70163_u - player.field_70163_u) < 14.0D && Math.abs(zombie.field_70161_v - player.field_70161_v) < 14.0D)
/* 249 */             flag = true; 
/* 250 */           zombie.func_70690_d(new PotionEffect(5, 10000, 3));
/* 251 */           zombie.func_70690_d(new PotionEffect(11, 10000, 2));
/*     */         } 
/*     */       } 
/*     */       
/* 255 */       if (flag) player.func_70690_d(new PotionEffect(2, 500, 3)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void joinWorld(EntityJoinWorldEvent event) {
/* 262 */     if (event.entity instanceof EntityPlayerSP) {
/* 263 */       speedNeedsUpdating = true;
/* 264 */       DraconicEvolution.network.sendToServer((IMessage)new MountUpdatePacket(0));
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void getBreakSpeed(PlayerEvent.BreakSpeed event) {
/* 270 */     if (event.entityPlayer != null) {
/* 271 */       float newDigSpeed = event.originalSpeed;
/* 272 */       CustomArmorHandler.ArmorSummery summery = (new CustomArmorHandler.ArmorSummery()).getSummery(event.entityPlayer);
/* 273 */       if (summery == null)
/*     */         return; 
/* 275 */       if (event.entityPlayer.func_70055_a(Material.field_151586_h) && 
/* 276 */         summery.flight[0]) newDigSpeed *= 5.0F;
/*     */ 
/*     */       
/* 279 */       if (!event.entityPlayer.field_70122_E && 
/* 280 */         summery.flight[0]) newDigSpeed *= 5.0F;
/*     */ 
/*     */       
/* 283 */       if (event.newSpeed > 1.0F) {
/* 284 */         newDigSpeed += event.newSpeed - 1.0F;
/*     */       }
/*     */       
/* 287 */       event.newSpeed = newDigSpeed;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void worldUnload(WorldEvent.Unload e) {
/* 293 */     TileGrinder.fakePlayer = null;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void playerInteract(PlayerInteractEvent event) {
/* 298 */     if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
/* 299 */       ForgeDirection face = ForgeDirection.getOrientation(event.face);
/* 300 */       int x = event.x + face.offsetX;
/* 301 */       int y = event.y + face.offsetY;
/* 302 */       int z = event.z + face.offsetZ;
/* 303 */       if (event.world.func_147439_a(x, y, z) == ModBlocks.safetyFlame) {
/* 304 */         event.world.func_147468_f(x, y, z);
/* 305 */         event.world.func_72908_a(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 1.0F, event.world.field_73012_v.nextFloat() * 0.1F + 2.0F);
/* 306 */         event.setCanceled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\handler\MinecraftForgeEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */