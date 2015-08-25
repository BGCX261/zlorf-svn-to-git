package org.zlorf.view;

import java.awt.Graphics;

/**
 *
 * @author Mat Jaggard
 */
public interface Renderable
{

	/**
	 * Render the item to a screen or screen area.
	 * @param area The area of screen to display the contents on.
	 */
	public void render(RenderArea area, Graphics graphics);

	/**
	 * Render the item to a screen area in low quality or low detail.
	 * @param area The area of screen to display the contents on.
	 */
	public void renderThumb(RenderArea area, Graphics graphics);
}
