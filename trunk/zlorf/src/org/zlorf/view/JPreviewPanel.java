package org.zlorf.view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import org.zlorf.controller.Options_OLD;

/**
 *
 * @author Mat Jaggard
 */
public class JPreviewPanel extends JPanel implements RenderArea
{

	private Options_OLD theOptions;
	private final RenderHelper rh = new RenderHelper();

	public JPreviewPanel()
	{
		super();
		this.addComponentListener(new AspectRatioEnforcer(Options_OLD.getAspectRatio()));
		this.setBackground(Color.RED);
	}

	public void addFirstPaintListener(Renderable listener)
	{
		rh.addFirstPaintListener(listener);
		repaint();

	}

	public void addLastPaintListener(Renderable listener)
	{
		rh.addLastPaintListener(listener);
		repaint();
	}

	public void removePaintListener(Renderable listener)
	{
		rh.removePaintListener(listener);
		repaint();
	}

	@Override
	public void paint(Graphics g)
	{
		rh.paint(this, g, false);
	}
}
