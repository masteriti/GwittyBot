package com.masteriti.manager.client.activities;

import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.web.bindery.requestfactory.shared.Violation;
import com.masteriti.manager.client.ClientFactory;
import com.masteriti.manager.client.places.AddPersonPlace;
import com.masteriti.manager.client.places.EditPersonPlace;
import com.masteriti.manager.client.places.ListPersonPlace;
import com.masteriti.manager.shared.client.ManagerRequestFactory;
import com.masteriti.manager.shared.client.PersonRequest;
import com.masteriti.manager.shared.client.ui.EditPersonView;
import com.masteriti.manager.shared.proxy.PersonProxy;

public class EditPersonActivity extends BaseActivity implements EditPersonView.Presenter {

	private Logger log = Logger.getLogger(EditPersonActivity.class.getName());
	
	private EventBus eventBus;
	private ManagerRequestFactory rf;
	private EditPersonView view;
	private PlaceController placeController;
	private Place place;
	private PersonProxy editPerson = null;

	
	public EditPersonActivity(AddPersonPlace place, ClientFactory clientFactory) {
		
		init(place, clientFactory);
	}
	
	public EditPersonActivity(EditPersonPlace place, ClientFactory clientFactory) {
		// Initialize the Activity
		init(place, clientFactory);
		
		// Extract the proxyId from the place's token
		String proxyToken = place.getPlaceName();
		EntityProxyId<PersonProxy> proxyId = rf.getProxyId(proxyToken);

		// get the PersonProxy with that ID
		rf.personRequest().find(proxyId).fire(new Receiver<PersonProxy>() {

			@Override
			public void onFailure(ServerFailure error) {
				// TODO Auto-generated method stub
				view.setStatusMsg("onFailure: "+error.toString());
				super.onFailure(error);
			}



			@Override
			public void onConstraintViolation(
					Set<ConstraintViolation<?>> violations) {
				// TODO Auto-generated method stub
				view.setStatusMsg("onViolations: "+violations.toString());
				super.onConstraintViolation(violations);
			}

			@Override
			public void onSuccess(PersonProxy response) {
				assert (view != null);
				assert response != null;
				editPerson = response;
				// Fill in the fields on the ui view with the entity's properties.
				view.setFields(response);
				view.setStatusMsg("onSuccess: "+response.toString());
			}
		});
		


	}

	private void init(Place place, ClientFactory clientFactory) {
		
		this.rf = clientFactory.getRequestFactory();  	// connect to RPC service
		this.view = clientFactory.getEditPersonView();	// connect to UI
		view.setPresenter(this);						// make sure UI is connected to this Activity
		this.placeController = clientFactory.getPlaceController();	// connect to PlaceController
		this.place = place;								// reference to place this activity is working for
	}
	
	//////////////////////////////////////////////////////////////
	// Activity Methods start(), mayStop(), stop() and cancel() //
	//////////////////////////////////////////////////////////////
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		
		this.eventBus = eventBus;
		panel.setWidget(view);

	}

	
	
	////////////////////////////
	// View Presenter methods //
	////////////////////////////
	@Override
	public void goTo(Place place) {
		
		placeController.goTo(place);
	}

	@Override
	public void onSave(String firstName, String lastName,
			String mainPhone) {

		if(place instanceof AddPersonPlace) {
			// Simply create a new entity if we're adding
			PersonRequest req = rf.personRequest();
			PersonProxy person = req.create(PersonProxy.class);
			person.setNameFirst(firstName);
			person.setNameLast(lastName);
			person.setPhoneMain(mainPhone);
	//		person.setPhoneAlt(altPhone);
	//		person.setEmail(email);
			req.save(person).fire();
		} else if (place instanceof EditPersonPlace) {
			// Request an editable version of editPerson
			PersonRequest request = rf.personRequest();
			this.editPerson = request.edit(this.editPerson);
			this.editPerson.setNameFirst(firstName);
			this.editPerson.setNameLast(lastName);
			this.editPerson.setPhoneMain(mainPhone);
			request.save(editPerson).fire(new Receiver<Void>() {

				@Override
				public void onSuccess(Void response) {
					// Update the message if edit was successful
					placeController.goTo(new ListPersonPlace(""));
				}
				
			});
			
		}
	}


	
}
