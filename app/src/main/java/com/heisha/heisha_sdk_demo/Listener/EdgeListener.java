package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.EdgeComputing.PowerState;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/20 16:19
 */
public interface EdgeListener extends ComponentListener {
	void onPost(PowerState androidPowerState, PowerState NVIDIAPowerState);
}
