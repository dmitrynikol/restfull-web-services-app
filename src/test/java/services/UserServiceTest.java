package services;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.dmitrynikol.model.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

/**
 * Integration test for UserService service.
 * 
 * @author Dmitry Nikolaenko
 *
 */
public class UserServiceTest extends JerseyTest {
	
	private final WebResource webResource = client().resource("http://localhost:8080/RESTfulApp/rest");

	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}
	
	@Test
	public void testGetStub() {
		String result = webResource.path("/user/stub").get(String.class);
		Assert.assertEquals("stub", result);
	}
	
	@Test
	public void testCreateUser() throws JSONException {
		final User testUser = new User("123", "Tom", "Jenkins", "tom.jenkins@gmail.com");
		final ClientResponse response = webResource.path("/user/create").
				type(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).
				post(ClientResponse.class, testUser);
		final User user = response.getEntity(new GenericType<User>(){});
		
		Assert.assertEquals("123", user.getId());
		Assert.assertEquals("Tom", user.getFirstName());
		Assert.assertEquals("Jenkins", user.getLastName());
		Assert.assertEquals("tom.jenkins@gmail.com", user.getEmail());
		// check HTTP status code
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testDeleteUser() {
		final ClientResponse response = webResource.path("/user/id/12345").
				type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
		Assert.assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testUpdateUser() {
		User testUser = new User("12345", "Tom", "Jenkins", "tom.jenkins@gmail.com");
		final ClientResponse response = webResource.path("/user/id/12345").
				type(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON).
				put(ClientResponse.class, testUser);
		final User user = response.getEntity(new GenericType<User>(){});
		
		Assert.assertEquals("12345", user.getId());
		Assert.assertEquals("Tom", user.getFirstName());
		Assert.assertEquals("Jenkins", user.getLastName());
		Assert.assertEquals("tom.jenkins@gmail.com", user.getEmail());
	}
	
	@Test
	public void testGetAllUsers1() throws JSONException {
		final ClientResponse response = webResource.path("/user/all").
				accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		final JSONArray resultArray = response.getEntity(JSONArray.class);
		Assert.assertEquals(200, response.getStatus());
	}
	
	/**
	 * Jersey using JAXB for the marshalling/unmarshalling process, 
	 * and JAXB JSON processor is not standard.
	 * So, I chose Jackson instead of JAXB.
	 * 
	 * @throws JSONException
	 */
	@Test
	public void testGetAllUsers2() throws JSONException {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(clientConfig);
		final List<User> result = client.resource("http://localhost:8080/RESTfulApp/rest/user/all").
				get(new GenericType<List<User>>(){});
		
		final User user = result.get(0);
		Assert.assertEquals("1", user.getId());
		Assert.assertEquals("Tom", user.getFirstName());
		Assert.assertEquals("Jenkins", user.getLastName());
		Assert.assertEquals("tom.jenkins@gmail.com", user.getEmail());
		Assert.assertFalse(result.isEmpty());
	}
	
	@Test
	public void testGetUserById1() throws JSONException {
		JSONObject json = webResource.path("/user/id/12345").get(JSONObject.class);
		Assert.assertEquals("12345", json.get("id"));
		Assert.assertEquals("Max", json.get("firstName"));
		Assert.assertEquals("Liano", json.get("lastName"));
		Assert.assertEquals("maxliano@gmail.com", json.get("email"));
	}
	
	@Test
	public void testGetUserById2() throws JSONException {
		final ClientResponse response = webResource.path("/user/id/12345").
				accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		// GenericType<Type> restore and map into User.java class
		final User user = response.getEntity(new GenericType<User>(){});
		
		Assert.assertEquals("12345", user.getId());
		Assert.assertEquals("Max", user.getFirstName());
		Assert.assertEquals("Liano", user.getLastName());
		Assert.assertEquals("maxliano@gmail.com", user.getEmail());
		// check HTTP status code
		Assert.assertEquals(200, response.getStatus());
	}
}
