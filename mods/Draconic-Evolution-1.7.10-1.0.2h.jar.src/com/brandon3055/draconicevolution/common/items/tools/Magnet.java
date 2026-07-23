/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Magnet
/*     */   extends ItemDE
/*     */ {
/*     */   private IIcon draconium;
/*     */   private IIcon awakened;
/*     */   
/*     */   public Magnet() {
/*  37 */     func_77655_b("magnet");
/*  38 */     func_77637_a(DraconicEvolution.tabBlocksItems);
/*  39 */     func_77625_d(1);
/*  40 */     ModItems.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  45 */     this.draconium = iconRegister.func_94245_a("draconicevolution:magnetWyvern");
/*  46 */     this.awakened = iconRegister.func_94245_a("draconicevolution:magnetDraconic");
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int dmg) {
/*  51 */     return (dmg == 0) ? this.draconium : this.awakened;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77614_k() {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs p_150895_2_, List<ItemStack> list) {
/*  63 */     list.add(new ItemStack(item, 1, 0));
/*  64 */     list.add(new ItemStack(item, 1, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  69 */     return super.func_77667_c(itemStack) + ((itemStack.func_77960_j() == 0) ? ".wyvern" : ".draconic");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean hasEffect(ItemStack stack, int pass) {
/*  75 */     return ItemNBTHelper.getBoolean(stack, "MagnetEnabled", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack stack, World world, Entity entity, int slot, boolean hotbar) {
/*  82 */     if (world.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     if (!entity.func_70093_af() && entity.field_70173_aa % 5 == 0 && ItemNBTHelper.getBoolean(stack, "MagnetEnabled", false) && entity instanceof EntityPlayer) {
/*  87 */       int range = (stack.func_77960_j() == 0) ? 8 : 32;
/*     */       
/*  89 */       List<EntityItem> items = world.func_72872_a(EntityItem.class, AxisAlignedBB.func_72330_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v)
/*  90 */           .func_72314_b(range, range, range));
/*     */       
/*  92 */       boolean flag = false;
/*     */       
/*  94 */       for (EntityItem item : items) {
/*  95 */         if (item.func_92059_d() == null) {
/*     */           continue;
/*     */         }
/*     */         
/*  99 */         String name = Item.field_150901_e.func_148750_c(item.func_92059_d().func_77973_b());
/* 100 */         if (ConfigHandler.itemDislocatorBlacklistMap.containsKey(name) && (((Integer)ConfigHandler.itemDislocatorBlacklistMap.get(name)).intValue() == -1 || ((Integer)ConfigHandler.itemDislocatorBlacklistMap.get(name)).intValue() == item.func_92059_d()
/* 101 */           .func_77960_j())) {
/*     */           continue;
/*     */         }
/* 104 */         flag = true;
/*     */         
/* 106 */         if (item.field_145804_b > 0)
/* 107 */           item.field_145804_b = 0; 
/* 108 */         item.field_70159_w = item.field_70181_x = item.field_70179_y = 0.0D;
/* 109 */         item.func_70107_b(entity.field_70165_t - 0.2D + world.field_73012_v.nextDouble() * 0.4D, entity.field_70163_u - 0.6D, entity.field_70161_v - 0.2D + world.field_73012_v.nextDouble() * 0.4D);
/*     */       } 
/* 111 */       if (flag) {
/* 112 */         world.func_72956_a(entity, "random.orb", 0.1F, 0.5F * ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7F + 2.0F));
/*     */       }
/* 114 */       List<EntityXPOrb> xp = world.func_72872_a(EntityXPOrb.class, AxisAlignedBB.func_72330_a(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v)
/* 115 */           .func_72314_b(4.0D, 4.0D, 4.0D));
/*     */       
/* 117 */       EntityPlayer player = (EntityPlayer)entity;
/*     */       
/* 119 */       for (EntityXPOrb orb : xp) {
/* 120 */         if (world.field_72995_K || 
/* 121 */           orb.field_70532_c != 0 || 
/* 122 */           MinecraftForge.EVENT_BUS.post((Event)new PlayerPickupXpEvent(player, orb)))
/*     */           continue; 
/* 124 */         world.func_72956_a((Entity)player, "random.orb", 0.1F, 0.5F * ((world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.7F + 1.8F));
/* 125 */         player.func_71001_a((Entity)orb, 1);
/* 126 */         player.func_71023_q(orb.field_70530_e);
/* 127 */         orb.func_70106_y();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 136 */     if (player.func_70093_af())
/* 137 */       ItemNBTHelper.setBoolean(stack, "MagnetEnabled", !ItemNBTHelper.getBoolean(stack, "MagnetEnabled", false)); 
/* 138 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
/* 144 */     list.add(StatCollector.func_74838_a("info.de.shiftRightClickToActivate.txt"));
/* 145 */     int range = (stack.func_77960_j() == 0) ? 8 : 32;
/* 146 */     list.add(InfoHelper.HITC() + range + InfoHelper.ITC() + " " + StatCollector.func_74838_a("info.de.blockRange.txt"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\Magnet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */