package com.mobitel.MoBitelBackend.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mobitel.MoBitelBackend.dao.CategoryDAO;
import com.mobitel.MoBitelBackend.dao.ProductDAO;
import com.mobitel.MoBitelBackend.dao.SupplierDAO;
import com.mobitel.MoBitelBackend.model.Category;
import com.mobitel.MoBitelBackend.model.Product;
import com.mobitel.MoBitelBackend.model.Supplier;

@Configuration
@ComponentScan("com.mobitel.MobitelBackend")
@EnableTransactionManagement

public class DBConfig 
{
	@Bean(name = "dataSource")
	public DataSource getH2DataSource() 
	{
		System.out.println("Starting of the method getH2DataSource");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/Project");
		dataSource.setUsername("rahul2");
		dataSource.setPassword("rahul");
		System.out.println("Data Source Creation");
		return dataSource;
	}

	
	@Autowired
	@Bean(name="sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource)
	{
		System.out.println("---Hibernate Properties----");
		Properties prop=new Properties();
		prop.setProperty("hibernate.hbm2ddl.auto", "update");
		prop.put("hibernate.show_sql", "true"); //optional
		prop.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		System.out.println("---Hibernate Properties Created");
		
		System.out.println("---Local SessionFactory Builder Object Creation---");
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(getH2DataSource());
		sessionBuilder.addProperties(prop);
		System.out.println("---Factory Builder Object Created---");
		sessionBuilder.addAnnotatedClass(Category.class);
		sessionBuilder.addAnnotatedClass(Product.class);
		sessionBuilder.addAnnotatedClass(Supplier.class);
		System.out.println("Session Factory Object Creation");
		
		SessionFactory sessionFactory=sessionBuilder.buildSessionFactory();
		System.out.println("Session Factory Object Created");
		return sessionFactory;
	}
	
	@Autowired
	@Bean(name="transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory)
	{
		System.out.println("--Transaction manager Object Creation--");
		HibernateTransactionManager transactionManager=new HibernateTransactionManager(sessionFactory);
		System.out.println("--Transaction manager Object Created--");
		return transactionManager;
	}
	@Autowired
	@Bean(name="categoryDAO")
	public CategoryDAO getCategoryDAO(SessionFactory sessionFactory)
    {
		System.out.println("-- CategoryDAO Object Creation--");
		return new CategoryDAO(sessionFactory);
	}
	
	@Autowired
	@Bean(name="productDAO")
	public ProductDAO getProductDAO(SessionFactory sessionFactory)
    {
		System.out.println("-- ProductDAO Object Creation--");
		return new ProductDAO(sessionFactory);
	}
	
	@Autowired
	@Bean(name="supplierDAO")
	public SupplierDAO getSupplierDAO(SessionFactory sessionFactory)
    {
		System.out.println("-- SupplierDAO Object Creation--");
		return new SupplierDAO(sessionFactory);
	}
}
