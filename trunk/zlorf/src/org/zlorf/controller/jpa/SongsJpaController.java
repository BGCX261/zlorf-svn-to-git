package org.zlorf.controller.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.zlorf.controller.jpa.exceptions.IllegalOrphanException;
import org.zlorf.controller.jpa.exceptions.NonexistentEntityException;
import org.zlorf.model.Songs;
import org.zlorf.model.Verses;
import java.util.HashSet;
import java.util.Set;
import org.zlorf.model.SongItem;
import java.util.ArrayList;
import javax.persistence.TypedQuery;

/**
 *
 * @author JaggardM
 */
public class SongsJpaController {

	public SongsJpaController()
	{
		emf = Persistence.createEntityManagerFactory("zlorfPU");
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Songs songs)
	{
		if (songs.getVersesSet() == null)
		{
			songs.setVersesSet(new HashSet<Verses>());
		}
		if (songs.getSongItemSet() == null)
		{
			songs.setSongItemSet(new HashSet<SongItem>());
		}
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Set<Verses> attachedVersesSet = new HashSet<Verses>();
			for (Verses versesSetVersesToAttach : songs.getVersesSet())
			{
				versesSetVersesToAttach = em.getReference(versesSetVersesToAttach.getClass(), versesSetVersesToAttach.getId());
				attachedVersesSet.add(versesSetVersesToAttach);
			}
			songs.setVersesSet(attachedVersesSet);
			Set<SongItem> attachedSongItemSet = new HashSet<SongItem>();
			for (SongItem songItemSetSongItemToAttach : songs.getSongItemSet())
			{
				songItemSetSongItemToAttach = em.getReference(songItemSetSongItemToAttach.getClass(), songItemSetSongItemToAttach.getId());
				attachedSongItemSet.add(songItemSetSongItemToAttach);
			}
			songs.setSongItemSet(attachedSongItemSet);
			em.persist(songs);
			for (Verses versesSetVerses : songs.getVersesSet())
			{
				Songs oldSongsOfVersesSetVerses = versesSetVerses.getSongs();
				versesSetVerses.setSongs(songs);
				versesSetVerses = em.merge(versesSetVerses);
				if (oldSongsOfVersesSetVerses != null)
				{
					oldSongsOfVersesSetVerses.getVersesSet().remove(versesSetVerses);
					oldSongsOfVersesSetVerses = em.merge(oldSongsOfVersesSetVerses);
				}
			}
			for (SongItem songItemSetSongItem : songs.getSongItemSet())
			{
				Songs oldSongsOfSongItemSetSongItem = songItemSetSongItem.getSongs();
				songItemSetSongItem.setSongs(songs);
				songItemSetSongItem = em.merge(songItemSetSongItem);
				if (oldSongsOfSongItemSetSongItem != null)
				{
					oldSongsOfSongItemSetSongItem.getSongItemSet().remove(songItemSetSongItem);
					oldSongsOfSongItemSetSongItem = em.merge(oldSongsOfSongItemSetSongItem);
				}
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

	public void edit(Songs songs) throws IllegalOrphanException, NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Songs persistentSongs = em.find(Songs.class, songs.getId());
			Set<Verses> versesSetOld = persistentSongs.getVersesSet();
			Set<Verses> versesSetNew = songs.getVersesSet();
			Set<SongItem> songItemSetOld = persistentSongs.getSongItemSet();
			Set<SongItem> songItemSetNew = songs.getSongItemSet();
			List<String> illegalOrphanMessages = null;
			for (Verses versesSetOldVerses : versesSetOld)
			{
				if (!versesSetNew.contains(versesSetOldVerses))
				{
					if (illegalOrphanMessages == null)
					{
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Verses " + versesSetOldVerses + " since its songs field is not nullable.");
				}
			}
			for (SongItem songItemSetOldSongItem : songItemSetOld)
			{
				if (!songItemSetNew.contains(songItemSetOldSongItem))
				{
					if (illegalOrphanMessages == null)
					{
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain SongItem " + songItemSetOldSongItem + " since its songs field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Set<Verses> attachedVersesSetNew = new HashSet<Verses>();
			for (Verses versesSetNewVersesToAttach : versesSetNew)
			{
				versesSetNewVersesToAttach = em.getReference(versesSetNewVersesToAttach.getClass(), versesSetNewVersesToAttach.getId());
				attachedVersesSetNew.add(versesSetNewVersesToAttach);
			}
			versesSetNew = attachedVersesSetNew;
			songs.setVersesSet(versesSetNew);
			Set<SongItem> attachedSongItemSetNew = new HashSet<SongItem>();
			for (SongItem songItemSetNewSongItemToAttach : songItemSetNew)
			{
				songItemSetNewSongItemToAttach = em.getReference(songItemSetNewSongItemToAttach.getClass(), songItemSetNewSongItemToAttach.getId());
				attachedSongItemSetNew.add(songItemSetNewSongItemToAttach);
			}
			songItemSetNew = attachedSongItemSetNew;
			songs.setSongItemSet(songItemSetNew);
			songs = em.merge(songs);
			for (Verses versesSetNewVerses : versesSetNew)
			{
				if (!versesSetOld.contains(versesSetNewVerses))
				{
					Songs oldSongsOfVersesSetNewVerses = versesSetNewVerses.getSongs();
					versesSetNewVerses.setSongs(songs);
					versesSetNewVerses = em.merge(versesSetNewVerses);
					if (oldSongsOfVersesSetNewVerses != null && !oldSongsOfVersesSetNewVerses.equals(songs))
					{
						oldSongsOfVersesSetNewVerses.getVersesSet().remove(versesSetNewVerses);
						oldSongsOfVersesSetNewVerses = em.merge(oldSongsOfVersesSetNewVerses);
					}
				}
			}
			for (SongItem songItemSetNewSongItem : songItemSetNew)
			{
				if (!songItemSetOld.contains(songItemSetNewSongItem))
				{
					Songs oldSongsOfSongItemSetNewSongItem = songItemSetNewSongItem.getSongs();
					songItemSetNewSongItem.setSongs(songs);
					songItemSetNewSongItem = em.merge(songItemSetNewSongItem);
					if (oldSongsOfSongItemSetNewSongItem != null && !oldSongsOfSongItemSetNewSongItem.equals(songs))
					{
						oldSongsOfSongItemSetNewSongItem.getSongItemSet().remove(songItemSetNewSongItem);
						oldSongsOfSongItemSetNewSongItem = em.merge(oldSongsOfSongItemSetNewSongItem);
					}
				}
			}
			em.getTransaction().commit();
		}
		catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = songs.getId();
				if (findSongs(id) == null)
				{
					throw new NonexistentEntityException("The songs with id " + id + " no longer exists.");
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

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Songs songs;
			try
			{
				songs = em.getReference(Songs.class, id);
				songs.getId();
			}
			catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The songs with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Set<Verses> versesSetOrphanCheck = songs.getVersesSet();
			for (Verses versesSetOrphanCheckVerses : versesSetOrphanCheck)
			{
				if (illegalOrphanMessages == null)
				{
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Songs (" + songs + ") cannot be destroyed since the Verses " + versesSetOrphanCheckVerses + " in its versesSet field has a non-nullable songs field.");
			}
			Set<SongItem> songItemSetOrphanCheck = songs.getSongItemSet();
			for (SongItem songItemSetOrphanCheckSongItem : songItemSetOrphanCheck)
			{
				if (illegalOrphanMessages == null)
				{
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Songs (" + songs + ") cannot be destroyed since the SongItem " + songItemSetOrphanCheckSongItem + " in its songItemSet field has a non-nullable songs field.");
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(songs);
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

	public List<Songs> findSongsEntities()
	{
		return findSongsEntities(true, -1, -1);
	}

	public List<Songs> findSongsEntities(int maxResults, int firstResult)
	{
		return findSongsEntities(false, maxResults, firstResult);
	}

	private List<Songs> findSongsEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Songs> cq = em.getCriteriaBuilder().createQuery(Songs.class);
			cq.select(cq.from(Songs.class));
			TypedQuery<Songs> q = em.createQuery(cq);
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

	public Songs findSongs(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Songs.class, id);
		}
		finally
		{
			em.close();
		}
	}

	public int getSongsCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<Songs> rt = cq.from(Songs.class);
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
