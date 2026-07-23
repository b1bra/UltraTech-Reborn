/*     */ package com.brandon3055.draconicevolution.common.items.tools;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Teleporter;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityPersistentItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
/*     */ import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeleporterMKII
/*     */   extends TeleporterMKI
/*     */   implements IHudDisplayItem
/*     */ {
/*     */   public TeleporterMKII() {
/*  35 */     super(true);
/*  36 */     func_77655_b("teleporterMKII");
/*  37 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*  38 */     func_77625_d(1);
/*  39 */     ModItems.register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_94581_a(IIconRegister iconRegister) {
/*  45 */     this.field_77791_bV = iconRegister.func_94245_a("draconicevolution:teleporterMKII");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
/*  50 */     World world = player.field_70170_p;
/*  51 */     int fuel = ItemNBTHelper.getInteger(stack, "Fuel", 0);
/*     */     
/*  53 */     if (getLocation(stack) == null) {
/*  54 */       if (world.field_72995_K)
/*  55 */         FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 3, world, (int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v); 
/*  56 */       return true;
/*     */     } 
/*     */     
/*  59 */     if (!player.field_71075_bZ.field_75098_d && fuel <= 0) {
/*  60 */       if (world.field_72995_K) player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterOutOfFuel.txt", new Object[0])); 
/*  61 */       return true;
/*     */     } 
/*     */     
/*  64 */     if (entity instanceof EntityPlayer) {
/*  65 */       if (entity.func_70093_af()) {
/*  66 */         getLocation(stack).sendEntityToCoords(entity);
/*  67 */         if (!player.field_71075_bZ.field_75098_d && fuel > 0) ItemNBTHelper.setInteger(stack, "Fuel", fuel - 1);
/*     */       
/*  69 */       } else if (world.field_72995_K) {
/*  70 */         player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterPlayerConsent.txt", new Object[0]));
/*     */       } 
/*  72 */       return true;
/*  73 */     }  if (entity instanceof net.minecraft.entity.EntityLiving) {
/*  74 */       getLocation(stack).sendEntityToCoords(entity);
/*  75 */       if (!player.field_71075_bZ.field_75098_d && fuel > 0) ItemNBTHelper.setInteger(stack, "Fuel", fuel - 1);
/*     */     
/*     */     } 
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/*  83 */     int fuel = ItemNBTHelper.getInteger(stack, "Fuel", 0);
/*     */     
/*  85 */     if (player.func_70093_af()) {
/*  86 */       if (world.field_72995_K) {
/*  87 */         FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 3, world, (int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v);
/*     */       }
/*     */     } else {
/*     */       
/*  91 */       if (getLocation(stack) == null) {
/*  92 */         if (world.field_72995_K)
/*  93 */           FMLNetworkHandler.openGui(player, DraconicEvolution.instance, 3, world, (int)player.field_70165_t, (int)player.field_70163_u, (int)player.field_70161_v); 
/*  94 */         return stack;
/*     */       } 
/*     */       
/*  97 */       if (!player.field_71075_bZ.field_75098_d && fuel <= 0) {
/*  98 */         if (world.field_72995_K) player.func_145747_a((IChatComponent)new ChatComponentTranslation("msg.teleporterOutOfFuel.txt", new Object[0])); 
/*  99 */         return stack;
/*     */       } 
/*     */       
/* 102 */       if (!player.field_71075_bZ.field_75098_d && fuel > 0) ItemNBTHelper.setInteger(stack, "Fuel", fuel - 1);
/*     */       
/* 104 */       getLocation(stack).sendEntityToCoords((Entity)player);
/*     */     } 
/*     */     
/* 107 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack teleporter, EntityPlayer player, List<String> list2, boolean extraInformation) {
/* 114 */     short selected = ItemNBTHelper.getShort(teleporter, "Selection", (short)0);
/* 115 */     int selrctionOffset = ItemNBTHelper.getInteger(teleporter, "SelectionOffset", 0);
/* 116 */     NBTTagCompound compound = teleporter.func_77978_p();
/* 117 */     if (compound == null) compound = new NBTTagCompound(); 
/* 118 */     NBTTagList list = (NBTTagList)compound.func_74781_a("Locations");
/* 119 */     if (list == null) list = new NBTTagList(); 
/* 120 */     String selectedDest = list.func_150305_b(selected + selrctionOffset).func_74779_i("Name");
/*     */     
/* 122 */     list2.add(EnumChatFormatting.GOLD + "" + selectedDest);
/* 123 */     if (InfoHelper.holdShiftForDetails(list2)) {
/* 124 */       list2.add(EnumChatFormatting.WHITE + StatCollector.func_74838_a("info.teleporterInfFuel.txt") + " " + ItemNBTHelper.getInteger(teleporter, "Fuel", 0));
/* 125 */       list2.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.teleporterInfGUI.txt"));
/* 126 */       list2.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.teleporterInfScroll.txt"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 132 */     return EnumRarity.rare;
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
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String func_77653_i(ItemStack teleporter) {
/* 148 */     return super.func_77653_i(teleporter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Teleporter.TeleportLocation getLocation(ItemStack stack) {
/* 160 */     short selected = ItemNBTHelper.getShort(stack, "Selection", (short)0);
/* 161 */     int selrctionOffset = ItemNBTHelper.getInteger(stack, "SelectionOffset", 0);
/* 162 */     NBTTagCompound compound = stack.func_77978_p();
/* 163 */     if (compound == null) return null; 
/* 164 */     NBTTagList list = (NBTTagList)compound.func_74781_a("Locations");
/* 165 */     if (list == null) return null;
/*     */     
/* 167 */     Teleporter.TeleportLocation destination = new Teleporter.TeleportLocation();
/* 168 */     destination.readFromNBT(list.func_150305_b(selected + selrctionOffset));
/* 169 */     if (destination.getName().isEmpty()) return null;
/*     */     
/* 171 */     return destination;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getDisplayData(ItemStack stack) {
/* 176 */     List<String> list = new ArrayList<>();
/* 177 */     Teleporter.TeleportLocation location = getLocation(stack);
/* 178 */     if (location != null) {
/* 179 */       list.add(location.getName());
/*     */     }
/* 181 */     list.add(StatCollector.func_74838_a("info.teleporterInfFuel.txt") + " " + ItemNBTHelper.getInteger(stack, "Fuel", 0));
/*     */     
/* 183 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\TeleporterMKII.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */