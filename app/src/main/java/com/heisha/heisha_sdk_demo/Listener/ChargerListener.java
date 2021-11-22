package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.Charger.BatteryDetectState;
import com.heisha.heisha_sdk.Component.Charger.ChargeState;
import com.heisha.heisha_sdk.Component.Charger.DroneSwitchState;
import com.heisha.heisha_sdk.Component.ConnStatus;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/20 16:15
 */
public interface ChargerListener extends ComponentListener {
	void onPost(ConnStatus connStatus, ChargeState chargeState, BatteryDetectState batteryDetectState, DroneSwitchState droneSwitchState, int voltage, int current);
}
