package com.masteriti.manager.client.activities;

//import java.text.Collator;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.masteriti.manager.client.ClientFactory;
import com.masteriti.manager.client.places.AddPersonPlace;
import com.masteriti.manager.client.places.EditPersonPlace;
import com.masteriti.manager.shared.client.ManagerRequestFactory;
import com.masteriti.manager.shared.client.ui.ListPersonView;
import com.masteriti.manager.shared.proxy.PersonProxy;


public class ListPersonActivity extends BaseActivity implements ListPersonView.Presenter {

//	private Logger log = Logger.getLogger(ListPersonActivity.class.getName());
	
	private ManagerRequestFactory rf;
	private ListPersonView view;
	private PlaceController placeController;
	private ListPersonDataProvider listPersonDataProvider;
	private PersonProxy selectedPerson = null;
	
	/**
	 * Constructor
	 * @param clientFactory
	 */
	public ListPersonActivity(ClientFactory clientFactory) {
		
		this.rf = clientFactory.getRequestFactory();
		this.view = clientFactory.getListPersonView();
		view.setPresenter(this);
		this.listPersonDataProvider = new ListPersonDataProvider(this.rf);
		this.placeController = clientFactory.getPlaceController();
	}
	
	/**
	 * Class that maintains the list data for the UI.
	 * 
	 * @author Marco
	 *
	 */
	public static class ListPersonDataProvider extends ListDataProvider<PersonProxy>
	{
		private Logger log = Logger.getLogger(ListPersonActivity.ListPersonDataProvider.class.getName());
		private ManagerRequestFactory rf;
		
		public ListPersonDataProvider(ManagerRequestFactory requestFactory) {
			this.rf = requestFactory;
		}
		
		@Override
		protected void onRangeChanged(HasData<PersonProxy> display) {

			getData();
		}
		
		private void getData()
		{
			log.warning("getting data");
			// To retrieve relations and value types, use .with()
			Request<List<PersonProxy>> listAllRequest = rf.personRequest().listAll();
			// Receiver specifies return type
			listAllRequest.with("address").fire(new Receiver<List<PersonProxy>>(){

				@Override
				public void onSuccess(List<PersonProxy> response) {
					
/*					Collections.sort(response, new Comparator<PersonProxy>() {

						@Override
						public int compare(PersonProxy arg0, PersonProxy arg1) {
							// Sort by Last Name string.
							// TODO sort by combination of last and first name.
							Collator myCollator = Collator.getInstance();
							String string1, string2;
							string1 = arg0.getNameLast()+arg0.getNameFirst();
							string2 = arg1.getNameLast()+arg1.getNameFirst();
							return myCollator.compare(string1, string2);
						}
						
					});*/
					updateRowCount(response.size(), true);
					updateRowData(0, response);
					
				}
			});
		}
		
		private void getData(String filter) {
			// TODO Create a filtered listAll in ObjectifyDao to handle this.
			getData();
		}
		
	}

	
	/**
	 * Invoked by ActivityManager to start a new Activity
	 */
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(view.asWidget());
		
		// Triggers listPersonDataProvider#onRangedChanged() to call for data
		view.setStatusMsg("Getting Data...");
		listPersonDataProvider.addDataDisplay(view.getDataTable());
		view.setStatusMsg("ready");
		
	}

	@Override
	public void goTo(Place place) {
		// wraps the place controller method for the UI
		placeController.goTo(place);
	}

	@Override
	public void onAddPerson() {
		goTo(new AddPersonPlace(""));
		
	}

	@Override
	public void onList() {
		// get Data when user asks for it
		listPersonDataProvider.getData();
		
	}

	@Override
	public void onDeleteSelection() {
		// When the user wants to delete the selection
		// Check if something was selected
		if (this.selectedPerson !=null) {
			// Confirm deletion from user
			if(Window.confirm("Do you really wish to delete "+this.selectedPerson.getNameFirst()+" "+this.selectedPerson.getNameLast()+" from your records?")) {
				rf.personRequest().delete(selectedPerson).fire();
				listPersonDataProvider.getData();
				listPersonDataProvider.refresh();
				view.setStatusMsg(selectedPerson.getNameFirst()+" "+selectedPerson.getNameLast()+" was deleted!");		
			}
		}
	}

	@Override
	public void onSelected(PersonProxy selectedPerson) {
		// When the user selects an item on the list
		this.selectedPerson = selectedPerson;
		view.setStatusMsg(selectedPerson.getNameFirst()+" "+selectedPerson.getNameLast()+" was selected, id: "+selectedPerson.getId().toString());
		
	}

	@Override
	public void onEditSelection() {
		// First check if something was selected
		if(this.selectedPerson == null) {
			view.setStatusMsg("No item was selected. Select a person from the list you wish to edit.");
		} else {
			// Use requestFactory's getHistoryToken to store a token compatible string id representing the person.
			String token = rf.getHistoryToken(selectedPerson.stableId());
			view.setStatusMsg("Got Token: "+ token);
			goTo(new EditPersonPlace(token));
		}
		
	}

}
