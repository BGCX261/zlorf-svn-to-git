package org.zlorf.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
	@NamedQuery(name = "OOSItem.findAll", query = "SELECT o FROM OOSItem o"),
	@NamedQuery(name = "OOSItem.findById", query = "SELECT o FROM OOSItem o WHERE o.id = :id"),
	@NamedQuery(name = "OOSItem.findByName", query = "SELECT o FROM OOSItem o WHERE o.name = :name"),
	@NamedQuery(name = "OOSItem.findByItemType", query = "SELECT o FROM OOSItem o WHERE o.itemType = :itemType")
})
public class OOSItem implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false, length = 45)
	private String name;
	@Lob
	@Column(length = 32700)
	private String description;
	@Basic(optional = false)
	@Column(nullable = false)
	private int itemType;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "OOSItem")
	private Set<SongItem> songItemSet;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "OOSItem")
	private Set<VLCItem> vLCItemSet;
	@JoinColumn(referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false)
	private OOS oos;

	public OOSItem()
	{
	}

	public OOSItem(Integer id)
	{
		this.id = id;
	}

	public OOSItem(Integer id, String name, int itemType)
	{
		this.id = id;
		this.name = name;
		this.itemType = itemType;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getItemType()
	{
		return itemType;
	}

	public void setItemType(int itemType)
	{
		this.itemType = itemType;
	}

	public Set<SongItem> getSongItemSet()
	{
		return songItemSet;
	}

	public void setSongItemSet(Set<SongItem> songItemSet)
	{
		this.songItemSet = songItemSet;
	}

	public Set<VLCItem> getVLCItemSet()
	{
		return vLCItemSet;
	}

	public void setVLCItemSet(Set<VLCItem> vLCItemSet)
	{
		this.vLCItemSet = vLCItemSet;
	}

	public OOS getOos()
	{
		return oos;
	}

	public void setOos(OOS oos)
	{
		this.oos = oos;
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
		if (!(object instanceof OOSItem))
		{
			return false;
		}
		OOSItem other = (OOSItem) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "org.zlorf.model.OOSItem[id=" + id + "]";
	}
}
