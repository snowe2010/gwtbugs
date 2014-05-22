package com.gwtbugs.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtbugs.client.utilities.ElementCompositeContants;
import com.gwtbugs.client.utilities.KeyValue;
import com.gwtbugs.client.utilities.Utils;

public class MiddleLevelBinder extends Composite implements ElementCompositeContants, HISComposite, IsWidget, HasWidgets, HasChangeHandlers {

  private static HISListCompositeUiBinder listBinder = GWT.create(HISListCompositeUiBinder.class);

  interface HISListCompositeUiBinder extends UiBinder<Widget, MiddleLevelBinder> {
  }

  @UiField(provided = true)
  String labelText;
  @UiField(provided = true)
  String size;
  @UiField(provided = true)
  Boolean liveSearch;

  @UiField
  InputGroup inputGroup;
  @UiField
  InputGroupAddon label;
  @UiField Select selectList;
  private String name;
  private String dalmap;
  private int pelPosition;
  private String type;
  private boolean required;
  private String domain;
  private List<Option> optionList;

  @UiConstructor
  public MiddleLevelBinder(String label, String name) {
    // This sets the defaults
    this.size = COLUMN_SIZE;
    this.liveSearch = LIVE_SEARCH;
    this.type = DEFAULT_DATE_TYPE;

    this.labelText = label;
    optionList = new ArrayList<>();
    
    System.out.println(labelText);

    initWidget(listBinder.createAndBindUi(this));
    setName(name);
  }

  public void setLiveSearch(Boolean liveSearch) {
    this.liveSearch = liveSearch;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public void setRequired(Boolean required) {
    InputGroupAddon requiredIcon = new InputGroupAddon();
    inputGroup.add(requiredIcon);
    requiredIcon.setIcon(IconType.EXCLAMATION);
    this.required = required;
  }

  public boolean getRequired() {
    return required;
  }

  @Override
  public void populate(final Object value) {
    if (value != null)
      Scheduler.get().scheduleDeferred(new Command() {
        @Override
        public void execute() {
            selectList.setValue(value.toString());
        }
      });
  }

  @Override
  public int getPelPosition() {
    return pelPosition;
  }

  public void setPel_position(int pel_position) {
    this.pelPosition = pel_position;
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    selectList.getElement().setAttribute("name", pelPosition + "|" + name);
    this.name = name;
  }

  public void setDalmap(String dalmap) {
    this.dalmap = dalmap;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain.toUpperCase();
  }

  public Select getListElement() {
    return selectList;
  }

  public Option getSelectedOption() {
    List<String> selectedValues = selectList.getAllSelectedValues();
    if (selectedValues.isEmpty()) {
      return Utils.createDefaultHiddenOption();
    } else {
      String selected = selectedValues.get(0);
      for (Option option : optionList)
        if (option.getText().equals(selected))
          return option;
    }
    return Utils.createDefaultHiddenOption();
  }

  /**
   * This sets the options for the select list, only if they are currently
   * different than the options already in the select list. If they are they
   * remove the old ones and add the new ones.
   * 
   * @param map
   */
  public void setOptions(Map<String, List<KeyValue>> map) {
    System.out.println("------------Option Set------------");
    List<KeyValue> keyValues = map.get(domain);
    System.out.println("Domain: " + domain);
    System.out.println("KVs: " + keyValues);

    if (keyValues != null && !keyValues.isEmpty()) {
      List<Option> tempOptionList = new ArrayList<>();

      Collections.sort(keyValues, new KeyValueSorter());

      Option blankDefaultOption = Utils.createDefaultHiddenOption();
      tempOptionList.add(blankDefaultOption);
      
      for (KeyValue kv : keyValues) {
        Option opt = new Option();
        opt.setText(kv.value);
        opt.setId(kv.key);
        tempOptionList.add(opt);
      }

      final Option oldSelectedOption = getSelectedOption();
      
      System.out.println(tempOptionList);
      System.out.println(optionList);
      
      // if the lists aren't we need to remove all the old values 
      // from the dropdown and then add all the new values
      selectList.add(blankDefaultOption);
      if (!tempOptionList.equals(optionList)) {
        System.out.println("not equals");
        for (Option option : optionList) {
          selectList.remove(option);
        }
        for (Option option : tempOptionList) {
          selectList.add(option);
          optionList.add(option);
        }
      }
      
      for (int i = 0; i < selectList.getItemCount(); i++) {
        System.out.println(selectList.getValue(i));
      }
      
      System.out.println("Old value: " + oldSelectedOption);
      
      Scheduler.get().scheduleDeferred(new Command() {
        @Override
        public void execute() {
            selectList.setValue(oldSelectedOption);
        }
      });
//      selectList.setValue(oldSelectedOption);
      selectList.refresh();
    }
    System.out.println("---------------End Option Set---------------");
    System.out.println();
    
  }

  @Override
  public void add(Widget w) {
    selectList.add(w);
    optionList.add((Option) w);
  }

  @Override
  public void clear() {
    selectList.clear();
  }

  @Override
  public Iterator<Widget> iterator() {
    return selectList.iterator();
  }

  @Override
  public boolean remove(Widget w) {
    return selectList.remove(w);
  }

  @Override
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return selectList.addChangeHandler(handler);
  }

  public class KeyValueSorter implements Comparator<KeyValue> {

    @Override
    public int compare(KeyValue key1, KeyValue key2) {
      return key1.value.compareTo(key2.value);
    }

  }

  @Override
  public String getType() {
    // TODO A ROBOT MADE THIS METHOD! HECK YEAH!
    return null;
  }

  @Override
  public String getDalmap() {
    // TODO A ROBOT MADE THIS METHOD! HECK YEAH!
    return null;
  }
}
