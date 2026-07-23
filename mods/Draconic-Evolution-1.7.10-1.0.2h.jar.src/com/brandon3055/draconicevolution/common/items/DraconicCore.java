/*    */ package com.brandon3055.draconicevolution.common.items;
/*    */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*    */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*    */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*    */ import com.brandon3055.draconicevolution.common.ModItems;
/*    */ import com.brandon3055.draconicevolution.common.tileentities.TileCustomSpawner;
/*    */ import com.google.common.base.Strings;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import com.brandon3055.brandonscore.common.tags.Tags;
/*    */ import com.brandon3055.brandonscore.common.tags.SpawnerLogic;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class DraconicCore extends ItemDE {
/*    */   public DraconicCore() {
/* 23 */     func_77655_b("draconicCore");
/* 24 */     func_77637_a(DraconicEvolution.tabBlocksItems);
/* 25 */     ModItems.register(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
/* 32 */     par3List.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.draconicCore.txt"));
/* 33 */     par3List.add(InfoHelper.ITC() + StatCollector.func_74838_a("info.draconicCore1.txt"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
/* 38 */     SpawnerLogic logic = Tags.Spawners.MOB_SPAWNER.getLogic((IBlockAccess)world, x, y, z);
/*    */     
/* 40 */     if (logic != null) {
/* 41 */       String mobName = logic.getEntityName();
/* 42 */       world.func_147449_b(x, y, z, (Block)ModBlocks.customSpawner);
/*    */       
/* 44 */       TileEntity tile = world.func_147438_o(x, y, z);
/* 45 */       TileCustomSpawner newSpawner = (tile instanceof TileCustomSpawner) ? (TileCustomSpawner)tile : null;
/*    */       
/* 47 */       if (newSpawner != null && !Strings.isNullOrEmpty(mobName)) {
/* 48 */         (newSpawner.getBaseLogic()).entityName = mobName;
/* 49 */         newSpawner.isSetToSpawn = true;
/* 50 */         world.func_147471_g(x, y, z);
/*    */       } 
/*    */       
/* 53 */       stack.func_77979_a(1);
/* 54 */       return true;
/*    */     } 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\DraconicCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */