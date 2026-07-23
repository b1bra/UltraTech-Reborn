/*    */ package com.brandon3055.draconicevolution.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.IExtendedEntityProperties;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtendedPlayer
/*    */   implements IExtendedEntityProperties
/*    */ {
/*    */   public static final String EXT_PROP_NAME = "DEPlayerProperties";
/*    */   private final EntityPlayer player;
/*    */   private int spawnCount;
/*    */   
/*    */   public ExtendedPlayer(EntityPlayer player) {
/* 20 */     this.player = player;
/* 21 */     this.spawnCount = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void register(EntityPlayer player) {
/* 29 */     player.registerExtendedProperties("DEPlayerProperties", new ExtendedPlayer(player));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ExtendedPlayer get(EntityPlayer player) {
/* 37 */     return (ExtendedPlayer)player.getExtendedProperties("DEPlayerProperties");
/*    */   }
/*    */ 
/*    */   
/*    */   public void saveNBTData(NBTTagCompound compound) {
/* 42 */     NBTTagCompound properties = new NBTTagCompound();
/* 43 */     properties.func_74768_a("SpawnCount", this.spawnCount);
/* 44 */     compound.func_74782_a("DEPlayerProperties", (NBTBase)properties);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadNBTData(NBTTagCompound compound) {
/* 49 */     NBTTagCompound properties = (NBTTagCompound)compound.func_74781_a("DEPlayerProperties");
/* 50 */     this.spawnCount = properties.func_74762_e("SpawnCount");
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(Entity entity, World world) {}
/*    */ 
/*    */   
/*    */   public int getSpawnCount() {
/* 58 */     return this.spawnCount;
/*    */   }
/*    */   
/*    */   public void setSpawnCount(int count) {
/* 62 */     this.spawnCount = count;
/*    */   }
/*    */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\entity\ExtendedPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */