package org.zlorf.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author JaggardM
 */
@Entity
@NamedQueries(
{
	@NamedQuery(name = "SongItem.findAll", query = "SELECT s FROM SongItem s"),
	@NamedQuery(name = "SongItem.findById", query = "SELECT s FROM SongItem s WHERE s.id = :id")
})
public class SongItem implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer id;
	@JoinColumn(referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Songs songs;
	@JoinColumn(referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false)
	private OOSItem oOSItem;

	public SongItem()
	{
	}

	public SongItem(Integer id)
	{
		this.id = id;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Songs getSongs()
	{
		return songs;
	}

	public void setSongs(Songs songs)
	{
		this.songs = songs;
	}

	public OOSItem getOOSItem()
	{
		return oOSItem;
	}

	public void setOOSItem(OOSItem oOSItem)
	{
		this.oOSItem = oOSItem;
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
		if (!(object instanceof SongItem))
		{
			return false;
		}
		SongItem other = (SongItem) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "org.zlorf.model.SongItem[id=" + id + "]";
	}
}
