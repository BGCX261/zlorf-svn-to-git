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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.zlorf.controller.jpa.exceptions.NonexistentEntityException;
import org.zlorf.model.ColourItem;

/**
 *
 * @author JaggardM
 */
public class ColourItemJpaController {

	public ColourItemJpaController()
	{
		emf = Persistence.createEntityManagerFactory("zlorfPU");
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(ColourItem colourItem)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(colourItem);
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

	public void edit(ColourItem colourItem) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			colourItem = em.merge(colourItem);
			em.getTransaction().commit();
		}
		catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = colourItem.getId();
				if (findColourItem(id) == null)
				{
					throw new NonexistentEntityException("The colourItem with id " + id + " no longer exists.");
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
			ColourItem colourItem;
			try
			{
				colourItem = em.getReference(ColourItem.class, id);
				colourItem.getId();
			}
			catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The colourItem with id " + id + " no longer exists.", enfe);
			}
			em.remove(colourItem);
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

	public List<ColourItem> findColourItemEntities()
	{
		return findColourItemEntities(true, -1, -1);
	}

	public List<ColourItem> findColourItemEntities(int maxResults, int firstResult)
	{
		return findColourItemEntities(false, maxResults, firstResult);
	}

	private List<ColourItem> findColourItemEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(ColourItem.class));
			Query q = em.createQuery(cq);
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

	public ColourItem findColourItem(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(ColourItem.class, id);
		}
		finally
		{
			em.close();
		}
	}

	public int getColourItemCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<ColourItem> rt = cq.from(ColourItem.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		}
		finally
		{
			em.close();
		}
	}

}
