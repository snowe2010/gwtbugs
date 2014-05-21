package com.gwtbugs.client;

import java.util.Iterator;

import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.extras.select.client.ui.Option;
import org.gwtbootstrap3.extras.select.client.ui.Select;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class MiddleLevelBinder extends Composite implements IsWidget, HasWidgets {

  private static MiddleLevelBinderUiBinder uiBinder = GWT.create(MiddleLevelBinderUiBinder.class);
  interface MiddleLevelBinderUiBinder extends UiBinder<Widget, MiddleLevelBinder> {}

  @UiField InputGroup inputGroup;
  @UiField Select selectList;
  @UiField(provided = true) String labelText;
  
  @UiConstructor
  public MiddleLevelBinder(String label, String name) {
    this.labelText = label;
    initWidget(uiBinder.createAndBindUi(this));
    
    Option t = new Option();
    t.setText("test");
    selectList.add(t);
    selectList.setValue(t);
  }

  @Override public void add(Widget w) { selectList.add(w); }
  @Override public void clear() { selectList.clear(); }
  @Override public Iterator<Widget> iterator() { return selectList.iterator(); }
  @Override public boolean remove(Widget w) { return selectList.remove(w); }

}
