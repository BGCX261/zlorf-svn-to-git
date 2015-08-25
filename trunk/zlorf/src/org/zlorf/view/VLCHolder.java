package org.zlorf.view;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author JaggardM
 */
public interface VLCHolder
{
	public boolean addVLC(MediaPlayerFactory mediaPlayerFactory);
	public EmbeddedMediaPlayer getMediaPlayer();
}
