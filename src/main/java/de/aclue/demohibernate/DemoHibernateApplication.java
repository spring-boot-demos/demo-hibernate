package de.aclue.demohibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoHibernateApplication implements CommandLineRunner {
	
	@PersistenceContext
	EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(DemoHibernateApplication.class, args);
	}
	
	@Override
	@Transactional
	public void run(String... strings) throws Exception {
		List.of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
				.map(name -> {
					String[] split = name.split(" ");
					return new Customer(split[0], split[1]);
					})
				.forEach(c -> entityManager.persist(c));
		
		TypedQuery<Customer> query = entityManager.createQuery("from Customer where first_name = :firstname", Customer.class);
		query.setParameter("firstname", "Josh");
		List<Customer> customers = query.getResultList();
		
		customers.forEach(c -> System.out.println(c));
	}

}
