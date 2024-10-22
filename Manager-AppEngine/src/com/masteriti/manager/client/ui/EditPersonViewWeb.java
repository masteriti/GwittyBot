package com.masteriti.manager.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.masteriti.manager.shared.client.ui.EditPersonView;
import com.masteriti.manager.shared.proxy.PersonProxy;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.event.dom.client.KeyPressEvent;

public class EditPersonViewWeb extends Composite implements EditPersonView {

	private static EditPersonViewWebUiBinder uiBinder = GWT.create(EditPersonViewWebUiBinder.class);
	
	@UiField Button btnSave;
	@UiField Button btnDone;
	@UiField Button btnClear;
	@UiField TextBox fldFirstName;
	@UiField TextBox fldLastName;
	@UiField TextBox fldMainPhone;
	@UiField TextBox fldCity;
	@UiField Label lblStatus;
	
	Presenter presenter;
	
	interface EditPersonViewWebUiBinder extends	UiBinder<Widget, EditPersonViewWeb> {}
	
	
	/**
	 * Constructor
	 * @author Marco
	 *
	 */
	public EditPersonViewWeb() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		// Connect to presenter methods on EditPersonActivity
		this.presenter = presenter;
	}

	@Override
	public void setFields(PersonProxy editPerson) {
		// Set the field values based on editPerson properties
		fldFirstName.setValue(editPerson.getNameFirst());
		fldLastName.setValue(editPerson.getNameLast());
		fldMainPhone.setValue(editPerson.getPhoneMain());
		if(editPerson.getAddress() == null) {return;}
		else {
		fldCity.setValue(editPerson.getAddress().getCity());
		}
	}	

	@UiHandler("btnClear")
	void onBtnClearClick(ClickEvent event) {

		clearAllFields();
	}	@UiHandler("btnSave")
	void onBtnSaveClick(ClickEvent event) {
		// Save the data in the fields to persist a new entity
		presenter.onSave(fldFirstName.getValue(), fldLastName.getValue(), fldMainPhone.getValue(), fldCity.getValue());
		clearAllFields();
	}

	@UiHandler("btnDone")
	void onBtnDoneClick(ClickEvent event) {
		// When done take the user to the previous page.
		History.back();
	}
	
	private void clearAllFields() {
		// Clear all the fields
		fldFirstName.setValue("");
		fldLastName.setValue("");
		fldMainPhone.setValue("");
		fldCity.setValue("");

	}

	@Override
	public void setStatusMsg(String msg) {
		// Write a status message string
		lblStatus.setText(msg);		
	}
	
	private void onEnterKeyPress() {
		// make sure that user wants to save data
		if(Window.confirm("Save this contact?")) {
			presenter.onSave(fldFirstName.getValue(), fldLastName.getValue(), fldMainPhone.getValue(), fldCity.getValue());
			clearAllFields();
		}
	}
	
	@UiHandler("fldMainPhone")
	void onFldMainPhoneKeyPress(KeyPressEvent event) {
		// Check if Enter was pressed
		if(event.getCharCode() == KeyCodes.KEY_ENTER) {
			onEnterKeyPress();
		}
	}
	@UiHandler("fldLastName")
	void onFldLastNameKeyPress(KeyPressEvent event) {
		// Check if Enter was pressed
		if(event.getCharCode() == KeyCodes.KEY_ENTER) {
			onEnterKeyPress();
		}
	}
	@UiHandler("fldFirstName")
	void onFldFirstNameKeyPress(KeyPressEvent event) {
		// Check if Enter was pressed
		if(event.getCharCode() == KeyCodes.KEY_ENTER) {
			onEnterKeyPress();
		}
	}
}
