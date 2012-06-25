package com.masteriti.manager;

import com.masteriti.manager.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.masteriti.manager.shared.client.ManagerRequestFactory;
import com.masteriti.manager.shared.client.PersonRequest;
import com.masteriti.manager.shared.proxy.AddressProxy;
import com.masteriti.manager.shared.proxy.PersonProxy;

public class ContactActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "ContactActivity";
	EditText txtNameFirst;
	EditText txtNameLast;
	EditText txtAddress;
	EditText txtPhone;
	Button	 btnAdd;
	
	
	// Called when activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		// Find views
		txtNameFirst = (EditText) findViewById(R.id.editNameFirstText);
		txtNameLast = (EditText) findViewById(R.id.editNameLastText);
		txtAddress = (EditText) findViewById(R.id.editAddressText);
		txtPhone = (EditText) findViewById(R.id.editPhoneText);
		btnAdd = (Button) findViewById(R.id.buttonAddContact);
		
		btnAdd.setOnClickListener(this);
	}
	
	
	// Called when Add Contact button is clicked
	@Override
	public void onClick(View view) {
		Log.d(TAG, "onClicked");
		
		// TODO take the text from the fields and send them off.
		ManagerRequestFactory rf = Util.getRequestFactory(this, ManagerRequestFactory.class);
		PersonRequest req = rf.personRequest();
		AddressProxy address = req.create(AddressProxy.class);
		address.setCity(txtAddress.getText().toString());
		PersonProxy person = req.create(PersonProxy.class);
		person.setNameFirst(txtNameFirst.getText().toString());
		person.setNameLast(txtNameLast.getText().toString());		
		person.setAddress(address);
		person.setPhoneMain(txtPhone.getText().toString());
		req.save(person).with("address").fire();
		
	}



}
