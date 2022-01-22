package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.ConnStatus;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/22 21:47
 */
public interface ControlCenterListener extends ComponentListener {
	void onServerConnectionChanged(ConnStatus connectionStatus, int connectionTimes);

	void onDeviceConnectionChanged(ConnStatus connectionStatus, String deviceName);

	void onGetConfigVersionInfo(int version, int paramNum);
}
