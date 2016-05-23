package com.java.store.java.hibernate.dao;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import com.java.store.java.hibernate.entity.User;

/**
* @author ltao
* @version 1.0 创建时间：2016年5月20日 上午11:40:25
* 
*/
@Component("userDao")
public class UserDaoImpl extends GenericHibernateDao<User, Integer> implements UserDao{

	@Override
	public User getUserByPro(String proName, Object[] params, Class clazz) {
		StringBuffer pname = new StringBuffer("{call ");
        pname.append(proName).append("(");
        for (int i = 0; i < params.length; i++) {
            pname.append(i==0?"?":",?");
        }
        pname.append(")}");
        
        SQLQuery query = getHibernateTemplate().createSQLQuery(pname.toString());
        
        //参数填入
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
        //数据返回格式
        if(clazz != null){
            query.setResultTransformer(Transformers.aliasToBean(clazz));
        }else{
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        User list=(User) query.list().get(0); //这里报错
        return list;

	}
}
