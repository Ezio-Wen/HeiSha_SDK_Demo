package com.heisha.heisha_sdk_demo;

import android.app.Application;

import com.heisha.heisha_sdk.Manager.HSSDKManager;
import com.heisha.heisha_sdk.Product.DNEST;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/9/23 17:38
 */
public class MyApplication extends Application {
	private static volatile DNEST sDNEST;

	static DNEST getDNESTInstance() {
		if (sDNEST == null) {
			synchronized (HSSDKManager.class) {
				if (sDNEST == null) {
//					sDNEST = HSSDKManager.getInstance().getProduct();
				}
			}
		}
		return sDNEST;
	}
}
