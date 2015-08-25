package org.zlorf.controller;

import java.beans.PropertyVetoException;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zlorf.model.ColourItem;
import org.zlorf.view.RenderArea;
import org.zlorf.view.ZlorfBasicView;

/**
 *
 * @author JaggardM
 */
public class MainController
{
	//TODO: Separate the required functions in ZlorfBasicView into an interface. (Ditto this class)
	//THIS CLASS MUST MAKE A NEW THREAD FOR ANY LONG RUNNING PROCESSES.
	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
	private ColourItem blankColourItem = null;
	private Set<RenderArea> displays = new HashSet<RenderArea>();

	public MainController()
	{
	}

	/*
	public MainController(Set<RenderArea> displays)
	{
		this.displays = displays;
	}
	*/

	public void blankButton(boolean pressed) throws PropertyVetoException
	{
		if (blankColourItem == null)
		{
			blankColourItem = new ColourItem();
			blankColourItem.setFirstColour("#000000");
		}
		for (RenderArea display : displays)
		{
			if (pressed)
			{
				LOG.info("About to add paint listener");
				display.addLastPaintListener(blankColourItem);
			}
			else
			{
				LOG.info("About to remove paint listener");
				display.removePaintListener(blankColourItem);
			}
		}
	}

	public void setupDisplays(ZlorfBasicView zbv)
	{
		int numberOfPreviewScreens = 0;
		try
		{
			numberOfPreviewScreens = Options_OLD.getBestOptionInt(Options_OLD.SCREEN_OPTIONS, Options_OLD.NUMBER_OF_PREVIEW_SCREENS);
		}
		catch (InvalidOptionException ioe)
		{
			LOG.warn("Unable to find Number of Preview Screens at startup, assuming zero.", ioe);
		}
		for (int i = 0; i < numberOfPreviewScreens; i++)
		{
			RenderArea preview = zbv.createPreview();
			displays.add(preview);
		}
	}

	public void addDisplay(RenderArea ra)
	{
		displays.add(ra);
	}
}
