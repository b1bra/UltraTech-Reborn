/*     */ package com.brandon3055.draconicevolution.common.utills;
/*     */ 
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TileDislocatorReceptacle;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.TilePortalBlock;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PortalHelper
/*     */ {
/*  17 */   static int iterationNow = 0;
/*     */   
/*     */   public static boolean isFrame(Block block) {
/*  20 */     return (block == ModBlocks.infusedObsidian);
/*     */   }
/*     */   
/*     */   public static boolean isReceptacle(Block block) {
/*  24 */     return (block == ModBlocks.dislocatorReceptacle);
/*     */   }
/*     */   
/*     */   public static boolean isPortal(Block block) {
/*  28 */     return (block == ModBlocks.portal);
/*     */   }
/*     */   
/*     */   public static PortalStructure getValidStructure(World world, int x, int y, int z) {
/*  32 */     if (world.field_72995_K) return null;
/*     */     
/*  34 */     for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
/*  35 */       for (ForgeDirection plane : ForgeDirection.VALID_DIRECTIONS) {
/*  36 */         if (plane != direction && plane != direction.getOpposite()) {
/*  37 */           PortalStructure structure = traceFrame(world, x, y, z, direction, plane);
/*  38 */           if (structure != null && structure.scanPortal(world, x, y, z, false, false)) return structure;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*  43 */     return null;
/*     */   }
/*     */   
/*     */   public static PortalStructure traceFrame(World world, int x, int y, int z, ForgeDirection startDir, ForgeDirection plane) {
/*  47 */     int MAX_SIZE = 150;
/*  48 */     int startX = x + startDir.offsetX;
/*  49 */     int startY = y + startDir.offsetY;
/*  50 */     int startZ = z + startDir.offsetZ;
/*     */ 
/*     */     
/*  53 */     if (!world.func_147437_c(startX, startY, startZ)) return null;
/*     */     
/*  55 */     int xSize = 0;
/*  56 */     int ySize = 0;
/*  57 */     int yOffset = 0;
/*     */     
/*     */     int i;
/*  60 */     for (i = 0; i <= MAX_SIZE; i++) {
/*  61 */       Block block = world.func_147439_a(startX + i * startDir.offsetX, startY + i * startDir.offsetY, startZ + i * startDir.offsetZ);
/*  62 */       if (isFrame(block)) {
/*  63 */         xSize = i; break;
/*     */       } 
/*  65 */       if (!world.func_147437_c(startX + i * startDir.offsetX, startY + i * startDir.offsetY, startZ + i * startDir.offsetZ)) {
/*  66 */         return null;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     for (i = 0; i <= MAX_SIZE; i++) {
/*  71 */       Block block = world.func_147439_a(startX + i * plane.offsetX, startY + i * plane.offsetY, startZ + i * plane.offsetZ);
/*  72 */       if (isFrame(block)) {
/*  73 */         ySize = i; break;
/*     */       } 
/*  75 */       if (!world.func_147437_c(startX + i * plane.offsetX, startY + i * plane.offsetY, startZ + i * plane.offsetZ)) {
/*  76 */         return null;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     for (i = 0; i <= MAX_SIZE; i++) {
/*  81 */       Block block = world.func_147439_a(startX - i * plane.offsetX, startY - i * plane.offsetY, startZ - i * plane.offsetZ);
/*  82 */       if (isFrame(block)) {
/*  83 */         ySize += i - 1;
/*  84 */         yOffset = i; break;
/*     */       } 
/*  86 */       if (!world.func_147437_c(startX - i * plane.offsetX, startY - i * plane.offsetY, startZ - i * plane.offsetZ)) {
/*  87 */         return null;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     if (xSize == 0 || ySize == 0 || ySize > MAX_SIZE) return null;
/*     */     
/*  93 */     PortalStructure structure = new PortalStructure(xSize, ySize, yOffset, startDir, plane);
/*     */     
/*  95 */     if (!structure.checkFrameIsValid(world, x, y, z) || !structure.scanPortal(world, x, y, z, false, false)) {
/*  96 */       return null;
/*     */     }
/*     */     
/*  99 */     return structure;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PortalStructure
/*     */   {
/*     */     public int xSize;
/*     */     public int ySize;
/*     */     public int yOffset;
/*     */     public ForgeDirection startDir;
/*     */     public ForgeDirection plane;
/*     */     
/*     */     public PortalStructure() {}
/*     */     
/*     */     public PortalStructure(int xSize, int ySize, int yOffset, ForgeDirection startDir, ForgeDirection plane) {
/* 114 */       this.xSize = xSize;
/* 115 */       this.ySize = ySize;
/* 116 */       this.yOffset = yOffset;
/* 117 */       this.startDir = startDir;
/* 118 */       this.plane = plane;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean checkFrameIsValid(World world, int x, int y, int z) {
/* 123 */       int startX = x + this.startDir.offsetX;
/* 124 */       int startY = y + this.startDir.offsetY;
/* 125 */       int startZ = z + this.startDir.offsetZ;
/*     */ 
/*     */       
/* 128 */       for (int y1 = 1; y1 <= this.ySize; y1++) {
/* 129 */         int y2 = y1 - this.yOffset;
/*     */         
/* 131 */         int inX = startX + y2 * this.plane.offsetX - this.startDir.offsetX;
/* 132 */         int inY = startY + y2 * this.plane.offsetY - this.startDir.offsetY;
/* 133 */         int inZ = startZ + y2 * this.plane.offsetZ - this.startDir.offsetZ;
/*     */         
/* 135 */         int outX = startX + y2 * this.plane.offsetX + this.xSize * this.startDir.offsetX;
/* 136 */         int outY = startY + y2 * this.plane.offsetY + this.xSize * this.startDir.offsetY;
/* 137 */         int outZ = startZ + y2 * this.plane.offsetZ + this.xSize * this.startDir.offsetZ;
/*     */         
/* 139 */         if (!PortalHelper.isFrame(world.func_147439_a(inX, inY, inZ)) && (inX != x || inY != y || inZ != z)) return false; 
/* 140 */         if (!PortalHelper.isFrame(world.func_147439_a(outX, outY, outZ))) return false;
/*     */       
/*     */       } 
/*     */       
/* 144 */       for (int x1 = 0; x1 < this.xSize; x1++) {
/* 145 */         int upX = startX + x1 * this.startDir.offsetX - this.yOffset * this.plane.offsetX;
/* 146 */         int upY = startY + x1 * this.startDir.offsetY - this.yOffset * this.plane.offsetY;
/* 147 */         int upZ = startZ + x1 * this.startDir.offsetZ - this.yOffset * this.plane.offsetZ;
/*     */         
/* 149 */         int downX = startX + x1 * this.startDir.offsetX + (this.ySize - this.yOffset + 1) * this.plane.offsetX;
/* 150 */         int downY = startY + x1 * this.startDir.offsetY + (this.ySize - this.yOffset + 1) * this.plane.offsetY;
/* 151 */         int downZ = startZ + x1 * this.startDir.offsetZ + (this.ySize - this.yOffset + 1) * this.plane.offsetZ;
/*     */         
/* 153 */         if (!PortalHelper.isFrame(world.func_147439_a(upX, upY, upZ))) return false; 
/* 154 */         if (!PortalHelper.isFrame(world.func_147439_a(downX, downY, downZ))) return false;
/*     */       
/*     */       } 
/* 157 */       return true;
/*     */     }
/*     */     
/*     */     public boolean scanPortal(World world, int x, int y, int z, boolean setPortalBlocks, boolean checkPortalBlocks) {
/* 161 */       int startX = x + this.startDir.offsetX;
/* 162 */       int startY = y + this.startDir.offsetY;
/* 163 */       int startZ = z + this.startDir.offsetZ;
/*     */       
/* 165 */       TileDislocatorReceptacle receptacle = (TileDislocatorReceptacle)world.func_147438_o(x, y, z);
/* 166 */       if (receptacle == null) return false; 
/* 167 */       if (setPortalBlocks) receptacle.updating = true;
/*     */       
/* 169 */       for (int x1 = 0; x1 < this.xSize; x1++) {
/* 170 */         for (int y1 = 1; y1 <= this.ySize; y1++) {
/* 171 */           int y2 = y1 - this.yOffset;
/* 172 */           int X = startX + x1 * this.startDir.offsetX + y2 * this.plane.offsetX;
/* 173 */           int Y = startY + x1 * this.startDir.offsetY + y2 * this.plane.offsetY;
/* 174 */           int Z = startZ + x1 * this.startDir.offsetZ + y2 * this.plane.offsetZ;
/*     */           
/* 176 */           Block block = world.func_147439_a(X, Y, Z);
/*     */           
/* 178 */           if (checkPortalBlocks)
/* 179 */           { if (!PortalHelper.isPortal(block)) return false;  }
/* 180 */           else if (setPortalBlocks)
/* 181 */           { world.func_147449_b(X, Y, Z, (Block)ModBlocks.portal);
/* 182 */             Chunk chunk = world.func_72938_d(X, Z);
/* 183 */             TilePortalBlock tile = (TilePortalBlock)chunk.func_150806_e(X & 0xF, Y, Z & 0xF);
/* 184 */             tile.masterX = x;
/* 185 */             tile.masterY = y;
/* 186 */             tile.masterZ = z; }
/* 187 */           else if (!world.func_147437_c(X, Y, Z)) { return false; }
/*     */         
/*     */         } 
/*     */       } 
/* 191 */       if (setPortalBlocks) receptacle.updating = false;
/*     */       
/* 193 */       return true;
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound compound) {
/* 197 */       compound.func_74768_a("XSize", this.xSize);
/* 198 */       compound.func_74768_a("YSize", this.ySize);
/* 199 */       compound.func_74768_a("YOffset", this.yOffset);
/* 200 */       compound.func_74778_a("StartDir", this.startDir.name());
/* 201 */       compound.func_74778_a("Plane", this.plane.name());
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound compound) {
/* 205 */       this.xSize = compound.func_74762_e("XSize");
/* 206 */       this.ySize = compound.func_74762_e("YSize");
/* 207 */       this.yOffset = compound.func_74762_e("YOffset");
/* 208 */       this.startDir = ForgeDirection.valueOf(compound.func_74779_i("StartDir"));
/* 209 */       this.plane = ForgeDirection.valueOf(compound.func_74779_i("Plane"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\commo\\utills\PortalHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */