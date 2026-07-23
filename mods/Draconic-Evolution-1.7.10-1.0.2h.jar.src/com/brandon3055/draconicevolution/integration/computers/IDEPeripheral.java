package com.brandon3055.draconicevolution.integration.computers;

public interface IDEPeripheral {
  String getName();
  
  String[] getMethodNames();
  
  Object[] callMethod(String paramString, Object... paramVarArgs);
}


/* Location:              C:\Users\Vasya\OneDrive\Рабочий стол\Draconic-Evolution-1.7.10-1.0.2h.jar!\com\brandon3055\draconicevolution\integration\computers\IDEPeripheral.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */