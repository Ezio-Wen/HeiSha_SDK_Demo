package com.heisha.heisha_sdk_demo.fragment;

import android.os.Bundle;

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
import com.heisha.heisha_sdk.Manager.HSSDKManager;
import com.heisha.heisha_sdk.Manager.ServiceCode;
import com.heisha.heisha_sdk.Manager.ServiceResult;
import com.heisha.heisha_sdk_demo.Listener.ControlCenterListener;
import com.heisha.heisha_sdk_demo.MainActivity;
import com.heisha.heisha_sdk_demo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ControlCenterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlCenterFragment extends Fragment {

	private TextView txtServerConnStatus;
	private TextView txtDeviceConnStatus;
	private TextView txtDeviceName;
	private TextView txtParamVersion;
	private TextView txtParamTotal;
	private TextView txtReconnTimes;

	private Button btnSystemReset;
	private Button btnDisconnect;

	private MainActivity mContainerActivity;

	public ControlCenterFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ControlCenterFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ControlCenterFragment newInstance(String param1, String param2) {
		ControlCenterFragment fragment = new ControlCenterFragment();
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
//			mParam1 = getArguments().getString(ARG_PARAM1);
//			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_control_center, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		requestParam(ConfigParameter.SERVICE_PARAM_VERSION);
	}

	private void initView(View view) {
		txtServerConnStatus = view.findViewById(R.id.txt_server_conn_status);
		txtDeviceConnStatus = view.findViewById(R.id.txt_device_conn_status);
		txtDeviceName = view.findViewById(R.id.txt_device_name);
		txtReconnTimes = view.findViewById(R.id.txt_reconn_times);
		txtParamVersion = view.findViewById(R.id.txt_param_version);
		txtParamTotal = view.findViewById(R.id.txt_param_total);

		btnSystemReset = view.findViewById(R.id.btn_system_reset);
		btnDisconnect = view.findViewById(R.id.btn_disconnect);

		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
					switch(v.getId()) {
						case R.id.btn_system_reset:
							mContainerActivity.mControlCenter.resetSystem();
							break;
						case R.id.btn_disconnect:
							break;
					}
				}
			}
		};

		btnSystemReset.setOnClickListener(listener);
		btnDisconnect.setOnClickListener(listener);
	}

	private void initListener() {
		mContainerActivity = (MainActivity) getActivity();

		txtServerConnStatus.setText(mContainerActivity.isServerConnected ? ConnStatus.CONNECTED.toString() : ConnStatus.DISCONNECTED.toString());
		txtDeviceConnStatus.setText(mContainerActivity.isDeviceConnected ? ConnStatus.CONNECTED.toString() : ConnStatus.DISCONNECTED.toString());
		txtDeviceName.setText(mContainerActivity.mDeviceName);
		txtReconnTimes.setText(String.valueOf(mContainerActivity.reconnectionTimes));

		mContainerActivity.setControlCerterListener(new ControlCenterListener() {
			@Override
			public void onServerConnectionChanged(boolean connectionStatus, int connectionTimes) {
				txtServerConnStatus.setText(connectionStatus ? ConnStatus.CONNECTED.toString() : ConnStatus.DISCONNECTED.toString());
				txtReconnTimes.setText(String.valueOf(connectionTimes));
			}

			@Override
			public void onDeviceConnectionChanged(boolean connectionStatus, String deviceName) {
				txtDeviceConnStatus.setText(connectionStatus ? ConnStatus.CONNECTED.toString() : ConnStatus.DISCONNECTED.toString());
				txtDeviceName.setText(deviceName);
			}

			@Override
			public void onGetConfigVersionInfo(int version, int paramNum) {
				txtParamVersion.setText(String.valueOf(version));
				txtParamTotal.setText(String.valueOf(paramNum));
			}

			@Override
			public void onOperationResult(ServiceCode serviceCode, ServiceResult serviceResult) {
				Toast.makeText(mContainerActivity, serviceCode.toString() + " " + serviceResult.toString(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onGetParam(ServiceResult result, ConfigParameter paramIndex, int value) {
			}

			@Override
			public void onSetParam(ServiceResult serviceResult, ConfigParameter configParameter, ConfigFailReason configFailReason) {
			}
		});
	}

	private void requestParam(ConfigParameter parameter) {
		if (mContainerActivity.isServerConnected && mContainerActivity.isDeviceConnected) {
			mContainerActivity.mControlCenter.getConfigParameter(parameter);
		}
	}
}