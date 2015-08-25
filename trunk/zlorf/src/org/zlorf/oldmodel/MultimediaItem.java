package org.zlorf.oldmodel;

/**
 * A class defining a multimedia item. Should be used as a super class
 * to any multimedia based OOSItem.
 * @author Mat Jaggard
 */
public abstract class MultimediaItem extends OOSItem
{
	  float fadeInTime;
	  float fadeOutTime;
	  float length;

	  public float getFadeInTime()
	  {
			 return fadeInTime;
	  }

	  public void setFadeInTime(float fadeInTime)
	  {
			 this.fadeInTime = fadeInTime;
	  }

	  public float getFadeOutTime()
	  {
			 return fadeOutTime;
	  }

	  public void setFadeOutTime(float fadeOutTime)
	  {
			 this.fadeOutTime = fadeOutTime;
	  }

	  public float getLength()
	  {
			 return length;
	  }

	  public void setLength(float length)
	  {
			 this.length = length;
	  }
}
