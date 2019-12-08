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
public class ClientTest3DeleteEmployeeById {

	public static void main(String[] args) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		deleteEmployeeById(sessionFactory);
	}

	private static void deleteEmployeeById(SessionFactory sessionFactory) {

		int empId = 7;
		try(Session session = sessionFactory.openSession()) 
		{
			String hql = "DELETE From Employee WHERE employeeId=:empId"; 
						
			Query<?> query = session.createQuery(hql);
			query.setParameter("empId", empId);
			
			session.beginTransaction();
			int executeUpdate = query.executeUpdate();
			session.getTransaction().commit();
			if(executeUpdate>0)
				System.out.println( "Employee with id: "+empId + " is  deleted successfully!");						
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
<property name="hibernate.hbm2ddl.auto">update</property>
Hibernate: 
    delete 
    from
        employee_table 
    where
        employee_id=?
Employee with id: 7 is  deleted successfully!

*/
