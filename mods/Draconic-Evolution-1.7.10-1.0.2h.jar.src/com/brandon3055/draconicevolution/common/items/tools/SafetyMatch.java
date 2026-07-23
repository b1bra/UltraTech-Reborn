/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SafetyMatch
/*     */   extends ItemDE
/*     */ {
/*     */   IIcon boxIcon;
/*     */   
/*     */   public SafetyMatch() {
/*  31 */     func_77655_b("safetyMatch");
/*  32 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  33 */     func_77627_a(true);
/*  34 */     ModItems.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemStack) {
/*  39 */     if (itemStack.func_77960_j() == 1000) return super.func_77667_c(itemStack); 
/*  40 */     return super.func_77667_c(itemStack) + "Box";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs p_150895_2_, List<ItemStack> list) {
/*  47 */     list.add(new ItemStack(item, 1, 0));
/*  48 */     list.add(new ItemStack(item, 1, 1000));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemStackLimit(ItemStack stack) {
/*  53 */     return (stack.func_77960_j() == 1000) ? 16 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxDamage(ItemStack stack) {
/*  58 */     return (stack.func_77960_j() == 1000) ? 1000 : 20;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  64 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:safety_match");
/*  65 */     this.boxIcon = iconRegister.func_94245_a("draconicevolution:box_of_matches");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showDurabilityBar(ItemStack stack) {
/*  70 */     return (stack.func_77960_j() != 1000 && stack.func_77960_j() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int damage) {
/*  75 */     return (damage == 1000) ? this.field_77791_bV : this.boxIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
/*  80 */     if (p_77648_7_ == 0) {
/*  81 */       y--;
/*     */     }
/*     */     
/*  84 */     if (p_77648_7_ == 1) {
/*  85 */       y++;
/*     */     }
/*     */     
/*  88 */     if (p_77648_7_ == 2) {
/*  89 */       z--;
/*     */     }
/*     */     
/*  92 */     if (p_77648_7_ == 3) {
/*  93 */       z++;
/*     */     }
/*     */     
/*  96 */     if (p_77648_7_ == 4) {
/*  97 */       x--;
/*     */     }
/*     */     
/* 100 */     if (p_77648_7_ == 5) {
/* 101 */       x++;
/*     */     }
/*     */     
/* 104 */     if (!player.func_82247_a(x, y, z, p_77648_7_, stack)) {
/* 105 */       return false;
/*     */     }
/* 107 */     if (world.func_147437_c(x, y, z)) {
/* 108 */       world.func_72908_a(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, field_77697_d.nextFloat() * 0.4F + 0.8F);
/* 109 */       world.func_147449_b(x, y, z, ModBlocks.safetyFlame);
/*     */     } 
/*     */     
/* 112 */     if (!player.field_71075_bZ.field_75098_d)
/* 113 */       if (stack.func_77960_j() == 1000) { stack.field_77994_a--; }
/* 114 */       else { stack.func_77972_a(1, (EntityLivingBase)player); }
/*     */        
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
/* 124 */     list.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.de.safetyMatch.txt"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\SafetyMatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */