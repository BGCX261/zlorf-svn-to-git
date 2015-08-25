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
	@NamedQuery(name = "OOS.findAll", query = "SELECT o FROM OOS o"),
	@NamedQuery(name = "OOS.findById", query = "SELECT o FROM OOS o WHERE o.id = :id"),
	@NamedQuery(name = "OOS.findByName", query = "SELECT o FROM OOS o WHERE o.name = :name"),
	@NamedQuery(name = "OOS.findByTemplate", query = "SELECT o FROM OOS o WHERE o.template = :template")
})
public class OOS implements Serializable
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
	@Basic(optional = false)
	@Column(nullable = false)
	private short template;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "OOS")
	private Set<OOSItem> oOSItemSet;

	public OOS()
	{
	}

	public OOS(Integer id)
	{
		this.id = id;
	}

	public OOS(Integer id, String name, short template)
	{
		this.id = id;
		this.name = name;
		this.template = template;
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

	public short getTemplate()
	{
		return template;
	}

	public void setTemplate(short template)
	{
		this.template = template;
	}

	public Set<OOSItem> getOOSItemSet()
	{
		return oOSItemSet;
	}

	public void setOOSItemSet(Set<OOSItem> oOSItemSet)
	{
		this.oOSItemSet = oOSItemSet;
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
		if (!(object instanceof OOS))
		{
			return false;
		}
		OOS other = (OOS) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "org.zlorf.model.OOS[id=" + id + "]";
	}
}
