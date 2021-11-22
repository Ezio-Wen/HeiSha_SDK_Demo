package com.heisha.heisha_sdk_demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.heisha.heisha_sdk.Component.AirConditioner.AirConditioner;
import com.heisha.heisha_sdk.Component.AirConditioner.AirConditionerStateCallback;
import com.heisha.heisha_sdk.Component.AirConditioner.AirConditionerWorkingMode;
import com.heisha.heisha_sdk.Component.BaseComponent;
import com.heisha.heisha_sdk.Component.Canopy.Canopy;
import com.heisha.heisha_sdk.Component.Canopy.CanopyState;
import com.heisha.heisha_sdk.Component.Canopy.CanopyStateCallback;
import com.heisha.heisha_sdk.Component.Charger.BatteryDetectState;
import com.heisha.heisha_sdk.Component.Charger.ChargeState;
import com.heisha.heisha_sdk.Component.Charger.Charger;
import com.heisha.heisha_sdk.Component.Charger.ChargerStateCallback;
import com.heisha.heisha_sdk.Component.Charger.DroneSwitchState;
import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Component.ControlCenter.ControlCenter;
import com.heisha.heisha_sdk.Component.ControlCenter.ControlCenterStateCallback;
import com.heisha.heisha_sdk.Component.ControlCenter.ThingLevel;
import com.heisha.heisha_sdk.Component.EdgeComputing.EdgeComputing;
import com.heisha.heisha_sdk.Component.EdgeComputing.EdgeStateCallback;
import com.heisha.heisha_sdk.Component.EdgeComputing.PowerState;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBar;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBarState;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBarStateCallback;
import com.heisha.heisha_sdk.Manager.HSSDKManager;
import com.heisha.heisha_sdk.Manager.SDKManagerCallback;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk.Product.DNEST;
import com.heisha.heisha_sdk_demo.Listener.AirConditionerListener;
import com.heisha.heisha_sdk_demo.Listener.CanopyListener;
import com.heisha.heisha_sdk_demo.Listener.ChargerListener;
import com.heisha.heisha_sdk_demo.Listener.EdgeListener;
import com.heisha.heisha_sdk_demo.Listener.PositionBarListener;
import com.heisha.heisha_sdk_demo.fragment.ACFragment;
import com.heisha.heisha_sdk_demo.fragment.CanopyFragment;
import com.heisha.heisha_sdk_demo.fragment.ChargerFragment;
import com.heisha.heisha_sdk_demo.fragment.ControlCenterFragment;
import com.heisha.heisha_sdk_demo.fragment.EdgeFragment;
import com.heisha.heisha_sdk_demo.fragment.PositionBarFragment;
import com.heisha.heisha_sdk_demo.utils.ToolBarUtil;

import java.util.ArrayList;
import java.util.List;

import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_POST_RATE_CANOPY;


public class MainActivity extends AppCompatActivity {

	private SharedPreferences mPref;
	private static final String TAG = "MainActivity";
	private DNEST mDNEST;
	public Canopy mCanopy;
	public PositionBar mPositionBar;
	public Charger mCharger;
	public EdgeComputing mEdgeComputing;
	public ControlCenter mControlCenter;
	public AirConditioner mAirConditioner;

	private LinearLayout mMainToolbar;
	private ViewPager mMainViewPager;
	private ToolBarUtil mToolBarUtil;
	private List<Fragment> mFragmentList = new ArrayList<>();

	private CanopyListener mCanopyListener;
	private PositionBarListener mPositionBarListener;
	private ChargerListener mChargerListener;
	private AirConditionerListener mACListener;
	private EdgeListener mEdgeListener;

	public boolean isServerConnected = false;
	public boolean isDeviceConnected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPref = getSharedPreferences("connection info", MODE_PRIVATE);

		initView();
		initListener();

		String serverURL = mPref.getString("serverURL", "");
		String deviceSerial = mPref.getString("deviceSerial", "");
		if (!serverURL.equals("") && !deviceSerial.equals("")) {
			registApp(serverURL, deviceSerial);
		}
	}

	private void registApp(final String serverURI, String deviceSerial) {
		HSSDKManager.getInstance().registAPP(deviceSerial, serverURI, new SDKManagerCallback() {
			@Override
			public void onRegister() {
				Log.d(TAG, "onRegister: 注册成功");
			}

			@Override
			public void onServerConnected(boolean b, String s) {
//				txtServerStatus.setText(ConnStatus.CONNECTED.toString());
				isServerConnected = true;
				Log.d(TAG, "connectComplete: 连接服务器成功");
				Toast.makeText(MainActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onServerDisconnected(Throwable throwable) {
//				txtServerStatus.setText(ConnStatus.DISCONNECTED.toString());
				isServerConnected = false;
				Log.d(TAG, "connectionLost: 服务器连接丢失");
				throwable.printStackTrace();
			}

			@Override
			public void onProductConnected(final String deviceName) {
				Log.d(TAG, "onProductConnected: 设备连接成功");
				isDeviceConnected = true;
//				txtDeviceName.setText(deviceName);
//				txtDeviceStatus.setText(ConnStatus.CONNECTED.toString());
				Toast.makeText(MainActivity.this, "设备连接成功", Toast.LENGTH_SHORT).show();
				initDevice(deviceName);
				initDeviceCallback();
			}

			@Override
			public void onProductDisconnected() {
//				txtDeviceStatus.setText(ConnStatus.DISCONNECTED.toString());
				isDeviceConnected = false;
				Log.d(TAG, "onProductConnected: 设备连接丢失");
			}

			@Override
			public void onComponentChanged(BaseComponent baseComponent, ConnStatus connStatus) {
				Log.d(TAG, "onComponentChanged: 组件状态改变");
			}
		});
	}

	private class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
			super(fm, behavior);
		}

		@NonNull
		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return 6;
		}
	}

	private void initView() {
		mMainToolbar = this.findViewById(R.id.layout_tool_bar);
		mMainViewPager = this.findViewById(R.id.view_pager_fragment);
		mToolBarUtil = new ToolBarUtil();
		String[] toolBarTitles = {"Control Center", "Canopy", "Position Bar", "Charger", "AC", "Edge Computing"};
		mToolBarUtil.createToolBar(mMainToolbar, toolBarTitles);
		mToolBarUtil.changeColor(0);

		mFragmentList.add(new ControlCenterFragment());
		mFragmentList.add(new CanopyFragment());
		mFragmentList.add(new PositionBarFragment());
		mFragmentList.add(new ChargerFragment());
		mFragmentList.add(new ACFragment());
		mFragmentList.add(new EdgeFragment());
		mMainViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
	}

	private void initListener() {
		mToolBarUtil.setOnToolBarClickListener(new ToolBarUtil.OnToolBarClickListener() {
			@Override
			public void onToolBarClick(int position) {
				mMainViewPager.setCurrentItem(position);
			}
		});

		mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				mToolBarUtil.changeColor(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	private void initDevice(String productName) {
		mDNEST = (DNEST) HSSDKManager.getInstance().getProduct(productName);
		mCanopy = mDNEST.getCanopy();
		mPositionBar = mDNEST.getPositionBar();
		mCharger = mDNEST.getCharger();
		mEdgeComputing = mDNEST.getEdgeComputing();
		mControlCenter = mDNEST.getControlCenter();
		mAirConditioner = mDNEST.getAirConditioner();
	}

	private void initDeviceCallback() {
		mCanopy.setStateCallback(new CanopyStateCallback() {
			@Override
			public void onUpdate(ConnStatus connStatus, CanopyState canopyState) {
				Log.d(TAG, "canopy: connection state:" + connStatus.toString() + ", state:" + canopyState.toString());
				if (mCanopyListener != null) {
					mCanopyListener.onPost(connStatus, canopyState);
				}
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "canopy operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
				if (mCanopyListener != null) {
					mCanopyListener.onOperationResult(serviceCode, serviceResult);
				}
			}
		});
		mPositionBar.setStateCallback(new PositionBarStateCallback() {
			@Override
			public void onUpdate(ConnStatus connStatus, PositionBarState positionBarState) {
				Log.d(TAG, "position bar: connection state:" + connStatus.toString() + ", state:" + positionBarState.toString());
				if (mPositionBarListener != null) {
					mPositionBarListener.onPost(connStatus, positionBarState);
				}
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "position bar operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
				if (mPositionBarListener != null) {
					mPositionBarListener.onOperationResult(serviceCode, serviceResult);
				}
			}
		});
		mCharger.setStateCallback(new ChargerStateCallback() {
			@Override
			public void onUpdate(ConnStatus connStatus, ChargeState chargeState, BatteryDetectState batteryDetectState, DroneSwitchState droneSwitchState, int voltage, int current) {
				Log.d(TAG, "charger: connection state:" + connStatus.toString() + ", state:" + chargeState.toString() +
						", battery detect:" + batteryDetectState.toString() + ", drone state:" + droneSwitchState.toString());
				if (mChargerListener != null) {
					mChargerListener.onPost(connStatus, chargeState, batteryDetectState, droneSwitchState, voltage, current);
				}
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "charger operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
				if (mChargerListener != null) {
					mChargerListener.onOperationResult(serviceCode, serviceResult);
				}
			}
		});
		mEdgeComputing.setStateCallback(new EdgeStateCallback() {
			@Override
			public void onUpdate(PowerState androidPowerState, PowerState NVIDIAPowerState) {
				Log.d(TAG, "Switch: android power:" + androidPowerState.toString() + ", NVIDIA power:" + NVIDIAPowerState.toString());
				if (mEdgeListener != null) {
					mEdgeListener.onPost(androidPowerState, NVIDIAPowerState);
				}
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "switch operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
				if (mEdgeListener != null) {
					mEdgeListener.onOperationResult(serviceCode, serviceResult);
				}
			}
		});
		mControlCenter.setStateCallback(new ControlCenterStateCallback() {
			@Override
			public void onUpdate() {

			}

			@Override
			public void onThingsPost(ThingLevel thingLevel, int code) {
				Log.d(TAG, "onThingsPost: " + thingLevel.toString() + " code" + code);
				Toast.makeText(MainActivity.this, thingLevel.toString() + ",code:" + code, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {

			}

			@Override
			public void onGetConfigVersionInfo(int version, int paramNum) {

			}

			@Override
			public void onGetConfig(ServiceResult result, ConfigParameter paramIndex, int value) {
				switch(paramIndex) {
					case SERVICE_PARAM_POST_RATE_CANOPY:
						if (mCanopyListener != null)
							mCanopyListener.onGetParam(result, paramIndex, value);
						break;
					case SERVICE_PARAM_POST_RATE_POSBAR:
						if (mPositionBarListener != null)
							mPositionBarListener.onGetParam(result, paramIndex, value);
						break;
					case SERVICE_PARAM_POST_RATE_CD:
						if (mChargerListener != null)
							mChargerListener.onGetParam(result, paramIndex, value);
						break;
					case SERVICE_PARAM_POST_RATE_EDGE:
						if (mEdgeListener != null)
							mEdgeListener.onGetParam(result, paramIndex, value);
						break;
				}
			}

			@Override
			public void onSetConfig(ServiceResult serviceResult, ConfigParameter configParameter, ConfigFailReason configFailReason) {
				switch(configParameter) {
					case SERVICE_PARAM_POST_RATE_CANOPY:
						if (mCanopyListener != null)
							mCanopyListener.onSetParam(serviceResult, configParameter, configFailReason);
						break;
					case SERVICE_PARAM_POST_RATE_POSBAR:
						if (mPositionBarListener != null)
							mPositionBarListener.onSetParam(serviceResult, configParameter, configFailReason);
						break;
					case SERVICE_PARAM_POST_RATE_CD:
						if (mChargerListener != null)
							mChargerListener.onSetParam(serviceResult, configParameter, configFailReason);
						break;
					case SERVICE_PARAM_POST_RATE_EDGE:
						if (mEdgeListener != null)
							mEdgeListener.onSetParam(serviceResult, configParameter, configFailReason);
						break;
				}
			}
		});

		mAirConditioner.setStateCallback(new AirConditionerStateCallback() {
			@Override
			public void onUpdate(ConnStatus connStatus, AirConditionerWorkingMode airConditionerWorkingMode) {
				Log.d(TAG, "AirConditioner: connection state:" + connStatus.toString() + ", Working Mode:" + airConditionerWorkingMode.toString());
				if (mACListener != null) {
					mACListener.onPost(connStatus, airConditionerWorkingMode);
				}
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "switch operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
				if (mACListener != null) {
					mACListener.onOperationResult(serviceCode, serviceResult);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.item_connect) {
			LayoutInflater inflater = LayoutInflater.from(this);
			LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_connect, null);
			final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
			dialog.setTitle("Connect to Device");
			dialog.setView(layout);
			dialog.show();
			final EditText editServerURL = layout.findViewById(R.id.edit_server_address);
			final EditText editDeviceSerial = layout.findViewById(R.id.edit_device_serial);
			Button btnConnect = layout.findViewById(R.id.btn_connect);
			editServerURL.setText(mPref.getString("serverURL", ""));
			editDeviceSerial.setText(mPref.getString("deviceSerial", ""));
			btnConnect.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String serverURL = editServerURL.getText().toString();
					String deviceSerial = editDeviceSerial.getText().toString();
					registApp(serverURL, deviceSerial);
					SharedPreferences.Editor edit = mPref.edit();
					edit.putString("serverURL", serverURL);
					edit.putString("deviceSerial", deviceSerial);
					edit.apply();
					dialog.dismiss();
				}
			});
		}
		return true;
	}

	public void setCanopyListener(CanopyListener canopyListener) {
		mCanopyListener = canopyListener;
	}

	public void setPositionBarListener(PositionBarListener positionBarListener) {
		mPositionBarListener = positionBarListener;
	}

	public void setChargerListener(ChargerListener chargerListener) {
		mChargerListener = chargerListener;
	}

	public void setACListener(AirConditionerListener ACListener) {
		mACListener = ACListener;
	}

	public void setEdgeListener(EdgeListener edgeListener) {
		mEdgeListener = edgeListener;
	}
}