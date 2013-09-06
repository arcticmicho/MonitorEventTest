package com.example.monitoreventostest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;

public class Monitor extends Activity {
	
	public TelephonyManager mTm;
	public Events events;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitor);
		this.events = new Events(this);
		this.mTm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		this.mTm.listen(events, events.LISTEN_SIGNAL_STRENGTHS | events.LISTEN_SERVICE_STATE
				| events.LISTEN_DATA_CONNECTION_STATE | events.LISTEN_CELL_LOCATION);
	}

	
	public int clickHandler(View v) {
		return 1;		
	}
	
    @Override
	protected void onStop() {
    	super.onStop();
    	this.mTm.listen(events, events.LISTEN_NONE);
    	this.events.finishTypeOfService();
	}

}
