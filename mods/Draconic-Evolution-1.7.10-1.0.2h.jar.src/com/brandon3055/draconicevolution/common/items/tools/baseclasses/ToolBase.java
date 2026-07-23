/*     */ package com.brandon3055.draconicevolution.common.items.tools.baseclasses;
/*     */ 
/*     */ import com.brandon3055.brandonscore.BrandonsCore;
/*     */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*     */ import com.brandon3055.brandonscore.common.utills.InfoHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.keybinding.KeyBindings;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.ToolModePacket;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import com.google.common.collect.Sets;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import org.lwjglx.input.Keyboard;
/*     */ import org.lwjglx.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ToolBase
/*     */   extends RFItemBase
/*     */ {
/*  41 */   private static final Set SHOVEL_OVERRIDES = Sets.newHashSet(new Object[] { Blocks.field_150349_c, Blocks.field_150346_d, Blocks.field_150354_m, Blocks.field_150351_n, Blocks.field_150431_aC, Blocks.field_150433_aE, Blocks.field_150435_aG, Blocks.field_150458_ak, Blocks.field_150425_aM, Blocks.field_150391_bh, Material.field_151577_b, Material.field_151578_c, Material.field_151595_p, Material.field_151597_y, Material.field_151596_z, Material.field_151571_B });
/*  42 */   private static final Set PICKAXE_OVERRIDES = Sets.newHashSet(new Object[] { Blocks.field_150347_e, Blocks.field_150334_T, Blocks.field_150333_U, Blocks.field_150348_b, Blocks.field_150322_A, Blocks.field_150341_Y, Blocks.field_150366_p, Blocks.field_150339_S, Blocks.field_150365_q, Blocks.field_150340_R, Blocks.field_150352_o, Blocks.field_150482_ag, Blocks.field_150484_ah, Blocks.field_150432_aD, Blocks.field_150424_aL, Blocks.field_150369_x, Blocks.field_150368_y, Blocks.field_150450_ax, Blocks.field_150439_ay, Blocks.field_150448_aq, Blocks.field_150319_E, Blocks.field_150318_D, Blocks.field_150408_cc, Material.field_151573_f, Material.field_151574_g, Material.field_151576_e, Material.field_151592_s, Material.field_151588_w, Material.field_151598_x });
/*  43 */   private static final Set AXE_OVERRIDES = Sets.newHashSet(new Object[] { Blocks.field_150344_f, Blocks.field_150342_X, Blocks.field_150364_r, Blocks.field_150363_s, Blocks.field_150486_ae, Blocks.field_150423_aK, Blocks.field_150428_aP, Material.field_151575_d, Material.field_151584_j, Material.field_151589_v, Material.field_151570_A, Material.field_151585_k, Material.field_151582_l });
/*     */ 
/*     */ 
/*     */   
/*     */   private Set blockOverrides;
/*     */ 
/*     */   
/*  50 */   private float efficiencyOnProperMaterial = 4.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   public float damageVsEntity;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Item.ToolMaterial toolMaterial;
/*     */ 
/*     */ 
/*     */   
/*  62 */   public int energyPerOperation = 0;
/*     */   
/*     */   protected ToolBase(float baseDamage, Item.ToolMaterial material, Set blockOverrides) {
/*  65 */     this.toolMaterial = material;
/*  66 */     this.blockOverrides = (blockOverrides == null) ? new HashSet() : blockOverrides;
/*  67 */     this.field_77777_bU = 1;
/*  68 */     func_77656_e(material.func_77997_a());
/*  69 */     this.efficiencyOnProperMaterial = material.func_77998_b();
/*  70 */     this.damageVsEntity = baseDamage + material.func_78000_c();
/*  71 */     func_77637_a(DraconicEvolution.tabToolsWeapons);
/*     */   }
/*     */   
/*     */   public float getEfficiency(ItemStack stack) {
/*  75 */     return this.efficiencyOnProperMaterial;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHarvestLevel(String toolClass, int level) {
/*  81 */     if (toolClass.equals("pickaxe")) this.blockOverrides.addAll(PICKAXE_OVERRIDES); 
/*  82 */     if (toolClass.equals("shovel")) this.blockOverrides.addAll(SHOVEL_OVERRIDES); 
/*  83 */     if (toolClass.equals("axe")) this.blockOverrides.addAll(AXE_OVERRIDES); 
/*  84 */     super.setHarvestLevel(toolClass, level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack stack, Block block) {
/*  92 */     return (this.blockOverrides.contains(block) || this.blockOverrides.contains(block.func_149688_o())) ? getEfficiency(stack) : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_150897_b(Block block) {
/* 100 */     if (getToolClasses(null).contains("pickaxe")) return true; 
/* 101 */     return (this.blockOverrides.contains(block) || this.blockOverrides.contains(block.func_149688_o()));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_77662_d() {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_77619_b() {
/* 112 */     return this.toolMaterial.func_77995_e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDigSpeed(ItemStack stack, Block block, int meta) {
/*     */     float speed;
/* 119 */     if (ForgeHooks.isToolEffective(stack, block, meta)) {
/* 120 */       speed = getEfficiency(stack);
/*     */     } else {
/* 122 */       speed = super.getDigSpeed(stack, block, meta);
/*     */     } 
/*     */     
/* 125 */     if (getCharge(stack) >= this.energyPerOperation) {
/* 126 */       float f = IConfigurableItem.ProfileHelper.getFloat(stack, "ToolDigMultiplier", 1.0F);
/* 127 */       if (speed > 50.0F) f *= f; 
/* 128 */       return f * speed;
/*     */     } 
/* 130 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemConfigField> getFields(ItemStack stack, int slot) {
/* 136 */     List<ItemConfigField> list = super.getFields(stack, slot);
/* 137 */     if (!getToolClasses(stack).isEmpty())
/* 138 */       list.add((new ItemConfigField(4, slot, "ToolDigMultiplier")).setMinMaxAndIncromente(Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(0.01F)).readFromItem(stack, Float.valueOf(1.0F)).setModifier("PERCENT")); 
/* 139 */     if (!getToolClasses(stack).isEmpty())
/* 140 */       list.add((new ItemConfigField(6, slot, "BaseSafeAOE")).readFromItem(stack, Boolean.valueOf(false))); 
/* 141 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean extended) {
/* 147 */     boolean show = InfoHelper.holdShiftForDetails(list);
/* 148 */     if (show) {
/* 149 */       if (hasProfiles()) {
/* 150 */         int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/* 151 */         list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.func_74838_a("info.de.capacitorMode.txt") + ": " + ItemNBTHelper.getString(stack, "ProfileName" + preset, "Profile " + preset));
/*     */       } 
/* 153 */       List<ItemConfigField> l = getFields(stack, 0);
/* 154 */       for (ItemConfigField f : l) list.add(f.getTooltipInfo());
/*     */     
/*     */     } 
/* 157 */     holdCTRLForUpgrades(list, stack);
/*     */     
/* 159 */     addAditionalInformation(stack, player, list, extended);
/* 160 */     InfoHelper.addEnergyInfo(stack, list);
/* 161 */     if (show && !ConfigHandler.disableLore) InfoHelper.addLore(stack, list, true); 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addAditionalInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean extended) {
/*     */     String key;
/* 167 */     if (KeyBindings.toolConfig.func_151463_i() >= 0 && KeyBindings.toolConfig.func_151463_i() < 256)
/* 168 */     { key = Keyboard.getKeyName(KeyBindings.toolConfig.func_151463_i()); }
/* 169 */     else { key = Mouse.getButtonName(KeyBindings.toolConfig.func_151463_i() + 101); }
/*     */     
/* 171 */     list.add(StatCollector.func_74838_a("info.de.press.txt") + " " + key + " " + StatCollector.func_74838_a("info.de.toOpenConfigGUI.txt"));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 176 */     if (stack.func_77977_a().contains(":wyvern")) return EnumRarity.rare; 
/* 177 */     if (stack.func_77977_a().contains(":draconic")) return EnumRarity.epic; 
/* 178 */     return EnumRarity.uncommon;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack stack, World world, EntityPlayer player) {
/* 183 */     if (!world.field_72995_K && !BrandonsCore.proxy.isDedicatedServer()) {
/* 184 */       handleModeChange(stack, player, InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown());
/* 185 */     } else if (world.field_72995_K && BrandonsCore.proxy.getMCServer() == null) {
/* 186 */       handleModeChange(stack, player, InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown());
/* 187 */       DraconicEvolution.network.sendToServer((IMessage)new ToolModePacket(InfoHelper.isShiftKeyDown(), InfoHelper.isCtrlKeyDown()));
/*     */     } 
/*     */     
/* 190 */     return super.func_77659_a(stack, world, player);
/*     */   }
/*     */   
/*     */   public static void handleModeChange(ItemStack stack, EntityPlayer player, boolean shift, boolean ctrl) {
/* 194 */     if (stack == null || !(stack.func_77973_b() instanceof IConfigurableItem))
/* 195 */       return;  IConfigurableItem item = (IConfigurableItem)stack.func_77973_b();
/*     */     
/* 197 */     if (shift && !ctrl) {
/* 198 */       List<ItemConfigField> fields = item.getFields(stack, player.field_71071_by.field_70461_c);
/* 199 */       for (ItemConfigField field : fields) {
/* 200 */         if (field.name.equals("ToolDigAOE")) {
/* 201 */           int aoe = ((Integer)field.value).intValue();
/* 202 */           aoe++;
/* 203 */           if (aoe > ((Integer)field.max).intValue()) aoe = ((Integer)field.min).intValue(); 
/* 204 */           field.value = Integer.valueOf(aoe);
/* 205 */           DataUtills.writeObjectToCompound(IConfigurableItem.ProfileHelper.getProfileCompound(stack), field.value, field.datatype, field.name);
/*     */         } 
/*     */       } 
/* 208 */     } else if (ctrl && !shift) {
/* 209 */       List<ItemConfigField> fields = item.getFields(stack, player.field_71071_by.field_70461_c);
/* 210 */       for (ItemConfigField field : fields) {
/* 211 */         if (field.name.equals("ToolDigDepth")) {
/* 212 */           int aoe = ((Integer)field.value).intValue();
/* 213 */           aoe++;
/* 214 */           if (aoe > ((Integer)field.max).intValue()) aoe = ((Integer)field.min).intValue(); 
/* 215 */           field.value = Integer.valueOf(aoe);
/* 216 */           DataUtills.writeObjectToCompound(IConfigurableItem.ProfileHelper.getProfileCompound(stack), field.value, field.datatype, field.name);
/*     */         } 
/*     */       } 
/* 219 */     } else if (ctrl && shift) {
/* 220 */       List<ItemConfigField> fields = item.getFields(stack, player.field_71071_by.field_70461_c);
/* 221 */       for (ItemConfigField field : fields) {
/* 222 */         if (field.name.equals("WeaponAttackAOE")) {
/* 223 */           int aoe = ((Integer)field.value).intValue();
/* 224 */           aoe++;
/* 225 */           if (aoe > ((Integer)field.max).intValue()) aoe = ((Integer)field.min).intValue(); 
/* 226 */           field.value = Integer.valueOf(aoe);
/* 227 */           DataUtills.writeObjectToCompound(IConfigurableItem.ProfileHelper.getProfileCompound(stack), field.value, field.datatype, field.name);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item.ToolMaterial getToolMaterial() {
/* 234 */     return this.toolMaterial;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void holdCTRLForUpgrades(List<String> list, ItemStack stack) {
/* 239 */     if (stack == null || !(stack.func_77973_b() instanceof IUpgradableItem))
/* 240 */       return;  if (!InfoHelper.isCtrlKeyDown()) {
/* 241 */       list.add(StatCollector.func_74838_a("info.de.hold.txt") + " " + EnumChatFormatting.AQUA + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.de.ctrl.txt") + EnumChatFormatting.RESET + " " + EnumChatFormatting.GRAY + StatCollector.func_74838_a("info.de.forUpgrades.txt"));
/*     */     } else {
/* 243 */       list.addAll(((IUpgradableItem)stack.func_77973_b()).getUpgradeStats(stack));
/* 244 */       list.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.ITALIC + StatCollector.func_74838_a("info.de.useUpgradeModifier.txt"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\items\tools\baseclasses\ToolBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */