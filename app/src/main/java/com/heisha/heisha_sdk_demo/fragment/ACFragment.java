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

import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_AIR_ROOM_MAXTEM;
import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_AIR_ROOM_MINTEM;
import static com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter.SERVICE_PARAM_POST_RATE_AC;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ACFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ACFragment extends Fragment {

	/*
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
	*/

	private TextView txtACConnStatus;
	private TextView txtACMode;
	private TextView txtADCabinTemperature;
	private TextView txtADVentTemperature;
	private TextView txtADWaterlogging;
	private TextView txtADSmoke;

	private EditText editMaxTemperature;
	private EditText editMinTemperature;
	private EditText editPostRate;

	private Button btnSetMaxTemperature;
	private Button btnSetMinTemperature;
	private Button btnSetPostRate;

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
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
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
		*/
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		/*
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
		*/
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				requestParam(SERVICE_PARAM_POST_RATE_AC);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestParam(SERVICE_PARAM_AIR_ROOM_MAXTEM);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestParam(SERVICE_PARAM_AIR_ROOM_MINTEM);
			}
		}).start();
	}

	private void initView(View view) {
		txtACConnStatus = view.findViewById(R.id.txt_ac_conn_status);
		txtACMode = view.findViewById(R.id.txt_ac_working_mode);
		txtADCabinTemperature = view.findViewById(R.id.txt_cabin_temperature);
		txtADVentTemperature = view.findViewById(R.id.txt_vent_temperature);
		txtADWaterlogging = view.findViewById(R.id.txt_waterlogging);
		txtADSmoke = view.findViewById(R.id.txt_smoke);

		editMaxTemperature = view.findViewById(R.id.edit_ac_max_temperature);
		editMinTemperature = view.findViewById(R.id.edit_ac_min_temperature);
		editPostRate = view.findViewById(R.id.edit_ac_post_rate);

		btnSetMaxTemperature = view.findViewById(R.id.btn_ac_max_temperature_set);
		btnSetMinTemperature = view.findViewById(R.id.btn_ac_min_temperature_set);
		btnSetPostRate = view.findViewById(R.id.btn_ac_post_rate_set);

		mContainerActivity = (MainActivity) getActivity();

		if (mContainerActivity.mAirConditioner != null) {
			txtACConnStatus.setText(mContainerActivity.mAirConditioner.getConnectionState().toString());
			txtACMode.setText(mContainerActivity.mAirConditioner.getAirConditionerWorkingMode().toString());
			txtADCabinTemperature.setText(String.valueOf(mContainerActivity.mAirConditioner.getCabinTemperature()));
			txtADVentTemperature.setText(String.valueOf(mContainerActivity.mAirConditioner.getVentTemperature()));
			txtADWaterlogging.setText(String.valueOf(mContainerActivity.mAirConditioner.getWaterloggingValue()));
			txtADSmoke.setText(String.valueOf(mContainerActivity.mAirConditioner.getSmokeValue()));
		}

		/*
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
		*/

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

						case R.id.btn_ac_max_temperature_set:
							if (TextUtils.isEmpty(editMaxTemperature.getText().toString())) {
								editMaxTemperature.setError("No Empty!");
								return;
							}
							setParam(SERVICE_PARAM_AIR_ROOM_MAXTEM, (int) (Float.parseFloat(editMaxTemperature.getText().toString()) * 10 + 1000));
							break;

						case R.id.btn_ac_min_temperature_set:
							if (TextUtils.isEmpty(editMinTemperature.getText().toString())) {
								editMinTemperature.setError("No Empty!");
								return;
							}
							setParam(SERVICE_PARAM_AIR_ROOM_MINTEM, (int) (Float.parseFloat(editMinTemperature.getText().toString()) * 10 + 1000));
							break;
					}
				}
			}
		};

		btnSetPostRate.setOnClickListener(listener);
		btnSetMaxTemperature.setOnClickListener(listener);
		btnSetMinTemperature.setOnClickListener(listener);
	}

	private void initListener() {

		mContainerActivity.setACListener(new AirConditionerListener() {
			@Override
			public void onPost(ConnStatus connStatus, AirConditionerWorkingMode airConditionerWorkingMode) {
				txtACConnStatus.setText(connStatus.toString());
				txtACMode.setText(airConditionerWorkingMode.toString());
				txtADCabinTemperature.setText(String.valueOf(mContainerActivity.mAirConditioner.getCabinTemperature()));
				txtADVentTemperature.setText(String.valueOf(mContainerActivity.mAirConditioner.getVentTemperature()));
				txtADWaterlogging.setText(String.valueOf(mContainerActivity.mAirConditioner.getWaterloggingValue()));
				txtADSmoke.setText(String.valueOf(mContainerActivity.mAirConditioner.getSmokeValue()));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
				if (result == ServiceResult.SUCCESS) {
					switch(paramIndex) {
						case SERVICE_PARAM_POST_RATE_AC:
							editPostRate.setText(String.valueOf(value));
							break;
						case SERVICE_PARAM_AIR_ROOM_MAXTEM:
							editMaxTemperature.setText(String.valueOf((value - 1000) / 10f));
							break;
						case SERVICE_PARAM_AIR_ROOM_MINTEM:
							editMinTemperature.setText(String.valueOf((value - 1000) / 10f));
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