/*     */ package com.brandon3055.draconicevolution.client.gui.componentguis;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentBase;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentButton;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentCollection;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentItemRenderer;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentTextField;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentTexturedRect;
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIBase;
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.brandon3055.brandonscore.common.utills.Utills;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.gui.GuiHudConfig;
/*     */ import com.brandon3055.draconicevolution.client.gui.guicomponents.ComponentConfigItemButton;
/*     */ import com.brandon3055.draconicevolution.client.gui.guicomponents.ComponentFieldAdjuster;
/*     */ import com.brandon3055.draconicevolution.client.gui.guicomponents.ComponentFieldButton;
/*     */ import com.brandon3055.draconicevolution.common.container.ContainerAdvTool;
/*     */ import com.brandon3055.draconicevolution.common.items.weapons.BowHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.ItemConfigPacket;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import cpw.mods.fml.common.network.simpleimpl.IMessage;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ public class GUIToolConfig extends GUIBase {
/*  34 */   private static final ResourceLocation inventoryTexture = new ResourceLocation("draconicevolution", "textures/gui/ToolConfig.png"); public EntityPlayer player;
/*  35 */   private int screenLevel = 0;
/*     */   private ItemStack editingItem;
/*     */   private ContainerAdvTool container;
/*     */   private int slot;
/*     */   
/*     */   public GUIToolConfig(EntityPlayer player, ContainerAdvTool container) {
/*  41 */     super((Container)container, 198, 89);
/*  42 */     this.container = container;
/*  43 */     this.player = player;
/*  44 */     container.setSlotsActive(false);
/*  45 */     addDependentComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ComponentCollection assembleComponents() {
/*  96 */     ComponentCollection c = (new ComponentCollection(0, 0, this.field_146999_f, this.field_147000_g, this)).setOpenBoarders();
/*  97 */     c.addComponent((ComponentBase)new ComponentTexturedRect(0, -15, 198, 20, inventoryTexture)).setGroup("TEXT_TAB");
/*  98 */     c.addComponent((ComponentBase)new ComponentTexturedRect(0, 0, 198, 89, inventoryTexture)).setGroup("BACKGROUND");
/*  99 */     c.addComponent((ComponentBase)new ComponentTexturedRect(0, 13, 0, 9, 198, 80, inventoryTexture, false))
/* 100 */       .setGroup("BACKGROUND_EXTENSION");
/* 101 */     c.addComponent((ComponentBase)new ComponentButton(3, 26, 20, 12, 0, this, "<=", StatCollector.func_74838_a("gui.back")))
/* 102 */       .setGroup("BUTTONS")
/* 103 */       .setName("BACK_BUTTON");
/* 104 */     c.addComponent((ComponentBase)new ComponentButton(3, 39, 20, 12, 1, this, "Inv", StatCollector.func_74838_a("gui.de.itemInventory.txt")))
/* 105 */       .setGroup("BUTTONS")
/* 106 */       .setName("INVENTORY_BUTTON");
/* 107 */     c.addComponent((ComponentBase)new ComponentFieldAdjuster(4, 34, null, this))
/* 108 */       .setGroup("FIELD_BUTTONS")
/* 109 */       .setName("FIELD_CONFIG_BUTTON_ARRAY");
/* 110 */     c.addComponent((new ComponentButton(0, this.field_147000_g, this.field_146999_f, 14, 2, this, StatCollector.func_74838_a("gui.de.configureGuiElements.txt"))).setGroup("INV_SCREEN"));
/*     */ 
/*     */ 
/*     */     
/* 114 */     ComponentTextField textField = (ComponentTextField)(new ComponentTextField(this, 3, -12, 191, 12)).setLabel(StatCollector.func_74838_a("gui.de.profile.txt") + ":", 14737632).setGroup("TEXT_TAB").setName("PROFILE_TEXT_FIELD");
/* 115 */     c.addComponent((ComponentBase)textField);
/* 116 */     return c;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDependentComponents() {
/* 121 */     for (int x = 0; x < 9; x++) {
/* 122 */       this.collection.addComponent((ComponentBase)new ComponentConfigItemButton(29 + 18 * x, 64, x, this.player))
/* 123 */         .setGroup("INV_SCREEN");
/*     */     }
/*     */     int y;
/* 126 */     for (y = 0; y < 3; y++) {
/* 127 */       for (int i = 0; i < 9; i++) {
/* 128 */         this.collection.addComponent((ComponentBase)new ComponentConfigItemButton(29 + 18 * i, 7 + y * 18, i + y * 9 + 9, this.player))
/* 129 */           .setGroup("INV_SCREEN");
/*     */       }
/*     */     } 
/*     */     
/* 133 */     for (y = 0; y < 4; y++) {
/* 134 */       this.collection.addComponent((ComponentBase)new ComponentConfigItemButton(6, 7 + y * 19, 39 - y, this.player))
/* 135 */         .setGroup("INV_SCREEN");
/*     */     }
/*     */     
/* 138 */     setLevel(0);
/*     */   }
/*     */   
/*     */   public void updateItemButtons() {
/* 142 */     for (ComponentBase component : this.collection.getComponents()) {
/* 143 */       if (component instanceof ComponentConfigItemButton) {
/* 144 */         ((ComponentConfigItemButton)component).refreshState();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_73864_a(int x, int y, int button) {
/* 150 */     super.func_73864_a(x, y, button);
/*     */     
/* 152 */     if (this.buttonPressed) {
/*     */       return;
/*     */     }
/* 155 */     int fieldOffsetX = 24;
/* 156 */     int fieldOffsetY = 5;
/*     */     
/* 158 */     for (ComponentBase component : this.collection.getComponents()) {
/* 159 */       if (component.isEnabled() && component instanceof ComponentConfigItemButton && component.isMouseOver(x - this.field_147003_i, y - this.field_147009_r) && ((ComponentConfigItemButton)component).hasValidItem) {
/* 160 */         ItemStack stack = this.player.field_71071_by.func_70301_a(((ComponentConfigItemButton)component).slot);
/* 161 */         if (stack == null || !(stack.func_77973_b() instanceof IConfigurableItem))
/*     */           return; 
/* 163 */         this.buttonPressed = true;
/* 164 */         IConfigurableItem item = (IConfigurableItem)stack.func_77973_b();
/*     */         
/* 166 */         setEditingItem(stack, ((ComponentConfigItemButton)component).slot);
/* 167 */         setLevel(1);
/* 168 */         for (ItemConfigField field : item.getFields(stack, ((ComponentConfigItemButton)component).slot)) {
/* 169 */           this.collection.addComponent((ComponentBase)new ComponentFieldButton(fieldOffsetX, fieldOffsetY, this.player, field, this))
/* 170 */             .setGroup("LIST_SCREEN");
/* 171 */           fieldOffsetY += 12;
/*     */         } 
/*     */         
/* 174 */         if (this.collection.getComponent("PROFILE_TEXT_FIELD") instanceof ComponentTextField) {
/* 175 */           int preset = ItemNBTHelper.getInteger(stack, "ConfigProfile", 0);
/* 176 */           String presetName = ItemNBTHelper.getString(stack, "ProfileName" + preset, "Profile " + preset);
/* 177 */           ((ComponentTextField)this.collection.getComponent("PROFILE_TEXT_FIELD")).textField.func_146180_a(presetName);
/*     */         } 
/*     */         
/* 180 */         this.collection.addComponent((ComponentBase)new ComponentItemRenderer(3, 5, stack)).setGroup("LIST_SCREEN");
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 185 */     if (this.collection.getComponent("PROFILE_TEXT_FIELD") instanceof ComponentTextField && 
/* 186 */       !this.collection.getComponent("PROFILE_TEXT_FIELD").isMouseOver(x - this.field_147003_i, y - this.field_147009_r)) {
/* 187 */       ((ComponentTextField)this.collection.getComponent("PROFILE_TEXT_FIELD")).textField.func_146195_b(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void buttonClicked(int id, int button) {
/* 192 */     super.buttonClicked(id, button);
/*     */ 
/*     */     
/* 195 */     if (id == 0 && this.screenLevel > 0) {
/* 196 */       setLevel(this.screenLevel - 1);
/* 197 */     } else if (id == 1 && this.editingItem != null) {
/* 198 */       setLevel(3);
/* 199 */       Minecraft.func_71410_x().func_147108_a((GuiScreen)new GUIToolInventory(this.player, this.container, this));
/*     */     
/*     */     }
/* 202 */     else if (id == 2) {
/* 203 */       Minecraft.func_71410_x().func_147108_a((GuiScreen)new GuiHudConfig(this));
/*     */     } 
/*     */   }
/*     */   public void setLevel(int level) {
/* 207 */     this.screenLevel = level;
/*     */     
/* 209 */     if (level == 0) {
/* 210 */       this.collection.schedulRemoval("LIST_SCREEN");
/* 211 */       this.collection.setOnlyGroupEnabled("INV_SCREEN");
/* 212 */       this.collection.setGroupEnabled("BACKGROUND", true);
/* 213 */       this.collection.setComponentEnabled("BACK_BUTTON", false);
/* 214 */       this.slot = -1;
/* 215 */     } else if (level == 1) {
/* 216 */       this.collection.setOnlyGroupEnabled("LIST_SCREEN");
/* 217 */       this.collection.setGroupEnabled("BACKGROUND", true);
/* 218 */       this.collection.setGroupEnabled("BACKGROUND_EXTENSION", true);
/* 219 */       if (this.editingItem != null && this.editingItem.func_77973_b() instanceof IConfigurableItem && ((IConfigurableItem)this.editingItem.func_77973_b()).hasProfiles())
/* 220 */         this.collection.setGroupEnabled("TEXT_TAB", true); 
/* 221 */       this.collection.setComponentEnabled("BACK_BUTTON", true);
/* 222 */       if (this.editingItem != null && this.editingItem.func_77973_b() instanceof com.brandon3055.draconicevolution.common.utills.IInventoryTool)
/* 223 */         this.collection.setComponentEnabled("INVENTORY_BUTTON", true); 
/* 224 */       if (this.collection.getComponent("BACK_BUTTON") != null)
/* 225 */         this.collection.getComponent("BACK_BUTTON").setY(26); 
/* 226 */     } else if (level == 2) {
/* 227 */       this.collection.setOnlyGroupEnabled("FIELD_BUTTONS");
/* 228 */       this.collection.setGroupEnabled("BACKGROUND", true);
/* 229 */       this.collection.setComponentEnabled("BACK_BUTTON", true);
/* 230 */       if (this.collection.getComponent("BACK_BUTTON") != null) {
/* 231 */         this.collection.getComponent("BACK_BUTTON").setY(3);
/*     */       }
/* 233 */     } else if (level == 3) {
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFieldBeingEdited(ItemConfigField field) {
/* 239 */     ((ComponentFieldAdjuster)this.collection.getComponent("FIELD_CONFIG_BUTTON_ARRAY")).field = field;
/* 240 */     setLevel(2);
/*     */   }
/*     */   
/*     */   public void setEditingItem(ItemStack stack, int slot) {
/* 244 */     this.editingItem = stack;
/* 245 */     this.container.updateInventoryStack(slot);
/* 246 */     this.slot = slot;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_146983_a(int p_146983_1_) {
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char par1, int par2) {
/* 256 */     if (this.collection.getComponent("PROFILE_TEXT_FIELD") instanceof ComponentTextField && ((ComponentTextField)this.collection.getComponent("PROFILE_TEXT_FIELD")).isFocused() && par2 != 1) {
/* 257 */       this.collection.keyTyped(par1, par2);
/*     */       return;
/*     */     } 
/* 260 */     super.func_73869_a(par1, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void componentCallBack(ComponentBase component) {
/* 265 */     if (component instanceof ComponentTextField && this.editingItem != null && this.slot != -1) {
/* 266 */       ComponentTextField textField = (ComponentTextField)component;
/* 267 */       if (!StringUtils.func_151246_b(textField.textField.func_146179_b())) {
/* 268 */         DraconicEvolution.network.sendToServer((IMessage)new ItemConfigPacket(this.slot, textField.textField.func_146179_b()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_146979_b(int mouseX, int mouseY) {
/* 274 */     super.func_146979_b(mouseX, mouseY);
/*     */     
/* 276 */     if (this.slot > -1)
/* 277 */       this.editingItem = this.player.field_71071_by.func_70301_a(this.slot); 
/* 278 */     if (this.slot > -1 && this.editingItem != null && this.editingItem.func_77977_a()
/* 279 */       .toLowerCase()
/* 280 */       .contains("bow")) {
/* 281 */       BowHandler.BowProperties properties = new BowHandler.BowProperties(this.editingItem, this.player);
/*     */       
/* 283 */       if (!properties.canFire() && properties.cantFireMessage != null && !properties.cantFireMessage.equals("msg.de.outOfArrows.name")) {
/* 284 */         this.field_146289_q.func_78279_b(StatCollector.func_74838_a(properties.cantFireMessage), 0, this.field_147000_g + 5, this.field_146999_f, 16711680);
/*     */       }
/* 286 */       List<String> list = new ArrayList<>();
/* 287 */       list.add(StatCollector.func_74838_a("gui.de.rfPerShot.txt") + ": " + Utills.addCommas(properties.calculateEnergyCost()));
/* 288 */       list.add(StatCollector.func_74838_a("gui.de.maxDamage.txt") + ": " + (properties.arrowDamage * properties.arrowSpeed * 3.0F));
/* 289 */       drawHoveringText(list, this.field_146999_f - 8, 0, this.field_146289_q);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\componentguis\GUIToolConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */