package com.gwtbugs.client;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.gwtbugs.client.utilities.Utils;

public class MiddleLevelSelect extends Select {
  
  private String domain;
  private String name;
  private int pelPosition;
  private String type;
  private boolean required;
  List<Option> optionList = new ArrayList<>();
  
  public String getDomain() {
    return domain;
  }
  
  public void setDomain(String domain) {
    this.domain = domain;
  }
  
  public Option getSelectedOption() {
    List<String> selectedValues = this.getAllSelectedValues();
    if (selectedValues.isEmpty())
      return Utils.createDefaultHiddenOption();
    else {
      String selected = selectedValues.get(0);
      for (Option option : optionList)
        if (option.getText().equals(selected))
          return option;
    }
    return Utils.createDefaultHiddenOption();
  }

}
