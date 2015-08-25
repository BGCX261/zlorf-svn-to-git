package org.zlorf.controller;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zlorf.view.ScreenHandler;
import org.zlorf.view.options.ScreenOption;
import org.zlorf.view.options.ScreenMode;

/**
 * @author Mat Jaggard
 */
public class Options_OLD
{

	private static final Logger LOG = LoggerFactory.getLogger(Options_OLD.class);
	public static final String ASPECT_RATIO = "aspect_ratio";
	public static final String SCREEN_OPTIONS = "screen";
	public static final String NUMBER_OF_PREVIEW_SCREENS = "number_of_preview_screens";
	public static final String SCREEN_NAME_MODE_MAP = "screen/screen_name_mode_map";
	public static final float USE_SCREEN = -1;

	public static String getBestOption(String optionPath, String optionName)
	{
		String toReturn = OptionLevel.FILE.getPreferenceNode(optionPath).get(optionName, null);
		if (toReturn != null)
		{
			return toReturn;
		}
		else
		{
			toReturn = OptionLevel.USER.getPreferenceNode(optionPath).get(optionName, null);
			if (toReturn != null)
			{
				return toReturn;
			}
			else
			{
				toReturn = OptionLevel.SYSTEM.getPreferenceNode(optionPath).get(optionName, null);
				return toReturn;
			}
		}
	}

	public static int getBestOptionInt(String optionPath, String optionName) throws InvalidOptionException
	{
		String option = getBestOption(optionPath, optionName);
		if (option == null)
		{
			throw new InvalidOptionException("No option found " + optionName + " under the path " + optionPath);
		}
		try
		{
			return Integer.parseInt(option);
		}
		catch (NumberFormatException nfe)
		{
			throw new InvalidOptionException(nfe);
		}
	}

	public static void saveScreenOption(ScreenOption so)
	{
		OptionLevel.SYSTEM.getPreferenceNode(SCREEN_NAME_MODE_MAP).put(so.getName(), so.getCurrentScreenMode().name());
	}

	public static List<ScreenOption> getScreenOptions()
	{
		List<String> mapKeys = null;
		Preferences pref1 = null;
		Preferences pref2 = null;
		try
		{
			pref1 = OptionLevel.SYSTEM.getPreferenceNode(SCREEN_NAME_MODE_MAP);
			mapKeys = Arrays.asList(pref1.keys());
			pref2 = OptionLevel.USER.getPreferenceNode(SCREEN_NAME_MODE_MAP);
			mapKeys.addAll(Arrays.asList(pref2.keys()));
		}
		catch (BackingStoreException bse)
		{
			//Ignore this and continue to make the new devices.
		}
		for (String key : mapKeys) //Loop through all saved monitor configurations
		{
			LOG.debug(key);
		}
		int i = 0;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gdArray = ge.getScreenDevices();
		List<ScreenOption> toReturn = new ArrayList<ScreenOption>();
		List<String> unusedMonitors = new ArrayList<String>(mapKeys);
		//Create a copy of the keys array.
		for (GraphicsDevice gd : gdArray) //Loop through all graphics devices
		{
			boolean createdGD = false;
			for (String key : mapKeys) //Loop through all saved monitor configurations
			{
				if (gd.getIDstring().equals(key)) //Ignore every combination that doesn't match
				{
					String smstring = pref1.get(key, null);
					if (smstring == null)
						smstring = pref2.get(key, null);
					LOG.debug("String:" + smstring);
					if (smstring != null)
					{
						ScreenMode sm = ScreenMode.valueOf(ScreenMode.class, smstring);
						toReturn.add(new ScreenOption(gd, sm));
					}
					else
					{
						toReturn.add(new ScreenOption(gd, null));
					}
					unusedMonitors.remove(key);
					createdGD = true;
					LOG.debug("Removing:" + smstring + " : " + key);
				}
			}
			if (!createdGD)
			{
				toReturn.add(new ScreenOption(gd));
			}
		}
		for (String monitor : unusedMonitors)
		{
			String smstring = pref1.get(monitor, null);
			if(smstring == null)
			{
				smstring = pref2.get(monitor, null);
			}
			ScreenMode sm = ScreenMode.valueOf(ScreenMode.class, smstring);
			toReturn.add(new ScreenOption(sm));
			LOG.debug("Unused:" + smstring + " : " + monitor);
		}
		return toReturn;
	}

	public static void setOption(String optionPath, String optionName, OptionLevel optionLevel, String setting)
	{
		optionLevel.getPreferenceNode(optionPath).put(optionName, setting);
	}

	public static float getAspectRatio()
	{
		float aspectRatio = 0;
		String aspectRatioOption = getBestOption(SCREEN_OPTIONS, ASPECT_RATIO);
		if (aspectRatioOption != null)
		{
			aspectRatio = Float.parseFloat(aspectRatioOption);
		}
		else
		{
			aspectRatio = (float) 4 / 3;
		}
		if (aspectRatio == USE_SCREEN)
		{
			return ScreenHandler.getScreenHandler().getAnAspectRatio();
		}
		return aspectRatio;
	}

	public GraphicsDevice[] getWindowedDevices()
	{
		GraphicsDevice[] toReturn = new GraphicsDevice[10];
		for (int loopi = 0; loopi < 10; loopi++)
		{
		}
		return toReturn;
	}
}
