package org.zlorf.view;

/**
 *
 * @author JaggardM
 */
public interface RenderArea
{
	public void addFirstPaintListener(Renderable listener);
	public void addLastPaintListener(Renderable listener);
	public void removePaintListener(Renderable listener);
}
