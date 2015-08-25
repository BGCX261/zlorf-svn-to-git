package org.zlorf.view;

import java.awt.Window;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;

/**
 *
 * @author JaggardM
 */
public class ZlorfFullScreenStrategy implements FullScreenStrategy
{

	private Window window;

	public ZlorfFullScreenStrategy(Window window)
	{
		if (window != null)
		{
			this.window = window;
		}
		else
		{
			throw new IllegalArgumentException("Window must not be null");
		}
	}

	@Override
	public void enterFullScreenMode()
	{
		window.getGraphicsConfiguration().getDevice().setFullScreenWindow(window);
	}

	@Override
	public void exitFullScreenMode()
	{
		window.getGraphicsConfiguration().getDevice().setFullScreenWindow(null);
	}

	@Override
	public boolean isFullScreenMode()
	{
		return window.getGraphicsConfiguration().getDevice().getFullScreenWindow() != null;
	}
}
