/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import org.zlorf.model.OOS;

/**
 *
 * @author JaggardM
 */
public class OOSJpaController {

	public OOSJpaController()
	{
		emf = Persistence.createEntityManagerFactory("zlorfPU");
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(OOS OOS)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(OOS);
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

	@SuppressWarnings("AssignmentToMethodParameter")
	public void edit(OOS oos) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			oos = em.merge(oos);
			em.getTransaction().commit();
		}
		catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = oos.getId();
				if (findOOS(id) == null)
				{
					throw new NonexistentEntityException("The oOS with id " + id + " no longer exists.");
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
			OOS OOS;
			try
			{
				OOS = em.getReference(OOS.class, id);
				OOS.getId();
			}
			catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The OOS with id " + id + " no longer exists.", enfe);
			}
			em.remove(OOS);
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

	public List<OOS> findOOSEntities()
	{
		return findOOSEntities(true, -1, -1);
	}

	public List<OOS> findOOSEntities(int maxResults, int firstResult)
	{
		return findOOSEntities(false, maxResults, firstResult);
	}

	private List<OOS> findOOSEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<OOS> cq = em.getCriteriaBuilder().createQuery(OOS.class);
			cq.select(cq.from(OOS.class));
			TypedQuery<OOS> q = em.createQuery(cq);
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

	public OOS findOOS(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(OOS.class, id);
		}
		finally
		{
			em.close();
		}
	}

	public int getOOSCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
			Root<OOS> rt = cq.from(OOS.class);
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
