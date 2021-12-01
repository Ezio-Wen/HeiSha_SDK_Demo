package com.heisha.heisha_sdk_demo.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Component.EdgeComputing.GPSLocator;
import com.heisha.heisha_sdk.Component.EdgeComputing.Hygrothermograph;
import com.heisha.heisha_sdk.Component.EdgeComputing.PowerState;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk_demo.Listener.EdgeListener;
import com.heisha.heisha_sdk_demo.Listener.RemoteControlListener;
import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RemoteControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemoteControlFragment extends Fragment {

	private static final String ARG_RC_CONN_STATUS = "rc_conn";
	private static final String ARG_RC_POWER_STATUS = "rc_power_status";
	private static final String ARG_RC_USB_STATUS = "rc_usb_status";
	private static final String ARG_TEMPERATURE = "temperature";
	private static final String ARG_HUMIDITY = "humidity";
	private static final String ARG_POST_RATE = "post_rate";

	private String valueRCConn;
	private String valueRCPowerStatus;
	private String valueRCUSBStatus;
	private String valueTemperature;
	private String valueHumidity;
	private String valuePostRate;

	private TextView txtRCConnStatus;
	private TextView txtRCPowerStatus;
	private TextView txtRCUSBStatus;
	private TextView txtTemperature;
	private TextView txtHumidity;

	private EditText editPostRate;

	private Button btnSetPostRate;
	private Button btnTurnOnRC;
	private Button btnTurnOffRC;
	private Button btnPlugInUSB;
	private Button btnPullOutUSB;
	private MainActivity mContainerActivity;

	public RemoteControlFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment RemoteControlFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RemoteControlFragment newInstance(String param1, String param2) {
		RemoteControlFragment fragment = new RemoteControlFragment();
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
			valueRCConn = getArguments().getString(ARG_RC_CONN_STATUS);
			valueRCPowerStatus = getArguments().getString(ARG_RC_POWER_STATUS);
			valueRCUSBStatus = getArguments().getString(ARG_RC_USB_STATUS);
			valueTemperature = getArguments().getString(ARG_TEMPERATURE);
			valueHumidity = getArguments().getString(ARG_HUMIDITY);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		Bundle args = new Bundle();
		args.putString(ARG_RC_CONN_STATUS, txtRCConnStatus.getText().toString());
		args.putString(ARG_RC_POWER_STATUS, txtRCPowerStatus.getText().toString());
		args.putString(ARG_RC_USB_STATUS, txtRCUSBStatus.getText().toString());
		args.putString(ARG_TEMPERATURE, txtTemperature.getText().toString());
		args.putString(ARG_HUMIDITY, txtHumidity.getText().toString());
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_remote_control, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		requestParam(ConfigParameter.SERVICE_PARAM_POST_RATE_RC);
	}

	private void initView(View view) {
		txtRCConnStatus = view.findViewById(R.id.txt_rc_conn_status);
		txtRCPowerStatus = view.findViewById(R.id.txt_rc_power_status);
		txtRCUSBStatus = view.findViewById(R.id.txt_rc_usb_status);
		txtTemperature = view.findViewById(R.id.txt_rc_temperature);
		txtHumidity = view.findViewById(R.id.txt_rc_humidity);

		editPostRate = view.findViewById(R.id.edit_rc_post_rate);

		btnSetPostRate = view.findViewById(R.id.btn_rc_post_rate_set);
		btnTurnOnRC = view.findViewById(R.id.btn_rc_turn_on);
		btnTurnOffRC = view.findViewById(R.id.btn_rc_turn_off);
		btnPlugInUSB = view.findViewById(R.id.btn_rc_usb_plug_in);
		btnPullOutUSB = view.findViewById(R.id.btn_rc_usb_pull_out);

		if (getArguments() != null) {
			txtRCConnStatus.setText(valueRCConn);
			txtRCPowerStatus.setText(valueRCPowerStatus);
			txtRCUSBStatus.setText(valueRCUSBStatus);
			txtTemperature.setText(valueTemperature);
			txtHumidity.setText(valueHumidity);
			editPostRate.setText(valuePostRate);
		}

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
					switch(v.getId()) {
						case R.id.btn_rc_post_rate_set:
							if (TextUtils.isEmpty(editPostRate.getText().toString())) {
								editPostRate.setError("No Empty!");
								return;
							}
							setParam(ConfigParameter.SERVICE_PARAM_POST_RATE_RC, Integer.parseInt(editPostRate.getText().toString()));
							break;

						case R.id.btn_rc_turn_on:
							mContainerActivity.mRemoteControl.RCTurnOn();
							break;

						case R.id.btn_rc_turn_off:
							mContainerActivity.mRemoteControl.RCTurnOff();
							break;

						case R.id.btn_rc_usb_plug_in:
							mContainerActivity.mRemoteControl.RCUSBPlugIn();
							break;

						case R.id.btn_rc_usb_pull_out:
							mContainerActivity.mRemoteControl.RCUSBPullOut();
							break;
					}
				}
			}
		};

		btnSetPostRate.setOnClickListener(listener);
		btnTurnOnRC.setOnClickListener(listener);
		btnTurnOffRC.setOnClickListener(listener);
		btnPlugInUSB.setOnClickListener(listener);
		btnPullOutUSB.setOnClickListener(listener);
	}

	private void initListener() {
		mContainerActivity = (MainActivity) getActivity();

		mContainerActivity.setRemoteControlListener(new RemoteControlListener() {
			@Override
			public void onPost(ConnStatus connStatus) {
				Hygrothermograph hygrothermograph = mContainerActivity.mRemoteControl.getHygrothermograph();
				txtRCConnStatus.setText(connStatus.toString());
				txtRCPowerStatus.setText(mContainerActivity.mRemoteControl.getRCPowerState().toString());
				txtRCUSBStatus.setText(mContainerActivity.mRemoteControl.getRCUSBCableStatus() == 1 ? "Plug In" : "Pull Out");
				float temperature = (hygrothermograph.getTemperature() - 1000) / 10f;
				txtTemperature.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
				txtHumidity.setText(String.valueOf(hygrothermograph.getHumidity() / 10f));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
				if (result == ServiceResult.SUCCESS) {
					if (paramIndex == ConfigParameter.SERVICE_PARAM_POST_RATE_RC) {
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