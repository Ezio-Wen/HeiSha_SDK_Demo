package com.heisha.heisha_sdk_demo.Listener;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/22 21:47
 */
public interface ControlCenterListener extends ComponentListener {
	void onServerConnectionChanged(boolean connectionStatus, int connectionTimes);

	void onDeviceConnectionChanged(boolean connectionStatus, String deviceName);

	void onGetConfigVersionInfo(int version, int paramNum);
}
