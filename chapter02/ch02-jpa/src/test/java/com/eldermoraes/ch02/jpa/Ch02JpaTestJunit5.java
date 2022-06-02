//package com.eldermoraes.ch02.jpa;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//import java.util.Properties;
//import javax.ejb.EJB;
//import javax.ejb.embeddable.EJBContainer;
//import javax.naming.NamingException;
//import org.apache.openejb.jee.EjbJar;
//import org.apache.openejb.jee.StatefulBean;
//import org.apache.openejb.jee.StatelessBean;
//import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
////import org.apache.openejb.junit5.RunWithApplicationComposer;
//import org.apache.openejb.testing.Configuration;
//import org.apache.openejb.testing.Module;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import javax.annotation.Resource;
//import javax.ejb.EJB;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
//import java.util.List;
//import java.util.Properties;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// * @author eldermoraes
// */
////@RunWithApplicationComposer
//public class Ch02JpaTest {
//	public Ch02JpaTest() {
//	}
//
//	@EJB
//	private UserBean userBean;
//	private EJBContainer ejbContainer;
//    
//    
//   
////    @Resource
////    private UserTransaction userTransaction;
////
////    @PersistenceContext
////    private EntityManager entityManager;
////    
////    @Module
////    public PersistenceUnit persistence() {
////        PersistenceUnit unit = new PersistenceUnit("ch02-jpa-pu");
////        unit.setJtaDataSource("movieDatabase");
////        unit.setNonJtaDataSource("movieDatabaseUnmanaged");
////        unit.getClazz().add(UserBean.class.getName());
////        unit.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
////        return unit;
////    }
////    
////    @Module
////    public EjbJar beans() {
////        EjbJar ejbJar = new EjbJar("user-beans");
////        ejbJar.addEnterpriseBean(new StatelessBean(UserBean.class));
////        return ejbJar;
////    }
////
////    @Configuration
////    public Properties config() throws Exception {
////        Properties p = new Properties();
////        p.put("userDb", "new://Resource?type=DataSource");
////        p.put("userDb.JdbcDriver", "org.hsqldb.jdbcDriver");
////        p.put("userDb.JdbcUrl", "jdbc:hsqldb:mem:userdatabase");
////        return p;
////    }
//    
////    @Test
////    public void test1() throws Exception {
////        userTransaction.begin();
////        try {
////        	User user  = new User(null, "Elder Moraes", "elder@eldermoraes.com");           
////            entityManager.persist(user); 
////            user.setName("John Doe"); 
////            User userDb = userBean.findById(1L);
////            assertEquals(userDb.getName(), "John Doe"); 
////        } finally {
////            userTransaction.commit();
////        }
////    }
//    
//    @BeforeEach
//    public void setUp() throws NamingException {
//        Properties p = new Properties();
//        p.put("userDb", "new://Resource?type=DataSource");
//        p.put("userDb.JdbcDriver", "org.hsqldb.jdbcDriver");
//        p.put("userDb.JdbcUrl", "jdbc:hsqldb:mem:userdatabase");
//
//        ejbContainer = EJBContainer.createEJBContainer(p);
//        ejbContainer.getContext().bind("inject", this);
//    }
//    
//    @AfterEach
//    public void tearDown() {
//        ejbContainer.close();
//    }
//    
//    @Test
//    public void persistData() throws Exception{
//        User user  = new User(null, "Elder Moraes", "elder@eldermoraes.com");
//        
//        userBean.add(user);
//        user.setName("John Doe");
//        userBean.update(user);
//        
//        User userDb = userBean.findById(1L);
//        assertEquals(userDb.getName(), "John Doe");        
//    }
//    
//}
