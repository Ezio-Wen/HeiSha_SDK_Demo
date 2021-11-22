package com.heisha.heisha_sdk_demo.utils;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heisha.heisha_sdk_demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Administrator
 * @version 1.0
 * @date 2021/11/19 10:37
 */
public class ToolBarUtil {
	private List<TextView> mTextViewList = new ArrayList<>();

	public void createToolBar(LinearLayout layout, String[] toolBarTitles) {
		for (int i = 0; i < toolBarTitles.length; i++) {
			TextView textView = (TextView) View.inflate(layout.getContext(), R.layout.inflate_toolbar_btn, null);
			textView.setText(toolBarTitles[i]);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
			layout.addView(textView, layoutParams);

			mTextViewList.add(textView);

			final int finalI = i;
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mOnToolBarClickListener.onToolBarClick(finalI);
				}
			});
		}
	}

	public interface OnToolBarClickListener {
		void onToolBarClick(int position);

	}

	private OnToolBarClickListener mOnToolBarClickListener;

	public void setOnToolBarClickListener(OnToolBarClickListener onToolBarClickListener) {
		mOnToolBarClickListener = onToolBarClickListener;
	}

	public void changeColor(int position) {
		for (TextView tv : mTextViewList) {
			tv.setSelected(false);
		}
		mTextViewList.get(position).setSelected(true);
	}
}
