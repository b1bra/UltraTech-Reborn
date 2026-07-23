/*    */ package com.brandon3055.draconicevolution.common.items;
/*    */ 
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InfoTablet
/*    */   extends ItemDE
/*    */ {
/*    */   public InfoTablet() {
/* 20 */     func_77655_b("infoTablet");
/* 21 */     func_77637_a(DraconicEvolution.tabBlocksItems);
/* 22 */     func_77625_d(1);
/* 23 */     ModItems.register(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_94581_a(IIconRegister iconRegister) {
/* 29 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:stone_tablet");
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77659_a(ItemStack p_77659_1_, World world, EntityPlayer player) {
/* 34 */     FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 9, world, (int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
/* 35 */     return super.func_77659_a(p_77659_1_, world, player);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\InfoTablet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */