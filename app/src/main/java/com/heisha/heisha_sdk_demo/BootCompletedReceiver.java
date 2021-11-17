package com.heisha.heisha_sdk_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Intent startIntent = new Intent(context, MainActivity.class);
			context.startActivity(startIntent);
		}
	}
}
