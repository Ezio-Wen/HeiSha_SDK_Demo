package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.ConnStatus;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/12/1 9:49
 */
public interface RemoteControlListener extends ComponentListener {
	void onPost(ConnStatus connStatus);
}
