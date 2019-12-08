package com.hibernateinfo.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.hibernateinfo.util.HibernateUtil;

/**
 * @author Pasha Sadi
 * Remember the golden rule: readable code is often faster code. 
 * Produce readable code first and only change it if it proves to be too slow.
 */
public class ClientTest2UpdateEmployeeEmailById {

	public static void main(String[] args) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		updateEmployeeEmailById(sessionFactory);
	}

	private static void updateEmployeeEmailById(SessionFactory sessionFactory) {

		int empId = 6;
		String newEmail = "tester@gmail.com";
		try(Session session = sessionFactory.openSession()) 
		{
			String hql = "UPDATE Employee set email=:newEmail WHERE employeeId=:empId"; 
						
			Query<?> query = session.createQuery(hql);
			query.setParameter("newEmail", newEmail);
			query.setParameter("empId", empId);
			
			session.beginTransaction();
			int executeUpdate = query.executeUpdate();
			session.getTransaction().commit();
			if(executeUpdate>0)
				System.out.println( "Employee Email is updated successfully!");						
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
<property name="hibernate.hbm2ddl.auto">update</property>
Hibernate: 
    update
        employee_table 
    set
        email=? 
    where
        employee_id=?
Employee Email is updated successfully! 
*/