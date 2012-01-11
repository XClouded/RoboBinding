/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package sample.robobinding.customwidget;

import org.robobinding.binder.Binder;
import org.robobinding.customwidget.BindableView;
import org.robobinding.customwidget.CustomBindingAttributeMappings;
import org.robobinding.viewattribute.view.OnClickAttribute;

import sample.robobinding.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class NavigationBar extends RelativeLayout implements BindableView<NavigationBar>
{
	private final Context context;
	private Button leftButton;
	private Button rightButton;
	private final AttributeSet attrs;
	
	public NavigationBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		this.attrs = attrs;
	}

	private void initializeViews()
	{
		leftButton = (Button)findViewById(R.id.left_button);
		rightButton = (Button)findViewById(R.id.right_button);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
		boolean leftButtonVisible = a.getBoolean(R.styleable.NavigationBar_leftButtonVisible, true);
		boolean rightButtonVisible = a.getBoolean(R.styleable.NavigationBar_rightButtonVisible, true);
		String leftButtonText = a.getString(R.styleable.NavigationBar_leftButtonLabel);
		String rightButtonText = a.getString(R.styleable.NavigationBar_rightButtonLabel);
		a.recycle();
		
		leftButton.setVisibility(leftButtonVisible ? View.VISIBLE : View.INVISIBLE);
		rightButton.setVisibility(rightButtonVisible ? View.VISIBLE : View.INVISIBLE);
		leftButton.setText(leftButtonText);
		rightButton.setText(rightButtonText);
	}

	private boolean firstMapping;
	
	@Override
	public void mapBindingAttributes(CustomBindingAttributeMappings<NavigationBar> mappings)
	{
		if (!firstMapping)
		{
			firstMapping = true;
			return;
		}
		
		mappings.mapCommandAttribute(leftButton, OnClickAttribute.class, "leftButtonAction");
		mappings.mapCommandAttribute(rightButton, OnClickAttribute.class, "rightButtonAction");
	}

	public void setPresentationModel(Object presentationModel)
	{
		Binder.bindViewAndAttachToRoot(context, R.layout.navigation_bar, presentationModel, this);
		
		initializeViews();
	}
}
