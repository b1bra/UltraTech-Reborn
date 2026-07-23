/*     */ package com.brandon3055.draconicevolution.common.tileentities;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.gamerforea.draconicevolution.EventConfig;
/*     */ import com.gamerforea.eventhelper.imc.client.ISpawnerEntity;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CustomSpawnerBaseLogic
/*     */ {
/*  28 */   public int spawnDelay = 20;
/*     */ 
/*     */ 
/*     */   
/*  32 */   public String entityName = "";
/*     */   
/*     */   public double renderRotation0;
/*     */   public double renderRotation1;
/*  36 */   private int minSpawnDelay = 400;
/*  37 */   private int maxSpawnDelay = 600;
/*     */ 
/*     */ 
/*     */   
/*  41 */   private int spawnCount = 6;
/*     */   private Entity renderedEntity;
/*  43 */   private int maxNearbyEntities = 20;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean powered = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ltPowered = false;
/*     */ 
/*     */   
/*     */   public boolean requiresPlayer = true;
/*     */ 
/*     */   
/*     */   public boolean ignoreSpawnRequirements = false;
/*     */ 
/*     */   
/*  60 */   public int spawnSpeed = 1;
/*     */ 
/*     */ 
/*     */   
/*  64 */   private int activatingRangeFromPlayer = 24;
/*     */ 
/*     */ 
/*     */   
/*  68 */   private int spawnRange = 4;
/*  69 */   public int skeletonType = 0;
/*     */ 
/*     */   
/*  72 */   private static final Random RANDOM = new Random();
/*     */   
/*     */   public CustomSpawnerBaseLogic() {
/*  75 */     if (!EventConfig.spawnerMobRotation) {
/*  76 */       for (int spawnDelay = 0, rnd = MathHelper.func_76136_a(RANDOM, 2, 19); spawnDelay < rnd; spawnDelay++) {
/*  77 */         this.renderRotation1 = this.renderRotation0;
/*  78 */         this.renderRotation0 = (this.renderRotation0 + (1000.0F / (spawnDelay + 200.0F))) % 360.0D;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEntityNameToSpawn() {
/*  88 */     return this.entityName;
/*     */   }
/*     */   
/*     */   public void setEntityName(String name) {
/*  92 */     this.entityName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActivated() {
/*  99 */     if (!this.requiresPlayer)
/* 100 */       return true; 
/* 101 */     return 
/* 102 */       (getSpawnerWorld().func_72977_a(getSpawnerX() + 0.5D, getSpawnerY() + 0.5D, getSpawnerZ() + 0.5D, this.activatingRangeFromPlayer) != null);
/*     */   }
/*     */   
/*     */   public void updateSpawner() {
/* 106 */     if (!isActivated() || this.powered) {
/*     */       return;
/*     */     }
/* 109 */     World world = getSpawnerWorld();
/*     */     
/* 111 */     if (world.field_72995_K) {
/* 112 */       double d0 = (getSpawnerX() + world.field_73012_v.nextFloat());
/* 113 */       double d1 = (getSpawnerY() + world.field_73012_v.nextFloat());
/* 114 */       double d2 = (getSpawnerZ() + world.field_73012_v.nextFloat());
/* 115 */       world.func_72869_a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/* 116 */       world.func_72869_a("flame", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */       
/* 118 */       if (this.spawnDelay > 0) {
/* 119 */         this.spawnDelay--;
/*     */       }
/*     */       
/* 122 */       if (EventConfig.spawnerMobRotation) {
/*     */ 
/*     */         
/* 125 */         this.renderRotation1 = this.renderRotation0;
/* 126 */         this.renderRotation0 = (this.renderRotation0 + (1000.0F / (this.spawnDelay + 200.0F))) % 360.0D;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 132 */     if (this.spawnDelay == -1) {
/* 133 */       resetTimer();
/*     */     }
/* 135 */     if (this.spawnDelay > 0) {
/* 136 */       this.spawnDelay--;
/*     */       
/*     */       return;
/*     */     } 
/* 140 */     boolean flag = false;
/*     */     
/* 142 */     for (int i = 0; i < this.spawnCount; i++) {
/* 143 */       Entity entity = EntityList.func_75620_a(getEntityNameToSpawn(), world);
/*     */       
/* 145 */       if (entity == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 150 */       int j = world.func_72872_a(entity.getClass(), AxisAlignedBB.func_72330_a(getSpawnerX(), getSpawnerY(), getSpawnerZ(), (getSpawnerX() + 1), (getSpawnerY() + 1), (getSpawnerZ() + 1)).func_72314_b((this.spawnRange * 2), 4.0D, (this.spawnRange * 2))).size();
/*     */       
/* 152 */       if (j >= this.maxNearbyEntities) {
/* 153 */         resetTimer();
/*     */         
/*     */         return;
/*     */       } 
/* 157 */       int x = getSpawnerX() + (int)((world.field_73012_v.nextDouble() - world.field_73012_v.nextDouble()) * this.spawnRange);
/* 158 */       int y = getSpawnerY() + world.field_73012_v.nextInt(3) - 1;
/* 159 */       int z = getSpawnerZ() + (int)((world.field_73012_v.nextDouble() - world.field_73012_v.nextDouble()) * this.spawnRange);
/* 160 */       EntityLiving entityliving = (entity instanceof EntityLiving) ? (EntityLiving)entity : null;
/* 161 */       entity.func_70012_b(x + 0.5D, y + 0.5D, z + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
/*     */       
/* 163 */       if (entityliving == null || entityliving.func_70601_bi() || (this.ignoreSpawnRequirements && world.func_147439_a(x, y, z) == Blocks.field_150350_a)) {
/* 164 */         spawnEntity(entity);
/* 165 */         world.func_72926_e(2004, getSpawnerX(), getSpawnerY(), getSpawnerZ(), 0);
/*     */         
/* 167 */         if (entityliving != null) {
/* 168 */           entityliving.func_70656_aK();
/*     */         }
/* 170 */         flag = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 175 */     if (flag) {
/* 176 */       resetTimer();
/*     */     }
/*     */   }
/*     */   
/*     */   public Entity spawnEntity(Entity par1Entity) {
/* 181 */     if (par1Entity instanceof net.minecraft.entity.EntityLivingBase && par1Entity.field_70170_p != null) {
/* 182 */       if (par1Entity instanceof EntitySkeleton)
/* 183 */       { ((EntitySkeleton)par1Entity).func_82201_a(this.skeletonType);
/* 184 */         if (this.skeletonType == 1) {
/* 185 */           par1Entity.func_70062_b(0, new ItemStack(Items.field_151052_q));
/* 186 */           ((EntitySkeleton)par1Entity).func_96120_a(0, 0.0F);
/*     */         } else {
/* 188 */           par1Entity.func_70062_b(0, new ItemStack((Item)Items.field_151031_f));
/*     */         }  }
/* 190 */       else { ((EntityLiving)par1Entity).func_110161_a(null); }
/*     */       
/* 192 */       if (!this.requiresPlayer) {
/* 193 */         ((EntityLiving)par1Entity).func_110163_bv();
/* 194 */         par1Entity.getEntityData().func_74772_a("SpawnedByDESpawner", getSpawnerWorld().func_82737_E());
/*     */       } 
/*     */       
/* 197 */       if (!(getSpawnerWorld()).field_72995_K) {
/* 198 */         getSpawnerWorld().func_72838_d(par1Entity);
/*     */       }
/*     */     } 
/* 201 */     return par1Entity;
/*     */   }
/*     */   
/*     */   private void resetTimer() {
/* 205 */     if (this.maxSpawnDelay <= this.minSpawnDelay) {
/* 206 */       this.spawnDelay = this.minSpawnDelay;
/*     */     } else {
/* 208 */       int i = this.maxSpawnDelay - this.minSpawnDelay;
/* 209 */       this.spawnDelay = this.minSpawnDelay + (getSpawnerWorld()).field_73012_v.nextInt(i);
/*     */     } 
/*     */     
/* 212 */     blockEvent(1);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
/* 216 */     this.entityName = par1NBTTagCompound.func_74779_i("EntityId");
/* 217 */     this.spawnDelay = par1NBTTagCompound.func_74765_d("Delay");
/* 218 */     if (ConfigHandler.spawnerListType != Arrays.<String>asList(ConfigHandler.spawnerList).contains(this.entityName)) {
/* 219 */       this.entityName = "Pig";
/* 220 */       par1NBTTagCompound.func_74757_a("Running", false);
/*     */     } 
/*     */     
/* 223 */     this.powered = par1NBTTagCompound.func_74767_n("Powered");
/* 224 */     this.spawnSpeed = par1NBTTagCompound.func_74765_d("Speed");
/* 225 */     this.requiresPlayer = par1NBTTagCompound.func_74767_n("RequiresPlayer");
/* 226 */     this.ignoreSpawnRequirements = par1NBTTagCompound.func_74767_n("IgnoreSpawnRequirements");
/* 227 */     this.skeletonType = par1NBTTagCompound.func_74762_e("SkeletonType");
/*     */     
/* 229 */     this.minSpawnDelay = par1NBTTagCompound.func_74765_d("MinSpawnDelay");
/* 230 */     this.maxSpawnDelay = par1NBTTagCompound.func_74765_d("MaxSpawnDelay");
/* 231 */     this.spawnCount = par1NBTTagCompound.func_74765_d("SpawnCount");
/*     */     
/* 233 */     if (par1NBTTagCompound.func_150297_b("MaxNearbyEntities", 99)) {
/* 234 */       this.maxNearbyEntities = par1NBTTagCompound.func_74765_d("MaxNearbyEntities");
/* 235 */       this.activatingRangeFromPlayer = par1NBTTagCompound.func_74765_d("RequiredPlayerRange");
/*     */     } 
/*     */     
/* 238 */     if (par1NBTTagCompound.func_150297_b("SpawnRange", 99)) {
/* 239 */       this.spawnRange = par1NBTTagCompound.func_74765_d("SpawnRange");
/*     */     }
/* 241 */     if (getSpawnerWorld() != null && (getSpawnerWorld()).field_72995_K)
/* 242 */       this.renderedEntity = null; 
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
/* 246 */     par1NBTTagCompound.func_74778_a("EntityId", getEntityNameToSpawn());
/* 247 */     par1NBTTagCompound.func_74777_a("Delay", (short)this.spawnDelay);
/* 248 */     par1NBTTagCompound.func_74777_a("MinSpawnDelay", (short)this.minSpawnDelay);
/* 249 */     par1NBTTagCompound.func_74777_a("MaxSpawnDelay", (short)this.maxSpawnDelay);
/* 250 */     par1NBTTagCompound.func_74777_a("SpawnCount", (short)this.spawnCount);
/* 251 */     par1NBTTagCompound.func_74777_a("MaxNearbyEntities", (short)this.maxNearbyEntities);
/* 252 */     par1NBTTagCompound.func_74777_a("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
/* 253 */     par1NBTTagCompound.func_74777_a("SpawnRange", (short)this.spawnRange);
/* 254 */     par1NBTTagCompound.func_74757_a("Powered", this.powered);
/* 255 */     par1NBTTagCompound.func_74777_a("Speed", (short)this.spawnSpeed);
/* 256 */     par1NBTTagCompound.func_74757_a("RequiresPlayer", this.requiresPlayer);
/* 257 */     par1NBTTagCompound.func_74757_a("IgnoreSpawnRequirements", this.ignoreSpawnRequirements);
/* 258 */     par1NBTTagCompound.func_74768_a("SkeletonType", this.skeletonType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setDelayToMin(int par1) {
/* 265 */     if (par1 == 1 && (getSpawnerWorld()).field_72995_K) {
/* 266 */       this.spawnDelay = this.minSpawnDelay;
/* 267 */       return true;
/*     */     } 
/* 269 */     return false;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public Entity getEntityForRenderer() {
/* 274 */     if (this.renderedEntity == null) {
/* 275 */       Entity entity = EntityList.func_75620_a(getEntityNameToSpawn(), getSpawnerWorld());
/* 276 */       entity = spawnEntity(entity);
/* 277 */       if (entity instanceof EntitySkeleton) {
/* 278 */         ((EntitySkeleton)entity).func_82201_a(this.skeletonType);
/*     */       }
/*     */       
/* 281 */       if (entity instanceof ISpawnerEntity) {
/* 282 */         ((ISpawnerEntity)entity).normalizeSpawner();
/*     */       }
/*     */       
/* 285 */       this.renderedEntity = entity;
/*     */     } 
/*     */     
/* 288 */     return this.renderedEntity;
/*     */   }
/*     */   
/*     */   public abstract void blockEvent(int paramInt);
/*     */   
/*     */   public abstract World getSpawnerWorld();
/*     */   
/*     */   public abstract int getSpawnerX();
/*     */   
/*     */   public abstract int getSpawnerY();
/*     */   
/*     */   public abstract int getSpawnerZ();
/*     */   
/*     */   public void setSpawnRate(int i) {
/* 302 */     this.spawnSpeed = i;
/* 303 */     this.minSpawnDelay = 400 - i * 150;
/* 304 */     this.maxSpawnDelay = 600 - i * 200;
/* 305 */     this.spawnCount = 4 + i * 2;
/* 306 */     if (i == 3) {
/* 307 */       this.minSpawnDelay = 40;
/* 308 */       this.maxSpawnDelay = 40;
/* 309 */       this.spawnCount = 12;
/*     */     } 
/* 311 */     if (this.minSpawnDelay < 0)
/* 312 */       this.minSpawnDelay = 0; 
/* 313 */     if (this.maxSpawnDelay < 1)
/* 314 */       this.maxSpawnDelay = 1; 
/* 315 */     resetTimer();
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\tileentities\CustomSpawnerBaseLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */