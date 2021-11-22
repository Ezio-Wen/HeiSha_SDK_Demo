package com.heisha.heisha_sdk_demo.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChargerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChargerFragment extends Fragment {

	private static final String ARG_CHARGER_CONN_STATUS = "charger_conn";
	private static final String ARG_CHARGING_STATUS = "charging_status";
	private static final String ARG_BATTERY_DETECTION_STATUS = "battery_detection_status";
	private static final String ARG_DRONE_STATUS = "drone_status";
	private static final String ARG_CHARGING_VOLTAGE = "charging_voltage";
	private static final String ARG_CHARGING_CURRENT = "charging_current";
	private static final String ARG_BATTERY_TEMPERATURE = "battery_temperature";
	private static final String ARG_BATTERY_PERCENTAGE = "battery_percentage";
	private static final String ARG_CHARGING_DURATION = "charging_duration";
	private static final String ARG_CHARGING_REMAINING = "charging_remaining";
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
	private String valueChargingRemaining;
	private int valueBatteryType;
	private boolean valueChargingPowerOn;
	private String valuePostRate;

	private TextView txtChargerConnStatus;
	private TextView txtChargingStatus;
	private TextView txtBatteryDetectionStatus;
	private TextView txtDroneStatus;
	private TextView txtChargingVoltage;
	private TextView txtChargingCurrent;
	private TextView txtBatteryTemperature;
	private TextView txtBatteryPercentage;
	private TextView txtChargingDuration;
	private TextView txtChargingRemaining;

	private EditText editPostRate;

	private Button btnStartCharging;
	private Button btnStopCharging;
	private Button btnTurnOnDrone;
	private Button btnTurnOffDrone;
	private Button btnSetPostRate;

	private Spinner spinnerBatteryType;

	private Switch switchPowerOnCharging;

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
//		args.putString(ARG_PARAM1, param1);
//		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			valueChargingRemaining = getArguments().getString(ARG_CHARGING_REMAINING);
			valueBatteryType = getArguments().getInt(ARG_BATTERY_TYPE);
			valueChargingPowerOn = getArguments().getBoolean(ARG_CHARGING_POWER_ON);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
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
		args.putString(ARG_CHARGING_REMAINING, txtChargingRemaining.getText().toString());
		args.putInt(ARG_BATTERY_TYPE, spinnerBatteryType.getSelectedItemPosition());
		//todo 到这儿了！！！
//		args.putBoolean(ARG_CHARGING_POWER_ON, switchPowerOnCharging.get);
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_charger, container, false);
	}
}