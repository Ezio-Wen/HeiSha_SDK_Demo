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

import com.heisha.heisha_sdk.Component.ConnStatus;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigFailReason;
import com.heisha.heisha_sdk.Component.ControlCenter.ConfigParameter;
import com.heisha.heisha_sdk.Component.PositionBar.PositionBarState;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk_demo.Listener.PositionBarListener;
import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PositionBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PositionBarFragment extends Fragment {

	/*
	private static final String ARG_POSBAR_CONN_STATUS = "posbar_conn";
	private static final String ARG_POSBAR_STATUS = "posbar_status";
	private static final String ARG_SWITCH_STATUS = "switch_status";
	private static final String ARG_SWITCH_FAULT_STATUS = "switch_fault_status";
	private static final String ARG_MOTOR_FAULT_STATUS = "motor_fault_status";
	private static final String ARG_VIBRATION = "vibration";
	private static final String ARG_TILT = "tilt";
	private static final String ARG_POST_RATE = "post_rate";

	private String valuePosbarConn;
	private String valuePosbarStatus;
	private String valueSwitchStatus;
	private String valueSwitchFaultStatus;
	private String valueMotorFaultStatus;
	private String valueVibration;
	private String valueTilt;
	private String valuePostRate;
	*/

	private TextView txtPosbarConnStatus;
	private TextView txtPosbarStatus;
	private TextView txtSwitchStatus;
	private TextView txtSwitchFaultStatus;
	private TextView txtMotorFaultStatus;
	private TextView txtVibration;
	private TextView txtTilt;

	private EditText editPostRate;

	private Button btnReleasePosbar;
	private Button btnTightenPosbar;
	private Button btnResetPosbar;
	private Button btnSetPostRate;
	private MainActivity mContainerActivity;

	public PositionBarFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment PositionBarFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PositionBarFragment newInstance(String param1, String param2) {
		PositionBarFragment fragment = new PositionBarFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		if (getArguments() != null) {
			valuePosbarConn = getArguments().getString(ARG_POSBAR_CONN_STATUS);
			valuePosbarStatus = getArguments().getString(ARG_POSBAR_STATUS);
			valueSwitchStatus = getArguments().getString(ARG_SWITCH_STATUS);
			valueSwitchFaultStatus = getArguments().getString(ARG_SWITCH_FAULT_STATUS);
			valueMotorFaultStatus = getArguments().getString(ARG_MOTOR_FAULT_STATUS);
			valueVibration = getArguments().getString(ARG_VIBRATION);
			valueTilt = getArguments().getString(ARG_TILT);
			valuePostRate = getArguments().getString(ARG_POST_RATE);
		}
		*/
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		/*
		Bundle args = new Bundle();
		args.putString(ARG_POSBAR_CONN_STATUS, txtPosbarConnStatus.getText().toString());
		args.putString(ARG_POSBAR_STATUS, txtPosbarStatus.getText().toString());
		args.putString(ARG_SWITCH_STATUS, txtSwitchStatus.getText().toString());
		args.putString(ARG_SWITCH_FAULT_STATUS, txtSwitchFaultStatus.getText().toString());
		args.putString(ARG_MOTOR_FAULT_STATUS, txtMotorFaultStatus.getText().toString());
		args.putString(ARG_VIBRATION, txtVibration.getText().toString());
		args.putString(ARG_TILT, txtTilt.getText().toString());
		args.putString(ARG_POST_RATE, editPostRate.getText().toString());
		if (!this.isStateSaved())
			this.setArguments(args);
		*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_position_bar, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		requestParam(ConfigParameter.SERVICE_PARAM_POST_RATE_POSBAR);
	}

	private void initView(View view) {
		txtPosbarConnStatus = view.findViewById(R.id.txt_position_bar_conn_status);
		txtPosbarStatus = view.findViewById(R.id.txt_position_bar_status);
		txtSwitchStatus = view.findViewById(R.id.txt_limit_switch_status);
		txtSwitchFaultStatus = view.findViewById(R.id.txt_limit_switch_fault_status);
		txtMotorFaultStatus = view.findViewById(R.id.txt_motor_fault_status);
		txtVibration = view.findViewById(R.id.txt_vibration);
		txtTilt = view.findViewById(R.id.txt_tilt);

		editPostRate = view.findViewById(R.id.edit_position_bar_post_rate);

		btnSetPostRate = view.findViewById(R.id.btn_position_bar_post_rate_set);
		btnReleasePosbar = view.findViewById(R.id.btn_position_bar_release);
		btnTightenPosbar = view.findViewById(R.id.btn_position_bar_tighten);
		btnResetPosbar = view.findViewById(R.id.btn_position_bar_reset);

		mContainerActivity = (MainActivity) getActivity();

		if (mContainerActivity.mPositionBar != null) {
			txtPosbarConnStatus.setText(mContainerActivity.mPositionBar.getConnectionState().toString());
			txtPosbarStatus.setText(mContainerActivity.mPositionBar.getPositionBarState().toString());

			byte barLimitSwitchStateSet = mContainerActivity.mPositionBar.getBarLimitSwitchStateSet();
			byte[] switchStatus = {0, 0, 0, 0, 0, 0, 0, 0};
			for (int i = 0; i < switchStatus.length; i++) {
				switchStatus[switchStatus.length - 1 - i] = (byte) (barLimitSwitchStateSet >> i & 0x01);
			}
			txtSwitchStatus.setText(Arrays.toString(switchStatus));

			byte barLimitSwitchFaultStateSet = mContainerActivity.mPositionBar.getBarLimitSwitchFaultStateSet();
			for (int i = 0; i < switchStatus.length; i++) {
				switchStatus[switchStatus.length - 1 - i] = (byte) (barLimitSwitchFaultStateSet >> i & 0x01);
			}
			txtSwitchFaultStatus.setText(Arrays.toString(switchStatus));

			byte motorFaultStateSet = mContainerActivity.mPositionBar.getMotorFaultStateSet();
			byte[] motorFaultStatus = {0, 0, 0, 0};
			for (int i = 0; i < motorFaultStatus.length; i++) {
				motorFaultStatus[motorFaultStatus.length - 1 - i] = (byte) (motorFaultStateSet >> i & 0x01);
			}
			txtMotorFaultStatus.setText(Arrays.toString(motorFaultStatus));

			txtVibration.setText(String.valueOf(mContainerActivity.mPositionBar.getVibration()));
			txtTilt.setText(String.valueOf(mContainerActivity.mPositionBar.getTilt()));
		}

		/*
		if (getArguments() != null) {
			txtPosbarConnStatus.setText(valuePosbarConn);
			txtPosbarStatus.setText(valuePosbarStatus);
			txtSwitchStatus.setText(valueSwitchStatus);
			txtSwitchFaultStatus.setText(valueSwitchFaultStatus);
			txtMotorFaultStatus.setText(valueMotorFaultStatus);
			txtVibration.setText(valueVibration);
			txtTilt.setText(valueTilt);
			editPostRate.setText(valuePostRate);
		}
		*/

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
					switch(v.getId()) {
						case R.id.btn_position_bar_post_rate_set:
							if (TextUtils.isEmpty(editPostRate.getText().toString())) {
								editPostRate.setError("No Empty!");
								return;
							}
							setParam(ConfigParameter.SERVICE_PARAM_POST_RATE_POSBAR, Integer.parseInt(editPostRate.getText().toString()));
							break;
						case R.id.btn_position_bar_release:
							mContainerActivity.mPositionBar.startReleasing();
							break;
						case R.id.btn_position_bar_tighten:
							mContainerActivity.mPositionBar.startTightening();
							break;
						case R.id.btn_position_bar_reset:
							mContainerActivity.mPositionBar.resetState();
							break;
					}
				}
			}
		};

		btnSetPostRate.setOnClickListener(listener);
		btnReleasePosbar.setOnClickListener(listener);
		btnTightenPosbar.setOnClickListener(listener);
		btnResetPosbar.setOnClickListener(listener);
	}

	private void initListener() {

		mContainerActivity.setPositionBarListener(new PositionBarListener() {
			@Override
			public void onPost(ConnStatus connStatus, PositionBarState positionBarState) {
				txtPosbarConnStatus.setText(connStatus.toString());
				txtPosbarStatus.setText(positionBarState.toString());

				byte barLimitSwitchStateSet = mContainerActivity.mPositionBar.getBarLimitSwitchStateSet();
				byte[] switchStatus = {0, 0, 0, 0, 0, 0, 0, 0};
				for (int i = 0; i < switchStatus.length; i++) {
					switchStatus[switchStatus.length - 1 - i] = (byte) (barLimitSwitchStateSet >> i & 0x01);
				}
				txtSwitchStatus.setText(Arrays.toString(switchStatus));

				byte barLimitSwitchFaultStateSet = mContainerActivity.mPositionBar.getBarLimitSwitchFaultStateSet();
				for (int i = 0; i < switchStatus.length; i++) {
					switchStatus[switchStatus.length - 1 - i] = (byte) (barLimitSwitchFaultStateSet >> i & 0x01);
				}
				txtSwitchFaultStatus.setText(Arrays.toString(switchStatus));

				byte motorFaultStateSet = mContainerActivity.mPositionBar.getMotorFaultStateSet();
				byte[] motorFaultStatus = {0, 0, 0, 0};
				for (int i = 0; i < motorFaultStatus.length; i++) {
					motorFaultStatus[motorFaultStatus.length - 1 - i] = (byte) (motorFaultStateSet >> i & 0x01);
				}
				txtMotorFaultStatus.setText(Arrays.toString(motorFaultStatus));

				txtVibration.setText(String.valueOf(mContainerActivity.mPositionBar.getVibration()));
				txtTilt.setText(String.valueOf(mContainerActivity.mPositionBar.getTilt()));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
				if (result == ServiceResult.SUCCESS) {
					if (paramIndex == ConfigParameter.SERVICE_PARAM_POST_RATE_POSBAR) {
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