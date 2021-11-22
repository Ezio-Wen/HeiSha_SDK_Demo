package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.Canopy.CanopyState;
import com.heisha.heisha_sdk.Component.ConnStatus;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/19 16:26
 */
public interface CanopyListener extends ComponentListener {
	void onPost(ConnStatus connStatus, CanopyState canopyState);
}
