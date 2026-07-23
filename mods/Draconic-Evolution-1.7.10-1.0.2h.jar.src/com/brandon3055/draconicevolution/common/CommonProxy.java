/*     */ package com.brandon3055.draconicevolution.common;
/*     */ import com.brandon3055.draconicevolution.DraconicEvolution;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.ParticleEnergyBeam;
/*     */ import com.brandon3055.draconicevolution.client.render.particle.ParticleEnergyField;
/*     */ import com.brandon3055.draconicevolution.common.achievements.Achievements;
/*     */ import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
/*     */ import com.brandon3055.draconicevolution.common.network.BlockUpdatePacket;
/*     */ import com.brandon3055.draconicevolution.common.network.ButtonPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.ContributorPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.GenericParticlePacket;
/*     */ import com.brandon3055.draconicevolution.common.network.ItemConfigPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.MountUpdatePacket;
/*     */ import com.brandon3055.draconicevolution.common.network.ParticleGenPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.PlacedItemPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.PlayerDetectorButtonPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.PlayerDetectorStringPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.ShieldHitPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.SpeedRequestPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.TeleporterPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.TileObjectPacket;
/*     */ import com.brandon3055.draconicevolution.common.network.ToolModePacket;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileCustomSpawner;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
/*     */ import com.brandon3055.draconicevolution.common.tileentities.gates.TileFluidGate;
/*     */ import com.brandon3055.draconicevolution.common.utills.LogHelper;
/*     */ import com.gamerforea.draconicevolution.network.TileMultiObjectPacket;
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.registry.EntityRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ public class CommonProxy {
/*     */   public void preInit(FMLPreInitializationEvent event) {
/*  41 */     ConfigHandler.init(event.getSuggestedConfigurationFile());
/*  42 */     BalanceConfigHandler.init(event.getModConfigurationDirectory());
/*  43 */     registerEventListeners(event.getSide());
/*  44 */     ModBlocks.init();
/*  45 */     ModItems.init();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     registerTileEntities();
/*  51 */     initializeNetwork();
/*     */     
/*  53 */     DraconicEvolution.reaperEnchant = (Enchantment)new EnchantmentReaper(ConfigHandler.reaperEnchantID);
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
/*  78 */     Achievements.addModAchievements();
/*  79 */     LogHelper.info("Finished PreInitialization");
/*     */   }
/*     */   
/*     */   public void init(FMLInitializationEvent event) {
/*  83 */     CraftingHandler.init();
/*  84 */     registerGuiHandeler();
/*  85 */     registerEntities();
/*  86 */     DETab.initialize();
/*  87 */     PotionHandler.init();
/*  88 */     CCOCIntegration.init();
/*  89 */     ModHelper.init();
/*     */     
/*  91 */     LogHelper.info("Finished Initialization");
/*     */   }
/*     */   
/*     */   public void postInit(FMLPostInitializationEvent event) {
/*  95 */     BalanceConfigHandler.finishLoading();
/*  96 */     Achievements.registerAchievementPane();
/*     */ 
/*     */     
/*  99 */     MineTweakerIntegration.init();
/*     */ 
/*     */     
/* 102 */     LogHelper.info("Finished PostInitialization");
/*     */   }
/*     */   
/*     */   public void initializeNetwork() {
/* 106 */     DraconicEvolution.network = NetworkRegistry.INSTANCE.newSimpleChannel("DEvolutionNC");
/* 107 */     DraconicEvolution.network.registerMessage(ButtonPacket.Handler.class, ButtonPacket.class, 0, Side.SERVER);
/* 108 */     DraconicEvolution.network.registerMessage(ParticleGenPacket.Handler.class, ParticleGenPacket.class, 1, Side.SERVER);
/* 109 */     DraconicEvolution.network.registerMessage(PlacedItemPacket.Handler.class, PlacedItemPacket.class, 2, Side.SERVER);
/* 110 */     DraconicEvolution.network.registerMessage(PlayerDetectorButtonPacket.Handler.class, PlayerDetectorButtonPacket.class, 3, Side.SERVER);
/* 111 */     DraconicEvolution.network.registerMessage(PlayerDetectorStringPacket.Handler.class, PlayerDetectorStringPacket.class, 4, Side.SERVER);
/* 112 */     DraconicEvolution.network.registerMessage(TeleporterPacket.Handler.class, TeleporterPacket.class, 5, Side.SERVER);
/* 113 */     DraconicEvolution.network.registerMessage(TileObjectPacket.Handler.class, TileObjectPacket.class, 6, Side.CLIENT);
/* 114 */     DraconicEvolution.network.registerMessage(MountUpdatePacket.Handler.class, MountUpdatePacket.class, 7, Side.CLIENT);
/* 115 */     DraconicEvolution.network.registerMessage(MountUpdatePacket.Handler.class, MountUpdatePacket.class, 8, Side.SERVER);
/* 116 */     DraconicEvolution.network.registerMessage(ItemConfigPacket.Handler.class, ItemConfigPacket.class, 9, Side.SERVER);
/* 117 */     DraconicEvolution.network.registerMessage(TileObjectPacket.Handler.class, TileObjectPacket.class, 10, Side.SERVER);
/* 118 */     DraconicEvolution.network.registerMessage(BlockUpdatePacket.Handler.class, BlockUpdatePacket.class, 11, Side.SERVER);
/* 119 */     DraconicEvolution.network.registerMessage(SpeedRequestPacket.Handler.class, SpeedRequestPacket.class, 12, Side.SERVER);
/* 120 */     DraconicEvolution.network.registerMessage(SpeedRequestPacket.Handler.class, SpeedRequestPacket.class, 13, Side.CLIENT);
/* 121 */     DraconicEvolution.network.registerMessage(ToolModePacket.Handler.class, ToolModePacket.class, 14, Side.SERVER);
/* 122 */     DraconicEvolution.network.registerMessage(GenericParticlePacket.Handler.class, GenericParticlePacket.class, 15, Side.CLIENT);
/* 123 */     DraconicEvolution.network.registerMessage(ShieldHitPacket.Handler.class, ShieldHitPacket.class, 16, Side.CLIENT);
/* 124 */     DraconicEvolution.network.registerMessage(ContributorPacket.Handler.class, ContributorPacket.class, 17, Side.CLIENT);
/* 125 */     DraconicEvolution.network.registerMessage(ContributorPacket.Handler.class, ContributorPacket.class, 18, Side.SERVER);
/*     */ 
/*     */     
/* 128 */     DraconicEvolution.network.registerMessage(TileMultiObjectPacket.Handler.class, TileMultiObjectPacket.class, 19, Side.CLIENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTileEntities() {
/* 133 */     GameRegistry.registerTileEntity(TileGrinder.class, "draconicevolution:TileGrinder");
/* 134 */     GameRegistry.registerTileEntity(TilePotentiometer.class, "draconicevolution:TilePotentiometer");
/* 135 */     GameRegistry.registerTileEntity(TileParticleGenerator.class, "draconicevolution:TileParticleGenerator");
/* 136 */     GameRegistry.registerTileEntity(TilePlayerDetector.class, "draconicevolution:TilePlayerDetector");
/* 137 */     GameRegistry.registerTileEntity(TilePlayerDetectorAdvanced.class, "draconicevolution:TilePlayerDetectorAdvanced");
/* 138 */     GameRegistry.registerTileEntity(TileEnergyInfuser.class, "draconicevolution:TileEnergyInfuser");
/* 139 */     GameRegistry.registerTileEntity(TileCustomSpawner.class, "draconicevolution:TileCustomSpawner");
/* 140 */     GameRegistry.registerTileEntity(TileEnergyStorageCore.class, "draconicevolution:TileEnergyStorageCore");
/* 141 */     GameRegistry.registerTileEntity(TileInvisibleMultiblock.class, "draconicevolution:TileInvisibleMultiblock");
/* 142 */     GameRegistry.registerTileEntity(TileEnergyPylon.class, "draconicevolution:TileEnergyPylon");
/* 143 */     GameRegistry.registerTileEntity(TilePlacedItem.class, "draconicevolution:TilePlacedItem");
/* 144 */     GameRegistry.registerTileEntity(TileDissEnchanter.class, "draconicevolution:TileDissEnchanter");
/* 145 */     GameRegistry.registerTileEntity(TileTeleporterStand.class, "draconicevolution:TileTeleporterStand");
/* 146 */     GameRegistry.registerTileEntity(TileDislocatorReceptacle.class, "draconicevolution:TileDislocatorReceptacle");
/* 147 */     GameRegistry.registerTileEntity(TilePortalBlock.class, "draconicevolution:TilePortalBlock");
/* 148 */     GameRegistry.registerTileEntity(TileFluidGate.class, "draconicevolution:TileFluidGate");
/* 149 */     GameRegistry.registerTileEntity(TileUpgradeModifier.class, "draconicevolution:TileEnhancementModifier");
/*     */   }
/*     */   
/*     */   public void registerEventListeners(Side s) {
/* 153 */     MinecraftForge.EVENT_BUS.register(new MinecraftForgeEventHandler());
/* 154 */     Achievements achievements = new Achievements();
/* 155 */     MinecraftForge.EVENT_BUS.register(achievements);
/* 156 */     FMLCommonHandler.instance().bus().register(achievements);
/* 157 */     FMLCommonHandler.instance().bus().register(new FMLEventHandler());
/*     */   }
/*     */   
/*     */   public void registerGuiHandeler() {
/* 161 */     new GuiHandler();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEntities() {
/* 166 */     EntityRegistry.registerModEntity(EntityPersistentItem.class, "Persistent Item", 1, DraconicEvolution.instance, 32, 5, true);
/* 167 */     EntityRegistry.registerModEntity(EntityDraconicArrow.class, "Arrow", 2, DraconicEvolution.instance, 32, 5, true);
/* 168 */     EntityRegistry.registerModEntity(EntityEnderArrow.class, "Ender Arrow", 3, DraconicEvolution.instance, 32, 1, true);
/*     */     
/* 170 */     EntityRegistry.registerModEntity(EntityCustomArrow.class, "CustomArrow", 11, DraconicEvolution.instance, 128, 1, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleEnergyBeam energyBeam(World worldObj, double x, double y, double z, double tx, double ty, double tz, int powerFlow, boolean advanced, ParticleEnergyBeam oldBeam, boolean render, int beamType) {
/* 175 */     return null;
/*     */   }
/*     */   
/*     */   public ParticleEnergyField energyField(World worldObj, double x, double y, double z, int type, boolean advanced, ParticleEnergyField oldBeam, boolean render) {
/* 179 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnParticle(Object particle, int range) {}
/*     */ 
/*     */   
/*     */   public ISound playISound(ISound sound) {
/* 187 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\common\CommonProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */