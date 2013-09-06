package com.example.monitoreventostest;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class Events extends PhoneStateListener {

	public Context context;
	public ConnectivityManager connectivityManager;
	private BroadcastReceiver  broadcastReciever;
	private NetworkInfo typeOfService;
	private IntentFilter intentFilterNetwork;
	
	public Events(Context context){
		this.context = context;
		initTypeOfService(context);
		
	}
	
	@Override
	public void onSignalStrengthsChanged (SignalStrength signalStrength){
		Log.e("Señal:", ((2 * signalStrength.getGsmSignalStrength())-113)+"");
	}
	
	@Override
	public void onDataConnectionStateChanged (int state){
		switch (state) {
		case TelephonyManager.DATA_CONNECTED:
			Log.e("Data:", "Conectado");
			break;

		case TelephonyManager.DATA_DISCONNECTED:
			Log.e("Data:", "Desconectado");
			break;
			
		case TelephonyManager.DATA_SUSPENDED:
			Log.e("Data:", "Suspendido");
			break;
			
		case TelephonyManager.DATA_CONNECTING:
			Log.e("Data:", "Conectando");
			break;
		}
		
	}
	
	@Override
	public void onServiceStateChanged (ServiceState serviceState){
		Log.e("Operador:", serviceState.getOperatorAlphaLong()+"");
		Log.e("Operador2:", serviceState.getOperatorAlphaShort());
		switch (serviceState.getState()) {
		case ServiceState.STATE_IN_SERVICE:
			Log.e("Estado:", "En Servicio");
			break;

		case ServiceState.STATE_OUT_OF_SERVICE:
			Log.e("Estado:", "Fuera de Servicio");
			break;
		}
	}
	
	@Override
	public void onCellLocationChanged (CellLocation location){
		GsmCellLocation loc = (GsmCellLocation) location;
		Log.e("Antenna Info:", loc.getCid()+"|"+loc.getLac());
	}
	
	public void initTypeOfService(Context context){
		
		intentFilterNetwork = new IntentFilter();
		intentFilterNetwork.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		broadcastReciever = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub.
				if( intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
					typeOfService = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
					Log.e("Type of service", typeOfService.getSubtypeName());
				}
				
			}
			
		};
		context.registerReceiver(broadcastReciever,intentFilterNetwork);
		
	}
	
	public void finishTypeOfService(){
		context.unregisterReceiver(broadcastReciever);
	}

}
