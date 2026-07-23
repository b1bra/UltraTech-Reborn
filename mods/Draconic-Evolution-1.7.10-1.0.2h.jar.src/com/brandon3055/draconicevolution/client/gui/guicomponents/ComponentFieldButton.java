/*    */ package com.brandon3055.draconicevolution.client.gui.guicomponents;
/*    */ 
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentBase;
/*    */ import com.brandon3055.brandonscore.common.utills.DataUtills;
/*    */ import com.brandon3055.draconicevolution.client.gui.componentguis.GUIToolConfig;
/*    */ import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
/*    */ import com.brandon3055.draconicevolution.common.utills.ItemConfigField;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentFieldButton
/*    */   extends ComponentBase
/*    */ {
/* 23 */   private static final ResourceLocation widgets = new ResourceLocation("draconicevolution", "textures/gui/Widgets.png");
/*    */   
/*    */   public EntityPlayer player;
/*    */   public int slot;
/*    */   public ItemStack stack;
/*    */   public ItemConfigField field;
/*    */   public GUIToolConfig gui;
/*    */   
/*    */   public ComponentFieldButton(int x, int y, EntityPlayer player, ItemConfigField field, GUIToolConfig gui) {
/* 32 */     super(x, y);
/* 33 */     this.player = player;
/* 34 */     this.slot = field.slot;
/* 35 */     this.stack = player.field_71071_by.func_70301_a(this.slot);
/* 36 */     this.field = field;
/* 37 */     this.gui = gui;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 42 */     return 150;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 47 */     return 12;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 52 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(widgets);
/*    */     
/* 54 */     if (!isMouseOver(mouseX - offsetX, mouseY - offsetY)) {
/* 55 */       func_73729_b(this.x, this.y, 18, 0, getWidth() - 50, getHeight());
/* 56 */       func_73729_b(this.x, this.y + getHeight() - 1, 18, 19, getWidth() - 50, 1);
/*    */       
/* 58 */       func_73729_b(this.x + 50, this.y, 19, 0, getWidth() - 50 - 1, getHeight());
/* 59 */       func_73729_b(this.x + 50, this.y + getHeight() - 1, 19, 19, getWidth() - 50 - 1, 1);
/*    */     } else {
/* 61 */       func_73729_b(this.x, this.y, 18, 20, getWidth() - 50, getHeight());
/* 62 */       func_73729_b(this.x, this.y + getHeight() - 1, 18, 39, getWidth() - 50, 1);
/*    */       
/* 64 */       func_73729_b(this.x + 50, this.y, 19, 20, getWidth() - 50 - 1, getHeight());
/* 65 */       func_73729_b(this.x + 50, this.y + getHeight() - 1, 19, 39, getWidth() - 50 - 1, 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 71 */     func_73731_b(this.fontRendererObj, this.field.getLocalizedName(), this.x + offsetX + 2, this.y + offsetY + getHeight() / 2 - this.fontRendererObj.field_78288_b / 2, 16777215);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
/* 77 */     if (isMouseOver(mouseX, mouseY)) {
/* 78 */       List<String> list = new ArrayList();
/* 79 */       list.add(this.field.getFormattedValue());
/* 80 */       drawHoveringText(list, mouseX + offsetX, mouseY + offsetY + 10, this.fontRendererObj);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int x, int y, int button) {
/* 86 */     Minecraft.func_71410_x().func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 87 */     if (this.field.datatype == 6) {
/* 88 */       this.field.value = Boolean.valueOf(!((Boolean)this.field.value).booleanValue());
/* 89 */       this.field.sendChanges();
/* 90 */       ItemStack stack = this.gui.player.field_71071_by.func_70301_a(this.field.slot);
/* 91 */       if (stack != null && stack.func_77973_b() instanceof IConfigurableItem) {
/* 92 */         DataUtills.writeObjectToCompound(IConfigurableItem.ProfileHelper.getProfileCompound(stack), this.field.value, this.field.datatype, this.field.name);
/*    */       }
/*    */       return;
/*    */     } 
/* 96 */     this.gui.setFieldBeingEdited(this.field);
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\guicomponents\ComponentFieldButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */