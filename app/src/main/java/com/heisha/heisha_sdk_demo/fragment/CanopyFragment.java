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

import com.heisha.heisha_sdk.Component.Canopy.CanopyState;
import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Component.EdgeComputing.Hygrothermograph;
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

	/*
	private static final String ARG_CANOPY_CONN_STATUS = "canopy_conn";
	private static final String ARG_CANOPY_STATUS = "canopy_status";
	private static final String ARG_OPEN_STATUS = "open_status";
	private static final String ARG_CLOSE_STATUS = "close_status";
	private static final String ARG_HYGROTHERMOGRAPH_CONN = "hygrothermograph_conn";
	private static final String ARG_TEM = "tem";
	private static final String ARG_HUM = "hum";
	private static final String ARG_POST_RATE = "post_rate";
	private static final String ARG_LIGHT_BRIGHTNESS = "light_brightness";
	private static final String ARG_DELAY_TIME = "delay_time";

	private String valueCanopyConn;
	private String valueCanopyStatus;
	private String valueOpenStatus;
	private String valueCloseStatus;
	private String valueHygrothermographConn;
	private String valueTem;
	private String valueHum;
	private String valuePostRate;
	private String valueLightBrightness;
	private String valueDelayTime;
	*/

	private TextView txtCanopyConnStatus;
	private TextView txtCanopyStatus;
	private TextView txtCanopyOpenStatus;
	private TextView txtCanopyCloseStatus;
	private TextView txtHygrothermographConnStatus;
	private TextView txtTem;
	private TextView txtHum;

	private EditText editPostRate;
	private EditText editLightBrightness;
	private EditText editDelayTime;

	private Button btnOpenCanopy;
	private Button btnCloseCanopy;
	private Button btnResetCanopy;
	private Button btnSetPostRate;
	private Button btnSetLightBrightness;
	private Button btnSetDelayTime;

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
		fragment.setArguments(args);
		return fragment;
	}

	public CanopyFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		if (getArguments() != null) {
			valueCanopyConn = getArguments().getString(ARG_CANOPY_CONN_STATUS);
			valueCanopyStatus = getArguments().getString(ARG_CANOPY_STATUS);
			valueOpenStatus = getArguments().getString(ARG_OPEN_STATUS);
			valueCloseStatus = getArguments().getString(ARG_CLOSE_STATUS);
			valueHygrothermographConn = getArguments().getString(ARG_HYGROTHERMOGRAPH_CONN);
			valueTem = getArguments().getString(ARG_TEM);
			valueHum = getArguments().getString(ARG_HUM);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
			valueLightBrightness = getArguments().getString(ARG_LIGHT_BRIGHTNESS);
			valueDelayTime = getArguments().getString(ARG_DELAY_TIME);
		}
		*/
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		/*
		Bundle args = new Bundle();
		args.putString(ARG_CANOPY_CONN_STATUS, txtCanopyConnStatus.getText().toString());
		args.putString(ARG_CANOPY_STATUS, txtCanopyStatus.getText().toString());
		args.putString(ARG_OPEN_STATUS, txtCanopyOpenStatus.getText().toString());
		args.putString(ARG_CLOSE_STATUS, txtCanopyCloseStatus.getText().toString());
		args.putString(ARG_HYGROTHERMOGRAPH_CONN, txtHygrothermographConnStatus.getText().toString());
		args.putString(ARG_TEM, txtTem.getText().toString());
		args.putString(ARG_HUM, txtHum.getText().toString());
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		args.putString(ARG_LIGHT_BRIGHTNESS, editLightBrightness.getText().toString());
		args.putString(ARG_DELAY_TIME, editDelayTime.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
		*/
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
		new Thread(new Runnable() {
			public void run() {
				requestParam(ConfigParameter.SERVICE_PARAM_POST_RATE_CANOPY);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestParam(ConfigParameter.SERVICE_PARAM_STRIP_LIGHT_BRIGHTNESS);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestParam(ConfigParameter.SERVICE_PARAM_CANOPY_DELAY_TIME);
			}
		}).start();
	}

	private void initView(View view) {
		txtCanopyConnStatus = view.findViewById(R.id.txt_canopy_conn_status);
		txtCanopyStatus = view.findViewById(R.id.txt_canopy_status);
		txtCanopyOpenStatus = view.findViewById(R.id.txt_canopy_open_switch_status);
		txtCanopyCloseStatus = view.findViewById(R.id.txt_canopy_close_switch_status);
		txtHygrothermographConnStatus = view.findViewById(R.id.txt_canopy_hygrothermograph_conn_status);
		txtTem = view.findViewById(R.id.txt_canopy_temp);
		txtHum = view.findViewById(R.id.txt_canopy_hum);

		editPostRate = view.findViewById(R.id.edit_canopy_post_rate);
		editLightBrightness = view.findViewById(R.id.edit_strip_light_brightness);
		editDelayTime = view.findViewById(R.id.edit_canopy_delay_time);

		btnSetPostRate = view.findViewById(R.id.btn_canopy_post_rate_set);
		btnSetLightBrightness = view.findViewById(R.id.btn_strip_light_brightness_set);
		btnSetDelayTime = view.findViewById(R.id.btn_canopy_delay_time_set);
		btnOpenCanopy = view.findViewById(R.id.btn_canopy_open);
		btnCloseCanopy = view.findViewById(R.id.btn_canopy_close);
		btnResetCanopy = view.findViewById(R.id.btn_canopy_reset);

		mContainerActivity = (MainActivity) getActivity();

		if (mContainerActivity.mCanopy != null) {
			txtCanopyConnStatus.setText(mContainerActivity.mCanopy.getConnectionState().toString());
			txtCanopyStatus.setText(mContainerActivity.mCanopy.getCanopyState().toString());
			txtCanopyOpenStatus.setText((mContainerActivity.mCanopy.getOpenedLimitSwitchStatus() == 1 ? "TRIGGER_YES" : "TRIGGER_NO"));
			txtCanopyCloseStatus.setText((mContainerActivity.mCanopy.getClosedLimitSwitchStatus() == 1 ? "TRIGGER_YES" : "TRIGGER_NO"));
			Hygrothermograph hygrothermograph = mContainerActivity.mCanopy.getHygrothermograph();
			txtHygrothermographConnStatus.setText(hygrothermograph.getConnStatus().toString());
			float temperature = hygrothermograph.getTemperature();
			txtTem.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
			txtHum.setText(String.valueOf(hygrothermograph.getHumidity()));
		}

		/*
		if (getArguments() != null) {
			txtCanopyConnStatus.setText(valueCanopyConn);
			txtCanopyStatus.setText(valueCanopyStatus);
			txtCanopyOpenStatus.setText(valueOpenStatus);
			txtCanopyCloseStatus.setText(valueCloseStatus);
			txtHygrothermographConnStatus.setText(valueHygrothermographConn);
			txtTem.setText(valueTem);
			txtHum.setText(valueHum);
			editPostRate.setText(valuePostRate);
			editLightBrightness.setText(valueLightBrightness);
			editDelayTime.setText(valueDelayTime);
		}
		*/

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
						case R.id.btn_strip_light_brightness_set:
							if (TextUtils.isEmpty(editLightBrightness.getText().toString())) {
								editLightBrightness.setError("No Empty!");
								return;
							}
							setParam(ConfigParameter.SERVICE_PARAM_STRIP_LIGHT_BRIGHTNESS, Integer.parseInt(editLightBrightness.getText().toString()));
							break;
						case R.id.btn_canopy_delay_time_set:
							if (TextUtils.isEmpty(editDelayTime.getText().toString())) {
								editDelayTime.setError("No Empty!");
								return;
							}
							setParam(ConfigParameter.SERVICE_PARAM_CANOPY_DELAY_TIME, Integer.parseInt(editDelayTime.getText().toString()));
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
		btnSetLightBrightness.setOnClickListener(listener);
		btnSetDelayTime.setOnClickListener(listener);
		btnOpenCanopy.setOnClickListener(listener);
		btnCloseCanopy.setOnClickListener(listener);
		btnResetCanopy.setOnClickListener(listener);
	}

	private void initListener() {

		mContainerActivity.setCanopyListener(new CanopyListener() {
			@Override
			public void onPost(ConnStatus connStatus, CanopyState canopyState) {
				txtCanopyConnStatus.setText(connStatus.toString());
				txtCanopyStatus.setText(canopyState.toString());
				txtCanopyOpenStatus.setText((mContainerActivity.mCanopy.getOpenedLimitSwitchStatus() == 1 ? "TRIGGER_YES" : "TRIGGER_NO"));
				txtCanopyCloseStatus.setText((mContainerActivity.mCanopy.getClosedLimitSwitchStatus() == 1 ? "TRIGGER_YES" : "TRIGGER_NO"));
				Hygrothermograph hygrothermograph = mContainerActivity.mCanopy.getHygrothermograph();
				txtHygrothermographConnStatus.setText(hygrothermograph.getConnStatus().toString());
				float temperature = hygrothermograph.getTemperature();
				txtTem.setText((temperature > -40f && temperature < 100f) ? String.valueOf(temperature) : "N/A");
				txtHum.setText(String.valueOf(hygrothermograph.getHumidity()));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_SHORT).show();
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