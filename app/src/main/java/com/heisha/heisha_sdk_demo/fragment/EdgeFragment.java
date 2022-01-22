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

import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Component.EdgeComputing.GPSLocator;
import com.heisha.heisha_sdk.Component.EdgeComputing.PowerState;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk_demo.Listener.EdgeListener;
import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EdgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EdgeFragment extends Fragment {

	/*
	private static final String ARG_ANDROID_SWITCH = "android_switch";
	private static final String ARG_NVIDIA_SWITCH = "nvidia_switch";
	private static final String ARG_GPS_CONN_STATUS = "gps_conn";
	private static final String ARG_GPS_LOCATE_MODE = "gps_locate_mode";
	private static final String ARG_LOCATION = "location";
	private static final String ARG_HYGROTHERMOGRAPH_CONN_STATUS = "hygrothermograph_conn";
	private static final String ARG_TEMPERATURE = "temperature";
	private static final String ARG_HUMIDITY = "humidity";
	private static final String ARG_POST_RATE = "post_rate";

	private String valueAndroidSwitch;
	private String valueNVIDIASwitch;
	private String valueGPSConn;
	private String valueGPSLocateMode;
	private String valueLocation;
	private String valueHygrothermographConn;
	private String valueTemperature;
	private String valueHumidity;
	private String valuePostRate;
	*/

	private TextView txtAndroidSwitch;
	private TextView txtNVIDIASwitch;
	private TextView txtAliCloudConn;
	private TextView txtGPSConn;
	private TextView txtGPSLocateMode;
	private TextView txtLocation;
	private TextView txtMeteorologicalStationConn;
	private TextView txtTemperature;
	private TextView txtHumidity;
	private TextView txtWindSpeed;
	private TextView txtRainfall;

	private EditText editPostRate;

	private Button btnSetPostRate;
	private Button btnTurnOnAndroid;
	private Button btnTurnOffAndroid;
	private Button btnTurnOnNVIDIA;
	private Button btnTurnOffNVIDIA;

	private MainActivity mContainerActivity;

	public EdgeFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment EdgeFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static EdgeFragment newInstance(String param1, String param2) {
		EdgeFragment fragment = new EdgeFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		if (getArguments() != null) {
			valueAndroidSwitch = getArguments().getString(ARG_ANDROID_SWITCH);
			valueNVIDIASwitch = getArguments().getString(ARG_NVIDIA_SWITCH);
			valueGPSConn = getArguments().getString(ARG_GPS_CONN_STATUS);
			valueGPSLocateMode = getArguments().getString(ARG_GPS_LOCATE_MODE);
			valueLocation = getArguments().getString(ARG_LOCATION);
			valueHygrothermographConn = getArguments().getString(ARG_HYGROTHERMOGRAPH_CONN_STATUS);
			valueTemperature = getArguments().getString(ARG_TEMPERATURE);
			valueHumidity = getArguments().getString(ARG_HUMIDITY);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
		}
		*/
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		/*
		Bundle args = new Bundle();
		args.putString(ARG_ANDROID_SWITCH, txtAndroidSwitch.getText().toString());
		args.putString(ARG_NVIDIA_SWITCH, txtNVIDIASwitch.getText().toString());
		args.putString(ARG_GPS_CONN_STATUS, txtGPSConn.getText().toString());
		args.putString(ARG_GPS_LOCATE_MODE, txtGPSLocateMode.getText().toString());
		args.putString(ARG_LOCATION, txtLocation.getText().toString());
		args.putString(ARG_HYGROTHERMOGRAPH_CONN_STATUS, txtHygrothermographConn.getText().toString());
		args.putString(ARG_TEMPERATURE, txtTemperature.getText().toString());
		args.putString(ARG_HUMIDITY, txtHumidity.getText().toString());
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
		*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_edge, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		requestParam(ConfigParameter.SERVICE_PARAM_POST_RATE_EDGE);
	}

	private void initView(View view) {
		txtAndroidSwitch = view.findViewById(R.id.txt_android_switch_status);
		txtNVIDIASwitch = view.findViewById(R.id.txt_nvidia_switch_status);
		txtAliCloudConn = view.findViewById(R.id.txt_ali_conn_status);
		txtGPSConn = view.findViewById(R.id.txt_gps_conn_status);
		txtGPSLocateMode = view.findViewById(R.id.txt_gps_locate_mode);
		txtLocation = view.findViewById(R.id.txt_location);
		txtMeteorologicalStationConn = view.findViewById(R.id.txt_meteo_conn_status);
		txtTemperature = view.findViewById(R.id.txt_meteo_temperature);
		txtHumidity = view.findViewById(R.id.txt_meteo_humidity);
		txtWindSpeed = view.findViewById(R.id.txt_wind_speed);
		txtRainfall = view.findViewById(R.id.txt_rainfall);

		editPostRate = view.findViewById(R.id.edit_edge_post_rate);

		btnSetPostRate = view.findViewById(R.id.btn_edge_post_rate_set);
		btnTurnOnAndroid = view.findViewById(R.id.btn_android_turn_on);
		btnTurnOffAndroid = view.findViewById(R.id.btn_android_turn_off);
		btnTurnOnNVIDIA = view.findViewById(R.id.btn_nvidia_turn_on);
		btnTurnOffNVIDIA = view.findViewById(R.id.btn_nvidia_turn_off);

		mContainerActivity = (MainActivity) getActivity();

		if (mContainerActivity.mEdgeComputing != null) {
			GPSLocator gpsLocator = mContainerActivity.mEdgeComputing.getGPSLocator();
			txtAndroidSwitch.setText(mContainerActivity.mEdgeComputing.getPowerSupplyController().getAndroidPowerState().toString());
			txtNVIDIASwitch.setText(mContainerActivity.mEdgeComputing.getPowerSupplyController().getNVIDIAPowerState().toString());
			txtAliCloudConn.setText(mContainerActivity.mEdgeComputing.getAliCloudConnStatus().toString());
			txtGPSConn.setText(gpsLocator.getConnStatus().toString());
			switch(gpsLocator.getLocateMode()) {
				case 0:
					txtGPSLocateMode.setText("Not Positioned");
					break;
				case 1:
					txtGPSLocateMode.setText("SPS");
					break;
				case 2:
					txtGPSLocateMode.setText("Differential");
					break;
				case 3:
					txtGPSLocateMode.setText("PPS");
					break;
			}
			txtLocation.setText(gpsLocator.getLongitude() / 10000000f + (gpsLocator.getEastOrWest() == 0 ? "E, " : "W, ")
					+ gpsLocator.getLatitude() / 10000000f + (gpsLocator.getSouthOrNorth() == 0 ? "N" : "S"));
			txtMeteorologicalStationConn.setText(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getConnStatus().toString());
			float temperature = mContainerActivity.mEdgeComputing.getMeteorologicalStation().getHygrothermograph().getTemperature();
			txtTemperature.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
			txtHumidity.setText(String.valueOf(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getHygrothermograph().getHumidity()));
			txtWindSpeed.setText(String.valueOf(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getAnemograph().getWindSpeed()));
			txtRainfall.setText(String.valueOf(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getRainGauge().getRainfall()));
		}

		/*
		if (getArguments() != null) {
			txtAndroidSwitch.setText(valueAndroidSwitch);
			txtNVIDIASwitch.setText(valueNVIDIASwitch);
			txtGPSConn.setText(valueGPSConn);
			txtGPSLocateMode.setText(valueGPSLocateMode);
			txtLocation.setText(valueLocation);
			txtHygrothermographConn.setText(valueHygrothermographConn);
			txtTemperature.setText(valueTemperature);
			txtHumidity.setText(valueHumidity);
			editPostRate.setText(valuePostRate);
		}
		*/

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
					switch(v.getId()) {
						case R.id.btn_edge_post_rate_set:
							if (TextUtils.isEmpty(editPostRate.getText().toString())) {
								editPostRate.setError("No Empty!");
								return;
							}
							setParam(ConfigParameter.SERVICE_PARAM_POST_RATE_EDGE, Integer.parseInt(editPostRate.getText().toString()));
							break;
						case R.id.btn_android_turn_on:
							mContainerActivity.mEdgeComputing.androidTurnOn();
							break;
						case R.id.btn_android_turn_off:
							mContainerActivity.mEdgeComputing.androidTurnOff();
							break;
						case R.id.btn_nvidia_turn_on:
							mContainerActivity.mEdgeComputing.NVIDIATurnOn();
							break;
						case R.id.btn_nvidia_turn_off:
							mContainerActivity.mEdgeComputing.NVIDIATurnOff();
							break;
					}
				}
			}
		};

		btnSetPostRate.setOnClickListener(listener);
		btnTurnOnAndroid.setOnClickListener(listener);
		btnTurnOffAndroid.setOnClickListener(listener);
		btnTurnOnNVIDIA.setOnClickListener(listener);
		btnTurnOffNVIDIA.setOnClickListener(listener);
	}

	private void initListener() {
		mContainerActivity = (MainActivity) getActivity();

		mContainerActivity.setEdgeListener(new EdgeListener() {
			@Override
			public void onPost(PowerState androidPowerState, PowerState NVIDIAPowerState) {
				GPSLocator gpsLocator = mContainerActivity.mEdgeComputing.getGPSLocator();
				txtAndroidSwitch.setText(androidPowerState.toString());
				txtNVIDIASwitch.setText(NVIDIAPowerState.toString());
				txtAliCloudConn.setText(mContainerActivity.mEdgeComputing.getAliCloudConnStatus().toString());
				txtGPSConn.setText(gpsLocator.getConnStatus().toString());
				switch(gpsLocator.getLocateMode()) {
					case 0:
						txtGPSLocateMode.setText("Not Positioned");
						break;
					case 1:
						txtGPSLocateMode.setText("SPS");
						break;
					case 2:
						txtGPSLocateMode.setText("Differential");
						break;
					case 3:
						txtGPSLocateMode.setText("PPS");
						break;
				}
				txtLocation.setText(gpsLocator.getLongitude() / 10000000f + (gpsLocator.getEastOrWest() == 0 ? "E, " : "W, ")
						+ gpsLocator.getLatitude() / 10000000f + (gpsLocator.getSouthOrNorth() == 0 ? "N" : "S"));
				txtMeteorologicalStationConn.setText(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getConnStatus().toString());
				float temperature = mContainerActivity.mEdgeComputing.getMeteorologicalStation().getHygrothermograph().getTemperature();
				txtTemperature.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
				txtHumidity.setText(String.valueOf(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getHygrothermograph().getHumidity()));
				txtWindSpeed.setText(String.valueOf(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getAnemograph().getWindSpeed()));
				txtRainfall.setText(String.valueOf(mContainerActivity.mEdgeComputing.getMeteorologicalStation().getRainGauge().getRainfall()));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
				if (result == ServiceResult.SUCCESS) {
					if (paramIndex == ConfigParameter.SERVICE_PARAM_POST_RATE_EDGE) {
						editPostRate.setText(String.valueOf(value));
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