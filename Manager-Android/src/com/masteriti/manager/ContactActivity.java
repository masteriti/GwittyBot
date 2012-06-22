package com.masteriti.manager;

import com.masteriti.manager.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContactActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "ContactActivity";
	EditText txtName;
	EditText txtAddress;
	EditText txtPhone;
	Button	 btnAdd;
	
	
	// Called when activity is first created
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		// Find views
		txtName = (EditText) findViewById(R.id.editNameText);
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

	}



}
