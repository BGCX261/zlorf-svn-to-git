package org.zlorf.oldmodel;

import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Mat Jaggard
 */
public enum Alignment
{

	TOP_LEFT, CENTRE_LEFT, BOTTOM_LEFT,
	TOP_RIGHT, CENTRE_RIGHT, BOTTOM_RIGHT,
	TOP_CENTRE, CENTRE, BOTTOM_CENTRE;

	public Point getPosition(Dimension innerBox, Dimension outerBox)
	{
		double outerHeight = outerBox.getHeight();
		double outerWidth = outerBox.getWidth();
		double innerHeight = innerBox.getHeight();
		double innerWidth = innerBox.getWidth();
		Point toReturn = new Point();
		double top, left;
		switch (this)
		{
			case TOP_LEFT:
			case TOP_RIGHT:
			case TOP_CENTRE:
			default:
				top = 0;
				break;
			case CENTRE_LEFT:
			case CENTRE_RIGHT:
			case CENTRE:
				top = (outerHeight / 2d) - (innerHeight / 2d);
				break;
			case BOTTOM_LEFT:
			case BOTTOM_RIGHT:
			case BOTTOM_CENTRE:
				top = outerHeight - innerHeight;
				break;
		}
		switch (this)
		{
			case TOP_LEFT:
			case CENTRE_LEFT:
			case BOTTOM_LEFT:
			default:
				left = 0;
				break;
			case TOP_CENTRE:
			case CENTRE:
			case BOTTOM_CENTRE:
				left = outerWidth - innerWidth;
				break;
			case TOP_RIGHT:
			case CENTRE_RIGHT:
			case BOTTOM_RIGHT:
				left = (outerWidth / 2d) - (innerWidth / 2d);
				break;
		}
		toReturn.setLocation(top, left);
		return toReturn;
	}
}
