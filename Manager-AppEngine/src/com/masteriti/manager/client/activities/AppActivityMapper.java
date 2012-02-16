package com.masteriti.manager.client.activities;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.masteriti.manager.client.ClientFactory;
import com.masteriti.manager.client.places.AddPersonPlace;
import com.masteriti.manager.client.places.EditPersonPlace;
import com.masteriti.manager.client.places.ListPersonPlace;

public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;
	
	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link Activity}
	 * 
	 * @param clientFactory
	 * 			Factory to be passed to activities
	 */
	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}
	
	/**
	 * Map each Place to its corresponding Activity.
	 */
	@Override
	public Activity getActivity(Place place) {

		if(place instanceof AddPersonPlace) {
			return new EditPersonActivity((AddPersonPlace) place, clientFactory);
		} else if (place instanceof ListPersonPlace) {
			return new ListPersonActivity(clientFactory);
		} else if (place instanceof EditPersonPlace) {
			return new EditPersonActivity((EditPersonPlace) place, clientFactory);
		}
		
		return null;
	}

}
