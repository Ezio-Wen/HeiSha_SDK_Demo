package com.heisha.heisha_sdk_demo.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.heisha.heisha_sdk.Component.Charger.BatteryDetectState;
import com.heisha.heisha_sdk.Component.Charger.BatteryPackType;
import com.heisha.heisha_sdk.Component.Charger.ChargeState;
import com.heisha.heisha_sdk.Component.Charger.DroneSwitchState;
import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk_demo.Listener.ChargerListener;
import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_BATTERY_TYPE;
import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_POST_RATE_CD;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChargerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChargerFragment extends Fragment {

	/*
	private static final String ARG_CHARGER_CONN_STATUS = "charger_conn";
	private static final String ARG_CHARGING_STATUS = "charging_status";
	private static final String ARG_BATTERY_DETECTION_STATUS = "battery_detection_status";
	private static final String ARG_DRONE_STATUS = "drone_status";
	private static final String ARG_CHARGING_VOLTAGE = "charging_voltage";
	private static final String ARG_CHARGING_CURRENT = "charging_current";
	private static final String ARG_BATTERY_TEMPERATURE = "battery_temperature";
	private static final String ARG_BATTERY_PERCENTAGE = "battery_percentage";
	private static final String ARG_CHARGING_DURATION = "charging_duration";
	private static final String ARG_BATTERY_TYPE = "battery_type";
	private static final String ARG_CHARGING_POWER_ON = "charging_power_on";
	private static final String ARG_POST_RATE = "post_rate";

	private String valueChargerConn;
	private String valueChargingStatus;
	private String valueBatteryDetectionStatus;
	private String valueDroneStatus;
	private String valueChargingVoltage;
	private String valueChargingCurrent;
	private String valueBatteryTemperature;
	private String valueBatteryPercentage;
	private String valueChargingDuration;
	private int valueBatteryType;
	private boolean valueChargingPowerOn;
	private String valuePostRate;
	*/

	private TextView txtChargerConnStatus;
	private TextView txtChargingStatus;
	private TextView txtBatteryDetectionStatus;
	private TextView txtDroneStatus;
	private TextView txtChargingVoltage;
	private TextView txtChargingCurrent;
	private TextView txtBatteryTemperature;
	private TextView txtBatteryPercentage;
	private TextView txtChargingDuration;

	private EditText editPostRate;

	private Button btnStartCharging;
	private Button btnStopCharging;
	private Button btnTurnOnDrone;
	private Button btnTurnOffDrone;
	private Button btnSetPostRate;

	private Spinner spinnerBatteryType;

	private MainActivity mContainerActivity;

	public ChargerFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ChargerFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ChargerFragment newInstance(String param1, String param2) {
		ChargerFragment fragment = new ChargerFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		if (getArguments() != null) {
			valueChargerConn = getArguments().getString(ARG_CHARGER_CONN_STATUS);
			valueChargingStatus = getArguments().getString(ARG_CHARGING_STATUS);
			valueBatteryDetectionStatus = getArguments().getString(ARG_BATTERY_DETECTION_STATUS);
			valueDroneStatus = getArguments().getString(ARG_DRONE_STATUS);
			valueChargingVoltage = getArguments().getString(ARG_CHARGING_VOLTAGE);
			valueChargingCurrent = getArguments().getString(ARG_CHARGING_CURRENT);
			valueBatteryTemperature = getArguments().getString(ARG_BATTERY_TEMPERATURE);
			valueBatteryPercentage = getArguments().getString(ARG_BATTERY_PERCENTAGE);
			valueChargingDuration = getArguments().getString(ARG_CHARGING_DURATION);
			valueBatteryType = getArguments().getInt(ARG_BATTERY_TYPE);
			valueChargingPowerOn = getArguments().getBoolean(ARG_CHARGING_POWER_ON);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
		}
		*/
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		/*
		Bundle args = new Bundle();
		args.putString(ARG_CHARGER_CONN_STATUS, txtChargerConnStatus.getText().toString());
		args.putString(ARG_CHARGING_STATUS, txtChargingStatus.getText().toString());
		args.putString(ARG_BATTERY_DETECTION_STATUS, txtBatteryDetectionStatus.getText().toString());
		args.putString(ARG_DRONE_STATUS, txtDroneStatus.getText().toString());
		args.putString(ARG_CHARGING_VOLTAGE, txtChargingVoltage.getText().toString());
		args.putString(ARG_CHARGING_CURRENT, txtChargingCurrent.getText().toString());
		args.putString(ARG_BATTERY_TEMPERATURE, txtBatteryTemperature.getText().toString());
		args.putString(ARG_BATTERY_PERCENTAGE, txtBatteryPercentage.getText().toString());
		args.putString(ARG_CHARGING_DURATION, txtChargingDuration.getText().toString());
		args.putInt(ARG_BATTERY_TYPE, spinnerBatteryType.getSelectedItemPosition());
		args.putBoolean(ARG_CHARGING_POWER_ON, switchPowerOnCharging.isChecked());
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
		*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_charger, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		new Thread(new Runnable() {
			@Override
			public void run() {
				requestParam(SERVICE_PARAM_POST_RATE_CD);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestParam(SERVICE_PARAM_BATTERY_TYPE);
			}
		}).start();
	}

	private void initView(View view) {
		txtChargerConnStatus = view.findViewById(R.id.txt_charger_conn_status);
		txtChargingStatus = view.findViewById(R.id.txt_charging_status);
		txtBatteryDetectionStatus = view.findViewById(R.id.txt_battery_detection_status);
		txtDroneStatus = view.findViewById(R.id.txt_drone_status);
		txtChargingVoltage = view.findViewById(R.id.txt_charging_voltage);
		txtChargingCurrent = view.findViewById(R.id.txt_charging_current);
		txtBatteryTemperature = view.findViewById(R.id.txt_battery_temperature);
		txtBatteryPercentage = view.findViewById(R.id.txt_battery_percentage);
		txtChargingDuration = view.findViewById(R.id.txt_charging_duration);

		spinnerBatteryType = view.findViewById(R.id.spinner_battery_type);

		editPostRate = view.findViewById(R.id.edit_charger_post_rate);

		btnSetPostRate = view.findViewById(R.id.btn_charger_post_rate_set);
		btnStartCharging = view.findViewById(R.id.btn_charging_start);
		btnStopCharging = view.findViewById(R.id.btn_charging_stop);
		btnTurnOnDrone = view.findViewById(R.id.btn_drone_turn_on);
		btnTurnOffDrone = view.findViewById(R.id.btn_drone_turn_off);

		spinnerBatteryType.setSelection(0, true);

		mContainerActivity = (MainActivity) getActivity();

		if (mContainerActivity.mCharger != null) {
			txtChargerConnStatus.setText(mContainerActivity.mCharger.getConnectionStatus().toString());
			txtChargingStatus.setText(mContainerActivity.mCharger.getChargingStatus().toString());
			txtBatteryDetectionStatus.setText(mContainerActivity.mCharger.getBatteryManager().getBatteryDetectStatus().toString());
			txtDroneStatus.setText(mContainerActivity.mCharger.getDroneSwitch().getDroneStatus().toString());
			txtChargingVoltage.setText(String.valueOf(mContainerActivity.mCharger.getChargingVoltage()));
			txtChargingCurrent.setText(String.valueOf(mContainerActivity.mCharger.getChargingCurrent()));
			float temperature = mContainerActivity.mCharger.getBatteryManager().getBatteryTemperature();
			txtBatteryTemperature.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
			txtBatteryPercentage.setText(String.valueOf(mContainerActivity.mCharger.getBatteryManager().getPercentRemaining()));
			txtChargingDuration.setText(String.valueOf(mContainerActivity.mCharger.getChargingDuration()));
		}

		/*
		if (getArguments() != null) {
			txtChargerConnStatus.setText(valueChargerConn);
			txtChargingStatus.setText(valueChargingStatus);
			txtBatteryDetectionStatus.setText(valueBatteryDetectionStatus);
			txtDroneStatus.setText(valueDroneStatus);
			txtChargingVoltage.setText(valueChargingVoltage);
			txtChargingCurrent.setText(valueChargingCurrent);
			txtBatteryTemperature.setText(valueBatteryTemperature);
			txtBatteryPercentage.setText(valueBatteryPercentage);
			txtChargingDuration.setText(valueChargingDuration);

			spinnerBatteryType.setSelection(valueBatteryType, true);

			switchPowerOnCharging.setChecked(valueChargingPowerOn);

			editPostRate.setText(valuePostRate);
		}
		*/

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
					switch(v.getId()) {
						case R.id.btn_charger_post_rate_set:
							if (TextUtils.isEmpty(editPostRate.getText().toString())) {
								editPostRate.setError("No Empty!");
								return;
							}
							setParam(SERVICE_PARAM_POST_RATE_CD, Integer.parseInt(editPostRate.getText().toString()));
							break;
						case R.id.btn_charging_start:
							mContainerActivity.mCharger.startCharging();
							break;
						case R.id.btn_charging_stop:
							mContainerActivity.mCharger.stopCharging();
							break;
						case R.id.btn_drone_turn_on:
							mContainerActivity.mCharger.getDroneSwitch().turnDroneON();
							break;
						case R.id.btn_drone_turn_off:
							mContainerActivity.mCharger.getDroneSwitch().turnDroneOFF();
							break;
					}
				}
			}
		};

		btnSetPostRate.setOnClickListener(listener);
		btnStartCharging.setOnClickListener(listener);
		btnStopCharging.setOnClickListener(listener);
		btnTurnOnDrone.setOnClickListener(listener);
		btnTurnOffDrone.setOnClickListener(listener);

		spinnerBatteryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position > 0) {
					setParam(SERVICE_PARAM_BATTERY_TYPE, position - 1);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}

	private void initListener() {

		mContainerActivity.setChargerListener(new ChargerListener() {

			@Override
			public void onPost(ConnStatus connStatus, ChargeState chargeState, BatteryDetectState batteryDetectState, DroneSwitchState droneSwitchState, int voltage, int current) {
				txtChargerConnStatus.setText(connStatus.toString());
				txtChargingStatus.setText(chargeState.toString());
				txtBatteryDetectionStatus.setText(batteryDetectState.toString());
				txtDroneStatus.setText(droneSwitchState.toString());
				txtChargingVoltage.setText(String.valueOf(voltage / 10f));
				txtChargingCurrent.setText(String.valueOf(current / 10f));
				float temperature = mContainerActivity.mCharger.getBatteryManager().getBatteryTemperature();
				txtBatteryTemperature.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
				txtBatteryPercentage.setText(String.valueOf(mContainerActivity.mCharger.getBatteryManager().getPercentRemaining()));
				txtChargingDuration.setText(String.valueOf(mContainerActivity.mCharger.getChargingDuration()));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
				if (result == ServiceResult.SUCCESS) {
					switch(paramIndex) {
						case SERVICE_PARAM_POST_RATE_CD:
							editPostRate.setText(String.valueOf(value));
							break;
						case SERVICE_PARAM_BATTERY_TYPE:
							BatteryPackType batteryPackType = BatteryPackType.values()[value];
							if (value < spinnerBatteryType.getCount() - 1) {
								spinnerBatteryType.setSelection(batteryPackType.ordinal() + 1, false);
							}
							break;
					}
				}
			}

			@Override
			public void onSetParam(ServiceResult serviceResult, ConfigParameter configParameter, ConfigFailReason configFailReason) {
				Toast.makeText(mContainerActivity, "Set " + configParameter.toString() + " " + serviceResult.toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void requestParam(ConfigParameter parameter) {
		if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
			mContainerActivity.mControlCenter.getConfigParameter(parameter);
		}
	}

	private void setParam(ConfigParameter parameter, int value) {
		if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
			mContainerActivity.mControlCenter.setConfigParameter(parameter, value);
		}
	}
}