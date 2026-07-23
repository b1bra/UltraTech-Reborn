/*     */ package com.brandon3055.draconicevolution.client;
/*     */ import com.brandon3055.draconicevolution.client.handler.ClientEventHandler;
/*     */ import com.brandon3055.draconicevolution.client.handler.ParticleHandler;
/*     */ import com.brandon3055.draconicevolution.client.keybinding.KeyBindings;
/*     */ import com.brandon3055.draconicevolution.client.render.IRenderTweak;
/*     */ import com.brandon3055.draconicevolution.client.render.block.RenderParticleGen;
/*     */ import com.brandon3055.draconicevolution.client.render.block.RenderTeleporterStand;
/*     */ import com.brandon3055.draconicevolution.client.render.block.RenderUpgradeModifier;
/*     */ import com.brandon3055.draconicevolution.client.render.item.RenderArmor;
/*     */ import com.brandon3055.draconicevolution.client.render.item.RenderBow;
/*     */ import com.brandon3055.draconicevolution.client.render.item.RenderBowModel;
/*     */ import com.brandon3055.draconicevolution.client.render.item.RenderStabilizerPart;
/*     */ import com.brandon3055.draconicevolution.client.render.item.RenderTool;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.ParticleEnergyBeam;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.ParticleEnergyField;
/*     */ import com.brandon3055.draconicevolution.common.ModBlocks;
/*     */ import com.brandon3055.draconicevolution.common.ModItems;
/*     */ import com.brandon3055.draconicevolution.common.entity.EntityCustomArrow;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.lib.References;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileCustomSpawner;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.client.registry.ClientRegistry;
/*     */ import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.IItemRenderer;
/*     */ import net.minecraftforge.client.MinecraftForgeClient;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ public class ClientProxy extends CommonProxy {
/*     */   public void preInit(FMLPreInitializationEvent event) {
/*  44 */     super.preInit(event);
/*     */     
/*  46 */     ResourceHandler.init(event);
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
/*     */   public static String downloadLocation;
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
/*     */   public void init(FMLInitializationEvent event) {
/*  98 */     super.init(event);
/*  99 */     FMLCommonHandler.instance().bus().register(new KeyInputHandler());
/* 100 */     FMLCommonHandler.instance().bus().register(new ClientEventHandler());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     MinecraftForge.EVENT_BUS.register(new HudHandler());
/* 107 */     MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
/* 108 */     KeyBindings.init();
/* 109 */     registerRenderIDs();
/* 110 */     registerRendering();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerRendering() {
/* 115 */     MinecraftForgeClient.registerItemRenderer(ModItems.wyvernBow, (IItemRenderer)new RenderBow());
/* 116 */     MinecraftForgeClient.registerItemRenderer(ModItems.draconicBow, (IItemRenderer)new RenderBow());
/* 117 */     MinecraftForgeClient.registerItemRenderer((Item)ModItems.mobSoul, (IItemRenderer)new RenderMobSoul());
/* 118 */     MinecraftForgeClient.registerItemRenderer((Item)ModItems.reactorStabilizerParts, (IItemRenderer)new RenderStabilizerPart());
/*     */     
/* 120 */     if (!ConfigHandler.useOldArmorModel) {
/* 121 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.wyvernHelm, (IItemRenderer)new RenderArmor(ModItems.wyvernHelm));
/* 122 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.wyvernChest, (IItemRenderer)new RenderArmor(ModItems.wyvernChest));
/* 123 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.wyvernLeggs, (IItemRenderer)new RenderArmor(ModItems.wyvernLeggs));
/* 124 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.wyvernBoots, (IItemRenderer)new RenderArmor(ModItems.wyvernBoots));
/* 125 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.draconicHelm, (IItemRenderer)new RenderArmor(ModItems.draconicHelm));
/* 126 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.draconicChest, (IItemRenderer)new RenderArmor(ModItems.draconicChest));
/* 127 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.draconicLeggs, (IItemRenderer)new RenderArmor(ModItems.draconicLeggs));
/* 128 */       MinecraftForgeClient.registerItemRenderer((Item)ModItems.draconicBoots, (IItemRenderer)new RenderArmor(ModItems.draconicBoots));
/*     */     } 
/*     */     
/* 131 */     if (!ConfigHandler.useOldD2DToolTextures) {
/* 132 */       MinecraftForgeClient.registerItemRenderer(ModItems.draconicSword, (IItemRenderer)new RenderTool("models/tools/DraconicSword.obj", "textures/models/tools/DraconicSword.png", (IRenderTweak)ModItems.draconicSword));
/* 133 */       MinecraftForgeClient.registerItemRenderer(ModItems.wyvernPickaxe, (IItemRenderer)new RenderTool("models/tools/Pickaxe.obj", "textures/models/tools/Pickaxe.png", (IRenderTweak)ModItems.wyvernPickaxe));
/* 134 */       MinecraftForgeClient.registerItemRenderer(ModItems.draconicPickaxe, (IItemRenderer)new RenderTool("models/tools/DraconicPickaxe.obj", "textures/models/tools/DraconicPickaxe.png", (IRenderTweak)ModItems.draconicPickaxe));
/* 135 */       MinecraftForgeClient.registerItemRenderer(ModItems.draconicAxe, (IItemRenderer)new RenderTool("models/tools/DraconicLumberAxe.obj", "textures/models/tools/DraconicLumberAxe.png", (IRenderTweak)ModItems.draconicAxe));
/* 136 */       MinecraftForgeClient.registerItemRenderer(ModItems.wyvernShovel, (IItemRenderer)new RenderTool("models/tools/Shovel.obj", "textures/models/tools/Shovel.png", (IRenderTweak)ModItems.wyvernShovel));
/* 137 */       MinecraftForgeClient.registerItemRenderer(ModItems.draconicShovel, (IItemRenderer)new RenderTool("models/tools/DraconicShovel.obj", "textures/models/tools/DraconicShovel.png", (IRenderTweak)ModItems.draconicShovel));
/* 138 */       MinecraftForgeClient.registerItemRenderer(ModItems.wyvernSword, (IItemRenderer)new RenderTool("models/tools/Sword.obj", "textures/models/tools/Sword.png", (IRenderTweak)ModItems.wyvernSword));
/* 139 */       MinecraftForgeClient.registerItemRenderer(ModItems.draconicDestructionStaff, (IItemRenderer)new RenderTool("models/tools/DraconicStaffOfPower.obj", "textures/models/tools/DraconicStaffOfPower.png", (IRenderTweak)ModItems.draconicDestructionStaff));
/* 140 */       MinecraftForgeClient.registerItemRenderer(ModItems.draconicHoe, (IItemRenderer)new RenderTool("models/tools/DraconicHoe.obj", "textures/models/tools/DraconicHoe.png", (IRenderTweak)ModItems.draconicHoe));
/* 141 */       MinecraftForgeClient.registerItemRenderer(ModItems.draconicBow, (IItemRenderer)new RenderBowModel(true));
/* 142 */       MinecraftForgeClient.registerItemRenderer(ModItems.wyvernBow, (IItemRenderer)new RenderBowModel(false));
/*     */     } 
/*     */     
/* 145 */     MinecraftForgeClient.registerItemRenderer(Item.func_150898_a((Block)ModBlocks.particleGenerator), (IItemRenderer)new RenderParticleGen());
/* 146 */     MinecraftForgeClient.registerItemRenderer(Item.func_150898_a((Block)ModBlocks.energyInfuser), (IItemRenderer)new RenderEnergyInfuser());
/* 147 */     MinecraftForgeClient.registerItemRenderer(Item.func_150898_a((Block)ModBlocks.upgradeModifier), (IItemRenderer)new RenderUpgradeModifier());
/*     */ 
/*     */     
/* 150 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderTeleporterStand());
/* 151 */     RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new RenderPortal());
/*     */ 
/*     */     
/* 154 */     ClientRegistry.bindTileEntitySpecialRenderer(TileParticleGenerator.class, (TileEntitySpecialRenderer)new RenderTileParticleGen());
/* 155 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyInfuser.class, (TileEntitySpecialRenderer)new RenderTileEnergyInfiser());
/* 156 */     ClientRegistry.bindTileEntitySpecialRenderer(TileCustomSpawner.class, (TileEntitySpecialRenderer)new RenderTileCustomSpawner());
/*     */     
/* 158 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyStorageCore.class, (TileEntitySpecialRenderer)new RenderTileEnergyStorageCore());
/* 159 */     ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyPylon.class, (TileEntitySpecialRenderer)new RenderTileEnergyPylon());
/* 160 */     ClientRegistry.bindTileEntitySpecialRenderer(TilePlacedItem.class, (TileEntitySpecialRenderer)new RenderTilePlacedItem());
/* 161 */     ClientRegistry.bindTileEntitySpecialRenderer(TileDissEnchanter.class, (TileEntitySpecialRenderer)new RenderTileDissEnchanter());
/* 162 */     ClientRegistry.bindTileEntitySpecialRenderer(TileTeleporterStand.class, (TileEntitySpecialRenderer)new RenderTileTeleporterStand());
/* 163 */     ClientRegistry.bindTileEntitySpecialRenderer(TileUpgradeModifier.class, (TileEntitySpecialRenderer)new RenderTileUpgradeModifier());
/*     */ 
/*     */     
/* 166 */     RenderingRegistry.registerEntityRenderingHandler(EntityCustomArrow.class, (Render)new RenderEntityCustomArrow());
/*     */   }
/*     */   
/*     */   public void registerRenderIDs() {
/* 170 */     References.idTeleporterStand = RenderingRegistry.getNextAvailableRenderId();
/* 171 */     References.idPortal = RenderingRegistry.getNextAvailableRenderId();
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleEnergyBeam energyBeam(World worldObj, double x, double y, double z, double tx, double ty, double tz, int powerFlow, boolean advanced, ParticleEnergyBeam oldBeam, boolean render, int beamType) {
/* 176 */     if (!worldObj.field_72995_K)
/* 177 */       return null; 
/* 178 */     ParticleEnergyBeam beam = oldBeam;
/* 179 */     boolean inRange = (ParticleHandler.isInRange(x, y, z, 50.0D) || ParticleHandler.isInRange(tx, ty, tz, 50.0D));
/*     */     
/* 181 */     if (beam == null || beam.field_70128_L)
/* 182 */     { if (inRange) {
/* 183 */         beam = new ParticleEnergyBeam(worldObj, x, y, z, tx, ty, tz, 8, powerFlow, advanced, beamType);
/*     */         
/* 185 */         (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((EntityFX)beam);
/*     */       }  }
/* 187 */     else { if (!inRange) {
/* 188 */         beam.func_70106_y();
/* 189 */         return null;
/*     */       } 
/* 191 */       beam.update(powerFlow, render); }
/* 192 */      return beam;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleEnergyField energyField(World worldObj, double x, double y, double z, int type, boolean advanced, ParticleEnergyField oldBeam, boolean render) {
/* 197 */     if (!worldObj.field_72995_K)
/* 198 */       return null; 
/* 199 */     ParticleEnergyField beam = oldBeam;
/* 200 */     boolean inRange = ParticleHandler.isInRange(x, y, z, 50.0D);
/*     */     
/* 202 */     if (beam == null || beam.field_70128_L)
/* 203 */     { if (inRange) {
/* 204 */         beam = new ParticleEnergyField(worldObj, x, y, z, 8, type, advanced);
/*     */         
/* 206 */         (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((EntityFX)beam);
/*     */       }  }
/* 208 */     else { if (!inRange) {
/* 209 */         beam.func_70106_y();
/* 210 */         return null;
/*     */       } 
/* 212 */       beam.update(render); }
/* 213 */      return beam;
/*     */   }
/*     */   
/*     */   public boolean isOp(String paramString) {
/* 217 */     return (Minecraft.func_71410_x()).field_71441_e.func_72912_H().func_76077_q().func_77145_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnParticle(Object particle, int range) {
/* 222 */     if (particle instanceof EntityFX && ((EntityFX)particle).field_70170_p.field_72995_K) {
/* 223 */       ParticleHandler.spawnCustomParticle((EntityFX)particle, range);
/*     */     }
/*     */   }
/*     */   
/*     */   public ISound playISound(ISound sound) {
/* 228 */     FMLClientHandler.instance().getClient().func_147118_V().func_147682_a(sound);
/* 229 */     return sound;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\client\ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */