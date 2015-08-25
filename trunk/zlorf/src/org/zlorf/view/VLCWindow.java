package org.zlorf.view;

import java.awt.Canvas;
import java.awt.Window;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author JaggardM
 */
public class VLCWindow
{
	private EmbeddedMediaPlayer mediaPlayer;
	private Window overlay;

	protected VLCWindow(Canvas videoSurface, Window overlay, EmbeddedMediaPlayer mediaPlayer)
	{
		mediaPlayer.setVideoSurface(videoSurface);
		this.overlay = overlay;
		this.mediaPlayer = mediaPlayer;
		repaint();
	}

	public void repaint()
	{
		mediaPlayer.setOverlay(overlay);
		mediaPlayer.enableOverlay(true);
	}

	public EmbeddedMediaPlayer getMediaPlayer()
	{
		return mediaPlayer;
	}
}
