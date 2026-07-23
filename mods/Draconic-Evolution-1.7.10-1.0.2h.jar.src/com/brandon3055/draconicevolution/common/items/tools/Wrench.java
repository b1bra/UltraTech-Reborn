/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.api.tile.IWrenchBoundTile;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.items.ItemDE;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Wrench
/*     */   extends ItemDE
/*     */   implements IHudDisplayItem
/*     */ {
/*     */   public static final String BIND_MODE = "bind";
/*     */   public static final String UNBIND_MODE = "unBind";
/*     */   public static final String CLEAR_BINDINGS = "unBindAll";
/*     */   public static final String MODE_SWITCH = "modeSwitch";
/*     */   
/*     */   public Wrench() {
/*  37 */     func_77655_b("wrench");
/*  38 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  39 */     func_77625_d(1);
/*     */     
/*  41 */     ModItems.register(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/*  50 */     if (player.func_70093_af()) { cycleMode(stack, world, player); }
/*  51 */     else if (ItemNBTHelper.getCompound(stack).func_74764_b("LinkData") && ItemNBTHelper.getCompound(stack).func_74775_l("LinkData").func_74767_n("Bound"))
/*  52 */     { ItemNBTHelper.getCompound(stack).func_74775_l("LinkData").func_74757_a("Bound", false); }
/*  53 */      return super.func_77659_a(stack, world, player);
/*     */   }
/*     */   
/*  56 */   static final String[] modes = new String[] { "bind", "unBind", "unBindAll", "modeSwitch" };
/*     */   
/*     */   private static void cycleMode(ItemStack stack, World world, EntityPlayer player) {
/*  59 */     String currentMode = ItemNBTHelper.getString(stack, "Mode", "bind");
/*  60 */     int mode = 0;
/*  61 */     for (String s : modes) {
/*  62 */       if (s.equals(currentMode)) {
/*  63 */         if (mode + 1 >= modes.length) { currentMode = modes[0]; break; }
/*  64 */          currentMode = modes[mode + 1];
/*     */         break;
/*     */       } 
/*  67 */       mode++;
/*     */     } 
/*  69 */     ItemNBTHelper.setString(stack, "Mode", currentMode);
/*  70 */     if (world.field_72995_K) {
/*  71 */       player.func_146105_b((IChatComponent)new ChatComponentTranslation("msg.de.wrenchMode." + currentMode + ".txt", new Object[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean bool) {
/*  78 */     list.add(StatCollector.func_74838_a("msg.de.wrenchMode." + ItemNBTHelper.getString(stack, "Mode", "bind") + ".txt"));
/*  79 */     NBTTagCompound linkDat = null;
/*  80 */     if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("LinkData"))
/*  81 */       linkDat = stack.func_77978_p().func_74775_l("LinkData"); 
/*  82 */     if (linkDat != null && linkDat.func_74767_n("Bound")) {
/*  83 */       list.add(StatCollector.func_74838_a("msg.de.boundTo.txt") + ": [X:" + linkDat.func_74762_e("XCoord") + ", Y:" + linkDat.func_74762_e("YCoord") + ", Z:" + linkDat.func_74762_e("ZCoord") + "]");
/*  84 */       list.add(StatCollector.func_74838_a("msg.de.rightClickUnbind.txt"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(ItemStack stack) {
/*  90 */     List<String> list = new ArrayList<>();
/*  91 */     list.add(StatCollector.func_74838_a("msg.de.wrenchMode." + ItemNBTHelper.getString(stack, "Mode", "bind") + ".txt"));
/*  92 */     NBTTagCompound linkDat = null;
/*  93 */     if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("LinkData"))
/*  94 */       linkDat = stack.func_77978_p().func_74775_l("LinkData"); 
/*  95 */     if (linkDat != null && linkDat.func_74767_n("Bound")) {
/*  96 */       list.add(StatCollector.func_74838_a("msg.de.boundTo.txt") + ": [X:" + linkDat.func_74762_e("XCoord") + ", Y:" + linkDat.func_74762_e("YCoord") + ", Z:" + linkDat.func_74762_e("ZCoord") + "]");
/*  97 */       list.add(StatCollector.func_74838_a("msg.de.rightClickUnbind.txt"));
/*     */     } 
/*  99 */     return list;
/*     */   }
/*     */   
/*     */   public static String getMode(ItemStack stack) {
/* 103 */     return ItemNBTHelper.getString(stack, "Mode", "bind");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 108 */     NBTTagCompound nbt = stack.func_77978_p();
/*     */     
/* 110 */     if (nbt != null && nbt.func_150297_b("LinkData", 10)) {
/* 111 */       NBTTagCompound linkData = nbt.func_74775_l("LinkData");
/*     */       
/* 113 */       if (linkData.func_74767_n("Bound")) {
/* 114 */         int linkedX = linkData.func_74762_e("XCoord");
/* 115 */         int linkedY = linkData.func_74762_e("YCoord");
/* 116 */         int linkedZ = linkData.func_74762_e("ZCoord");
/* 117 */         TileEntity linkedTile = world.func_147438_o(linkedX, linkedY, linkedZ);
/*     */         
/* 119 */         if (linkedTile instanceof IWrenchBoundTile) {
/* 120 */           String mode = ItemNBTHelper.getString(stack, "Mode", "bind");
/* 121 */           return ((IWrenchBoundTile)linkedTile).onBoundWrenchActivate(x, y, z, ForgeDirection.getOrientation(side), player, mode);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     Block clicked = world.func_147439_a(x, y, z);
/* 127 */     return (getMode(stack).equals("modeSwitch") && clicked.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side)) && !world.field_72995_K);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\Wrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */