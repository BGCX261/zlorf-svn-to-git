package org.zlorf.controller.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.zlorf.controller.jpa.exceptions.NonexistentEntityException;
import org.zlorf.model.Songs;
import org.zlorf.model.Verses;

/**
 *
 * @author JaggardM
 */
public class VersesJpaController {

	public VersesJpaController()
	{
		emf = Persistence.createEntityManagerFactory("zlorfPU");
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Verses verses)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Songs songs = verses.getSongs();
			if (songs != null)
			{
				songs = em.getReference(songs.getClass(), songs.getId());
				verses.setSongs(songs);
			}
			em.persist(verses);
			if (songs != null)
			{
				songs.getVersesSet().add(verses);
				songs = em.merge(songs);
			}
			em.getTransaction().commit();
		}
		finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(Verses verses) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Verses persistentVerses = em.find(Verses.class, verses.getId());
			Songs songsOld = persistentVerses.getSongs();
			Songs songsNew = verses.getSongs();
			if (songsNew != null)
			{
				songsNew = em.getReference(songsNew.getClass(), songsNew.getId());
				verses.setSongs(songsNew);
			}
			verses = em.merge(verses);
			if (songsOld != null && !songsOld.equals(songsNew))
			{
				songsOld.getVersesSet().remove(verses);
				songsOld = em.merge(songsOld);
			}
			if (songsNew != null && !songsNew.equals(songsOld))
			{
				songsNew.getVersesSet().add(verses);
				songsNew = em.merge(songsNew);
			}
			em.getTransaction().commit();
		}
		catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = verses.getId();
				if (findVerses(id) == null)
				{
					throw new NonexistentEntityException("The verses with id " + id + " no longer exists.");
				}
			}
			throw ex;
		}
		finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Verses verses;
			try
			{
				verses = em.getReference(Verses.class, id);
				verses.getId();
			}
			catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The verses with id " + id + " no longer exists.", enfe);
			}
			Songs songs = verses.getSongs();
			if (songs != null)
			{
				songs.getVersesSet().remove(verses);
				songs = em.merge(songs);
			}
			em.remove(verses);
			em.getTransaction().commit();
		}
		finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<Verses> findVersesEntities()
	{
		return findVersesEntities(true, -1, -1);
	}

	public List<Verses> findVersesEntities(int maxResults, int firstResult)
	{
		return findVersesEntities(false, maxResults, firstResult);
	}

	private List<Verses> findVersesEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Verses> cq = em.getCriteriaBuilder().createQuery(Verses.class);
			cq.select(cq.from(Verses.class));
			TypedQuery<Verses> q = em.createQuery(cq);
			if (!all)
			{
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		}
		finally
		{
			em.close();
		}
	}

	public Verses findVerses(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Verses.class, id);
		}
		finally
		{
			em.close();
		}
	}

	public int getVersesCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<Verses> rt = cq.from(Verses.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			TypedQuery<Long> q = em.createQuery(cq);
			return q.getSingleResult().intValue();
		}
		finally
		{
			em.close();
		}
	}

}
