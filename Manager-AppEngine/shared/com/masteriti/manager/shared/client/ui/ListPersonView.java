package com.masteriti.manager.shared.client.ui;



import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.HasData;
import com.masteriti.manager.shared.proxy.PersonProxy;

public interface ListPersonView extends IsWidget {
	
	// Sets the Presenter/Activity connection
	void setPresenter(Presenter presenter);
	
	/**
	 * Exposes the CellList to the presenter/activity class so the AsyncDataProvider can update the list.
	 * Uses the {@link HasData} interface so as not to leak a Widget into the presenter. 
	 * @return
	 */
	HasData<PersonProxy> getDataTable();
	
	void setStatusMsg(String msg);
	
	public interface Presenter
	{
		void goTo(Place place);
		
		void onAddPerson();
		
		void onList();
		
		void onDeleteSelection();

		void onSelected(PersonProxy selectedPerson);

		void onEditSelection();
	}


}
