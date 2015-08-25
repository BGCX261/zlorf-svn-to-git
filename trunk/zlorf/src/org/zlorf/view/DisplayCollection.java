package org.zlorf.view;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Mat Jaggard
 */
public class DisplayCollection
{
	private Set<RenderArea> previews = new HashSet<RenderArea>();
	private Set<RenderArea> screens = new HashSet<RenderArea>();

	public void addPreview(RenderArea toAdd)
	{
		previews.add(toAdd);
	}

	public void addScreen(RenderArea toAdd)
	{
		screens.add(toAdd);
	}

	public Iterator<RenderArea> screenIterator()
	{
		return screens.iterator();
	}

	public Iterator<RenderArea> previewIterator()
	{
		return previews.iterator();
	}

	public boolean contains(RenderArea toCheck)
	{
		if (previews.contains(toCheck))
			return true;
		else if (screens.contains(toCheck))
			return true;
		else
			return false;
	}

	public boolean remove(RenderArea toRemove)
	{
		boolean toReturn = false;
		if (previews.remove(toRemove))
			toReturn = true;
		if (screens.remove(toRemove))
			toReturn = true;
		return toReturn;
	}
}