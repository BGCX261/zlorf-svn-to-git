package org.zlorf.view.options;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import org.zlorf.controller.Options_OLD;

/**
 *
 * @author Mat Jaggard
 */
public class ScreenTableModel extends AbstractTableModel
{
	private static final String[] columnNames = {"Screen Name",
		"Control",
		"Windowed",
		"Full Screen"
	};
	private static final int STM_NAME = 0;
	private static final int STM_CONTROL = 1;
	private static final int STM_WINDOWED = 2;
	private static final int STM_FULLSCREEN = 3;
	private List<ScreenOption> data;// = new ArrayList<ScreenOption>();

	public ScreenTableModel()
	{
		/*
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gdArray = ge.getScreenDevices();
		for (GraphicsDevice gd : gdArray)
		{
			addScreen(gd);
		}
		 */
		loadOptions();
	}

	@Override
	public boolean isCellEditable(int row, int col)
	{
		switch(col)
		{
			case STM_NAME:
				return false;
			case STM_CONTROL:
				return data.get(row).allowed(ScreenMode.CONTROL);
			case STM_WINDOWED:
				return data.get(row).allowed(ScreenMode.WINDOWED);
			case STM_FULLSCREEN:
				return data.get(row).allowed(ScreenMode.FULLSCREEN);
			default:
				return false;
		}
	}

	@Override
	public String getColumnName(int col)
	{
		return columnNames[col];
	}

	public int getRowCount()
	{
		return data.size();
	}

	public int getColumnCount()
	{
		return columnNames.length;
	}

	public Object getValueAt(int row, int col)
	{
		switch(col)
		{
			case STM_NAME:
				return data.get(row).getName();
			case STM_CONTROL:
				return ScreenMode.CONTROL.equals(data.get(row).getCurrentScreenMode());
			case STM_WINDOWED:
				return ScreenMode.WINDOWED.equals(data.get(row).getCurrentScreenMode());
			case STM_FULLSCREEN:
				return ScreenMode.FULLSCREEN.equals(data.get(row).getCurrentScreenMode());
			default:
				return false;
		}
	}

	@Override
	public Class getColumnClass(int col)
	{
		switch(col)
		{
			case STM_NAME:
				return String.class;
			default:
				return Boolean.class;
		}
	}

	public void addScreen(GraphicsDevice gd)
	{
		data.add(new ScreenOption(gd));
	}

	@Override
	public void setValueAt(Object value, int row, int col)
	{
		//System.err.println("Setting value at, Object value is " + value);
		//We ignore a setting of false - you can't untick a box except by ticking a different one.
		switch(col)
		{
			case STM_CONTROL:
				data.get(row).setCurrentScreenMode(ScreenMode.CONTROL);
				break;
			case STM_WINDOWED:
				data.get(row).setCurrentScreenMode(ScreenMode.WINDOWED);
				break;
			case STM_FULLSCREEN:
				data.get(row).setCurrentScreenMode(ScreenMode.FULLSCREEN);
				break;
		}
		fireTableRowsUpdated(row, row);
	}

	public void saveOptions()
	{
		for (ScreenOption so : data)
		{
			Options_OLD.saveScreenOption(so);
		}
	}

	public void loadOptions()
	{
		data = Options_OLD.getScreenOptions();
	}
}
