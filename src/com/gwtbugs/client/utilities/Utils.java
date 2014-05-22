package com.gwtbugs.client.utilities;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gwtbootstrap3.extras.select.client.ui.Option;

public class Utils {

  private static Logger logger = Logger.getLogger(Utils.class.getName());
  private static Option defaultOption = null;

  public static Option createDefaultHiddenOption() {
    if (defaultOption == null) {
      defaultOption = new Option();
      defaultOption.setText("");
      defaultOption.setEnabled(false);
      defaultOption.setHiddenOn("SM MD LG");
    }
    return defaultOption;
  }

  @SuppressWarnings("rawtypes")
  public static Boolean hasValue(Object obj) {
    if (obj instanceof String) {
      return ((String) obj) != null && !((String) obj).trim().equals("");
    }
    if (obj instanceof Integer) {
      return ((Integer) obj) != null;
    }
    if (obj instanceof Double) {
      return ((Double) obj) != null;
    }
    if (obj instanceof Long) {
      return ((Long) obj) != null;
    }
    if (obj instanceof Short) {
      return ((Short) obj) != null;
    }
    if (obj instanceof List) {
      return ((List) obj) != null && ((List) obj).size() > 0;
    }
    if (obj instanceof Map) {
      return ((Map) obj) != null && ((Map) obj).size() > 0;
    }
    if (obj instanceof Set) {
      return ((Set) obj) != null && ((Set) obj).size() > 0;
    }
    if (obj instanceof Date) {
      return ((Date) obj) != null;
    }
    if (obj instanceof String[]) {
      return ((String[]) obj) != null && ((String[]) obj).length > 0;
    }
    if (obj instanceof LinkedList) {
      return ((LinkedList) obj) != null && ((LinkedList) obj).size() > 0;
    }
    return false;
  }

  public static Integer getIntFromString(String value) {
    Integer integerValuel = null;
    if (Utils.hasValue(value)) {
      try {
        integerValuel = Integer.parseInt(value);
      } catch (Exception e) {
        logger.log(Level.SEVERE, e.toString());
      }
    }
    return integerValuel;
  }

  public static boolean getBooleanFromString(String value) {
    boolean booleanvalue = false;
    if (Utils.hasValue(value)) {
      try {
        booleanvalue = (value.toUpperCase().startsWith("Y")) ? Boolean.TRUE : Boolean.FALSE;
      } catch (Exception e) {
        logger.log(Level.SEVERE, e.toString());
      }
    }
    return booleanvalue;
  }

  public static String getPayer(int payer) {
    String payerType = null;
    if (payer == 0) {
      payerType = "Primary";
    } else if (payer == 1) {
      payerType = "Secondary";
    } else if (payer == 2) {
      payerType = "Tertiary";
    } else if (payer == 3) {
      payerType = "Quaternary";
    }
    return payerType;
  }

  public static Object fromString(String str) {
    Object fin = null;
    if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
      fin = Boolean.valueOf(str);
    } else {
      try {
        fin = Integer.parseInt(str);
      } catch (NumberFormatException e) {
        fin = str;
      }
    }
    return fin;
  }
}
