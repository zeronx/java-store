package com.java.store.java.hibernate.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GenericHibernateDao 继承 HibernateDao，简单封装 HibernateTemplate 各项功能，
 * 简化基于Hibernate Dao 的编写。
 * 
 *
 * @author ltao
 * @version 1.0 创建时间：2016年5月20日 上午11:38:37
 * 
 */
 
@SuppressWarnings("unchecked")
@Component
public class GenericHibernateDao<T extends Serializable, PK extends Serializable> implements GenericDao<T, PK> {
    // 实体类类型(由构造方法自动赋值)
    private Class<T> entityClass;
    private SessionFactory sessionFactory;
    @Autowired
    public final void setSessionFactory(SessionFactory sessionFactory) {
    	this.sessionFactory = sessionFactory;
	}
    
    public final SessionFactory getSessionFactory() {
    	return this.sessionFactory;
    }
    // 构造方法，根据实例类自动获取实体类类型
    public GenericHibernateDao() {
        this.entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }

    public Session getHibernateTemplate() {
    	return this.getSessionFactory().getCurrentSession();
    }
    // -------------------- 基本检索、增加、修改、删除操作 --------------------

    // 根据主键获取实体。如果没有相应的实体，返回 null。
    public T get(PK id) {
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    // 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
    public T getWithLock(PK id, LockMode lock) {
        T t = (T) getHibernateTemplate().get(entityClass, id, lock);
        if (t != null) {
            this.flush(); // 立即刷新，否则锁不会生效。
        }
        return t;
    }

    // 根据主键获取实体。如果没有相应的实体，抛出异常。
    public T load(PK id) {
        return (T) getHibernateTemplate().load(entityClass, id);
    }

    // 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
    public T loadWithLock(PK id, LockMode lock) {
        T t = (T) getHibernateTemplate().load(entityClass, id, lock);
        if (t != null) {
            this.flush(); // 立即刷新，否则锁不会生效。
        }
        return t;
    }

    // 更新实体
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    // 存储实体到数据库
    public void save(T entity) {
        getHibernateTemplate().save(entity);
    }

    // saveWithLock()？

    // 增加或更新实体
    public void saveOrUpdate(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    // 增加或更新集合中的全部实体
    public void saveOrUpdateAll(Collection<T> entities) {
        getHibernateTemplate().saveOrUpdate(entities);
    }

    // 删除指定的实体
    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    // 根据主键删除指定实体
    public void deleteByKey(PK id) {
        this.delete(this.load(id));
    }

    // 根据主键加锁并删除指定的实体
    public void deleteByKeyWithLock(PK id, LockMode lock) {
        this.deleteWithLock(this.load(id), lock);
    }

    // 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
    public List<T> findEqualByEntity(T entity, String[] propertyNames) {
        Criteria criteria = this.createCriteria();
        Example exam = Example.create(entity);
        exam.excludeZeroes();
        String[] defPropertys = getSessionFactory().getClassMetadata(
                entityClass).getPropertyNames();
        for (String defProperty : defPropertys) {
            int ii = 0;
            for (ii = 0; ii < propertyNames.length; ++ii) {
                if (defProperty.equals(propertyNames[ii])) {
                    criteria.addOrder(Order.asc(defProperty));
                    break;
                }
            }
            if (ii == propertyNames.length) {
                exam.excludeProperty(defProperty);
            }
        }
        criteria.add(exam);
        return (List<T>) criteria.list();
    }

    // 使用指定的实体及属性检索（满足属性 like 串实体值）数据
    public List<T> findLikeByEntity(T entity, String[] propertyNames) {
        Criteria criteria = this.createCriteria();
        for (String property : propertyNames) {
            try {
                Object value = PropertyUtils.getProperty(entity, property);
                if (value instanceof String) {
                    criteria.add(Restrictions.like(property, (String) value,
                            MatchMode.ANYWHERE));
                    criteria.addOrder(Order.asc(property));
                } else {
                    criteria.add(Restrictions.eq(property, value));
                    criteria.addOrder(Order.asc(property));
                }
            } catch (Exception ex) {
                // 忽略无效的检索参考数据。
            }
        }
        return (List<T>) criteria.list();
    }

    // 使用指定的检索标准获取满足标准的记录数
    public Integer getRowCount(DetachedCriteria criteria) {
        criteria.setProjection(Projections.rowCount());
        List list = this.findByCriteria(criteria, 0, 1);
        return (Integer) list.get(0);
    }

    // 使用指定的检索标准检索数据，返回指定统计值(max,min,avg,sum)
    public Object getStatValue(DetachedCriteria criteria, String propertyName,
            String StatName) {
        if (StatName.toLowerCase().equals("max"))
            criteria.setProjection(Projections.max(propertyName));
        else if (StatName.toLowerCase().equals("min"))
            criteria.setProjection(Projections.min(propertyName));
        else if (StatName.toLowerCase().equals("avg"))
            criteria.setProjection(Projections.avg(propertyName));
        else if (StatName.toLowerCase().equals("sum"))
            criteria.setProjection(Projections.sum(propertyName));
        else
            return null;
        List list = this.findByCriteria(criteria, 0, 1);
        return list.get(0);
    }

    // -------------------------------- Others --------------------------------

    // 加锁指定的实体
    public void lock(T entity, LockMode lock) {
        getHibernateTemplate().lock(entity, lock);
    }

    // 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
    public void flush() {
        getHibernateTemplate().flush();
    }

	@Override
	public List<T> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateWithLock(T entity, LockMode lock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteWithLock(T entity, LockMode lock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Collection<T> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int bulkUpdate(String queryString) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int bulkUpdate(String queryString, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List find(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List find(String queryString, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByNamedParam(String queryString, String[] paramNames, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByNamedQuery(String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByNamedQuery(String queryName, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator iterate(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator iterate(String queryString, Object[] values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeIterator(Iterator it) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DetachedCriteria createDetachedCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Criteria createCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCriteria(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(Object proxy) {
		// TODO Auto-generated method stub
		
	}
}