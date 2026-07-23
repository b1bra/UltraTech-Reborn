/*     */ package com.brandon3055.draconicevolution.common.utills;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.api.IDraconicElectricItem;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IUpgradableItem
/*     */ {
/*     */   List<EnumUpgrade> getUpgrades(ItemStack paramItemStack);
/*     */   
/*     */   int getUpgradeCap(ItemStack paramItemStack);
/*     */   
/*     */   int getMaxTier(ItemStack paramItemStack);
/*     */   
/*     */   int getMaxUpgradePoints(int paramInt);
/*     */   
/*     */   int getMaxUpgradePoints(int paramInt, ItemStack paramItemStack);
/*     */   
/*     */   int getBaseUpgradePoints(int paramInt);
/*     */   
/*     */   List<String> getUpgradeStats(ItemStack paramItemStack);
/*     */   
/*     */   public enum EnumUpgrade
/*     */   {
/*  47 */     RF_CAPACITY(0, 1, "RFCapacity")
/*     */     {
/*     */       public void onRemovedFromItem(ItemStack stack) {
/*  50 */         if (stack != null && stack.func_77973_b() instanceof IElectricItem) {
/*  51 */           IElectricItem item = (IElectricItem)stack.func_77973_b();
/*  52 */           double charge = IC2Helper.getCharge(stack);
/*  53 */           double maxCharge = item.getMaxCharge(stack);
/*     */           
/*  55 */           if (charge > maxCharge) {
/*  56 */             if (item instanceof IDraconicElectricItem) {
/*  57 */               ((IDraconicElectricItem)item).setCharge(stack, maxCharge);
/*     */             } else {
/*  59 */               ElectricItem.manager.discharge(stack, maxCharge - charge, 2147483647, true, false, false);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     },
/*  65 */     DIG_SPEED(1, 1, "DigSpeed"),
/*  66 */     DIG_AOE(2, 4, "DigAOE")
/*     */     {
/*     */       public void onRemovedFromItem(ItemStack itemStack) {
/*  69 */         int profile = ItemNBTHelper.getInteger(itemStack, "ConfigProfile", 0);
/*     */         
/*  71 */         for (int i = 0; i < 5; i++) {
/*  72 */           ItemNBTHelper.setInteger(itemStack, "ConfigProfile", i);
/*  73 */           if (IConfigurableItem.ProfileHelper.getInteger(itemStack, "ToolDigAOE", 0) > getUpgradePoints(itemStack)) {
/*  74 */             IConfigurableItem.ProfileHelper.setInteger(itemStack, "ToolDigAOE", getUpgradePoints(itemStack));
/*     */           }
/*     */         } 
/*  77 */         ItemNBTHelper.setInteger(itemStack, "ConfigProfile", profile);
/*     */       }
/*     */     },
/*  80 */     DIG_DEPTH(3, 2, "DigDepth")
/*     */     {
/*     */       public void onRemovedFromItem(ItemStack itemStack) {
/*  83 */         int profile = ItemNBTHelper.getInteger(itemStack, "ConfigProfile", 0);
/*     */         
/*  85 */         for (int i = 0; i < 5; i++) {
/*  86 */           ItemNBTHelper.setInteger(itemStack, "ConfigProfile", i);
/*  87 */           if (IConfigurableItem.ProfileHelper.getInteger(itemStack, "ToolDigDepth", 0) > getUpgradePoints(itemStack)) {
/*  88 */             IConfigurableItem.ProfileHelper.setInteger(itemStack, "ToolDigDepth", getUpgradePoints(itemStack));
/*     */           }
/*     */         } 
/*  91 */         ItemNBTHelper.setInteger(itemStack, "ConfigProfile", profile);
/*     */       }
/*     */     },
/*  94 */     ATTACK_DAMAGE(4, 1, "AttackDamage"),
/*  95 */     ATTACK_AOE(5, 2, "AttackAOE")
/*     */     {
/*     */       public void onRemovedFromItem(ItemStack itemStack) {
/*  98 */         int profile = ItemNBTHelper.getInteger(itemStack, "ConfigProfile", 0);
/*     */         
/* 100 */         for (int i = 0; i < 5; i++) {
/* 101 */           ItemNBTHelper.setInteger(itemStack, "ConfigProfile", i);
/* 102 */           if (IConfigurableItem.ProfileHelper.getInteger(itemStack, "WeaponAttackAOE", 0) > getUpgradePoints(itemStack)) {
/* 103 */             IConfigurableItem.ProfileHelper.setInteger(itemStack, "WeaponAttackAOE", getUpgradePoints(itemStack));
/*     */           }
/*     */         } 
/* 106 */         ItemNBTHelper.setInteger(itemStack, "ConfigProfile", profile);
/*     */       }
/*     */     },
/* 109 */     ARROW_DAMAGE(6, 1, "ArrowDamage")
/*     */     {
/*     */       public void onRemovedFromItem(ItemStack itemStack) {
/* 112 */         int profile = ItemNBTHelper.getInteger(itemStack, "ConfigProfile", 0);
/*     */         
/* 114 */         for (int i = 0; i < 5; i++) {
/* 115 */           ItemNBTHelper.setInteger(itemStack, "ConfigProfile", i);
/* 116 */           if (IConfigurableItem.ProfileHelper.getInteger(itemStack, "BowArrowDamage", 0) > getUpgradePoints(itemStack)) {
/* 117 */             IConfigurableItem.ProfileHelper.setInteger(itemStack, "BowArrowDamage", getUpgradePoints(itemStack));
/*     */           }
/*     */         } 
/* 120 */         ItemNBTHelper.setInteger(itemStack, "ConfigProfile", profile);
/*     */       }
/*     */     },
/* 123 */     DRAW_SPEED(7, 2, "DrawSpeed"),
/* 124 */     ARROW_SPEED(8, 2, "ArrowSpeed")
/*     */     {
/*     */       public void onRemovedFromItem(ItemStack itemStack) {
/* 127 */         int profile = ItemNBTHelper.getInteger(itemStack, "ConfigProfile", 0);
/*     */         
/* 129 */         for (int i = 0; i < 5; i++) {
/* 130 */           ItemNBTHelper.setInteger(itemStack, "ConfigProfile", i);
/* 131 */           if (IConfigurableItem.ProfileHelper.getInteger(itemStack, "BowArrowSpeedModifier", 0) > getUpgradePoints(itemStack)) {
/* 132 */             IConfigurableItem.ProfileHelper.setInteger(itemStack, "BowArrowSpeedModifier", getUpgradePoints(itemStack));
/*     */           }
/*     */         } 
/* 135 */         ItemNBTHelper.setInteger(itemStack, "ConfigProfile", profile);
/*     */       }
/*     */     },
/* 138 */     SHIELD_CAPACITY(9, 1, "ShieldCapacity"),
/* 139 */     SHIELD_RECOVERY(10, 1, "ShieldRecovery"),
/* 140 */     MOVE_SPEED(11, 1, "MoveSpeed"),
/* 141 */     JUMP_BOOST(12, 1, "JumpBoost");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     private final String COMPOUND_NAME = "Upgrades";
/*     */     
/*     */     EnumUpgrade(int index, int pointConversion, String name) {
/* 152 */       this.index = index;
/* 153 */       this.pointConversion = pointConversion;
/* 154 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int index;
/*     */     
/*     */     public final int pointConversion;
/*     */     
/*     */     public final String name;
/*     */     
/*     */     public int[] getCoresApplied(ItemStack stack) {
/* 165 */       if (stack == null) return new int[] { 0, 0, 0, 0 }; 
/* 166 */       NBTTagCompound compound = ItemNBTHelper.getCompound(stack);
/* 167 */       getClass(); if (!compound.func_74764_b("Upgrades")) return new int[] { 0, 0, 0, 0 }; 
/* 168 */       getClass(); NBTTagCompound upgrades = compound.func_74775_l("Upgrades");
/* 169 */       if (upgrades.func_74764_b(this.name) && (upgrades.func_74759_k(this.name)).length == 4)
/* 170 */         return upgrades.func_74759_k(this.name); 
/* 171 */       return new int[] { 0, 0, 0, 0 };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCoresApplied(ItemStack stack, int[] cores) {
/*     */       NBTTagCompound upgrades;
/* 179 */       if (cores.length != 4) {
/* 180 */         LogHelper.error("[EnumUpgrade] Error applying upgrades to stack.");
/*     */         
/*     */         return;
/*     */       } 
/* 184 */       NBTTagCompound compound = ItemNBTHelper.getCompound(stack);
/*     */       
/* 186 */       getClass(); if (compound.func_74764_b("Upgrades")) { getClass(); upgrades = compound.func_74775_l("Upgrades"); }
/* 187 */       else { upgrades = new NBTTagCompound(); }
/* 188 */        upgrades.func_74783_a(this.name, cores);
/* 189 */       getClass(); compound.func_74782_a("Upgrades", (NBTBase)upgrades);
/*     */     }
/*     */     
/*     */     public String getLocalizedName() {
/* 193 */       return StatCollector.func_74838_a("gui.de." + this.name + ".txt");
/*     */     }
/*     */     
/*     */     public static EnumUpgrade getUpgradeByIndex(int index) {
/* 197 */       for (EnumUpgrade upgrade : values()) {
/* 198 */         if (upgrade.index == index) return upgrade; 
/*     */       } 
/* 200 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getUpgradePoints(ItemStack itemStack) {
/* 207 */       int[] applied = getCoresApplied(itemStack);
/* 208 */       int totalPoints = applied[0] + applied[1] * 2 + applied[2] * 4 + applied[3] * 8;
/* 209 */       if (itemStack != null && itemStack.func_77973_b() instanceof IUpgradableItem) {
/* 210 */         int points = ((IUpgradableItem)itemStack.func_77973_b()).getBaseUpgradePoints(this.index) + totalPoints / this.pointConversion;
/* 211 */         return Math.min(points, ((IUpgradableItem)itemStack.func_77973_b()).getMaxUpgradePoints(this.index, itemStack));
/*     */       } 
/* 213 */       return 0;
/*     */     }
/*     */     
/*     */     public void onAppliedToItem(ItemStack itemStack) {}
/*     */     
/*     */     public void onRemovedFromItem(ItemStack itemStack) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\IUpgradableItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */