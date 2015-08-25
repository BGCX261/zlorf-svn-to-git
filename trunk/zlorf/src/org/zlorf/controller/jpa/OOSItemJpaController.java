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
import org.zlorf.model.OOS;
import org.zlorf.model.OOSItem;
import org.zlorf.model.SongItem;
import java.util.HashSet;
import java.util.Set;
import org.zlorf.model.VLCItem;
import java.util.ArrayList;
import javax.persistence.TypedQuery;

/**
 *
 * @author JaggardM
 */
public class OOSItemJpaController {

	public OOSItemJpaController()
	{
		emf = Persistence.createEntityManagerFactory("zlorfPU");
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	@SuppressWarnings("AssignmentToForLoopParameter")
	public void create(OOSItem OOSItem)
	{
		if (OOSItem.getSongItemSet() == null)
		{
			OOSItem.setSongItemSet(new HashSet<SongItem>());
		}
		if (OOSItem.getVLCItemSet() == null)
		{
			OOSItem.setVLCItemSet(new HashSet<VLCItem>());
		}
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			OOS oos = OOSItem.getOos();
			if (oos != null)
			{
				oos = em.getReference(oos.getClass(), oos.getId());
				OOSItem.setOos(oos);
			}
			Set<SongItem> attachedSongItemSet = new HashSet<SongItem>();
			for (SongItem songItemSetSongItemToAttach : OOSItem.getSongItemSet())
			{
				songItemSetSongItemToAttach = em.getReference(songItemSetSongItemToAttach.getClass(), songItemSetSongItemToAttach.getId());
				attachedSongItemSet.add(songItemSetSongItemToAttach);
			}
			OOSItem.setSongItemSet(attachedSongItemSet);
			Set<VLCItem> attachedVLCItemSet = new HashSet<VLCItem>();
			for (VLCItem VLCItemSetVLCItemToAttach : OOSItem.getVLCItemSet())
			{
				VLCItemSetVLCItemToAttach = em.getReference(VLCItemSetVLCItemToAttach.getClass(), VLCItemSetVLCItemToAttach.getId());
				attachedVLCItemSet.add(VLCItemSetVLCItemToAttach);
			}
			OOSItem.setVLCItemSet(attachedVLCItemSet);
			em.persist(OOSItem);
			if (oos != null)
			{
				oos.getOOSItemSet().add(OOSItem);
				oos = em.merge(oos);
			}
			for (SongItem songItemSetSongItem : OOSItem.getSongItemSet())
			{
				OOSItem oldOOSItemOfSongItemSetSongItem = songItemSetSongItem.getOOSItem();
				songItemSetSongItem.setOOSItem(OOSItem);
				songItemSetSongItem = em.merge(songItemSetSongItem);
				if (oldOOSItemOfSongItemSetSongItem != null)
				{
					oldOOSItemOfSongItemSetSongItem.getSongItemSet().remove(songItemSetSongItem);
					oldOOSItemOfSongItemSetSongItem = em.merge(oldOOSItemOfSongItemSetSongItem);
				}
			}
			for (VLCItem VLCItemSetVLCItem : OOSItem.getVLCItemSet())
			{
				OOSItem oldOOSItemOfVLCItemSetVLCItem = VLCItemSetVLCItem.getOOSItem();
				VLCItemSetVLCItem.setOOSItem(OOSItem);
				VLCItemSetVLCItem = em.merge(VLCItemSetVLCItem);
				if (oldOOSItemOfVLCItemSetVLCItem != null)
				{
					oldOOSItemOfVLCItemSetVLCItem.getVLCItemSet().remove(VLCItemSetVLCItem);
					oldOOSItemOfVLCItemSetVLCItem = em.merge(oldOOSItemOfVLCItemSetVLCItem);
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

	public void edit(OOSItem OOSItem) throws IllegalOrphanException, NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			OOSItem persistentOOSItem = em.find(OOSItem.class, OOSItem.getId());
			OOS oosOld = persistentOOSItem.getOos();
			OOS oosNew = OOSItem.getOos();
			Set<SongItem> songItemSetOld = persistentOOSItem.getSongItemSet();
			Set<SongItem> songItemSetNew = OOSItem.getSongItemSet();
			Set<VLCItem> VLCItemSetOld = persistentOOSItem.getVLCItemSet();
			Set<VLCItem> VLCItemSetNew = OOSItem.getVLCItemSet();
			List<String> illegalOrphanMessages = null;
			for (SongItem songItemSetOldSongItem : songItemSetOld)
			{
				if (!songItemSetNew.contains(songItemSetOldSongItem))
				{
					if (illegalOrphanMessages == null)
					{
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain SongItem " + songItemSetOldSongItem + " since its OOSItem field is not nullable.");
				}
			}
			for (VLCItem VLCItemSetOldVLCItem : VLCItemSetOld)
			{
				if (!VLCItemSetNew.contains(VLCItemSetOldVLCItem))
				{
					if (illegalOrphanMessages == null)
					{
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain VLCItem " + VLCItemSetOldVLCItem + " since its OOSItem field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			if (oosNew != null)
			{
				oosNew = em.getReference(oosNew.getClass(), oosNew.getId());
				OOSItem.setOos(oosNew);
			}
			Set<SongItem> attachedSongItemSetNew = new HashSet<SongItem>();
			for (SongItem songItemSetNewSongItemToAttach : songItemSetNew)
			{
				songItemSetNewSongItemToAttach = em.getReference(songItemSetNewSongItemToAttach.getClass(), songItemSetNewSongItemToAttach.getId());
				attachedSongItemSetNew.add(songItemSetNewSongItemToAttach);
			}
			songItemSetNew = attachedSongItemSetNew;
			OOSItem.setSongItemSet(songItemSetNew);
			Set<VLCItem> attachedVLCItemSetNew = new HashSet<VLCItem>();
			for (VLCItem VLCItemSetNewVLCItemToAttach : VLCItemSetNew)
			{
				VLCItemSetNewVLCItemToAttach = em.getReference(VLCItemSetNewVLCItemToAttach.getClass(), VLCItemSetNewVLCItemToAttach.getId());
				attachedVLCItemSetNew.add(VLCItemSetNewVLCItemToAttach);
			}
			VLCItemSetNew = attachedVLCItemSetNew;
			OOSItem.setVLCItemSet(VLCItemSetNew);
			OOSItem = em.merge(OOSItem);
			if (oosOld != null && !oosOld.equals(oosNew))
			{
				oosOld.getOOSItemSet().remove(OOSItem);
				oosOld = em.merge(oosOld);
			}
			if (oosNew != null && !oosNew.equals(oosOld))
			{
				oosNew.getOOSItemSet().add(OOSItem);
				oosNew = em.merge(oosNew);
			}
			for (SongItem songItemSetNewSongItem : songItemSetNew)
			{
				if (!songItemSetOld.contains(songItemSetNewSongItem))
				{
					OOSItem oldOOSItemOfSongItemSetNewSongItem = songItemSetNewSongItem.getOOSItem();
					songItemSetNewSongItem.setOOSItem(OOSItem);
					songItemSetNewSongItem = em.merge(songItemSetNewSongItem);
					if (oldOOSItemOfSongItemSetNewSongItem != null && !oldOOSItemOfSongItemSetNewSongItem.equals(OOSItem))
					{
						oldOOSItemOfSongItemSetNewSongItem.getSongItemSet().remove(songItemSetNewSongItem);
						oldOOSItemOfSongItemSetNewSongItem = em.merge(oldOOSItemOfSongItemSetNewSongItem);
					}
				}
			}
			for (VLCItem VLCItemSetNewVLCItem : VLCItemSetNew)
			{
				if (!VLCItemSetOld.contains(VLCItemSetNewVLCItem))
				{
					OOSItem oldOOSItemOfVLCItemSetNewVLCItem = VLCItemSetNewVLCItem.getOOSItem();
					VLCItemSetNewVLCItem.setOOSItem(OOSItem);
					VLCItemSetNewVLCItem = em.merge(VLCItemSetNewVLCItem);
					if (oldOOSItemOfVLCItemSetNewVLCItem != null && !oldOOSItemOfVLCItemSetNewVLCItem.equals(OOSItem))
					{
						oldOOSItemOfVLCItemSetNewVLCItem.getVLCItemSet().remove(VLCItemSetNewVLCItem);
						oldOOSItemOfVLCItemSetNewVLCItem = em.merge(oldOOSItemOfVLCItemSetNewVLCItem);
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
				Integer id = OOSItem.getId();
				if (findOOSItem(id) == null)
				{
					throw new NonexistentEntityException("The oOSItem with id " + id + " no longer exists.");
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
			OOSItem OOSItem;
			try
			{
				OOSItem = em.getReference(OOSItem.class, id);
				OOSItem.getId();
			}
			catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The OOSItem with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			Set<SongItem> songItemSetOrphanCheck = OOSItem.getSongItemSet();
			for (SongItem songItemSetOrphanCheckSongItem : songItemSetOrphanCheck)
			{
				if (illegalOrphanMessages == null)
				{
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This OOSItem (" + OOSItem + ") cannot be destroyed since the SongItem " + songItemSetOrphanCheckSongItem + " in its songItemSet field has a non-nullable OOSItem field.");
			}
			Set<VLCItem> VLCItemSetOrphanCheck = OOSItem.getVLCItemSet();
			for (VLCItem VLCItemSetOrphanCheckVLCItem : VLCItemSetOrphanCheck)
			{
				if (illegalOrphanMessages == null)
				{
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This OOSItem (" + OOSItem + ") cannot be destroyed since the VLCItem " + VLCItemSetOrphanCheckVLCItem + " in its VLCItemSet field has a non-nullable OOSItem field.");
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			OOS oos = OOSItem.getOos();
			if (oos != null)
			{
				oos.getOOSItemSet().remove(OOSItem);
				oos = em.merge(oos);
			}
			em.remove(OOSItem);
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

	public List<OOSItem> findOOSItemEntities()
	{
		return findOOSItemEntities(true, -1, -1);
	}

	public List<OOSItem> findOOSItemEntities(int maxResults, int firstResult)
	{
		return findOOSItemEntities(false, maxResults, firstResult);
	}

	private List<OOSItem> findOOSItemEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<OOSItem> cq = em.getCriteriaBuilder().createQuery(OOSItem.class);
			cq.select(cq.from(OOSItem.class));
			TypedQuery<OOSItem> q = em.createQuery(cq);
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

	public OOSItem findOOSItem(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(OOSItem.class, id);
		}
		finally
		{
			em.close();
		}
	}

	public int getOOSItemCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<OOSItem> rt = cq.from(OOSItem.class);
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
