/*    */ package com.brandon3055.draconicevolution.common.blocks;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ 
/*    */ public class BlockDE
/*    */   extends Block
/*    */ {
/*    */   public BlockDE(Material material) {
/* 13 */     super(material);
/* 14 */     func_149711_c(5.0F);
/* 15 */     func_149752_b(10.0F);
/*    */   }
/*    */   
/*    */   public BlockDE() {
/* 19 */     super(Material.field_151573_f);
/* 20 */     func_149711_c(5.0F);
/* 21 */     func_149752_b(10.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_149739_a() {
/* 26 */     return String.format("tile.%s%s", new Object[] { "draconicevolution:", getUnwrappedUnlocalizedName(super.func_149739_a()) });
/*    */   }
/*    */   
/*    */   public String getUnwrappedUnlocalizedName(String unlocalizedName) {
/* 30 */     return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister iconRegister) {
/* 36 */     this.field_149761_L = iconRegister.func_94245_a("draconicevolution:" + getUnwrappedUnlocalizedName(super.func_149739_a()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\blocks\BlockDE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */