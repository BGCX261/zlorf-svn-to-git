package org.zlorf.view;

import java.awt.Graphics;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author Mat Jaggard
 */
public class RenderHelper implements RenderArea
{

	private Deque<Renderable> paintListeners = new LinkedBlockingDeque<Renderable>();

	public RenderHelper()
	{
		super();
	}

	public void addLastPaintListener(Renderable listener)
	{
		paintListeners.addLast(listener);
	}

	public void addFirstPaintListener(Renderable listener)
	{
		paintListeners.addFirst(listener);
	}

	public void removePaintListener(Renderable listener)
	{
		while (paintListeners.remove(listener))
		{
			//Keep removing until there aren't any more.
		}
	}

	public void paint(RenderArea area, Graphics graphics, boolean goodQuality)
	{
		if (goodQuality)
		{
			for (Renderable r : paintListeners)
			{
				r.render(area, graphics);
			}
		}
		else
		{
			for (Renderable r : paintListeners)
			{
				r.renderThumb(area, graphics);
			}
		}
	}
}
