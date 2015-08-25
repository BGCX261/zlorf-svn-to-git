package org.zlorf.controller;

import java.util.prefs.Preferences;

/**
 *
 * @author Mat Jaggard
 */
public enum OptionLevel
{
	FILE ("/org/zlorf/options/file", Preferences.userRoot()),
	USER ("/org/zlorf/options/user", Preferences.userRoot()),
	SYSTEM ("/org/zlorf/options/system" , Preferences.systemRoot());

	private String location;
	private Preferences prefs;

	private OptionLevel(String location, Preferences prefs)
	{
		this.location = location;
		this.prefs = prefs;
	}

	private String getLocation()
	{
		return location;
	}

	public Preferences getPreferenceNode(String optionPath)
	{
		String toAdd;
		if (optionPath.startsWith("/") || optionPath.equals(""))
		{
			toAdd = "";
		}
		else
		{
			toAdd = "/";
		}
		return prefs.node(getLocation() + toAdd + optionPath);
	}

	public Preferences getPreferenceNode()
	{
		return getPreferenceNode("");
	}
}