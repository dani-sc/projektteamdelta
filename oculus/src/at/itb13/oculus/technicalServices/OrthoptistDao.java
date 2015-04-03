package at.itb13.oculus.technicalServices;
// default package
// Generated 03.04.2015 15:26:51 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Orthoptist.
 * @see .Orthoptist
 * @author Hibernate Tools
 */
public class OrthoptistDao {

	private static final Logger logger = LogManager.getLogger(OrthoptistDao.class.getName());
	
	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Orthoptist transientInstance) {
		log.debug("persisting Orthoptist instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Orthoptist instance) {
		log.debug("attaching dirty Orthoptist instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Orthoptist instance) {
		log.debug("attaching clean Orthoptist instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Orthoptist persistentInstance) {
		log.debug("deleting Orthoptist instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Orthoptist merge(Orthoptist detachedInstance) {
		log.debug("merging Orthoptist instance");
		try {
			Orthoptist result = (Orthoptist) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Orthoptist findById(java.lang.Integer id) {
		log.debug("getting Orthoptist instance with id: " + id);
		try {
			Orthoptist instance = (Orthoptist) sessionFactory
					.getCurrentSession().get("Orthoptist", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Orthoptist> findByExample(Orthoptist instance) {
		log.debug("finding Orthoptist instance by example");
		try {
			List<Orthoptist> results = (List<Orthoptist>) sessionFactory
					.getCurrentSession().createCriteria("Orthoptist")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}