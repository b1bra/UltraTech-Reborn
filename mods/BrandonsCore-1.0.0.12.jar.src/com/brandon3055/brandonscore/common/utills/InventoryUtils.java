/*     */ package com.brandon3055.brandonscore.common.utills;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.loliland.mctags.api.generic.IItemMatcher;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class InventoryUtils {
/*     */   public static boolean consumeItem(EntityPlayer player, IItemMatcher matcher) {
/*  27 */     int slot = findItem(player, matcher);
/*  28 */     if (slot == -1) {
/*  29 */       return false;
/*     */     }
/*     */     
/*  32 */     ItemStack stack = player.field_71071_by.field_70462_a[slot];
/*  33 */     stack.field_77994_a--;
/*  34 */     if (stack.field_77994_a <= 0) {
/*  35 */       player.field_71071_by.field_70462_a[slot] = null;
/*     */     }
/*     */     
/*  38 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findItem(EntityPlayer player, IItemMatcher matcher) {
/*  43 */     for (int slot = 0; slot < player.field_71071_by.field_70462_a.length; slot++) {
/*  44 */       if (matcher.is(player.field_71071_by.field_70462_a[slot])) {
/*  45 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/*  49 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean hasItem(EntityPlayer player, IItemMatcher matcher) {
/*  53 */     return (findItem(player, matcher) != -1);
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
/*     */   public static void tryInsertStack(IInventory targetInventory, int slot, ItemStack stack, boolean canMerge) {
/*  68 */     if (targetInventory.func_94041_b(slot, stack)) {
/*  69 */       ItemStack targetStack = targetInventory.func_70301_a(slot);
/*  70 */       if (targetStack == null) {
/*  71 */         targetInventory.func_70299_a(slot, stack.func_77946_l());
/*  72 */         stack.field_77994_a = 0;
/*  73 */       } else if (canMerge && 
/*  74 */         targetInventory.func_94041_b(slot, stack) && 
/*  75 */         areMergeCandidates(stack, targetStack)) {
/*  76 */         int space = targetStack.func_77976_d() - targetStack.field_77994_a;
/*     */         
/*  78 */         int mergeAmount = Math.min(space, stack.field_77994_a);
/*  79 */         ItemStack copy = targetStack.func_77946_l();
/*  80 */         copy.field_77994_a += mergeAmount;
/*  81 */         targetInventory.func_70299_a(slot, copy);
/*  82 */         stack.field_77994_a -= mergeAmount;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean areItemAndTagEqual(ItemStack stackA, ItemStack stackB) {
/*  89 */     return (stackA.func_77969_a(stackB) && ItemStack.func_77970_a(stackA, stackB));
/*     */   }
/*     */   
/*     */   public static boolean areMergeCandidates(ItemStack source, ItemStack target) {
/*  93 */     return (areItemAndTagEqual(source, target) && target.field_77994_a < target.func_77976_d());
/*     */   }
/*     */   
/*     */   public static void insertItemIntoInventory(IInventory inventory, ItemStack stack) {
/*  97 */     insertItemIntoInventory(inventory, stack, ForgeDirection.UNKNOWN, -1);
/*     */   }
/*     */   
/*     */   public static void insertItemIntoInventory(IInventory inventory, ItemStack stack, ForgeDirection side, int intoSlot) {
/* 101 */     insertItemIntoInventory(inventory, stack, side, intoSlot, true);
/*     */   }
/*     */   
/*     */   public static void insertItemIntoInventory(IInventory inventory, ItemStack stack, ForgeDirection side, int intoSlot, boolean doMove) {
/* 105 */     insertItemIntoInventory(inventory, stack, side, intoSlot, doMove, true);
/*     */   }
/*     */   
/*     */   public static void insertItemIntoInventory(IInventory inventory, ItemStack stack, ForgeDirection side, int intoSlot, boolean doMove, boolean canStack) {
/* 109 */     if (stack == null)
/*     */       return; 
/* 111 */     int sideId = side.ordinal();
/* 112 */     IInventory targetInventory = inventory;
/*     */ 
/*     */     
/* 115 */     if (!doMove) {
/* 116 */       GenericInventory copy = new GenericInventory("temporary.inventory", false, targetInventory.func_70302_i_());
/* 117 */       copy.copyFrom(inventory);
/* 118 */       targetInventory = copy;
/*     */     } 
/*     */     
/* 121 */     Set<Integer> attemptSlots = Sets.newTreeSet();
/*     */ 
/*     */     
/* 124 */     boolean isSidedInventory = (inventory instanceof ISidedInventory && side != ForgeDirection.UNKNOWN);
/*     */     
/* 126 */     if (isSidedInventory) {
/* 127 */       int[] accessibleSlots = ((ISidedInventory)inventory).func_94128_d(sideId);
/* 128 */       for (int slot : accessibleSlots) {
/* 129 */         attemptSlots.add(Integer.valueOf(slot));
/*     */       }
/*     */     } else {
/* 132 */       for (int a = 0; a < inventory.func_70302_i_(); a++) {
/* 133 */         attemptSlots.add(Integer.valueOf(a));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 138 */     if (intoSlot > -1) attemptSlots.retainAll((Collection<?>)ImmutableSet.of(Integer.valueOf(intoSlot)));
/*     */     
/* 140 */     if (attemptSlots.isEmpty())
/*     */       return; 
/* 142 */     for (Integer slot : attemptSlots) {
/* 143 */       if (stack.field_77994_a <= 0)
/* 144 */         break;  if (isSidedInventory && !((ISidedInventory)inventory).func_102007_a(slot.intValue(), stack, sideId))
/* 145 */         continue;  tryInsertStack(targetInventory, slot.intValue(), stack, canStack);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int moveItemInto(IInventory fromInventory, int fromSlot, Object target, int intoSlot, int maxAmount, ForgeDirection direction, boolean doMove) {
/* 150 */     return moveItemInto(fromInventory, fromSlot, target, intoSlot, maxAmount, direction, doMove, true);
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
/*     */   public static int moveItemInto(IInventory fromInventory, int fromSlot, Object target, int intoSlot, int maxAmount, ForgeDirection direction, boolean doMove, boolean canStack) {
/* 180 */     fromInventory = getInventory(fromInventory);
/*     */ 
/*     */     
/* 183 */     ItemStack sourceStack = fromInventory.func_70301_a(fromSlot);
/* 184 */     if (sourceStack == null) {
/* 185 */       return 0;
/*     */     }
/*     */     
/* 188 */     if (fromInventory instanceof ISidedInventory && 
/* 189 */       !((ISidedInventory)fromInventory).func_102008_b(fromSlot, sourceStack, direction.ordinal())) {
/* 190 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 194 */     ItemStack clonedSourceStack = sourceStack.func_77946_l();
/* 195 */     clonedSourceStack.field_77994_a = Math.min(clonedSourceStack.field_77994_a, maxAmount);
/* 196 */     int amountToMove = clonedSourceStack.field_77994_a;
/* 197 */     int inserted = 0;
/*     */     
/* 199 */     if (target instanceof IInventory) {
/* 200 */       IInventory targetInventory = getInventory((IInventory)target);
/* 201 */       ForgeDirection side = direction.getOpposite();
/*     */ 
/*     */       
/* 204 */       insertItemIntoInventory(targetInventory, clonedSourceStack, side, intoSlot, doMove, canStack);
/* 205 */       inserted = amountToMove - clonedSourceStack.field_77994_a;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     if (doMove) {
/* 212 */       ItemStack newSourcestack = sourceStack.func_77946_l();
/* 213 */       newSourcestack.field_77994_a -= inserted;
/* 214 */       if (newSourcestack.field_77994_a == 0) {
/* 215 */         fromInventory.func_70299_a(fromSlot, null);
/*     */       } else {
/* 217 */         fromInventory.func_70299_a(fromSlot, newSourcestack);
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     return inserted;
/*     */   }
/*     */   
/*     */   private static IInventory doubleChestFix(TileEntity te) {
/* 225 */     World world = te.func_145831_w();
/* 226 */     int x = te.field_145851_c;
/* 227 */     int y = te.field_145848_d;
/* 228 */     int z = te.field_145849_e;
/* 229 */     if (world.func_147439_a(x - 1, y, z) == Blocks.field_150486_ae)
/* 230 */       return (IInventory)new InventoryLargeChest("Large chest", (IInventory)world.func_147438_o(x - 1, y, z), (IInventory)te); 
/* 231 */     if (world.func_147439_a(x + 1, y, z) == Blocks.field_150486_ae)
/* 232 */       return (IInventory)new InventoryLargeChest("Large chest", (IInventory)te, (IInventory)world.func_147438_o(x + 1, y, z)); 
/* 233 */     if (world.func_147439_a(x, y, z - 1) == Blocks.field_150486_ae)
/* 234 */       return (IInventory)new InventoryLargeChest("Large chest", (IInventory)world.func_147438_o(x, y, z - 1), (IInventory)te); 
/* 235 */     if (world.func_147439_a(x, y, z + 1) == Blocks.field_150486_ae)
/* 236 */       return (IInventory)new InventoryLargeChest("Large chest", (IInventory)te, (IInventory)world.func_147438_o(x, y, z + 1)); 
/* 237 */     return (te instanceof IInventory) ? (IInventory)te : null;
/*     */   }
/*     */   
/*     */   public static IInventory getInventory(World world, int x, int y, int z) {
/* 241 */     TileEntity tileEntity = world.func_147438_o(x, y, z);
/* 242 */     if (tileEntity instanceof net.minecraft.tileentity.TileEntityChest) return doubleChestFix(tileEntity); 
/* 243 */     if (tileEntity instanceof IInventory) return (IInventory)tileEntity; 
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   public static IInventory getInventory(World world, int x, int y, int z, ForgeDirection direction) {
/* 248 */     if (direction != null) {
/* 249 */       x += direction.offsetX;
/* 250 */       y += direction.offsetY;
/* 251 */       z += direction.offsetZ;
/*     */     } 
/* 253 */     return getInventory(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static IInventory getInventory(IInventory inventory) {
/* 258 */     if (inventory instanceof net.minecraft.tileentity.TileEntityChest) return doubleChestFix((TileEntity)inventory); 
/* 259 */     return inventory;
/*     */   }
/*     */   
/*     */   public static List<ItemStack> getInventoryContents(IInventory inventory) {
/* 263 */     List<ItemStack> result = Lists.newArrayList();
/* 264 */     for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 265 */       ItemStack slot = inventory.func_70301_a(i);
/* 266 */       if (slot != null) result.add(slot); 
/*     */     } 
/* 268 */     return result;
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
/*     */   public static Set<Integer> getSlotsWithStack(IInventory inventory, ItemStack stack) {
/* 280 */     inventory = getInventory(inventory);
/* 281 */     Set<Integer> slots = Sets.newHashSet();
/* 282 */     for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 283 */       ItemStack stackInSlot = inventory.func_70301_a(i);
/* 284 */       if (stackInSlot != null && stackInSlot.func_77969_a(stack)) slots.add(Integer.valueOf(i)); 
/*     */     } 
/* 286 */     return slots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFirstSlotWithStack(IInventory inventory, ItemStack stack) {
/* 297 */     inventory = getInventory(inventory);
/* 298 */     for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 299 */       ItemStack stackInSlot = inventory.func_70301_a(i);
/* 300 */       if (stackInSlot != null && stackInSlot.func_77969_a(stack)) {
/* 301 */         return i;
/*     */       }
/*     */     } 
/* 304 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean consumeInventoryItem(IInventory inventory, ItemStack stack) {
/* 315 */     int slotWithStack = getFirstSlotWithStack(inventory, stack);
/* 316 */     if (slotWithStack > -1) {
/* 317 */       ItemStack stackInSlot = inventory.func_70301_a(slotWithStack);
/* 318 */       stackInSlot.field_77994_a--;
/* 319 */       if (stackInSlot.field_77994_a == 0) {
/* 320 */         inventory.func_70299_a(slotWithStack, null);
/*     */       }
/* 322 */       return true;
/*     */     } 
/* 324 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSlotIndexOfNextStack(IInventory invent) {
/* 334 */     for (int i = 0; i < invent.func_70302_i_(); i++) {
/* 335 */       ItemStack stack = invent.func_70301_a(i);
/* 336 */       if (stack != null) {
/* 337 */         return i;
/*     */       }
/*     */     } 
/* 340 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack removeNextItemStack(IInventory invent) {
/* 350 */     int nextFilledSlot = getSlotIndexOfNextStack(invent);
/* 351 */     if (nextFilledSlot > -1) {
/* 352 */       ItemStack copy = invent.func_70301_a(nextFilledSlot).func_77946_l();
/* 353 */       invent.func_70299_a(nextFilledSlot, null);
/* 354 */       return copy;
/*     */     } 
/* 356 */     return null;
/*     */   }
/*     */   
/*     */   public static int moveItemsFromOneOfSides(TileEntity te, IInventory inv, int maxAmount, int intoSlot, Set<ForgeDirection> sides) {
/* 360 */     return moveItemsFromOneOfSides(te, inv, null, maxAmount, intoSlot, sides);
/*     */   }
/*     */   
/*     */   public static int moveItemsFromOneOfSides(TileEntity te, IInventory inv, ItemStack filterStack, int maxAmount, int intoSlot, Set<ForgeDirection> sides) {
/* 364 */     List<ForgeDirection> shuffledSides = Lists.newArrayList(sides);
/* 365 */     Collections.shuffle(shuffledSides);
/*     */     
/* 367 */     IInventory ourInventory = getInventory(inv);
/*     */ 
/*     */     
/* 370 */     for (ForgeDirection dir : sides) {
/* 371 */       TileEntity tileOnSurface = getTileInDirection(te, dir);
/*     */       
/* 373 */       if (tileOnSurface instanceof IInventory) {
/* 374 */         Set<Integer> slots; IInventory neighbor = (IInventory)tileOnSurface;
/*     */ 
/*     */ 
/*     */         
/* 378 */         if (filterStack == null) { slots = getAllSlots(neighbor); }
/* 379 */         else { slots = getSlotsWithStack(neighbor, filterStack); }
/*     */         
/* 381 */         for (Integer slot : slots) {
/* 382 */           int moved = moveItemInto(neighbor, slot.intValue(), ourInventory, intoSlot, maxAmount, dir.getOpposite(), true);
/* 383 */           if (moved > 0) return moved; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 387 */     return 0;
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
/*     */   public static int testInventoryInsertion(IInventory inventory, ItemStack item) {
/* 399 */     if (item == null || item.field_77994_a == 0) return 0; 
/* 400 */     if (inventory == null) return 0; 
/* 401 */     int slotCount = inventory.func_70302_i_();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     int itemSizeCounter = item.field_77994_a;
/* 407 */     for (int i = 0; i < slotCount && itemSizeCounter > 0; i++) {
/*     */       
/* 409 */       if (inventory.func_94041_b(i, item)) {
/* 410 */         ItemStack inventorySlot = inventory.func_70301_a(i);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 415 */         if (inventorySlot == null) {
/* 416 */           itemSizeCounter -= Math.min(Math.min(itemSizeCounter, inventory.func_70297_j_()), item.func_77976_d());
/*     */         
/*     */         }
/* 419 */         else if (areMergeCandidates(item, inventorySlot)) {
/*     */ 
/*     */           
/* 422 */           int space = inventorySlot.func_77976_d() - inventorySlot.field_77994_a;
/*     */           
/* 424 */           itemSizeCounter -= Math.min(itemSizeCounter, space);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 429 */     if (itemSizeCounter != item.field_77994_a) {
/* 430 */       itemSizeCounter = Math.max(itemSizeCounter, 0);
/* 431 */       return item.field_77994_a - itemSizeCounter;
/*     */     } 
/* 433 */     return 0;
/*     */   }
/*     */   
/*     */   public static Set<Integer> getAllSlots(IInventory inventory) {
/* 437 */     inventory = getInventory(inventory);
/* 438 */     Set<Integer> slots = new HashSet<>();
/* 439 */     for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 440 */       slots.add(Integer.valueOf(i));
/*     */     }
/* 442 */     return slots;
/*     */   }
/*     */   
/*     */   public static Map<Integer, ItemStack> getAllItems(IInventory inventory) {
/* 446 */     Map<Integer, ItemStack> result = Maps.newHashMap();
/* 447 */     for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 448 */       ItemStack stack = inventory.func_70301_a(i);
/* 449 */       if (stack != null) result.put(Integer.valueOf(i), stack);
/*     */     
/*     */     } 
/* 452 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int moveItemsToOneOfSides(TileEntity te, IInventory inv, int fromSlot, int maxAmount, Set<ForgeDirection> sides) {
/* 459 */     IInventory inventory = getInventory(inv);
/*     */ 
/*     */     
/* 462 */     if (inventory.func_70301_a(fromSlot) == null) return 0;
/*     */ 
/*     */     
/* 465 */     List<ForgeDirection> shuffledSides = Lists.newArrayList(sides);
/* 466 */     Collections.shuffle(shuffledSides);
/*     */     
/* 468 */     for (ForgeDirection dir : shuffledSides) {
/*     */       
/* 470 */       TileEntity tileOnSurface = getTileInDirection(te, dir);
/* 471 */       if (tileOnSurface == null) {
/* 472 */         return 0;
/*     */       }
/*     */       
/* 475 */       int moved = moveItemInto(inventory, fromSlot, tileOnSurface, -1, maxAmount, dir, true);
/*     */ 
/*     */       
/* 478 */       if (moved > 0) return moved; 
/*     */     } 
/* 480 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean inventoryIsEmpty(IInventory inventory) {
/* 484 */     for (int i = 0, l = inventory.func_70302_i_(); i < l; i++) {
/* 485 */       if (inventory.func_70301_a(i) != null) return false; 
/* 486 */     }  return true;
/*     */   }
/*     */   
/*     */   public static boolean tryMergeStacks(ItemStack stackToMerge, ItemStack stackInSlot) {
/* 490 */     if (stackInSlot == null || !stackInSlot.func_77969_a(stackToMerge) || !ItemStack.func_77970_a(stackToMerge, stackInSlot)) {
/* 491 */       return false;
/*     */     }
/* 493 */     int newStackSize = stackInSlot.field_77994_a + stackToMerge.field_77994_a;
/*     */     
/* 495 */     int maxStackSize = stackToMerge.func_77976_d();
/* 496 */     if (newStackSize <= maxStackSize) {
/* 497 */       stackToMerge.field_77994_a = 0;
/* 498 */       stackInSlot.field_77994_a = newStackSize;
/* 499 */       return true;
/* 500 */     }  if (stackInSlot.field_77994_a < maxStackSize) {
/* 501 */       stackToMerge.field_77994_a -= maxStackSize - stackInSlot.field_77994_a;
/* 502 */       stackInSlot.field_77994_a = maxStackSize;
/* 503 */       return true;
/*     */     } 
/*     */     
/* 506 */     return false;
/*     */   }
/*     */   
/*     */   public static ItemStack returnItem(ItemStack stack) {
/* 510 */     return (stack == null || stack.field_77994_a <= 0) ? null : stack.func_77946_l();
/*     */   }
/*     */   
/*     */   public static void swapStacks(IInventory inventory, int slot1, int slot2) {
/* 514 */     swapStacks(inventory, slot1, slot2, true, true);
/*     */   }
/*     */   
/*     */   public static void swapStacks(IInventory inventory, int slot1, int slot2, boolean copy, boolean validate) {
/* 518 */     Preconditions.checkElementIndex(slot1, inventory.func_70302_i_(), "input slot id");
/* 519 */     Preconditions.checkElementIndex(slot2, inventory.func_70302_i_(), "output slot id");
/*     */     
/* 521 */     ItemStack stack1 = inventory.func_70301_a(slot1);
/* 522 */     ItemStack stack2 = inventory.func_70301_a(slot2);
/*     */     
/* 524 */     if (validate) {
/* 525 */       isItemValid(inventory, slot2, stack1);
/* 526 */       isItemValid(inventory, slot1, stack2);
/*     */     } 
/*     */     
/* 529 */     if (copy) {
/* 530 */       if (stack1 != null) stack1 = stack1.func_77946_l(); 
/* 531 */       if (stack2 != null) stack2 = stack2.func_77946_l();
/*     */     
/*     */     } 
/* 534 */     inventory.func_70299_a(slot1, stack2);
/* 535 */     inventory.func_70299_a(slot2, stack1);
/* 536 */     inventory.func_70296_d();
/*     */   }
/*     */   
/*     */   public static void swapStacks(ISidedInventory inventory, int slot1, ForgeDirection side1, int slot2, ForgeDirection side2) {
/* 540 */     swapStacks(inventory, slot1, side1, slot2, side2, true, true);
/*     */   }
/*     */   
/*     */   public static void swapStacks(ISidedInventory inventory, int slot1, ForgeDirection side1, int slot2, ForgeDirection side2, boolean copy, boolean validate) {
/* 544 */     Preconditions.checkElementIndex(slot1, inventory.func_70302_i_(), "input slot id");
/* 545 */     Preconditions.checkElementIndex(slot2, inventory.func_70302_i_(), "output slot id");
/*     */     
/* 547 */     ItemStack stack1 = inventory.func_70301_a(slot1);
/* 548 */     ItemStack stack2 = inventory.func_70301_a(slot2);
/*     */     
/* 550 */     if (validate) {
/* 551 */       isItemValid((IInventory)inventory, slot2, stack1);
/* 552 */       isItemValid((IInventory)inventory, slot1, stack2);
/*     */       
/* 554 */       canExtract(inventory, slot1, side1, stack1);
/* 555 */       canInsert(inventory, slot2, side2, stack1);
/*     */       
/* 557 */       canExtract(inventory, slot2, side2, stack2);
/* 558 */       canInsert(inventory, slot1, side1, stack2);
/*     */     } 
/*     */     
/* 561 */     if (copy) {
/* 562 */       if (stack1 != null) stack1 = stack1.func_77946_l(); 
/* 563 */       if (stack2 != null) stack2 = stack2.func_77946_l();
/*     */     
/*     */     } 
/* 566 */     inventory.func_70299_a(slot1, stack2);
/* 567 */     inventory.func_70299_a(slot2, stack1);
/* 568 */     inventory.func_70296_d();
/*     */   }
/*     */   
/*     */   protected static void isItemValid(IInventory inventory, int slot, ItemStack stack) {
/* 572 */     Preconditions.checkArgument(inventory.func_94041_b(slot, stack), "Slot %s cannot accept item", new Object[] { Integer.valueOf(slot) });
/*     */   }
/*     */   
/*     */   protected static void canInsert(ISidedInventory inventory, int slot, ForgeDirection side, ItemStack stack) {
/* 576 */     Preconditions.checkArgument(inventory.func_102007_a(slot, stack, side.ordinal()), "Item cannot be inserted into slot %s on side %s", new Object[] {
/* 577 */           Integer.valueOf(slot), side });
/*     */   }
/*     */   
/*     */   protected static void canExtract(ISidedInventory inventory, int slot, ForgeDirection side, ItemStack stack) {
/* 581 */     Preconditions.checkArgument(inventory.func_102008_b(slot, stack, side.ordinal()), "Item cannot be extracted from slot %s on side %s", new Object[] {
/* 582 */           Integer.valueOf(slot), side });
/*     */   }
/*     */   
/*     */   public static TileEntity getTileInDirection(TileEntity tile, ForgeDirection direction) {
/* 586 */     int targetX = tile.field_145851_c + direction.offsetX;
/* 587 */     int targetY = tile.field_145848_d + direction.offsetY;
/* 588 */     int targetZ = tile.field_145849_e + direction.offsetZ;
/* 589 */     return tile.func_145831_w().func_147438_o(targetX, targetY, targetZ);
/*     */   }
/*     */   
/*     */   public static class GenericInventory
/*     */     implements IInventory {
/*     */     protected String inventoryTitle;
/*     */     protected int slotsCount;
/*     */     protected ItemStack[] inventoryContents;
/*     */     protected boolean isInvNameLocalized;
/*     */     
/*     */     public GenericInventory(String name, boolean isInvNameLocalized, int size) {
/* 600 */       this.isInvNameLocalized = isInvNameLocalized;
/* 601 */       this.slotsCount = size;
/* 602 */       this.inventoryTitle = name;
/* 603 */       this.inventoryContents = new ItemStack[size];
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70298_a(int par1, int par2) {
/* 608 */       if (this.inventoryContents[par1] != null) {
/*     */ 
/*     */         
/* 611 */         if ((this.inventoryContents[par1]).field_77994_a <= par2) {
/* 612 */           ItemStack itemStack = this.inventoryContents[par1];
/* 613 */           this.inventoryContents[par1] = null;
/* 614 */           return itemStack;
/*     */         } 
/* 616 */         ItemStack itemstack = this.inventoryContents[par1].func_77979_a(par2);
/* 617 */         if ((this.inventoryContents[par1]).field_77994_a == 0) {
/* 618 */           this.inventoryContents[par1] = null;
/*     */         }
/*     */         
/* 621 */         return itemstack;
/*     */       } 
/* 623 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_70297_j_() {
/* 628 */       return 64;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_70302_i_() {
/* 633 */       return this.slotsCount;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70301_a(int i) {
/* 638 */       return this.inventoryContents[i];
/*     */     }
/*     */     
/*     */     public ItemStack getStackInSlot(Enum<?> i) {
/* 642 */       return func_70301_a(i.ordinal());
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_70304_b(int i) {
/* 647 */       if (i >= this.inventoryContents.length) {
/* 648 */         return null;
/*     */       }
/* 650 */       if (this.inventoryContents[i] != null) {
/* 651 */         ItemStack itemstack = this.inventoryContents[i];
/* 652 */         this.inventoryContents[i] = null;
/* 653 */         return itemstack;
/*     */       } 
/* 655 */       return null;
/*     */     }
/*     */     
/*     */     public boolean isItem(int slot, Item item) {
/* 659 */       return (this.inventoryContents[slot] != null && this.inventoryContents[slot]
/* 660 */         .func_77973_b() == item);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_94041_b(int i, ItemStack itemstack) {
/* 665 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_70300_a(EntityPlayer entityplayer) {
/* 670 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clearAndSetSlotCount(int amount) {
/* 675 */       this.slotsCount = amount;
/* 676 */       this.inventoryContents = new ItemStack[amount];
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound tag) {
/* 680 */       if (tag.func_74764_b("size")) {
/* 681 */         this.slotsCount = tag.func_74762_e("size");
/*     */       }
/* 683 */       NBTTagList nbttaglist = tag.func_150295_c("Items", 10);
/* 684 */       this.inventoryContents = new ItemStack[this.slotsCount];
/* 685 */       for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/* 686 */         NBTTagCompound stacktag = nbttaglist.func_150305_b(i);
/* 687 */         int j = stacktag.func_74771_c("Slot");
/* 688 */         if (j >= 0 && j < this.inventoryContents.length) {
/* 689 */           this.inventoryContents[j] = ItemStack.func_77949_a(stacktag);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_70299_a(int i, ItemStack itemstack) {
/* 696 */       this.inventoryContents[i] = itemstack;
/*     */       
/* 698 */       if (itemstack != null && itemstack.field_77994_a > func_70297_j_()) {
/* 699 */         itemstack.field_77994_a = func_70297_j_();
/*     */       }
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound tag) {
/* 704 */       tag.func_74768_a("size", func_70302_i_());
/* 705 */       NBTTagList nbttaglist = new NBTTagList();
/* 706 */       for (int i = 0; i < this.inventoryContents.length; i++) {
/* 707 */         if (this.inventoryContents[i] != null) {
/* 708 */           NBTTagCompound stacktag = new NBTTagCompound();
/* 709 */           stacktag.func_74774_a("Slot", (byte)i);
/* 710 */           this.inventoryContents[i].func_77955_b(stacktag);
/* 711 */           nbttaglist.func_74742_a((NBTBase)stacktag);
/*     */         } 
/*     */       } 
/* 714 */       tag.func_74782_a("Items", (NBTBase)nbttaglist);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70296_d() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void copyFrom(IInventory inventory) {
/* 725 */       for (int i = 0; i < inventory.func_70302_i_(); i++) {
/* 726 */         if (i < func_70302_i_()) {
/* 727 */           ItemStack stack = inventory.func_70301_a(i);
/* 728 */           if (stack != null) {
/* 729 */             func_70299_a(i, stack.func_77946_l());
/*     */           } else {
/* 731 */             func_70299_a(i, null);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public List<ItemStack> contents() {
/* 738 */       return Arrays.asList(this.inventoryContents);
/*     */     }
/*     */ 
/*     */     
/*     */     public String func_145825_b() {
/* 743 */       return this.inventoryTitle;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_145818_k_() {
/* 748 */       return this.isInvNameLocalized;
/*     */     }
/*     */     
/*     */     public void func_70295_k_() {}
/*     */     
/*     */     public void func_70305_f() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\brandon3055\brandonscore\commo\\utills\InventoryUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */