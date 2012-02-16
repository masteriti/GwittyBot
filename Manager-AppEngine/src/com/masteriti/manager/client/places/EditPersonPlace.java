package com.masteriti.manager.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditPersonPlace extends Place {
	
	private String placeName;
	
	public EditPersonPlace(String token) {
		this.placeName = token;
	}
	
	public String getPlaceName() {
		return placeName;
	}
	
	public static class Tokenizer implements PlaceTokenizer<EditPersonPlace> {

		@Override
		public EditPersonPlace getPlace(String token) {
			
			return new EditPersonPlace(token);
		}

		@Override
		public String getToken(EditPersonPlace place) {
			
			return place.getPlaceName();
		}
	}

}
