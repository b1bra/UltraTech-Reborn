/*     */ package com.brandon3055.draconicevolution.client.render.item;
/*     */ 
/*     */ import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
/*     */ import com.gamerforea.draconicevolution.util.RenderUtils;
/*     */ import com.gamerforea.eventhelper.imc.client.ISpawnerEntity;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.monster.DEEntitySlimeAccessor;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import org.lwjglx.opengl.GL11;
/*     */ 
/*     */ public final class RenderMobSoul implements IItemRenderer {
/*  19 */   private static final String[] randomEntitys = new String[] { "Pig", "Sheep", "Enderman", "Zombie", "Creeper", "Cow", "Chicken", "Ozelot", "Witch", "Wolf", "MushroomCow", "Squid", "EntityHorse", "Spider", "Skeleton", "Blaze", "Bat", "Villager", "Silverfish" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/*  26 */     return (type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.ENTITY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/*  31 */     return false;
/*     */   }
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*     */     Entity mob;
/*  36 */     WorldClient world = (Minecraft.func_71410_x()).field_71441_e;
/*  37 */     if (world == null) {
/*     */       return;
/*     */     }
/*  40 */     String entityName = ItemNBTHelper.getString(item, "Name", "Pig");
/*     */ 
/*     */     
/*  43 */     if (entityName.equals("Any")) {
/*  44 */       mob = EntityList.func_75620_a(randomEntitys[(int)(Minecraft.func_71386_F() / 1000L % 18L)], (World)world);
/*     */     } else {
/*  46 */       mob = EntityList.func_75620_a(entityName, (World)world);
/*     */     } 
/*  48 */     if (mob instanceof EntitySkeleton) {
/*  49 */       ((EntitySkeleton)mob).func_82201_a(ItemNBTHelper.getInteger(item, "SkeletonType", 0));
/*     */     }
/*  51 */     if (mob == null) {
/*  52 */       mob = EntityList.func_75620_a("Pig", (World)world);
/*  53 */       if (mob == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*  57 */     boolean timedRotation = true;
/*     */     
/*  59 */     if (mob instanceof ISpawnerEntity) {
/*  60 */       ISpawnerEntity spawnerEntity = (ISpawnerEntity)mob;
/*  61 */       spawnerEntity.normalizeSpawner();
/*  62 */       timedRotation = spawnerEntity.shouldRotateSpawner();
/*  63 */     } else if (mob instanceof EntitySlime) {
/*  64 */       DEEntitySlimeAccessor.setSize((EntitySlime)mob, 2);
/*     */     } 
/*  66 */     GL11.glPushMatrix();
/*  67 */     GL11.glPushAttrib((type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) ? 1048575 : 8192);
/*  68 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*     */     
/*  70 */     if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/*  71 */       GL11.glScalef(13.0F, 13.0F, 13.0F);
/*  72 */       GL11.glTranslatef(1.2F, 2.2F, 0.0F);
/*  73 */       GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/*  74 */       rotate(timedRotation);
/*  75 */       RenderUtils.renderScaledEntity(mob, 1.0F);
/*  76 */     } else if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/*  77 */       GL11.glScalef(0.8F, 0.8F, 0.8F);
/*  78 */       GL11.glTranslatef(2.0F, 0.5F, 0.0F);
/*  79 */       GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
/*  80 */       rotate(timedRotation);
/*  81 */       RenderUtils.renderScaledEntity(mob, 1.0F);
/*  82 */     } else if (type == IItemRenderer.ItemRenderType.EQUIPPED) {
/*  83 */       GL11.glScalef(0.8F, 0.8F, 0.8F);
/*  84 */       GL11.glTranslatef(1.0F, 0.5F, 0.0F);
/*  85 */       GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
/*  86 */       rotate(timedRotation);
/*  87 */       RenderUtils.renderScaledEntity(mob, 1.0F);
/*     */     } else {
/*  89 */       GL11.glScalef(1.5F, 1.5F, 1.5F);
/*  90 */       rotate(timedRotation);
/*  91 */       GL11.glEnable(3042);
/*  92 */       GL11.glBlendFunc(770, 771);
/*  93 */       RenderUtils.renderScaledEntity(mob, 1.0F);
/*  94 */       GL11.glDisable(3042);
/*     */     } 
/*     */     
/*  97 */     GL11.glPopAttrib();
/*  98 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private static void rotate(boolean timedRotation) {
/* 102 */     if (timedRotation)
/* 103 */       GL11.glRotated(Minecraft.func_71386_F() / -10.0D, 0.0D, 1.0D, 0.0D); 
/* 104 */     GL11.glRotatef(-20.0F, 1.0F, 0.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\render\item\RenderMobSoul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */