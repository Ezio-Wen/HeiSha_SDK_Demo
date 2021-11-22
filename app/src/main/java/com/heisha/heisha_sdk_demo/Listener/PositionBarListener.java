package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBarState;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/20 16:13
 */
public interface PositionBarListener extends ComponentListener {
	void onPost(ConnStatus connStatus, PositionBarState positionBarState);
}
