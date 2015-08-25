package org.zlorf.oldmodel;

import java.awt.Graphics;
import org.zlorf.view.RenderArea;
/**
 * The standard class for containing other OOSItems. It is normally
 * used at the top of a tree and saved or loaded to a file. It does
 * not have any screen behaviour its self. Rendering does not change
 * the Container being rendered to.
 * @author Mat Jaggard
 */
public class OOS extends OOSItem
{

	  public
	  void render(RenderArea area, Graphics graphics)
	  {
			 throw new UnsupportedOperationException("Not supported yet.");
	  }

	  public
	  void renderThumb(RenderArea area, Graphics graphics)
	  {
			 throw new UnsupportedOperationException("Not supported yet.");
	  }
}