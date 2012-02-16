package com.masteriti.manager.shared.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.masteriti.manager.shared.proxy.PersonProxy;

public interface EditPersonView extends IsWidget {

	// Sets the Presenter/Activity connection
	void setPresenter(Presenter presenter);

	// Fills in the fields of the view with editPerson's currentProperties
	void setFields(PersonProxy editPerson);
	
	void setStatusMsg(String msg);
	
	// methods to implement in this view that are called on the activity
	public interface Presenter
	{
		void goTo(Place place);
		
		void onSave(String firstName,
					String lastName,
					String mainPhone);
	}



}
