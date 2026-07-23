/*    */ package com.brandon3055.draconicevolution.common.items;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.IIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DraconiumEnergyCore
/*    */   extends ItemDE
/*    */ {
/* 20 */   IIcon[] icons = new IIcon[2];
/*    */   
/*    */   public DraconiumEnergyCore() {
/* 23 */     func_77655_b("draconiumEnergyCore");
/* 24 */     func_77637_a(DraconicEvolution.tabBlocksItems);
/* 25 */     func_77627_a(true);
/* 26 */     ModItems.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister iconRegister) {
/* 32 */     this.icons[0] = iconRegister.func_94245_a(getUnwrappedUnlocalizedName(func_77658_a()) + Character.MIN_VALUE);
/* 33 */     this.icons[1] = iconRegister.func_94245_a(getUnwrappedUnlocalizedName(func_77658_a()) + '\001');
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int damage) {
/* 39 */     return this.icons[damage];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_150895_a(Item item, CreativeTabs tab, List<ItemStack> list) {
/* 46 */     list.add(new ItemStack(item, 1, 0));
/* 47 */     list.add(new ItemStack(item, 1, 1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack itemStack) {
/* 53 */     return super.func_77667_c(itemStack) + itemStack.func_77960_j();
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\DraconiumEnergyCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */