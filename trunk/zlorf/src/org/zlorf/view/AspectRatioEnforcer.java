package org.zlorf.view;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 *
 * @author Mat Jaggard
 */
public class AspectRatioEnforcer extends ComponentAdapter
{

	private final float aspectRatio;

	public AspectRatioEnforcer(float theAspectRatio)
	{
		aspectRatio = theAspectRatio;
	}

	public float getAspectRatio()
	{
		return aspectRatio;
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		Component theComponent = e.getComponent();
		int width = theComponent.getWidth();
		int height = theComponent.getHeight();
		float currentAspectRatio = (float) width / height;

		if (currentAspectRatio > aspectRatio)
		{
			width = (int) (height * aspectRatio);
		}
		else
		{
			height = (int) (width / aspectRatio);
		}
		theComponent.removeComponentListener(this);
		//theComponent.setVisible(false);
		theComponent.setSize(width, height);
		//theComponent.setVisible(true);
		theComponent.addComponentListener(this);
	}
}
