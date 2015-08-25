package org.zlorf.controller;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Class to allow loading and manipulation of images.
 * @author Mat Jaggard
 */
public class ImageUtil
{
	  public static BufferedImage loadImage(String ref)
	  {
			 BufferedImage bimg = null;
			 try
			 {
					bimg = ImageIO.read(new File(ref));
			 }
			 catch (Exception e)
			 {
					e.printStackTrace();
			 }
			 return bimg;
	  }

	  public static Dimension findNewDimensions(int width, int height, int maxWidth, int maxHeight)
	  {
			 float currentAspectRatio = (float) width / height;
			 float maxAspectRatio = (float) maxWidth / maxHeight;
			 int newWidth = maxWidth;
			 int newHeight = maxHeight;
			 if (currentAspectRatio > maxAspectRatio)
			 {
					newHeight = (int) (maxWidth / currentAspectRatio);
			 }
			 else
			 {
					newWidth = (int) (maxHeight * currentAspectRatio);
			 }
			 return new Dimension(newWidth, newHeight);
	  }

	  /**
	   * We do not allow instances of this class
	   */
	  private ImageUtil()
	  {
	  }
}
