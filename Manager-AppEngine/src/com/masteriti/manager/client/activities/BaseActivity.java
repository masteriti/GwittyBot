package com.masteriti.manager.client.activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public abstract class BaseActivity extends AbstractActivity {

	// Base Activity is simply a wrapper for EventBus in order to use the new
	// version 2.4 EventBus class which is in a new package as opposed to the
	// old version location used by AbstractActivity.
	public abstract void start(AcceptsOneWidget panel, EventBus eventBus);
	
	@Override	
	public void start(AcceptsOneWidget panel, com.google.gwt.event.shared.EventBus eventBus) {
		start(panel, (EventBus)eventBus);
	}

}
