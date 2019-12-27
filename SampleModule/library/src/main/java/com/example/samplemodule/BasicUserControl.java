package com.example.samplemodule;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.artech.base.controls.IGxControlRuntime;
import com.artech.base.metadata.ActionDefinition;
import com.artech.base.metadata.ActionParameter;
import com.artech.base.metadata.expressions.Expression;
import com.artech.base.metadata.layout.LayoutItemDefinition;
import com.artech.controls.IGxEdit;
import com.artech.ui.Coordinator;
import com.example.genexusmodule.R;

@SuppressLint("ViewConstructor")
public class BasicUserControl extends AppCompatTextView implements IGxEdit,
														  IGxControlRuntime {
	final static String NAME = "BasicUserControl";
	private final static String METHOD_SET_NAME = "SetName";
	private final static String EVENT_ON_TAP = "OnTap";
	private final Coordinator mCoordinator;

	private String mName;
	private int tapCount;

	public BasicUserControl(Context context, Coordinator coordinator, LayoutItemDefinition definition) {
		super(context);
		mCoordinator = coordinator;
		tapCount = 0;
		setOnClickListener(mOnClickListener);
	}

	@Override
	public String getGxValue() {
		return mName;
	}

	@Override
	public void setGxValue(String value) {
		setName(value);
	}

	@Override
	public String getGxTag() {
		return getTag().toString();
	}

	@Override
	public void setGxTag(String tag) {
		setTag(tag);
	}

	@Override
	public void setValueFromIntent(Intent data) {

	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public IGxEdit getViewControl() {
		return null;
	}

	@Override
	public IGxEdit getEditControl() {
		return null;
	}

	@Override
	public Expression.Value callMethod(String methodName, List<Expression.Value> parameters) {
		if (METHOD_SET_NAME.equals(methodName)) {
			String name = parameters.get(0).coerceToString();
			setName(name);
		}
		return null;
	}

	public void setName(String name) {
		mName = name;
		setText(getContext().getString(R.string.welcome_message, name));
	}

	public void runOnTapEvent() {
		ActionDefinition actionDef = mCoordinator.getControlEventHandler(this, EVENT_ON_TAP);

		for (ActionParameter param : actionDef.getEventParameters()) {
			String paramName = param.getValueDefinition().getName();
			mCoordinator.setValue(paramName, tapCount);
		}

		mCoordinator.runControlEvent(this, EVENT_ON_TAP);
	}

	private final View.OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			tapCount++;
			runOnTapEvent();
		}
	};
}
