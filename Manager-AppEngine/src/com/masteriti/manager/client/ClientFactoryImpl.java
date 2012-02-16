package com.masteriti.manager.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.masteriti.manager.client.ui.EditPersonViewWeb;
import com.masteriti.manager.client.ui.ListPersonViewWeb;
import com.masteriti.manager.shared.client.ManagerRequestFactory;
import com.masteriti.manager.shared.client.ui.EditPersonView;
import com.masteriti.manager.shared.client.ui.ListPersonView;


public class ClientFactoryImpl implements ClientFactory {
	
	private static final EventBus eventBus = new SimpleEventBus();
	private static final PlaceController placeController = new PlaceController(eventBus);
	private static final ManagerRequestFactory rf = GWT.create(ManagerRequestFactory.class);
	private static final EditPersonView editPersonView = new EditPersonViewWeb();
	private static final ListPersonView listPersonView = new ListPersonViewWeb();
	
	public ClientFactoryImpl()
	{
		rf.initialize(eventBus);
	}
	
	@Override
	public EventBus getEventBus() {
		
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		
		return placeController;
	}

	@Override
	public ManagerRequestFactory getRequestFactory() {
		
		return rf;
	}

	@Override
	public EditPersonView getEditPersonView() {
		
		return editPersonView;
	}

	@Override
	public ListPersonView getListPersonView() {
		
		return listPersonView;
	}
}
