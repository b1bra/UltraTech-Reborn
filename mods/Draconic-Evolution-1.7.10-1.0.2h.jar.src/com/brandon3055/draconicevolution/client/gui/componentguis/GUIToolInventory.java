/*    */ package com.brandon3055.draconicevolution.client.gui.componentguis;
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentBase;
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentCollection;
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentItemRenderer;
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentSlotBackground;
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.ComponentTexturedRect;
/*    */ import com.brandon3055.brandonscore.client.gui.guicomponents.GUIBase;
/*    */ import com.brandon3055.draconicevolution.common.container.ContainerAdvTool;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjglx.opengl.GL11;
/*    */ 
/*    */ public class GUIToolInventory extends GUIBase {
/* 16 */   private static final ResourceLocation inventoryTexture = new ResourceLocation("draconicevolution", "textures/gui/ToolConfig.png");
/*    */   
/*    */   private ContainerAdvTool container;
/*    */   private String inventoryName;
/*    */   private EntityPlayer player;
/*    */   private GUIToolConfig guiToolConfig;
/*    */   
/*    */   public GUIToolInventory(EntityPlayer player, ContainerAdvTool container) {
/* 24 */     super((Container)container, 198, 130);
/* 25 */     this.container = container;
/* 26 */     this.container.setSlotsActive(true);
/* 27 */     this.inventoryName = container.inventoryTool.func_145825_b();
/* 28 */     this.player = player;
/* 29 */     addDependentComponents();
/*    */   }
/*    */   
/*    */   public GUIToolInventory(EntityPlayer player, ContainerAdvTool container, GUIToolConfig guiToolConfig) {
/* 33 */     this(player, container);
/* 34 */     this.guiToolConfig = guiToolConfig;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ComponentCollection assembleComponents() {
/* 39 */     ComponentCollection c = new ComponentCollection(0, 0, this.field_146999_f, this.field_147000_g, this);
/* 40 */     c.addComponent((ComponentBase)new ComponentTexturedRect(0, 41, 198, 89, inventoryTexture)).setGroup("BACKGROUND");
/* 41 */     c.addComponent((ComponentBase)new ComponentTexturedRect(0, 0, 198, 80, inventoryTexture)).setGroup("BACKGROUND");
/* 42 */     c.addComponent((ComponentBase)new ComponentButton(172, 3, 18, 12, 0, this, "<=", "Back"));
/*    */     
/* 44 */     for (int i = 0; i < 5; i++) {
/* 45 */       c.addComponent((ComponentBase)new ComponentSlotBackground(172, 18 + i * 21)).setGroup("INVENTORY");
/* 46 */       c.addComponent((ComponentBase)new ComponentTexturedRect(173, 20 + i * 21, 0, 89, 16, 14, inventoryTexture, true)).setGroup("INVENTORY");
/*    */     } 
/* 48 */     return c;
/*    */   }
/*    */ 
/*    */   
/*    */   public void buttonClicked(int id, int button) {
/* 53 */     super.buttonClicked(id, button);
/* 54 */     if (this.guiToolConfig == null) this.guiToolConfig = new GUIToolConfig(this.player, this.container); 
/* 55 */     this.guiToolConfig.updateItemButtons();
/* 56 */     Minecraft.func_71410_x().func_147108_a((GuiScreen)this.guiToolConfig);
/* 57 */     this.guiToolConfig.setLevel(1);
/* 58 */     this.container.setSlotsActive(false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addDependentComponents() {
/* 63 */     for (int x = 0; x < this.container.inventoryTool.size; x++)
/* 64 */       this.collection.addComponent((ComponentBase)new ComponentSlotBackground(7 + x * 18, 18)).setGroup("INVENTORY"); 
/* 65 */     ComponentSlotBackground.addInventorySlots(this.collection, 7, 44, this.container.inventoryItemSlot);
/* 66 */     if (this.container.inventoryItemSlot > 35) {
/* 67 */       this.collection.addComponent((ComponentBase)new ComponentItemRenderer(6, 18, this.container.inventoryTool.inventoryItem)).setGroup("INVENTORY");
/*    */     }
/*    */   }
/*    */   
/*    */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 72 */     super.func_146976_a(f, mouseX, mouseY);
/* 73 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 74 */     this.field_146289_q.func_78276_b(this.inventoryName, this.field_147003_i + 5, this.field_147009_r + 5, 5592405);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_146983_a(int p_146983_1_) {
/* 79 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\gui\componentguis\GUIToolInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */