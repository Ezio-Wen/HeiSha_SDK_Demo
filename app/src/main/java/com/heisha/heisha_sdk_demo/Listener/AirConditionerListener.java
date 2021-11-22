package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.AirConditioner.AirConditionerWorkingMode;
import com.heisha.heisha_sdk.Component.ConnStatus;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/20 16:22
 */
public interface AirConditionerListener extends ComponentListener {
	void onPost(ConnStatus connStatus, AirConditionerWorkingMode airConditionerWorkingMode);
}
