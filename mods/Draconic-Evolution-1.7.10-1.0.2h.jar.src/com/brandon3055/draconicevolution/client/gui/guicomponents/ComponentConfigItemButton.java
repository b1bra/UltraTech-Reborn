/*     */ package com.brandon3055.draconicevolution.client.gui.guicomponents;
/*     */ 
/*     */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentBase;
/*     */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*     */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComponentConfigItemButton
/*     */   extends ComponentBase
/*     */ {
/*  24 */   private static final ResourceLocation texture = new ResourceLocation("draconicevolution", "textures/gui/Widgets.png");
/*     */   public int slot;
/*     */   private InventoryPlayer inventory;
/*     */   public boolean hasValidItem = false;
/*     */   
/*     */   public ComponentConfigItemButton(int x, int y, int slot, EntityPlayer player) {
/*  30 */     super(x, y);
/*  31 */     this.slot = slot;
/*  32 */     this.inventory = player.field_71071_by;
/*  33 */     refreshState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshState() {
/*  43 */     ItemStack stack = this.inventory.func_70301_a(this.slot);
/*     */     
/*  45 */     if (stack == null) {
/*  46 */       this.hasValidItem = false;
/*     */       
/*     */       return;
/*     */     } 
/*  50 */     Item item = stack.func_77973_b();
/*  51 */     this
/*  52 */       .hasValidItem = (item instanceof com.brandon3055.draconicevolution.common.utills.IInventoryTool || (item instanceof IConfigurableItem && !((IConfigurableItem)item).getFields(stack, this.slot).isEmpty()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  58 */     return 18;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  63 */     return 18;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  68 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(texture);
/*  69 */     int heightOffset = isMouseOver(mouseX, mouseY) ? 36 : 18;
/*     */     
/*  71 */     if (!this.hasValidItem) {
/*  72 */       func_73729_b(this.x, this.y, 0, 0, getWidth(), getHeight());
/*     */     } else {
/*  74 */       func_73729_b(this.x, this.y, 0, heightOffset, getWidth(), getHeight());
/*     */     } 
/*  76 */     ItemStack stack = this.inventory.func_70301_a(this.slot);
/*  77 */     if (stack != null) {
/*  78 */       drawItemStack(stack, this.x + 1, this.y + 1, "null");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/*  87 */     if (isMouseOver(mouseX, mouseY) && this.hasValidItem) {
/*     */ 
/*     */ 
/*     */       
/*  91 */       ItemStack stack = this.inventory.func_70301_a(this.slot);
/*  92 */       if (stack == null) {
/*     */         return;
/*     */       }
/*  95 */       Item item = stack.func_77973_b();
/*  96 */       if (!(item instanceof IConfigurableItem)) {
/*     */         return;
/*     */       }
/*  99 */       List<ItemConfigField> fields = ((IConfigurableItem)item).getFields(stack, this.slot);
/*     */ 
/*     */       
/* 102 */       List<String> list = new ArrayList<>();
/* 103 */       for (ItemConfigField field : fields) {
/* 104 */         list.add(field.getLocalizedName() + ": " + field.getFormattedValue());
/*     */       }
/* 106 */       drawHoveringText(list, mouseX + offsetX, mouseY + offsetY + 10, this.fontRendererObj);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int x, int y, int button) {
/* 112 */     if (this.hasValidItem)
/* 113 */       Minecraft.func_71410_x()
/* 114 */         .func_147118_V()
/* 115 */         .func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\guicomponents\ComponentConfigItemButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */