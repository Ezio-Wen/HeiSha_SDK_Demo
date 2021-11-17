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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.heisha.heisha_sdk.Component.EdgeComputing.GPSLocator;
import com.heisha.heisha_sdk.Component.EdgeComputing.Hygrothermograph;
import com.heisha.heisha_sdk.Component.EdgeComputing.PowerState;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBar;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBarState;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBarStateCallback;
import com.heisha.heisha_sdk.Manager.HSSDKManager;
import com.heisha.heisha_sdk.Manager.SDKManagerCallback;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk.Product.DNEST;

public class MainActivity extends AppCompatActivity {

	private SharedPreferences mPref;
	private static final String TAG = "MainActivity";
	private DNEST mDNEST;
	private Canopy mCanopy;
	private PositionBar mPositionBar;
	private Charger mCharger;
	private EdgeComputing mEdgeComputing;
	private ControlCenter mControlCenter;
	private AirConditioner mAirConditioner;

	private TextView txtServerStatus;
	private TextView txtDeviceName;
	private TextView txtDeviceStatus;
	private TextView txtCanopyStatus;
	private TextView txtPosBarStatus;
	private TextView txtChargingStatus;
	private TextView txtVoltage;
	private TextView txtCurrent;
	private TextView txtBatteryTemp;
	private TextView txtAirConditionerMode;
	private TextView txtAndroidPower;
	private TextView txtNVIDIAPower;

	private boolean mConnected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPref = getSharedPreferences("connection info", MODE_PRIVATE);

