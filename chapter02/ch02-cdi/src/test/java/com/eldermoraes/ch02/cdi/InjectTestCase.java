package com.eldermoraes.ch02.cdi;

import static java.util.logging.Logger.getLogger;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static java.lang.Thread.sleep;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.jboss.shrinkwrap.api.asset.EmptyAsset.INSTANCE;
import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.eclipse.jgit.util.Paths;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.eldermoraes.ch02.cdi.profile.ImplAdmin;
import com.eldermoraes.ch02.cdi.profile.ImplOperator;
import com.eldermoraes.ch02.cdi.profile.Profile;
import com.eldermoraes.ch02.cdi.profile.ProfileType;
import com.eldermoraes.ch02.cdi.profile.UserProfile;
 
@RunWith(Arquillian.class)
public class InjectTestCase {
	private static final Logger logger = getLogger(InjectTestCase.class.getName());
	
    @Inject
    private User user;
    
    @Inject
    @Profile(ProfileType.ADMIN)
    private UserProfile userProfileAdmin;
    
    @Inject
    @Profile(ProfileType.OPERATOR)
    private UserProfile userProfileOperator;
    
    @Inject
    private UserProfile userProfileDefault;
    
    @Inject
    private Event<User> userEvent;
     
	@ArquillianResource
	private URL url;
	
	@Deployment
	public static Archive<?> createJavaDeployment() {
		final JavaArchive jar = create(JavaArchive.class, "inject-test.jar");
		jar.addPackages(true,Application.class.getPackage()); 
		jar.addAsManifestResource(INSTANCE, "beans.xml"); 
		return jar;
	}
	/**
	 * Tests default annotation in a jar archive
	 */
	@Test
	public void testInjectWriter() {
		logger.info("starting inject test");
		assertTrue("it takes the default annotated Admin", userProfileAdmin instanceof ImplAdmin);
		assertTrue("it takes the default annotated Operator", userProfileOperator instanceof ImplOperator);
		
		System.out.println(userProfileDefault.getClass().getCanonicalName());
		
		assertEquals("injected User's Name ", user.getName(), "Elder Moraes");
		assertEquals("injected User's Email ", user.getEmail(), "elder@eldermoraes.com");

		userEvent.fire(user);
        userEvent.fireAsync(user);
	}
	@Test
	public void testMagicNumber() throws Exception {
		logger.info("start rest receive messages test");
		String myResponse = invokeFuture(url + "webresources/userservice/getUser");
		  System.out.println("-+-------------------------");
	    System.out.println(myResponse);
		logger.info("end rest receive messages test");
	}
	private String invokeFuture(String url) {
		Client client = newClient();
		WebTarget target = client.target(url);
		final AsyncInvoker asyncInvoker = target.request().async();
		Future<Response> future = asyncInvoker.get();
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			logger.log(SEVERE, "error", e);
		} 
		
		String result =null;
		try {
			Response response = future.get();
			result = response.readEntity(String.class);
		}catch (InterruptedException | ExecutionException e) {
			logger.log(SEVERE, "error", e);
		}
		
		 
		return result;
	}
}
