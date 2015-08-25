package org.zlorf.view.options;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mat Jaggard
 */
public class ScreenOption
{
	private ScreenMode current = null;
	private Set<ScreenMode> disallowed = new HashSet<ScreenMode>();
	private String name = "";

	public ScreenOption(GraphicsDevice gd)
	{
		if (gd != null)
		{
			if (!gd.isFullScreenSupported())
			{
				disallowed.add(ScreenMode.FULLSCREEN);
			}
			name = gd.getIDstring();
		}
		else
		{
			name = "Fake Device";
		}
		if (GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().equals(gd))
		{
			current = ScreenMode.CONTROL;
		}
		else
		{
			current = ScreenMode.WINDOWED;
		}
	}

	public ScreenOption(GraphicsDevice gd, ScreenMode startMode)
	{
		this(gd);
		current = startMode;
	}

	public ScreenOption(ScreenMode startMode)
	{
		this(null, startMode);
	}

	public boolean allowed(ScreenMode toCheck)
	{
		return !(disallowed.contains(toCheck));
	}

	public ScreenMode getCurrentScreenMode()
	{
		return current;
	}

	public void setCurrentScreenMode(ScreenMode toSet)
	{
		current = toSet;
	}

	public String getName()
	{
		return name;
	}
}
