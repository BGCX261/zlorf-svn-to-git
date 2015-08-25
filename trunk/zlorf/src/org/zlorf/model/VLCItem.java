package org.zlorf.model;

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

/**
 *
 * @author JaggardM
 */
@Entity
@NamedQueries(
{
	@NamedQuery(name = "VLCItem.findAll", query = "SELECT v FROM VLCItem v"),
	@NamedQuery(name = "VLCItem.findById", query = "SELECT v FROM VLCItem v WHERE v.id = :id"),
	@NamedQuery(name = "VLCItem.findByAccess", query = "SELECT v FROM VLCItem v WHERE v.access = :access"),
	@NamedQuery(name = "VLCItem.findByDevice", query = "SELECT v FROM VLCItem v WHERE v.device = :device"),
	@NamedQuery(name = "VLCItem.findByFile", query = "SELECT v FROM VLCItem v WHERE v.file = :file"),
	@NamedQuery(name = "VLCItem.findByOptions", query = "SELECT v FROM VLCItem v WHERE v.options = :options"),
	@NamedQuery(name = "VLCItem.findByRepeating", query = "SELECT v FROM VLCItem v WHERE v.repeating = :repeating")
})
public class VLCItem implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false, length = 10)
	private String access;
	@Column(length = 100)
	private String device;
	@Basic(optional = false)
	@Column(nullable = false, length = 45)
	private String file;
	@Column(length = 100)
	private String options;
	@Column
	private Integer repeating;
	@JoinColumn(referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false)
	private OOSItem oOSItem;

	public VLCItem()
	{
	}

	public VLCItem(Integer id)
	{
		this.id = id;
	}

	public VLCItem(Integer id, String access, String file)
	{
		this.id = id;
		this.access = access;
		this.file = file;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getAccess()
	{
		return access;
	}

	public void setAccess(String access)
	{
		this.access = access;
	}

	public String getDevice()
	{
		return device;
	}

	public void setDevice(String device)
	{
		this.device = device;
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public String getOptions()
	{
		return options;
	}

	public void setOptions(String options)
	{
		this.options = options;
	}

	public Integer getRepeating()
	{
		return repeating;
	}

	public void setRepeating(Integer repeating)
	{
		this.repeating = repeating;
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
		if (!(object instanceof VLCItem))
		{
			return false;
		}
		VLCItem other = (VLCItem) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "org.zlorf.model.VLCItem[id=" + id + "]";
	}
}
