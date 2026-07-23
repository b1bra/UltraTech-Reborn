/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.IC2Helper;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.RFItemBase;
/*     */ import com.brandon3055.draconicevolution.common.items.tools.baseclasses.ToolBase;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class DraconiumFluxCapacitor
/*     */   extends RFItemBase
/*     */   implements IUpgradableItem
/*     */ {
/*  35 */   IIcon[] icons = new IIcon[2];
/*     */   
/*     */   public DraconiumFluxCapacitor() {
/*  38 */     func_77655_b("draconiumFluxCapacitor");
/*  39 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  40 */     func_77627_a(true);
/*  41 */     func_77625_d(1);
/*  42 */     ModItems.register((ItemDE)this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  48 */     this.icons[0] = iconRegister.func_94245_a(getUnwrappedUnlocalizedName(func_77658_a()) + Character.MIN_VALUE);
/*  49 */     this.icons[1] = iconRegister.func_94245_a(getUnwrappedUnlocalizedName(func_77658_a()) + '\001');
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon func_77617_a(int damage) {
/*  55 */     return this.icons[damage];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/*  62 */     list.add(new ItemStack(item));
/*  63 */     list.add(makeChargedStack());
/*  64 */     list.add(new ItemStack(item, 1, 1));
/*  65 */     list.add(makeChargedStack(1));
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack stack) {
/*  70 */     return super.func_77667_c(stack) + stack.func_77960_j();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  75 */     int points = IUpgradableItem.EnumUpgrade.RF_CAPACITY.getUpgradePoints(stack);
/*  76 */     int damage = stack.func_77960_j();
/*  77 */     return (damage == 0) ? (BalanceConfigHandler.wyvernCapacitorBaseStorage + points * BalanceConfigHandler.wyvernCapacitorStoragePerUpgrade) : ((damage == 1) ? (BalanceConfigHandler.draconicCapacitorBaseStorage + points * BalanceConfigHandler.draconicCapacitorStoragePerUpgrade) : 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/*  82 */     int damage = stack.func_77960_j();
/*  83 */     return (damage == 0) ? BalanceConfigHandler.wyvernCapacitorMaxExtract : ((damage == 1) ? BalanceConfigHandler.draconicCapacitorMaxExtract : 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack stack) {
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack container, World world, Entity entity, int var1, boolean b) {
/*  94 */     if (world.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     if (!(entity instanceof EntityPlayer)) {
/*     */       return;
/*     */     }
/* 101 */     EntityPlayer player = (EntityPlayer)entity;
/* 102 */     int mode = ItemNBTHelper.getShort(container, "Mode", (short)0);
/* 103 */     if (mode == 0) {
/*     */       return;
/*     */     }
/* 106 */     double max = Math.min(getCharge(container), getTransferLimit(container));
/* 107 */     if (max <= 0.0D || !Double.isFinite(max)) {
/*     */       return;
/*     */     }
/*     */     
/* 111 */     if (mode == 1 || mode == 3)
/* 112 */       for (int i = 0; i < 9; i++) {
/* 113 */         ItemStack stack = player.field_71071_by.func_70301_a(i);
/*     */         
/* 115 */         if (stack != null && stack.field_77994_a == 1 && IC2Helper.isElectricItem(stack) && stack.func_77973_b() != ModItems.draconiumFluxCapacitor) {
/* 116 */           double charged = ElectricItem.manager.charge(stack, max, 2147483647, false, false);
/* 117 */           if (charged > 0.0D && Double.isFinite(charged)) {
/*     */ 
/*     */             
/* 120 */             ElectricItem.manager.discharge(container, charged, 2147483647, true, false, false);
/* 121 */             max = Math.min(getCharge(container), getTransferLimit(container));
/* 122 */             if (max <= 0.0D || !Double.isFinite(max)) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }  
/* 128 */     if (mode == 2 || mode == 3)
/* 129 */       for (int i = (mode == 3) ? 1 : 0; i < 5; i++) {
/* 130 */         ItemStack stack = player.func_71124_b(i);
/*     */         
/* 132 */         if (stack != null && stack.field_77994_a == 1 && IC2Helper.isElectricItem(stack) && stack.func_77973_b() != ModItems.draconiumFluxCapacitor) {
/* 133 */           double charged = ElectricItem.manager.charge(stack, max, 2147483647, false, false);
/* 134 */           if (charged > 0.0D && Double.isFinite(charged)) {
/*     */ 
/*     */             
/* 137 */             ElectricItem.manager.discharge(container, charged, 2147483647, true, false, false);
/* 138 */             max = Math.min(getCharge(container), getTransferLimit(container));
/* 139 */             if (max <= 0.0D || !Double.isFinite(max))
/*     */               return; 
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   public boolean hasEffect(ItemStack stack, int pass) {
/* 147 */     return (ItemNBTHelper.getShort(stack, "Mode", (short)0) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 152 */     if (player.func_70093_af()) {
/* 153 */       int mode = ItemNBTHelper.getShort(stack, "Mode", (short)0);
/* 154 */       int newMode = (mode == 3) ? 0 : (mode + 1);
/* 155 */       ItemNBTHelper.setShort(stack, "Mode", (short)newMode);
/* 156 */       if (world.field_72995_K)
/* 157 */         player.func_146105_b((IChatComponent)new ChatComponentTranslation(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.capacitorMode.txt") + ": " + InfoHelper.HITC() + StatCollector.func_74838_a("info.de.capacitorMode" + ItemNBTHelper.getShort(stack, "Mode", (short)0) + ".txt"), new Object[0])); 
/*     */     } 
/* 159 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean extraInformation) {
/* 166 */     if (InfoHelper.holdShiftForDetails(list)) {
/*     */       
/* 168 */       list.add(StatCollector.func_74838_a("info.de.changwMode.txt"));
/* 169 */       list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.capacitorMode.txt") + ": " + InfoHelper.HITC() + StatCollector.func_74838_a("info.de.capacitorMode" + ItemNBTHelper.getShort(stack, "Mode", (short)0) + ".txt"));
/*     */     } 
/*     */     
/* 172 */     ToolBase.holdCTRLForUpgrades(list, stack);
/* 173 */     InfoHelper.addEnergyInfo(stack, list);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProfiles() {
/* 178 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IUpgradableItem.EnumUpgrade> getUpgrades(ItemStack itemstack) {
/* 183 */     return new ArrayList<IUpgradableItem.EnumUpgrade>()
/*     */       {
/*     */       
/*     */       };
/*     */   }
/*     */   
/*     */   public int getUpgradeCap(ItemStack stack) {
/* 190 */     int damage = stack.func_77960_j();
/* 191 */     return (damage == 0) ? BalanceConfigHandler.wyvernCapacitorMaxUpgrades : ((damage == 1) ? BalanceConfigHandler.draconicCapacitorMaxUpgrades : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxTier(ItemStack stack) {
/* 196 */     return (stack.func_77960_j() == 0) ? 1 : 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex) {
/* 201 */     return Math.max(BalanceConfigHandler.wyvernCapacitorMaxUpgradePoints, BalanceConfigHandler.draconicCapacitorMaxUpgradePoints);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUpgradePoints(int upgradeIndex, ItemStack stack) {
/* 206 */     if (stack == null) {
/* 207 */       return getMaxUpgradePoints(upgradeIndex);
/*     */     }
/* 209 */     int damage = stack.func_77960_j();
/* 210 */     if (upgradeIndex == IUpgradableItem.EnumUpgrade.RF_CAPACITY.index) {
/* 211 */       return (damage == 0) ? BalanceConfigHandler.wyvernCapacitorMaxCapacityUpgradePoints : ((damage == 1) ? BalanceConfigHandler.draconicCapacitorMaxCapacityUpgradePoints : getMaxUpgradePoints(upgradeIndex));
/*     */     }
/* 213 */     return (damage == 0) ? BalanceConfigHandler.wyvernCapacitorMaxUpgradePoints : ((damage == 1) ? BalanceConfigHandler.draconicCapacitorMaxUpgradePoints : getMaxUpgradePoints(upgradeIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseUpgradePoints(int upgradeIndex) {
/* 218 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getUpgradeStats(ItemStack stack) {
/* 223 */     List<String> strings = new ArrayList<>();
/* 224 */     strings.add(InfoHelper.ITC() + StatCollector.func_74838_a("gui.de.RFCapacity.txt") + ": " + InfoHelper.HITC() + Utills.formatNumber(getMaxCharge(stack)));
/* 225 */     return strings;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\DraconiumFluxCapacitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */