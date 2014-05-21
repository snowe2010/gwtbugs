package com.gwtbugs.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class TopLevelBinder extends Composite {

  private static TopLevelBinderUiBinder uiBinder = GWT.create(TopLevelBinderUiBinder.class);
  interface TopLevelBinderUiBinder extends UiBinder<Widget, TopLevelBinder> {}

  public TopLevelBinder() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @UiFactory FlowPanel setFactoryAttribute(String factoryAttribute) {
    FlowPanel fp = new FlowPanel();
    return fp;
  }
}
