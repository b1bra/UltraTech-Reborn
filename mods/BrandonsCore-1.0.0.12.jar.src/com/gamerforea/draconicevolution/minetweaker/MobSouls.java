package com.gamerforea.draconicevolution.minetweaker;

import net.minecraft.entity.Entity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("draconicevolution.MobSoul")
public final class MobSouls {
  @ZenMethod
  public static void addBlackList(Class<? extends Entity> entityClass) {}
  
  @ZenMethod
  public static void addBlackList(String entityName) {}
}


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\BrandonsCore-1.0.0.12.jar!\com\gamerforea\draconicevolution\minetweaker\MobSouls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */