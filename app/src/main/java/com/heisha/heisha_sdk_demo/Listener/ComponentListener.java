package com.heisha.heisha_sdk_demo.Listener;

import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/19 16:13
 */
public interface ComponentListener {

	void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult);

	void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value);

	void onSetParam(ServiceResult serviceResult, ConfigParameter configParameter, ConfigFailReason configFailReason);
}
