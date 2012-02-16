package com.masteriti.manager.client;


import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.masteriti.manager.shared.client.ManagerRequestFactory;
import com.masteriti.manager.shared.client.ui.EditPersonView;
import com.masteriti.manager.shared.client.ui.ListPersonView;



public interface ClientFactory {
	
	// Needs an EventBus to handle events
	// TODO We could wrap this to handle Android events
	EventBus getEventBus();
	
	// Use this to handle places
	PlaceController getPlaceController();
	
	// Provide Objectify RequestFactory RPC service
	ManagerRequestFactory getRequestFactory();
	
	/**
	 * These are the Views the app will provide
	 */	
	EditPersonView getEditPersonView();

	ListPersonView getListPersonView();

}
