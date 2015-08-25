package org.zlorf.view;

import com.sun.jna.platform.WindowUtils;
import com.sun.awt.AWTUtilities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import org.slf4j.Logger;
import javax.swing.JFrame;
import javax.swing.JWindow;
import org.slf4j.LoggerFactory;
import org.zlorf.controller.Options_OLD;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.windows.WindowsCanvas;

/**
 *
 * @author Mat Jaggard
 */
public class JMainPanel extends JWindow implements RenderArea, VLCHolder
{
	private static final Logger LOG = LoggerFactory.getLogger(JMainPanel.class);
	private Options_OLD theOptions;
	private VLCWindow associatedVLCWindow = null;
	private JMainOuterFrame outerFrame;
	private RenderHelper rh = new RenderHelper();

	public JMainPanel(String title)
	{
		this(new JMainOuterFrame(title));
		outerFrame.setSize(400, 322);
		outerFrame.setLocationRelativeTo(null);
		outerFrame.setWindow(this);
	}

	private JMainPanel(JMainOuterFrame outerFrame)
	{
		super(outerFrame, WindowUtils.getAlphaCompatibleGraphicsConfiguration());
		this.outerFrame = outerFrame;
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

	public boolean addVLC(MediaPlayerFactory mediaPlayerFactory)
	{
		boolean useAWTUtil = true;
		boolean supportTransparency = true;
		boolean highVersionOfJava = false;
		try
		{
			String versionString = System.getProperty("java.version");
			float versionId = Float.parseFloat(versionString);
			if (versionId >= 7)
			{
				highVersionOfJava = true;
			}
			else if (versionId >= 1.7 && versionId < 3)
			{
				highVersionOfJava = true;
			}
		}
		catch (Exception e)
		{
			//We ignore errors getting a java version or parsing or whatever.
		}
		//If we're using a high version of java (7+), we set the background.
		if (highVersionOfJava)
		{
			LOG.debug("Cool, we found Java 7.");
			this.setBackground(new Color(0, 0, 0, 0)); // This is what you do in JDK7
		}
		else
		{
			LOG.debug("Failed to use Java 7, trying Java 6 update 10 method.");
			try
			{
				Class.forName("com.sun.awt.AWTUtilities");
			}
			catch (Exception e)
			{
				useAWTUtil = false;
				supportTransparency = false;
				LOG.error("This version of Java or this platform doesn't support transparency in a way zlorf knows about.");
			}
		}
		if (useAWTUtil)
		{
			AWTUtilities.setWindowOpaque(this, false); // Doesn't work in full-screen exclusive mode, you would have to use 'simulated' full-screen - requires Sun/Oracle JDK
		}
		//Only if we support transparency do we add VLC - pointless otherwise.
		if (supportTransparency)
		{
			outerFrame.remove(this);
			Canvas videoSurface;
			if (RuntimeUtil.isWindows())
			{
				// If running on Windows and you want the mouse/keyboard event hack...
				videoSurface = new WindowsCanvas();/*
				{
				@Override
				public void setSize(Dimension d)
				{
				super.setSize(d);
				repaint();
				}
				};*/
			}
			else
			{
				videoSurface = new Canvas();/*
				{

				@Override
				public void setSize(Dimension d)
				{
				super.setSize(d);
				repaint();
				}
				};*/
			}
			outerFrame.add(videoSurface);
			videoSurface.setBackground(Color.black);
			EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newMediaPlayer(new ZlorfFullScreenStrategy(outerFrame));
			associatedVLCWindow = new VLCWindow(videoSurface, this, mediaPlayer);
			return true;
		}
		return false;
	}

	@Override
	public void paint(Graphics g)
	{
		rh.paint(this, g, true);
/*
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		g.setColor(Color.white);
		g.fillRoundRect(100, 150, 100, 100, 32, 32);

		g.setFont(new Font("Sans", Font.BOLD, 32));
		g.drawString("This is a test", 100, 300);
 */
	}

	@Override
	public void repaint()
	{
		if (associatedVLCWindow != null)
		{
			associatedVLCWindow.repaint();
		}
		super.repaint();
	}

	public EmbeddedMediaPlayer getMediaPlayer()
	{
		if (associatedVLCWindow == null)
		{
			return null;
		}
		else
		{
			return associatedVLCWindow.getMediaPlayer();
		}
	}
}

class JMainOuterFrame extends JFrame
{

	private JMainPanel window = null;
	private AspectRatioEnforcer are;

	public JMainOuterFrame(String title)
	{
		super(title);
		are = new AspectRatioEnforcer(Options_OLD.getAspectRatio());
		addComponentListener(are);
		setAlwaysOnTop(true);
		setVisible(true);
		setBackground(Color.GREEN);
	}

	public void setWindow(JMainPanel window)
	{
		this.window = window;
	}

	public void setAspectRatio(float aspectRatio)
	{
		this.removeComponentListener(are);
		are = new AspectRatioEnforcer(aspectRatio);
		this.addComponentListener(are);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		if (window != null)
		{
			window.repaint();
		}
	}
}