		initView();

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
			/*	HSSDKManager.getInstance().connectToServer(serverURI, new ConnectServerCallback() {
					@Override
					public void connectComplete(boolean reconnect, String serverURI) {
						txtServerStatus.setText(ConnStatus.CONNECTED.toString());
						Log.d(TAG, "connectComplete: 连接服务器成功");
						Toast.makeText(MainActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void connectionLost(Throwable throwable) {
						txtServerStatus.setText(ConnStatus.DISCONNECTED.toString());
						Log.d(TAG, "connectionLost: 服务器连接丢失");
						throwable.printStackTrace();
					}
				});
			 */
			}

			@Override
			public void onServerConnected(boolean b, String s) {
				txtServerStatus.setText(ConnStatus.CONNECTED.toString());
				Log.d(TAG, "connectComplete: 连接服务器成功");
				Toast.makeText(MainActivity.this, "连接服务器成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onServerDisconnected(Throwable throwable) {
				txtServerStatus.setText(ConnStatus.DISCONNECTED.toString());
				Log.d(TAG, "connectionLost: 服务器连接丢失");
				throwable.printStackTrace();
			}

			@Override
			public void onProductConnected(final String deviceName) {
				Log.d(TAG, "onProductConnected: 设备连接成功");
				mConnected = true;
				txtDeviceName.setText(deviceName);
				txtDeviceStatus.setText(ConnStatus.CONNECTED.toString());
				Toast.makeText(MainActivity.this, "设备连接成功", Toast.LENGTH_SHORT).show();
				initDevice(deviceName);
				initDeviceCallback();
			}

			@Override
			public void onProductDisconnected() {
				txtDeviceStatus.setText(ConnStatus.DISCONNECTED.toString());
				Log.d(TAG, "onProductConnected: 设备连接丢失");
			}

			@Override
			public void onComponentChanged(BaseComponent baseComponent, ConnStatus connStatus) {
				Log.d(TAG, "onComponentChanged: 组件状态改变");
			}
		});
	}

	private void initView() {
		txtServerStatus = this.findViewById(R.id.txt_server_status);
		txtDeviceName = this.findViewById(R.id.txt_device_name);
		txtDeviceStatus = this.findViewById(R.id.txt_device_status);
		txtCanopyStatus = this.findViewById(R.id.txt_canopy_status);
		txtPosBarStatus = this.findViewById(R.id.txt_position_bar_status);
		txtChargingStatus = this.findViewById(R.id.txt_charge_status);
		txtVoltage = this.findViewById(R.id.txt_charge_voltage);
		txtCurrent = this.findViewById(R.id.txt_charge_current);
		txtBatteryTemp = this.findViewById(R.id.txt_battery_temp);
		txtAirConditionerMode = this.findViewById(R.id.txt_air_conditioner_mode);
		txtAndroidPower = this.findViewById(R.id.txt_android_power);
		txtNVIDIAPower = this.findViewById(R.id.txt_NVIDIA_power);

		Button btnCanopyOpen = this.findViewById(R.id.btn_canopy_open);
		Button btnCanopyClose = this.findViewById(R.id.btn_canopy_close);
		Button btnPosBarTighten = this.findViewById(R.id.btn_position_bar_lock);
		Button btnPosBarRelease = this.findViewById(R.id.btn_position_bar_unlock);
		Button btnChargeStart = this.findViewById(R.id.btn_charge_start);
		Button btnChargeStop = this.findViewById(R.id.btn_charge_stop);
		Button btnDroneOn = this.findViewById(R.id.btn_drone_on);
		Button btnDroneOff = this.findViewById(R.id.btn_drone_off);
		Button btnRCOn = this.findViewById(R.id.btn_rc_on);
		Button btnRCOff = this.findViewById(R.id.btn_rc_off);
		Button btnAndroidOn = this.findViewById(R.id.btn_android_on);
		Button btnAndroidOff = this.findViewById(R.id.btn_android_off);
		Button btnNVIDIAOn = this.findViewById(R.id.btn_NVIDIA_on);
		Button btnNVIDIAOff = this.findViewById(R.id.btn_NVIDIA_off);
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mConnected) {
					switch(v.getId()) {
						case R.id.btn_canopy_open:
							mCanopy.startOpening();
							break;
						case R.id.btn_canopy_close:
							mCanopy.startClosing();
							break;
						case R.id.btn_position_bar_lock:
							mPositionBar.startTightening();
							break;
						case R.id.btn_position_bar_unlock:
							mPositionBar.startReleasing();
							break;
						case R.id.btn_charge_start:
							mCharger.startCharging();
							break;
						case R.id.btn_charge_stop:
							mCharger.stopCharging();
							break;
						case R.id.btn_drone_on:
							mCharger.getDroneSwitch().turnDroneON();
							break;
						case R.id.btn_drone_off:
							mCharger.getDroneSwitch().turnDroneOFF();
							break;
						case R.id.btn_rc_on:
							mEdgeComputing.RCTurnOn();
							break;
						case R.id.btn_rc_off:
							mEdgeComputing.RCTurnOff();
							break;
						case R.id.btn_android_on:
							mEdgeComputing.androidTurnOn();
							break;
						case R.id.btn_android_off:
							mEdgeComputing.androidTurnOff();
							break;
						case R.id.btn_NVIDIA_on:
							mEdgeComputing.NVIDIATurnOn();
							break;
						case R.id.btn_NVIDIA_off:
							mEdgeComputing.NVIDIATurnOff();
							break;
					}
				}
			}
		};
		btnCanopyOpen.setOnClickListener(listener);
		btnCanopyClose.setOnClickListener(listener);
		btnPosBarTighten.setOnClickListener(listener);
		btnPosBarRelease.setOnClickListener(listener);
		btnChargeStart.setOnClickListener(listener);
		btnChargeStop.setOnClickListener(listener);
		btnDroneOn.setOnClickListener(listener);
		btnDroneOff.setOnClickListener(listener);
		btnRCOn.setOnClickListener(listener);
		btnRCOff.setOnClickListener(listener);
		btnAndroidOn.setOnClickListener(listener);
		btnAndroidOff.setOnClickListener(listener);
		btnNVIDIAOn.setOnClickListener(listener);
		btnNVIDIAOff.setOnClickListener(listener);
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
			public void onUpdate(ConnStatus connStatus, final CanopyState canopyState) {
				Log.d(TAG, "canopy: connection state:" + connStatus.toString() + ", state:" + canopyState.toString());
				txtCanopyStatus.setText(canopyState.toString());
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "canopy operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
			}
		});
		mPositionBar.setStateCallback(new PositionBarStateCallback() {
			@Override
			public void onUpdate(ConnStatus connStatus, final PositionBarState positionBarState) {
				Log.d(TAG, "position bar: connection state:" + connStatus.toString() + ", state:" + positionBarState.toString());
				txtPosBarStatus.setText(positionBarState.toString());
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "position bar operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
			}
		});
		mCharger.setStateCallback(new ChargerStateCallback() {
			@Override
			public void onUpdate(ConnStatus connStatus, final ChargeState chargeState, BatteryDetectState batteryDetectState, DroneSwitchState droneSwitchState, final int voltage, final int current) {
				Log.d(TAG, "charger: connection state:" + connStatus.toString() + ", state:" + chargeState.toString() +
						", battery detect:" + batteryDetectState.toString() + ", drone state:" + droneSwitchState.toString());
				txtChargingStatus.setText(chargeState.toString());
				txtVoltage.setText(String.valueOf(voltage / 10f));
				txtCurrent.setText(String.valueOf(current));
				txtBatteryTemp.setText(String.valueOf(mCharger.getBatteryManager().getTemperature()));
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "charger operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
			}
		});
		mEdgeComputing.setStateCallback(new EdgeStateCallback() {
			@Override
			public void onUpdate(PowerState androidPowerState, PowerState NVIDIAPowerState) {
				Log.d(TAG, "Switch: android power:" + androidPowerState.toString() + ", NVIDIA power:" + NVIDIAPowerState.toString());
				txtAndroidPower.setText(androidPowerState.toString());
				txtNVIDIAPower.setText(NVIDIAPowerState.toString());
				GPSLocator gpsLocator = mEdgeComputing.getGPSLocator();
				Hygrothermograph hygrothermograph = mEdgeComputing.getHygrothermograph();
				Toast.makeText(MainActivity.this, "(" + gpsLocator.getLongitude() + "," + gpsLocator.getLatitude() + ")", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Log.d(TAG, "switch operate:" + serviceCode.toString() + ", result:" + serviceResult.toString());
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

			}

			@Override
			public void onSetConfig(ServiceResult serviceResult, ConfigParameter configParameter, ConfigFailReason configFailReason) {

			}
		});

		mAirConditioner.setStateCallback(new AirConditionerStateCallback() {
			@Override
			public void onUpdate(ConnStatus connStatus, AirConditionerWorkingMode airConditionerWorkingMode) {
				txtAirConditionerMode.setText(airConditionerWorkingMode.toString());
				Log.d(TAG, "AirConditioner: connection state:" + connStatus.toString() + ", Working Mode:" + airConditionerWorkingMode.toString());
			}

			@Override
			public void onOperateResult(ServiceCode serviceCode, ServiceResult serviceResult) {

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
}