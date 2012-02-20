package com.masteriti.manager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.masteriti.manager.client.places.AddPersonPlace;
import com.masteriti.manager.shared.client.ui.ListPersonView;
import com.masteriti.manager.shared.proxy.PersonProxy;
import com.google.gwt.user.client.ui.Label;

public class ListPersonViewWeb extends Composite implements ListPersonView{

	private static ListPersonViewWebUiBinder uiBinder = GWT.create(ListPersonViewWebUiBinder.class);
	
	@UiField(provided=true) CellList<PersonProxy> cellList = new CellList<PersonProxy>(new ContactCell());
	
	@UiField Button btnList;
	@UiField Button btnAdd;
	@UiField Button btnDelete;
	@UiField Label lblStatus;
	@UiField Button btnEdit;
	
	private Presenter presenter;

	interface ListPersonViewWebUiBinder extends	UiBinder<Widget, ListPersonViewWeb> {}
	
	static class ContactCell extends AbstractCell<PersonProxy> {
		
		public ContactCell() {}
		
		@Override
		public void render(Context context, PersonProxy value, SafeHtmlBuilder sb) {
			
			/*
			* Always do a null check on the value. Cell widgets can pass null to
			* cells if the underlying data contains a null, or if the data arrives
			* out of order.
			*/
			if(value == null) return;
			
			sb.appendHtmlConstant("<table>");
			
			// Add the contact name and address
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getNameFirst()+" "+value.getNameLast());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			String data = value.getAddress().getCity();
			if(data == null) {
				data = "NoAddress";
			}
/*			try{
				data = value.getAddress().getCity();
			} catch(NullPointerException e){
				
				data = "NoAddress";

			}*/
			sb.appendEscaped(data);
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getPhoneMain());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}


	/**
	 * Constructor
	 */
	public ListPersonViewWeb() {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		// Add a selection model to cell list to handle user selection
		final SingleSelectionModel<PersonProxy> selectionModel = new SingleSelectionModel<PersonProxy>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				PersonProxy selectedPerson = selectionModel.getSelectedObject();
				if(selectedPerson != null) {
					presenter.onSelected(selectedPerson);
				} else {
					setStatusMsg("No item selected.");
				}
				
			}
		});
		
	}


	@Override
	public void setPresenter(Presenter presenter) {
		// Connect to presenter interface from Activity
		this.presenter = presenter;		
	}

	@Override
	public HasData<PersonProxy> getDataTable() {
		// Exposes the cellList table to the Activity
		return cellList;
	}
	
	@Override
	public void setStatusMsg(String msg) {
		// Display the msg text
		lblStatus.setText(msg);
	}
	
	@UiHandler("btnList")
	void onBtnListClick(ClickEvent event) {
		// Refresh viewed list from datastore
		presenter.onList();
	}
	
	@UiHandler("btnAdd")
	void onBtnAddClick(ClickEvent event) {
		// Go to AddPerson form to add new entities
		presenter.goTo(new AddPersonPlace(""));
	}
	
	@UiHandler("btnDelete")
	void onBtnDeleteClick(ClickEvent event) {
		// Delete selection, if any.
		presenter.onDeleteSelection();
	}


	@UiHandler("btnEdit")
	void onBtnEditClick(ClickEvent event) {
		// Open edit window for selected entity
		presenter.onEditSelection();
		
	}
}
