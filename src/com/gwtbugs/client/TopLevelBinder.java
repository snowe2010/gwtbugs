package com.gwtbugs.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.gwtbugs.client.utilities.KeyValue;

public class TopLevelBinder extends Composite implements HasWidgets {

  private static TopLevelBinderUiBinder uiBinder = GWT.create(TopLevelBinderUiBinder.class);
  interface TopLevelBinderUiBinder extends UiBinder<HTMLPanel, TopLevelBinder> {}

  @UiField HTMLPanel base;
  List<MiddleLevelBinder> composites;
  
  Map<String, List<KeyValue>> placeholderForAsyncCall = new HashMap<>();

  public TopLevelBinder() {
    initWidget(uiBinder.createAndBindUi(this));
    List<KeyValue> t = Arrays.asList(new KeyValue("A Key", "A Value"), new KeyValue("Key 2", "Value 2"),
                                     new KeyValue("Key 3", "Value 3"), new KeyValue("Key 4", "Value 4"));
    placeholderForAsyncCall.put("PROVIDER", t);
    
    getSelectLists();
    addEventHandlersToDropdowns();
    populateDropdowns();
  }
  
  public void populateDropdowns() {
    List<MiddleLevelBinder> list = getSelectLists();
    System.out.println(list);
    for (MiddleLevelBinder bin : list) {
      bin.setOptions(placeholderForAsyncCall);
    }
  }
  
  private void addEventHandlersToDropdowns() {
    for (MiddleLevelBinder composite : composites)
        composite.addChangeHandler(new SelectListChangeHandler());
  }
  
  
  public List<MiddleLevelBinder> getSelectLists() {
    composites = new ArrayList<>();
    getCompositesRecursively(this, composites);
    return composites;
  }
  
  private <T extends MiddleLevelBinder> void getCompositesRecursively(Widget parent, List<T> composites) {
    System.out.println(parent.getClass());
    if (parent instanceof HasWidgets) {
      System.out.println("here");
    } else {
      System.out.println("broken");
    }
    if (parent instanceof HasWidgets && !(parent instanceof MiddleLevelBinder)) {
      Iterator<Widget> currentLevel = ((HasWidgets) parent).iterator();
      while (currentLevel.hasNext()) {
        getCompositesRecursively(currentLevel.next(), composites);
      }
    } else if (parent instanceof MiddleLevelBinder) {
      composites.add((T) parent);
    }
  }

  @Override
  public void add(Widget w) {
    base.add(w);
  }

  @Override
  public void clear() {
    base.clear();
  }

  @Override
  public Iterator<Widget> iterator() {
    return base.iterator();
  }

  @Override
  public boolean remove(Widget w) {
    return base.remove(w);
  }
  
  private class SelectListChangeHandler implements ChangeHandler {

    String type;
    String providerid = null;
    String payerid = null;
    MiddleLevelSelect currentSelectBox = null;

    @Override
    public void onChange(ChangeEvent event) {
      currentSelectBox = (MiddleLevelSelect) event.getSource();
      type = currentSelectBox.getDomain();
      System.out.println(type);
      System.out.println(currentSelectBox);

      if (type.equals("PROVIDER")) {
        Option provideridBefore = currentSelectBox.getSelectedOption();
        providerid = provideridBefore.getId();
        // we need to do a lookup here, because all we can grab right now is the
        // text, not the value
        // map.get("PROVIDER");
//        providerid = searchList("PROVIDER", provideridBefore);
        System.out.println(providerid);
        System.out.println("this is the provider?");
      } else if (type.equals("PAYER")) {
        payerid = currentSelectBox.getAllSelectedValues().get(0);
      }
//      dropDownServlet.getAllDropDownData(domains, providerid, null, null, payerid, null);
      System.out.println("here??");
    }

    /*public String searchList(String listToSearch, String valueForKey) {
      List<KeyValue> keyValues = map.get(listToSearch);
      System.out.println(keyValues);
      System.out.println(valueForKey);

      for (KeyValue kv : keyValues) {
        if (kv.value.equals(valueForKey)) {
          return kv.key;
        }
      }
      return null;
    }*/
  }

}
