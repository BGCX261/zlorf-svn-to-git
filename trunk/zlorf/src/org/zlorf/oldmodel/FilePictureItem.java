package org.zlorf.oldmodel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zlorf.controller.ImageUtil;
import org.zlorf.view.RenderArea;

/**
 * A class to represent an image that has the data in an external file.
 * @author Mat Jaggard
 */
public class FilePictureItem extends PictureItem
{
	//private int height,  width;

	private String location;
	private BufferedImage img;
	private Alignment alignment = Alignment.CENTRE;
	private static final Logger LOG = LoggerFactory.getLogger(FilePictureItem.class);

	public FilePictureItem(String loc)
	{
		location = loc;
		img = null;
	}

	public void render(RenderArea area, Graphics graphics)
	{
		if (area instanceof Container)
		{
			Container cArea = (Container) area;
			loadImage();
			Dimension imageSize = ImageUtil.findNewDimensions(img.getWidth(), img.getHeight(), cArea.getWidth(), cArea.getHeight());
			Point topLeftPosition = alignment.getPosition(imageSize, cArea.getSize());
			((Graphics2D) graphics).drawImage(img, topLeftPosition.x, topLeftPosition.y, imageSize.width, imageSize.height, Color.BLACK, null);
		}
		else
		{
			LOG.error("An instance of RenderArea is not an instance of Container - WHY?");
		}
	}

	private void loadImage()
	{
		if (img == null)
		{
			img = ImageUtil.loadImage(location);
		}

	}

	public void renderThumb(RenderArea area, Graphics graphics)
	{
		render(area, graphics);
	}
}
