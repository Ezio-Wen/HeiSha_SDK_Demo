package com.heisha.heisha_sdk_demo.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.heisha.heisha_sdk.Component.Canopy.Anemograph;
import com.heisha.heisha_sdk.Component.Canopy.CanopyLocator;
import com.heisha.heisha_sdk.Component.Canopy.CanopyState;
import com.heisha.heisha_sdk.Component.Canopy.Thermohygrograph;
import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk_demo.Listener.CanopyListener;
import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CanopyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CanopyFragment extends Fragment {

	private static final String ARG_CANOPY_CONN_STATUS = "canopy_conn";
	private static final String ARG_CANOPY_STATUS = "canopy_status";
	private static final String ARG_OPEN_STATUS = "open_status";
	private static final String ARG_CLOSE_STATUS = "close_status";
	private static final String ARG_ANEMOGRAPH_CONN = "anomograph_conn";
	private static final String ARG_THERMOHYGROGRAPH_1_CONN = "thermohygrograph_1_conn";
	private static final String ARG_THERMOHYGROGRAPH_2_CONN = "thermohygrograph_2_conn";
	private static final String ARG_LOCATOR_CONN = "locator_conn";
	private static final String ARG_LOCATION = "location";
	private static final String ARG_TEM_1 = "tem_1";
	private static final String ARG_HUM_1 = "hum_1";
	private static final String ARG_TEM_2 = "tem_2";
	private static final String ARG_HUM_2 = "hum_2";
	private static final String ARG_WIND_SPEED = "wind_speed";
	private static final String ARG_POST_RATE = "post_rate";

	private String valueCanopyConn;
	private String valueCanopyStatus;
	private String valueOpenStatus;
	private String valueCloseStatus;
	private String valueAnemographConn;
	private String valueThermohygrograph1Conn;
	private String valueThermohygrograph2Conn;
	private String valueLocatorConn;
	private String valueLocation;
	private String valueTem1;
	private String valueHum1;
	private String valueTem2;
	private String valueHum2;
	private String valueWindSpeed;
	private String valuePostRate;

	private TextView txtCanopyConnStatus;
	private TextView txtCanopyStatus;
	private TextView txtCanopyOpenStatus;
	private TextView txtCanopyCloseStatus;
	private TextView txtAnemographConnStatus;
	private TextView txtThermohygrograph1ConnStatus;
	private TextView txtThermohygrograph2ConnStatus;
	private TextView txtLocatorConnStatus;
	private TextView txtCanopyLocation;
	private TextView txtTem1;
	private TextView txtHum1;
	private TextView txtTem2;
	private TextView txtHum2;
	private TextView txtWindSpeed;

	private EditText editPostRate;

	private Button btnOpenCanopy;
	private Button btnCloseCanopy;
	private Button btnResetCanopy;
	private Button btnSetPostRate;
	private MainActivity mContainerActivity;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment CanopyFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static CanopyFragment newInstance(String param1, String param2) {
		CanopyFragment fragment = new CanopyFragment();
		Bundle args = new Bundle();
//		args.putString(ARG_PARAM1, param1);
//		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public CanopyFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			valueCanopyConn = getArguments().getString(ARG_CANOPY_CONN_STATUS);
			valueCanopyStatus = getArguments().getString(ARG_CANOPY_STATUS);
			valueOpenStatus = getArguments().getString(ARG_OPEN_STATUS);
			valueCloseStatus = getArguments().getString(ARG_CLOSE_STATUS);
			valueAnemographConn = getArguments().getString(ARG_ANEMOGRAPH_CONN);
			valueThermohygrograph1Conn = getArguments().getString(ARG_THERMOHYGROGRAPH_1_CONN);
			valueThermohygrograph2Conn = getArguments().getString(ARG_THERMOHYGROGRAPH_2_CONN);
			valueLocatorConn = getArguments().getString(ARG_LOCATOR_CONN);
			valueLocation = getArguments().getString(ARG_LOCATION);
			valueTem1 = getArguments().getString(ARG_TEM_1);
			valueHum1 = getArguments().getString(ARG_HUM_1);
			valueTem2 = getArguments().getString(ARG_TEM_2);
			valueHum2 = getArguments().getString(ARG_HUM_2);
			valueWindSpeed = getArguments().getString(ARG_WIND_SPEED);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		Bundle args = new Bundle();
		args.putString(ARG_CANOPY_CONN_STATUS, txtCanopyConnStatus.getText().toString());
		args.putString(ARG_CANOPY_STATUS, txtCanopyStatus.getText().toString());
		args.putString(ARG_OPEN_STATUS, txtCanopyOpenStatus.getText().toString());
		args.putString(ARG_CLOSE_STATUS, txtCanopyCloseStatus.getText().toString());
		args.putString(ARG_ANEMOGRAPH_CONN, txtAnemographConnStatus.getText().toString());
		args.putString(ARG_THERMOHYGROGRAPH_1_CONN, txtThermohygrograph1ConnStatus.getText().toString());
		args.putString(ARG_THERMOHYGROGRAPH_2_CONN, txtThermohygrograph2ConnStatus.getText().toString());
		args.putString(ARG_LOCATOR_CONN, txtLocatorConnStatus.getText().toString());
		args.putString(ARG_LOCATION, txtCanopyLocation.getText().toString());
		args.putString(ARG_TEM_1, txtTem1.getText().toString());
		args.putString(ARG_HUM_1, txtHum1.getText().toString());
		args.putString(ARG_TEM_2, txtTem2.getText().toString());
		args.putString(ARG_HUM_2, txtHum2.getText().toString());
		args.putString(ARG_WIND_SPEED, txtWindSpeed.getText().toString());
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_canopy, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		requestParam(ConfigParameter.SERVICE_PARAM_POST_RATE_CANOPY);
	}

	private void initView(View view) {
		txtCanopyConnStatus = view.findViewById(R.id.txt_canopy_conn_status);
		txtCanopyStatus = view.findViewById(R.id.txt_canopy_status);
		txtCanopyOpenStatus = view.findViewById(R.id.txt_canopy_open_switch_status);
		txtCanopyCloseStatus = view.findViewById(R.id.txt_canopy_close_switch_status);
		txtAnemographConnStatus = view.findViewById(R.id.txt_anemograph_conn_status);
		txtThermohygrograph1ConnStatus = view.findViewById(R.id.txt_thermohygrograph_1_conn_status);
		txtThermohygrograph2ConnStatus = view.findViewById(R.id.txt_thermohygrograph_2_conn_status);
		txtLocatorConnStatus = view.findViewById(R.id.txt_canopy_locator_conn_status);
		txtCanopyLocation = view.findViewById(R.id.txt_canopy_location);
		txtTem1 = view.findViewById(R.id.txt_tem1);
		txtHum1 = view.findViewById(R.id.txt_hum1);
		txtTem2 = view.findViewById(R.id.txt_tem2);
		txtHum2 = view.findViewById(R.id.txt_hum2);
		txtWindSpeed = view.findViewById(R.id.txt_wind_speed);

		editPostRate = view.findViewById(R.id.edit_canopy_post_rate);

		btnSetPostRate = view.findViewById(R.id.btn_canopy_post_rate_set);
		btnOpenCanopy = view.findViewById(R.id.btn_canopy_open);
		btnCloseCanopy = view.findViewById(R.id.btn_canopy_close);
		btnResetCanopy = view.findViewById(R.id.btn_canopy_reset);

		if (getArguments() != null) {
			txtCanopyConnStatus.setText(valueCanopyConn);
			txtCanopyStatus.setText(valueCanopyStatus);
			txtCanopyOpenStatus.setText(valueOpenStatus);
			txtCanopyCloseStatus.setText(valueCloseStatus);
			txtAnemographConnStatus.setText(valueAnemographConn);
			txtThermohygrograph1ConnStatus.setText(valueThermohygrograph1Conn);
			txtThermohygrograph2ConnStatus.setText(valueThermohygrograph2Conn);
			txtLocatorConnStatus.setText(valueLocatorConn);
			txtCanopyLocation.setText(valueLocation);
			txtTem1.setText(valueTem1);
			txtHum1.setText(valueHum1);
			txtTem2.setText(valueTem2);
			txtHum2.setText(valueHum2);
			txtWindSpeed.setText(valueWindSpeed);
			editPostRate.setText(valuePostRate);
		}

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
					switch(v.getId()) {
						case R.id.btn_canopy_post_rate_set:
							if (TextUtils.isEmpty(editPostRate.getText().toString())) {
								editPostRate.setError("No Empty!");
								return;
							}
							setParam(ConfigParameter.SERVICE_PARAM_POST_RATE_CANOPY, Integer.parseInt(editPostRate.getText().toString()));
							break;
						case R.id.btn_canopy_open:
							mContainerActivity.mCanopy.startOpening();
							break;
						case R.id.btn_canopy_close:
							mContainerActivity.mCanopy.startClosing();
							break;
						case R.id.btn_canopy_reset:
							mContainerActivity.mCanopy.resetState();
							break;
					}
				}
			}
		};

		btnSetPostRate.setOnClickListener(listener);
		btnOpenCanopy.setOnClickListener(listener);
		btnCloseCanopy.setOnClickListener(listener);
		btnResetCanopy.setOnClickListener(listener);
	}

	private void initListener() {
		mContainerActivity = (MainActivity) getActivity();

		mContainerActivity.setCanopyListener(new CanopyListener() {
			@Override
			public void onPost(ConnStatus connStatus, CanopyState canopyState) {
				Anemograph anemograph = mContainerActivity.mCanopy.getAnemograph();
				Thermohygrograph thermohygrograph_1 = mContainerActivity.mCanopy.getThermohygrograph_1();
				Thermohygrograph thermohygrograph_2 = mContainerActivity.mCanopy.getThermohygrograph_2();
				CanopyLocator canopyLocator = mContainerActivity.mCanopy.getCanopyLocator();
				txtCanopyConnStatus.setText(connStatus.toString());
				txtCanopyStatus.setText(canopyState.toString());
				txtCanopyOpenStatus.setText((mContainerActivity.mCanopy.getOpenedLimitSwitchStatus() == 1 ? "TRIGGER_YES" : "TRIGGER_NO"));
				txtCanopyCloseStatus.setText((mContainerActivity.mCanopy.getClosedLimitSwitchStatus() == 1 ? "TRIGGER_YES" : "TRIGGER_NO"));
				txtAnemographConnStatus.setText(anemograph.getAnemographConnState().toString());
				txtThermohygrograph1ConnStatus.setText(thermohygrograph_1.getThermohygrographConnState().toString());
				txtThermohygrograph2ConnStatus.setText(thermohygrograph_2.getThermohygrographConnState().toString());
				txtLocatorConnStatus.setText(canopyLocator.getGPSConnState().toString());
				txtCanopyLocation.setText(canopyLocator.getLongitude() / 10000000f + (canopyLocator.getEastOrWest() == 0 ? "E, " : "W, ")
						+ canopyLocator.getLatitude() / 10000000f + (canopyLocator.getSouthOrNorth() == 0 ? "N" : "S"));
				float temperature = (thermohygrograph_1.getTemperature() - 1000) / 10f;
				txtTem1.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
				txtHum1.setText(String.valueOf(thermohygrograph_1.getHumidity()));
				temperature = (thermohygrograph_2.getTemperature() - 1000) / 10f;
				txtTem2.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
				txtHum2.setText(String.valueOf(thermohygrograph_2.getHumidity()));
				txtWindSpeed.setText(String.valueOf(anemograph.getWindSpeed() / 10f));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
				if (result == ServiceResult.SUCCESS) {
					if (paramIndex == ConfigParameter.SERVICE_PARAM_POST_RATE_CANOPY) {
						editPostRate.setText(String.valueOf(value));
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