package com.masteriti.manager.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class AddPersonPlace extends Place {
	
	private String placeName;
	
	public AddPersonPlace(String token) {
		this.placeName = token;
	}
	
	public String getPlaceName() {
		return placeName;
	}
	
	@Prefix("add")
	public static class Tokenizer implements PlaceTokenizer<AddPersonPlace> {

		@Override
		public AddPersonPlace getPlace(String token) {
			
			return new AddPersonPlace(token);
		}

		@Override
		public String getToken(AddPersonPlace place) {
			
			return place.getPlaceName();
		}
		
	}

}
