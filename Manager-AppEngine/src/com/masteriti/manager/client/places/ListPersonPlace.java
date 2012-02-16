package com.masteriti.manager.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ListPersonPlace extends Place {

	private String placeName;
	
	public ListPersonPlace(String token) {
		this.placeName = token;
	}
	
	public String getPlaceName() {
		return placeName;
	}
	
	@Prefix("list")
	public static class Tokenizer implements PlaceTokenizer<ListPersonPlace> {

		@Override
		public ListPersonPlace getPlace(String token) {
			
			return new ListPersonPlace(token);
		}

		@Override
		public String getToken(ListPersonPlace place) {
			
			return place.getPlaceName();
		}
		
	}
}
