Example of RESTful web services with JAX-RS
=========================

This is a little explanation a REST architecture and we will build a simple RESTfull application with JAX-RS library called Jersey.
Representational State Transfer or REST is a simple way to organize interactions between independent system. It can be used wherever 
HTTP can. One of the idea of the REST that application should have specific purposes, like retrieving, adding, deleting or updating 
data(HTTP service request). And actions should be performed no more than one at a time.
 - POST - create a new resource(resource is contained in the body of the POST request)
 - GET - retrieves a resource from the server
 - PUT - updates the state of a known resource
 - DELETE - deletes a resource on the server
      
Here’re some HTTP response codes which are often used with REST.
 - 200 ok - indicates that request was successful
 - 201 created - request was successful and a resource was created
 - 400 bad request - request was malformed
 - 401 unauthorized - must perform authentication before accessing the resource
 - 404 not found - resource could not be found
 - 500 internal server error
     
First, create a standard web project from maven archetype:
      mvn archetype:generate -DgroupId=com.dmitrynikol.rest -DartifactId=RESTfulApp -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
Now run a command to add dependencies in project, eclipse can resolve them, we just convert maven based Java project into to support Eclipse IDE.
     mvn eclipse:eclipse -Dwtpversion=2.0
After that you will see two new files “.classpath” and “.project”. And now you can easily import a project into your Eclipse workspace. 
So, now we have a maven web stub project.

* Here is the link to the [Maven pom.xml](https://github.com/dmitrynikol/restfull-web-services-app/blob/master/pom.xml) file with all dependencies that we need.
* [POJO class User](https://github.com/dmitrynikol/restfull-web-services-app/blob/master/src/main/java/com/dmitrynikol/model/User.java) object that mapping to JSON via JAXB with @XmlRootElement annotation. 

Time to create [UserService class](https://github.com/dmitrynikol/restfull-web-services-app/blob/master/src/main/java/com/dmitrynikol/services/UserService.java), that represent a Jersey resources as an POJO and will be manipulated by different HTTP methods. Service class contains many annotations to create different behaviours.
And Jersey provides a set of Java annotations that can be used to define the web service structure.
 - @Path - configure the URL pattern the class will handle
 - @Post, @GET, @PUT and @DELETE - method will answer to the HTTP POST, GET, PUT and DELETE request
 - @Produces - specifies the MIME type of the response that is returned to the client
 - @Consumes - specifies the content type that the service will accept as input
 - @QueryParam - mark a field that will be extracted from the URL in a GET request
 - @DefaultValue - mark the value that will be used by default
 - @PathParam - mark a field that will be extracted from a field in the URL path 
 
And don’t forget to add com.sun.jersey.api.json.POJOMappingFeature to [web.xml](https://github.com/dmitrynikol/restfull-web-services-app/blob/master/src/main/webapp/WEB-INF/web.xml) file that integrate JSON with Jersey. It will make Jersey support JSON/object mapping. 

That’s all. Now we’re ready to start the rest service with tomcat instance via command - mvn tomcat:run

