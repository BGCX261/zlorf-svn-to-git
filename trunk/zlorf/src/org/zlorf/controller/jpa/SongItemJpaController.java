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
import org.zlorf.model.SongItem;
import org.zlorf.model.Songs;
import org.zlorf.model.OOSItem;

/**
 *
 * @author JaggardM
 */
public class SongItemJpaController {

	public SongItemJpaController()
	{
		emf = Persistence.createEntityManagerFactory("zlorfPU");
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(SongItem songItem)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Songs songs = songItem.getSongs();
			if (songs != null)
			{
				songs = em.getReference(songs.getClass(), songs.getId());
				songItem.setSongs(songs);
			}
			OOSItem OOSItem = songItem.getOOSItem();
			if (OOSItem != null)
			{
				OOSItem = em.getReference(OOSItem.getClass(), OOSItem.getId());
				songItem.setOOSItem(OOSItem);
			}
			em.persist(songItem);
			if (songs != null)
			{
				songs.getSongItemSet().add(songItem);
				songs = em.merge(songs);
			}
			if (OOSItem != null)
			{
				OOSItem.getSongItemSet().add(songItem);
				OOSItem = em.merge(OOSItem);
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

	public void edit(SongItem songItem) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			SongItem persistentSongItem = em.find(SongItem.class, songItem.getId());
			Songs songsOld = persistentSongItem.getSongs();
			Songs songsNew = songItem.getSongs();
			OOSItem OOSItemOld = persistentSongItem.getOOSItem();
			OOSItem OOSItemNew = songItem.getOOSItem();
			if (songsNew != null)
			{
				songsNew = em.getReference(songsNew.getClass(), songsNew.getId());
				songItem.setSongs(songsNew);
			}
			if (OOSItemNew != null)
			{
				OOSItemNew = em.getReference(OOSItemNew.getClass(), OOSItemNew.getId());
				songItem.setOOSItem(OOSItemNew);
			}
			songItem = em.merge(songItem);
			if (songsOld != null && !songsOld.equals(songsNew))
			{
				songsOld.getSongItemSet().remove(songItem);
				songsOld = em.merge(songsOld);
			}
			if (songsNew != null && !songsNew.equals(songsOld))
			{
				songsNew.getSongItemSet().add(songItem);
				songsNew = em.merge(songsNew);
			}
			if (OOSItemOld != null && !OOSItemOld.equals(OOSItemNew))
			{
				OOSItemOld.getSongItemSet().remove(songItem);
				OOSItemOld = em.merge(OOSItemOld);
			}
			if (OOSItemNew != null && !OOSItemNew.equals(OOSItemOld))
			{
				OOSItemNew.getSongItemSet().add(songItem);
				OOSItemNew = em.merge(OOSItemNew);
			}
			em.getTransaction().commit();
		}
		catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = songItem.getId();
				if (findSongItem(id) == null)
				{
					throw new NonexistentEntityException("The songItem with id " + id + " no longer exists.");
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
			SongItem songItem;
			try
			{
				songItem = em.getReference(SongItem.class, id);
				songItem.getId();
			}
			catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The songItem with id " + id + " no longer exists.", enfe);
			}
			Songs songs = songItem.getSongs();
			if (songs != null)
			{
				songs.getSongItemSet().remove(songItem);
				songs = em.merge(songs);
			}
			OOSItem OOSItem = songItem.getOOSItem();
			if (OOSItem != null)
			{
				OOSItem.getSongItemSet().remove(songItem);
				OOSItem = em.merge(OOSItem);
			}
			em.remove(songItem);
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

	public List<SongItem> findSongItemEntities()
	{
		return findSongItemEntities(true, -1, -1);
	}

	public List<SongItem> findSongItemEntities(int maxResults, int firstResult)
	{
		return findSongItemEntities(false, maxResults, firstResult);
	}

	private List<SongItem> findSongItemEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<SongItem> cq = em.getCriteriaBuilder().createQuery(SongItem.class);
			cq.select(cq.from(SongItem.class));
			TypedQuery<SongItem> q = em.createQuery(cq);
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

	public SongItem findSongItem(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(SongItem.class, id);
		}
		finally
		{
			em.close();
		}
	}

	public int getSongItemCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<SongItem> rt = cq.from(SongItem.class);
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
