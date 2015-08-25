package org.zlorf.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
	@NamedQuery(name = "Verses.findAll", query = "SELECT v FROM Verses v"),
	@NamedQuery(name = "Verses.findById", query = "SELECT v FROM Verses v WHERE v.id = :id"),
	@NamedQuery(name = "Verses.findByType", query = "SELECT v FROM Verses v WHERE v.type = :type"),
	@NamedQuery(name = "Verses.findByNumber", query = "SELECT v FROM Verses v WHERE v.number = :number")
})
public class Verses implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false)
	private int type;
	@Basic(optional = false)
	@Column(nullable = false)
	private int number;
	@Basic(optional = false)
	@Lob
	@Column(nullable = false, length = 32700)
	private String text;
	@JoinColumn(referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false)
	private Songs songs;

	public Verses()
	{
	}

	public Verses(Integer id)
	{
		this.id = id;
	}

	public Verses(Integer id, int type, int number, String text)
	{
		this.id = id;
		this.type = type;
		this.number = number;
		this.text = text;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Songs getSongs()
	{
		return songs;
	}

	public void setSongs(Songs songs)
	{
		this.songs = songs;
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
		if (!(object instanceof Verses))
		{
			return false;
		}
		Verses other = (Verses) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "org.zlorf.model.Verses[id=" + id + "]";
	}
}
