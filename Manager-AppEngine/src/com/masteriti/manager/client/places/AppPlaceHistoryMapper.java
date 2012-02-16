package com.masteriti.manager.client.places;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;


/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
@WithTokenizers( {AddPersonPlace.Tokenizer.class, 
					ListPersonPlace.Tokenizer.class,
					EditPersonPlace.Tokenizer.class} )
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
