package com.heisha.heisha_sdk_demo.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.heisha.heisha_sdk.Component.AirConditioner.AirConditionerWorkingMode;
import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk_demo.Listener.AirConditionerListener;
import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_AIR_ROOM_MAXTEM;
import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_AIR_ROOM_MINTEM;
import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_POST_RATE_AC;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ACFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ACFragment extends Fragment {

	private static final String ARG_AC_CONN_STATUS = "ac_conn";
	private static final String ARG_AC_MODE = "ac_mode";
	private static final String ARG_AD_TEMPERATURE_1 = "ad_temperature_1";
	private static final String ARG_AD_TEMPERATURE_2 = "ad_temperature_2";
	private static final String ARG_AD_RAINFALL = "ad_rainfall";
	private static final String ARG_AD_SMOKE = "ad_smoke";
	private static final String ARG_COOLING_TEMPERATURE = "cooling_temperature";
	private static final String ARG_HEATING_TEMPERATURE = "heating_temperature";
	private static final String ARG_POST_RATE = "post_rate";

	private String valueACConn;
	private String valueACMode;
	private String valueADTemperature1;
	private String valueADTemperature2;
	private String valueADRainfall;
	private String valueADSmoke;
	private String valueCoolingTemperature;
	private String valueHeatingTemperature;
	private String valuePostRate;

	private TextView txtACConnStatus;
	private TextView txtACMode;
	private TextView txtADTemperature1;
	private TextView txtADTemperature2;
	private TextView txtADRainfall;
	private TextView txtADSmoke;

	private EditText editCoolingTemperature;
	private EditText editHeatingTemperature;
	private EditText editPostRate;

	private Button btnSetCoolingTemperature;
	private Button btnSetHeatingTemperature;
	private Button btnSetPostRate;

	private Button btnSetModeStandby;
	private Button btnSetModeStrongCooling;
	private Button btnSetModeAutoCooling;
	private Button btnSetModeStrongHeating;
	private Button btnSetModeAutoHeating;
	private Button btnSetModeVentilate;

	private MainActivity mContainerActivity;

	public ACFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ACFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ACFragment newInstance(String param1, String param2) {
		ACFragment fragment = new ACFragment();
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
			valueACConn = getArguments().getString(ARG_AC_CONN_STATUS);
			valueACMode = getArguments().getString(ARG_AC_MODE);
			valueADTemperature1 = getArguments().getString(ARG_AD_TEMPERATURE_1);
			valueADTemperature2 = getArguments().getString(ARG_AD_TEMPERATURE_2);
			valueADRainfall = getArguments().getString(ARG_AD_RAINFALL);
			valueADSmoke = getArguments().getString(ARG_AD_SMOKE);
			valueCoolingTemperature = getArguments().getString(ARG_COOLING_TEMPERATURE);
			valueHeatingTemperature = getArguments().getString(ARG_HEATING_TEMPERATURE);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		Bundle args = new Bundle();
		args.putString(ARG_AC_CONN_STATUS, txtACConnStatus.getText().toString());
		args.putString(ARG_AC_MODE, txtACMode.getText().toString());
		args.putString(ARG_AD_TEMPERATURE_1, txtADTemperature1.getText().toString());
		args.putString(ARG_AD_TEMPERATURE_2, txtADTemperature2.getText().toString());
		args.putString(ARG_AD_RAINFALL, txtADRainfall.getText().toString());
		args.putString(ARG_AD_SMOKE, txtADSmoke.getText().toString());
		args.putString(ARG_COOLING_TEMPERATURE, editCoolingTemperature.getText().toString());
		args.putString(ARG_HEATING_TEMPERATURE, editHeatingTemperature.getText().toString());
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_ac, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		final int[] requestNum = {3};
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (requestNum[0] == 3) {
					requestParam(SERVICE_PARAM_POST_RATE_AC);
				} else if (requestNum[0] == 2) {
					requestParam(SERVICE_PARAM_AIR_ROOM_MAXTEM);
				} else if (requestNum[0] == 1) {
					requestParam(SERVICE_PARAM_AIR_ROOM_MINTEM);
				} else {
					this.cancel();
					timer.cancel();
				}
				requestNum[0]--;
			}
		}, 0, 50);
	}

	private void initView(View view) {
		txtACConnStatus = view.findViewById(R.id.txt_ac_conn_status);
		txtACMode = view.findViewById(R.id.txt_ac_working_mode);
		txtADTemperature1 = view.findViewById(R.id.txt_ad_temperature_1);
		txtADTemperature2 = view.findViewById(R.id.txt_ad_temperature_2);
		txtADRainfall = view.findViewById(R.id.txt_ad_rainfall);
		txtADSmoke = view.findViewById(R.id.txt_ad_smoke);

		editCoolingTemperature = view.findViewById(R.id.edit_ac_cooling_temperature);
		editHeatingTemperature = view.findViewById(R.id.edit_ac_heating_temperature);
		editPostRate = view.findViewById(R.id.edit_ac_post_rate);

		btnSetCoolingTemperature = view.findViewById(R.id.btn_ac_cooling_temperature_set);
		btnSetHeatingTemperature = view.findViewById(R.id.btn_ac_heating_temperature_set);
		btnSetPostRate = view.findViewById(R.id.btn_ac_post_rate_set);

		btnSetModeStandby = view.findViewById(R.id.btn_ac_standby_set);
		btnSetModeStrongCooling = view.findViewById(R.id.btn_ac_strong_cooling_set);
		btnSetModeAutoCooling = view.findViewById(R.id.btn_ac_auto_cooling_set);
		btnSetModeStrongHeating = view.findViewById(R.id.btn_ac_strong_heating_set);
		btnSetModeAutoHeating = view.findViewById(R.id.btn_ac_auto_heating_set);
		btnSetModeVentilate = view.findViewById(R.id.btn_ac_ventilate_set);

		if (getArguments() != null) {
			txtACConnStatus.setText(valueACConn);
			txtACMode.setText(valueACMode);
			txtADTemperature1.setText(valueADTemperature1);
			txtADTemperature2.setText(valueADTemperature2);
			txtADRainfall.setText(valueADRainfall);
			txtADSmoke.setText(valueADSmoke);

			editCoolingTemperature.setText(valueCoolingTemperature);
			editHeatingTemperature.setText(valueHeatingTemperature);
			editPostRate.setText(valuePostRate);
		}

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
					switch(v.getId()) {
						case R.id.btn_ac_post_rate_set:
							if (TextUtils.isEmpty(editPostRate.getText().toString())) {
								editPostRate.setError("No Empty!");
								return;
							}
							setParam(SERVICE_PARAM_POST_RATE_AC, Integer.parseInt(editPostRate.getText().toString()));
							break;

						case R.id.btn_ac_cooling_temperature_set:
							if (TextUtils.isEmpty(editCoolingTemperature.getText().toString())) {
								editCoolingTemperature.setError("No Empty!");
								return;
							}
							setParam(SERVICE_PARAM_AIR_ROOM_MAXTEM, (int) (Float.parseFloat(editCoolingTemperature.getText().toString()) * 10));
							break;

						case R.id.btn_ac_heating_temperature_set:
							if (TextUtils.isEmpty(editHeatingTemperature.getText().toString())) {
								editHeatingTemperature.setError("No Empty!");
								return;
							}
							setParam(SERVICE_PARAM_AIR_ROOM_MINTEM, (int) (Float.parseFloat(editHeatingTemperature.getText().toString()) * 10));
							break;

						case R.id.btn_ac_standby_set:
							mContainerActivity.mAirConditioner.setAirConditionerWorkingMode(ServiceCode.AIR_CONDITION_MODE_IDLE);
							break;

						case R.id.btn_ac_strong_cooling_set:
							mContainerActivity.mAirConditioner.setAirConditionerWorkingMode(ServiceCode.AIR_CONDITION_MODE_STRONGCOLD);
							break;

						case R.id.btn_ac_auto_cooling_set:
							mContainerActivity.mAirConditioner.setAirConditionerWorkingMode(ServiceCode.AIR_CONDITION_MODE_AUTOCOLD);
							break;

						case R.id.btn_ac_strong_heating_set:
							mContainerActivity.mAirConditioner.setAirConditionerWorkingMode(ServiceCode.AIR_CONDITION_MODE_STRONGHEAT);
							break;

						case R.id.btn_ac_auto_heating_set:
							mContainerActivity.mAirConditioner.setAirConditionerWorkingMode(ServiceCode.AIR_CONDITION_MODE_AUTOHEAT);
							break;

						case R.id.btn_ac_ventilate_set:
							mContainerActivity.mAirConditioner.setAirConditionerWorkingMode(ServiceCode.AIR_CONDITION_MODE_VENTILATE);
							break;
					}
				}
			}
		};

		btnSetPostRate.setOnClickListener(listener);
		btnSetCoolingTemperature.setOnClickListener(listener);
		btnSetHeatingTemperature.setOnClickListener(listener);
		btnSetModeStandby.setOnClickListener(listener);
		btnSetModeStrongCooling.setOnClickListener(listener);
		btnSetModeAutoCooling.setOnClickListener(listener);
		btnSetModeStrongHeating.setOnClickListener(listener);
		btnSetModeAutoHeating.setOnClickListener(listener);
		btnSetModeVentilate.setOnClickListener(listener);
	}

	private void initListener() {
		mContainerActivity = (MainActivity) getActivity();

		mContainerActivity.setACListener(new AirConditionerListener() {
			@Override
			public void onPost(ConnStatus connStatus, AirConditionerWorkingMode airConditionerWorkingMode) {
				txtACConnStatus.setText(connStatus.toString());
				txtACMode.setText(airConditionerWorkingMode.toString());
				txtADTemperature1.setText(String.valueOf(mContainerActivity.mAirConditioner.getCabinTemperature() / 10f));
				txtADTemperature2.setText(String.valueOf(mContainerActivity.mAirConditioner.getAirOutletTemperature() / 10f));
				txtADRainfall.setText(String.valueOf(mContainerActivity.mAirConditioner.getRainValue()));
				txtADSmoke.setText(String.valueOf(mContainerActivity.mAirConditioner.getSmokeValue()));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
				if (result == ServiceResult.SUCCESS) {
					switch(paramIndex) {
						case SERVICE_PARAM_POST_RATE_AC:
							editPostRate.setText(String.valueOf(value));
							break;
						case SERVICE_PARAM_AIR_ROOM_MAXTEM:
							editCoolingTemperature.setText(String.valueOf(value / 10f));
							break;
						case SERVICE_PARAM_AIR_ROOM_MINTEM:
							editHeatingTemperature.setText(String.valueOf(value / 10f));
							break;
					}
				}
			}

			@Override
			public void onSetParam(ServiceResult serviceResult, ConfigParameter configParameter, ConfigFailReason configFailReason) {
				Toast.makeText(mContainerActivity, "Set " + configParameter.toString() + " " + serviceResult.toString(), Toast.LENGTH_LONG).show();
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