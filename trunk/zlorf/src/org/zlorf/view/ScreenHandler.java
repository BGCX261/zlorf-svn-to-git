package org.zlorf.view;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Mat Jaggard
 */
public class ScreenHandler extends LinkedList<Screen>
{
	  private static ScreenHandler theInstance = new ScreenHandler();
	  private GraphicsEnvironment ge;

	  public static ScreenHandler getScreenHandler()
	  {
			 return theInstance;
	  }

	  private ScreenHandler()
	  {
			 ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			 GraphicsDevice[] gs = ge.getScreenDevices();

			 // Get size of each screen
			 int screenWidth = 0;
			 int screenHeight = 0;
			 for (int i = 0; i < gs.length; i++)
			 {
					DisplayMode dm = gs[i].getDisplayMode();
					screenWidth = dm.getWidth();
					screenHeight = dm.getHeight();
					Screen screen = new Screen();
					screen.setHeight(screenHeight);
					screen.setWidth(screenWidth);
					screen.setName(gs[i].getIDstring());
					this.add(screen);
			 }
	  }

	  public int getWidestScreenWidth()
	  {
			 int biggest = 0;
			 Iterator iterator = this.iterator();
			 while (iterator.hasNext())
			 {
					Screen screen = (Screen) iterator.next();
					if (screen.getWidth() > biggest)
					{
						biggest = screen.getWidth();
					}
			 }
			 return biggest;
	  }

	  public int getHeighestScreenHeight()
	  {
			 int biggest = 0;
			 Iterator iterator = this.iterator();
			 while (iterator.hasNext())
			 {
					Screen screen = (Screen) iterator.next();
					if (screen.getHeight() > biggest)
					{
						biggest = screen.getHeight();
					}
			 }
			 return biggest;
	  }

	  public float getAnAspectRatio()
	  {
			 return (float) this.getFirst().getWidth() / this.getFirst().getHeight();
	  }
}
