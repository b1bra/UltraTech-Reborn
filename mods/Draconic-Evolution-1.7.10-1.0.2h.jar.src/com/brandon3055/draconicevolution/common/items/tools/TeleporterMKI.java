/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Teleporter;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityPersistentItem;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeleporterMKI
/*     */   extends ItemDE
/*     */ {
/*     */   public TeleporterMKI(boolean MKII) {}
/*     */   
/*     */   public TeleporterMKI() {
/*  35 */     func_77655_b("teleporterMKI");
/*  36 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  37 */     func_77656_e(19);
/*  38 */     func_77625_d(1);
/*  39 */     ModItems.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  45 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:teleporterMKI");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
/*  50 */     if (getLocation(stack) == null) {
/*  51 */       if (player.field_70170_p.field_72995_K)
/*  52 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterUnSet.txt", new Object[0])); 
/*  53 */       return true;
/*     */     } 
/*     */     
/*  56 */     if (entity instanceof EntityPlayer) {
/*  57 */       if (player.field_70170_p.field_72995_K)
/*  58 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterPlayerT1.txt", new Object[0])); 
/*  59 */       return true;
/*     */     } 
/*     */     
/*  62 */     if (entity instanceof net.minecraft.entity.boss.IBossDisplayData || !(entity instanceof net.minecraft.entity.EntityLiving)) return true;
/*     */     
/*  64 */     if (player.func_110143_aJ() > 2.0F || player.field_71075_bZ.field_75098_d) {
/*  65 */       stack.func_77972_a(1, (EntityLivingBase)player);
/*  66 */       if (!player.field_71075_bZ.field_75098_d) player.func_70606_j(player.func_110143_aJ() - 2.0F); 
/*  67 */       getLocation(stack).sendEntityToCoords(entity);
/*  68 */       if (player.field_70170_p.field_72995_K)
/*  69 */         player.func_145747_a((IChatComponent)new ChatComponentText((new ChatComponentTranslation("msg.teleporterSentMob.txt", new Object[0])).func_150254_d() + " x:" + (int)getLocation(stack).getXCoord() + " y:" + (int)getLocation(stack).getYCoord() + " z:" + (int)getLocation(stack).getZCoord() + " Dimension: " + getLocation(stack).getDimensionName())); 
/*  70 */     } else if (player.field_70170_p.field_72995_K) {
/*  71 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterLowHealth.txt", new Object[0]));
/*     */     } 
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/*  78 */     if (player.func_70093_af()) {
/*  79 */       if (getLocation(stack) == null) {
/*  80 */         if (world.field_72995_K) {
/*  81 */           player.func_145747_a((IChatComponent)new ChatComponentText((new ChatComponentTranslation("msg.teleporterBound.txt", new Object[0])).func_150254_d() + "{X:" + (int)player.field_70165_t + " Y:" + (int)player.field_70163_u + " Z:" + (int)player.field_70161_v + " Dim:" + player.field_70170_p.field_73011_w.func_80007_l() + "}"));
/*     */         } else {
/*  83 */           ItemNBTHelper.setDouble(stack, "X", player.field_70165_t);
/*  84 */           ItemNBTHelper.setDouble(stack, "Y", player.field_70163_u);
/*  85 */           ItemNBTHelper.setDouble(stack, "Z", player.field_70161_v);
/*  86 */           ItemNBTHelper.setFloat(stack, "Yaw", player.field_70177_z);
/*  87 */           ItemNBTHelper.setFloat(stack, "Pitch", player.field_70125_A);
/*  88 */           ItemNBTHelper.setInteger(stack, "Dimension", player.field_71093_bK);
/*  89 */           ItemNBTHelper.setBoolean(stack, "IsSet", true);
/*  90 */           ItemNBTHelper.setString(stack, "DimentionName", (BrandonsCore.proxy.getMCServer().func_71218_a(player.field_71093_bK)).field_73011_w.func_80007_l());
/*     */         } 
/*  92 */         return stack;
/*  93 */       }  if (world.field_72995_K) {
/*  94 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterAlreadySet.txt", new Object[0]));
/*     */       }
/*  96 */       return stack;
/*     */     } 
/*  98 */     if (getLocation(stack) == null) {
/*  99 */       if (world.field_72995_K) player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterUnSet.txt", new Object[0])); 
/* 100 */       return stack;
/*     */     } 
/*     */     
/* 103 */     if (player.func_110143_aJ() > 2.0F || player.field_71075_bZ.field_75098_d) {
/* 104 */       getLocation(stack).sendEntityToCoords((Entity)player);
/* 105 */       stack.func_77972_a(1, (EntityLivingBase)player);
/* 106 */       if (!player.field_71075_bZ.field_75098_d) player.func_70606_j(player.func_110143_aJ() - 2.0F); 
/* 107 */     } else if (world.field_72995_K) {
/* 108 */       player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterLowHealth.txt", new Object[0]));
/* 109 */     }  return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean extraInformation) {
/* 117 */     if (!ItemNBTHelper.getBoolean(stack, "IsSet", false)) {
/* 118 */       list.add(EnumChatFormatting.RED + StatCollector.func_74838_a("info.teleporterInfUnset1.txt"));
/* 119 */       list.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.teleporterInfUnset2.txt"));
/* 120 */       list.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.teleporterInfUnset3.txt"));
/* 121 */       list.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.teleporterInfUnset4.txt"));
/* 122 */       list.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.teleporterInfUnset5.txt"));
/*     */     } else {
/* 124 */       list.add(EnumChatFormatting.GREEN + StatCollector.func_74838_a("info.teleporterInfSet1.txt"));
/* 125 */       list.add(EnumChatFormatting.WHITE + "{x:" + (int)ItemNBTHelper.getDouble(stack, "X", 0.0D) + " y:" + (int)ItemNBTHelper.getDouble(stack, "Y", 0.0D) + " z:" + (int)ItemNBTHelper.getDouble(stack, "Z", 0.0D) + " Dim:" + getLocation(stack).getDimensionName() + "}");
/* 126 */       list.add(EnumChatFormatting.BLUE + String.valueOf(stack.func_77958_k() - stack.func_77960_j() + 1) + " " + StatCollector.func_74838_a("info.teleporterInfSet2.txt"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 132 */     return EnumRarity.uncommon;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomEntity(ItemStack stack) {
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity createEntity(World world, Entity location, ItemStack itemstack) {
/* 142 */     return (Entity)new EntityPersistentItem(world, location, itemstack);
/*     */   }
/*     */   
/*     */   public Teleporter.TeleportLocation getLocation(ItemStack stack) {
/* 146 */     if (!ItemNBTHelper.getBoolean(stack, "IsSet", false)) return null;
/*     */     
/* 148 */     Teleporter.TeleportLocation location = new Teleporter.TeleportLocation();
/* 149 */     location.readFromNBT(stack.func_77978_p());
/*     */     
/* 151 */     return location;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\TeleporterMKI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */