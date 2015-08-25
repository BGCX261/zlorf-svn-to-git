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
import org.zlorf.model.OOSItem;
import org.zlorf.model.VLCItem;

/**
 *
 * @author JaggardM
 */
public class VLCItemJpaController {

	public VLCItemJpaController()
	{
		emf = Persistence.createEntityManagerFactory("zlorfPU");
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(VLCItem VLCItem)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			OOSItem OOSItem = VLCItem.getOOSItem();
			if (OOSItem != null)
			{
				OOSItem = em.getReference(OOSItem.getClass(), OOSItem.getId());
				VLCItem.setOOSItem(OOSItem);
			}
			em.persist(VLCItem);
			if (OOSItem != null)
			{
				OOSItem.getVLCItemSet().add(VLCItem);
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

	public void edit(VLCItem VLCItem) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			VLCItem persistentVLCItem = em.find(VLCItem.class, VLCItem.getId());
			OOSItem OOSItemOld = persistentVLCItem.getOOSItem();
			OOSItem OOSItemNew = VLCItem.getOOSItem();
			if (OOSItemNew != null)
			{
				OOSItemNew = em.getReference(OOSItemNew.getClass(), OOSItemNew.getId());
				VLCItem.setOOSItem(OOSItemNew);
			}
			VLCItem = em.merge(VLCItem);
			if (OOSItemOld != null && !OOSItemOld.equals(OOSItemNew))
			{
				OOSItemOld.getVLCItemSet().remove(VLCItem);
				OOSItemOld = em.merge(OOSItemOld);
			}
			if (OOSItemNew != null && !OOSItemNew.equals(OOSItemOld))
			{
				OOSItemNew.getVLCItemSet().add(VLCItem);
				OOSItemNew = em.merge(OOSItemNew);
			}
			em.getTransaction().commit();
		}
		catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = VLCItem.getId();
				if (findVLCItem(id) == null)
				{
					throw new NonexistentEntityException("The vLCItem with id " + id + " no longer exists.");
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
			VLCItem VLCItem;
			try
			{
				VLCItem = em.getReference(VLCItem.class, id);
				VLCItem.getId();
			}
			catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The VLCItem with id " + id + " no longer exists.", enfe);
			}
			OOSItem OOSItem = VLCItem.getOOSItem();
			if (OOSItem != null)
			{
				OOSItem.getVLCItemSet().remove(VLCItem);
				OOSItem = em.merge(OOSItem);
			}
			em.remove(VLCItem);
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

	public List<VLCItem> findVLCItemEntities()
	{
		return findVLCItemEntities(true, -1, -1);
	}

	public List<VLCItem> findVLCItemEntities(int maxResults, int firstResult)
	{
		return findVLCItemEntities(false, maxResults, firstResult);
	}

	private List<VLCItem> findVLCItemEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<VLCItem> cq = em.getCriteriaBuilder().createQuery(VLCItem.class);
			cq.select(cq.from(VLCItem.class));
			TypedQuery<VLCItem> q = em.createQuery(cq);
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

	public VLCItem findVLCItem(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(VLCItem.class, id);
		}
		finally
		{
			em.close();
		}
	}

	public int getVLCItemCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<VLCItem> rt = cq.from(VLCItem.class);
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
