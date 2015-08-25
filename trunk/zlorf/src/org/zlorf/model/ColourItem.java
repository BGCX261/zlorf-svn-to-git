package org.zlorf.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zlorf.view.RenderArea;
import org.zlorf.view.Renderable;

/**
 *
 * @author JaggardM
 */
@Entity
@NamedQueries(
{
	@NamedQuery(name = "ColourItem.findAll", query = "SELECT v FROM ColourItem v"),
	@NamedQuery(name = "ColourItem.findById", query = "SELECT v FROM ColourItem v WHERE v.id = :id"),
	@NamedQuery(name = "ColourItem.findByAccess", query = "SELECT v FROM ColourItem v WHERE v.access = :access"),
	@NamedQuery(name = "ColourItem.findByDevice", query = "SELECT v FROM ColourItem v WHERE v.device = :device"),
	@NamedQuery(name = "ColourItem.findByFile", query = "SELECT v FROM ColourItem v WHERE v.file = :file"),
	@NamedQuery(name = "ColourItem.findByOptions", query = "SELECT v FROM ColourItem v WHERE v.options = :options"),
	@NamedQuery(name = "ColourItem.findByRepeating", query = "SELECT v FROM ColourItem v WHERE v.repeating = :repeating")
})
public class ColourItem implements Serializable, Renderable
{

	private static final Logger LOG = LoggerFactory.getLogger(ColourItem.class);
	public static final int SINGLE_COLOUR = 1;
	public static final int HORIZONTAL_GRADIENT = 2;
	public static final int VERTICAL_GRADIENT = 3;
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false, length = 10)
	private String firstColour;
	@Column(length = 10)
	private String secondColour;
	@Column
	private Integer fillType;
	@JoinColumn(referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false)
	private OOSItem oOSItem;

	public ColourItem()
	{
	}

	public ColourItem(Integer id)
	{
		this.id = id;
	}

	public ColourItem(Integer id, String firstColour)
	{
		this.id = id;
		this.firstColour = firstColour;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof ColourItem))
		{
			return false;
		}
		ColourItem other = (ColourItem) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "org.zlorf.model.ColourItem[id=" + id + "]";
	}

	/**
	 * @return the firstColour
	 */
	public String getFirstColour()
	{
		return firstColour;
	}

	/**
	 * @param firstColour the firstColour to set
	 */
	public void setFirstColour(String firstColour)
	{
		this.firstColour = firstColour;
	}

	/**
	 * @return the secondColour
	 */
	public String getSecondColour()
	{
		return secondColour;
	}

	/**
	 * @param secondColour the secondColour to set
	 */
	public void setSecondColour(String secondColour)
	{
		this.secondColour = secondColour;
	}

	/**
	 * @return the fillType
	 */
	public Integer getFillType()
	{
		return fillType;
	}

	/**
	 * @param fillType the fillType to set
	 */
	public void setFillType(Integer fillType)
	{
		this.fillType = fillType;
	}

	/**
	 * @return the oOSItem
	 */
	public OOSItem getOOSItem()
	{
		return oOSItem;
	}

	/**
	 * @param oOSItem the oOSItem to set
	 */
	public void setOOSItem(OOSItem oOSItem)
	{
		this.oOSItem = oOSItem;
	}

	@Override
	public void render(RenderArea area, Graphics graphics)
	{
		Graphics2D g2d = (Graphics2D) graphics;
		Paint backgroundPaint;
		if (firstColour != null)
		{
			LOG.info(firstColour);
			Color firstColor = Color.decode(firstColour);
			LOG.info(firstColor.toString());
			if (fillType == null || fillType == SINGLE_COLOUR)
			{
				backgroundPaint = firstColor;
			}
			else if (fillType == HORIZONTAL_GRADIENT || fillType == VERTICAL_GRADIENT)
			{
				float[] dist =
				{
					0f, 0f
				};
				Color secondColor = Color.decode(secondColour);
				Color[] colors =
				{
					firstColor, secondColor
				};
				if (fillType == VERTICAL_GRADIENT)
				{
					backgroundPaint = new LinearGradientPaint(0f, 0f, 1f, 0f, dist, colors);
				}
				else
				{
					backgroundPaint = new LinearGradientPaint(0f, 0f, 0f, 1f, dist, colors);
				}
			}
			else
			{
				backgroundPaint = firstColor;
			}
			g2d.setPaint(backgroundPaint);
			g2d.fill(g2d.getClip());
		}
		g2d.setPaint(Color.WHITE);
		g2d.drawString("Coloured Page", 100, 100);
		LOG.info("Drawn some coloury stuff");
	}

	@Override
	public void renderThumb(RenderArea area, Graphics graphics)
	{
		render(area, graphics);
	}
}
