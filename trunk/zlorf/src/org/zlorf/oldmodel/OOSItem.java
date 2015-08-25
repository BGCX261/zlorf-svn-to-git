package org.zlorf.oldmodel;

import org.zlorf.view.Renderable;
import java.awt.Container;

/**
 * The basic class for an Order of Service item. All items in an
 * Order of Service should be subclasses of this one. It defines
 * basic functions to display the item, etc. It can contain other
 * OOSItems.
 * @author Mat Jaggard
 */
public abstract class OOSItem implements Renderable
{
	  /**
	   * A string containing the name of the OOS Item.
	   */
	  String name;

	  /*
	   * TODO: Add any more options / defaults.
	   */

	  public String getName()
	  {
			 return name;
	  }

	  public void setName(String name)
	  {
			 this.name = name;
	  }

	@Override
		public String toString()
		{
			return name;
		}
}
