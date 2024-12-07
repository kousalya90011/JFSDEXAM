package com.klef.jfsd.exam;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ClientDemo 
{
	public static void main(String arg[])
	{
		ClientDemo cd = new ClientDemo();
		cd.addCustomer();
		cd.hcqloperations();
		
	}
	
	public void addCustomer()
	{
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		
		Transaction t = session.beginTransaction();
		
		Customer customer =  new Customer();
		customer.setName("JFSD Exam");
		customer.setAge(20.5);
		customer.setEmail("exam@gmail.com");
		customer.setLocation("Vijayawada");
		
		session.persist(customer);
		t.commit(); 
        System.out.println("Customer Added Successfully");
		
		session.close();
		sf.close();
	}
	
	public void hcqloperations()
	{
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory sf = cfg.buildSessionFactory();
		Session session = sf.openSession();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
	
		Root<Customer> root = cq.from(Customer.class); 
		
		cq.select(root).where(cb.lessThan(root.get("age"), 50));
//		cq.select(root).where(cb.greaterThan(root.get("age"), 50));
//		cq.select(root).where(cb.notEqual(root.get("name"),"CSE"));
//		cq.select(root).where(cb.like(root.get("name"),"E%"));  //starts with C ,lenght is infinite
//		cq.select(root).where(cb.between(root.get("age"), 30, 50));
		
		
		System.out.println("Cutomer Objects with Comparision Criteria");
		List<Customer> customers = session.createQuery(cq).getResultList();
		//System.out.println("Students Count="+customers.size());
		for(Customer c :customers) 
		{   //you can also use getter methods of Student Object(s)
			System.out.println(c.toString());
		}
		
		session.close();
		sf.close();
		
	}
}
