package org.zlorf.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author JaggardM
 */
@Entity
@NamedQueries(
{
	@NamedQuery(name = "Songs.findAll", query = "SELECT s FROM Songs s"),
	@NamedQuery(name = "Songs.findById", query = "SELECT s FROM Songs s WHERE s.id = :id"),
	@NamedQuery(name = "Songs.findByTitle", query = "SELECT s FROM Songs s WHERE s.title = :title"),
	@NamedQuery(name = "Songs.findByArtist", query = "SELECT s FROM Songs s WHERE s.artist = :artist")
})
public class Songs implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false, length = 45)
	private String title;
	@Column(length = 45)
	private String artist;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Songs")
	private Set<Verses> versesSet;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Songs")
	private Set<SongItem> songItemSet;

	public Songs()
	{
	}

	public Songs(Integer id)
	{
		this.id = id;
	}

	public Songs(Integer id, String title)
	{
		this.id = id;
		this.title = title;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public Set<Verses> getVersesSet()
	{
		return versesSet;
	}

	public void setVersesSet(Set<Verses> versesSet)
	{
		this.versesSet = versesSet;
	}

	public Set<SongItem> getSongItemSet()
	{
		return songItemSet;
	}

	public void setSongItemSet(Set<SongItem> songItemSet)
	{
		this.songItemSet = songItemSet;
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
		if (!(object instanceof Songs))
		{
			return false;
		}
		Songs other = (Songs) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "org.zlorf.model.Songs[id=" + id + "]";
	}
}
