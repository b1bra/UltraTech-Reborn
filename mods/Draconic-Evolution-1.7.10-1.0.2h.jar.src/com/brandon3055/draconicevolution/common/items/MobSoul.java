/*    */ package com.brandon3055.draconicevolution.common.items;
/*    */ 
/*    */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*    */ import com.gamerforea.eventhelper.imc.client.IHasDisplayList;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MobSoul
/*    */   extends ItemDE
/*    */   implements IHasDisplayList
/*    */ {
/*    */   public MobSoul() {
/* 30 */     func_77655_b("mobSoul");
/* 31 */     ModItems.register(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_77624_a(ItemStack stack, EntityPlayer par2EntityPlayer, List<String> list, boolean par4) {
/* 36 */     String name = ItemNBTHelper.getString(stack, "Name", "Pig");
/* 37 */     list.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.mobSoul1.txt"));
/* 38 */     list.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.mobSoul2.txt"));
/* 39 */     list.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.mobSoul3.txt"));
/* 40 */     list.add(EnumChatFormatting.DARK_PURPLE + name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister iconRegister) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
/* 51 */     if (!player.func_70093_af())
/* 52 */       return false; 
/* 53 */     String name = ItemNBTHelper.getString(stack, "Name", "Pig");
/* 54 */     Entity entity = EntityList.func_75620_a(name, world);
/* 55 */     double sX = (x + (ForgeDirection.getOrientation(side)).offsetX) + 0.5D;
/* 56 */     double sY = (y + (ForgeDirection.getOrientation(side)).offsetY) + 0.5D;
/* 57 */     double sZ = (z + (ForgeDirection.getOrientation(side)).offsetZ) + 0.5D;
/* 58 */     if (entity == null) {
/* 59 */       LogHelper.error("Mob Soul bound entity = null");
/* 60 */       return false;
/*    */     } 
/* 62 */     entity.func_70012_b(sX, sY, sZ, player.field_70177_z, 0.0F);
/*    */     
/* 64 */     if (entity instanceof net.minecraft.entity.EntityLivingBase && !world.field_72995_K) {
/* 65 */       ((EntityLiving)entity).func_110161_a(null);
/* 66 */       world.func_72838_d(entity);
/* 67 */       if (!player.field_71075_bZ.field_75098_d)
/* 68 */         stack.field_77994_a--; 
/*    */     } 
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\MobSoul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */