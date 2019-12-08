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
public class ClientTest1InsertEmployeeHQL {

	public static void main(String[] args) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		insertEmployee(sessionFactory);
	}

	private static void insertEmployee(SessionFactory sessionFactory) {

		//INSERT INTO TABLE_NAME SELECT ... is supported by hibernate but
		//INSERT INTO TABLE_NAME VALUES is not supported
		//to achieve this, we manually created backup_employee_table 
		//with values and a BackupEmployee class. register new class in hibernat.cfg
		//and read data from it and save it into employee_table
		//you can also use native/raw sql for insertion.
		try(Session session = sessionFactory.openSession()) 
		{
			String hql = "INSERT INTO Employee(employeeName, doj, salary, bonus, designation, email)"+
			"SELECT employeeName, doj, salary, bonus, designation, email FROM BackupEmployee"; 
						
			Query<?> query = session.createQuery(hql);
			session.beginTransaction();
			int executeUpdate = query.executeUpdate();
			if(executeUpdate>0)
				System.out.println(executeUpdate + " records are inserted successfully!");
						
			session.getTransaction().commit();			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
/*
<property name="hibernate.hbm2ddl.auto">update</property>
Hibernate: 
    insert 
    into
        employee_table
        ( employee_id, employee_name, date_of_joining, salary, bonus, designation, email ) select
            hibernate_sequence.nextval,
            backupempl0_.employee_name as col_0_0_,
            backupempl0_.date_of_joining as col_1_0_,
            backupempl0_.salary as col_2_0_,
            backupempl0_.bonus as col_3_0_,
            backupempl0_.designation as col_4_0_,
            backupempl0_.email as col_5_0_ 
        from
            backup_employee_table backupempl0_
1 records are inserted successfully! 
*/
