# Smart Campus API CourseWork

Module: 5COSC022W – Client-Server Architectures  
Coursework: Smart Campus Sensor & Room Management API

## Report

### Question 1:

JAX-RS resource classes are made on a per request basis by default. With a new instance instantiated for each incoming request. This is unfortunate because it means resource classes can’t be used to keep data that’s meant to persist through requests.

To solve this, a DataStore class is used. Storing all data needed to persist across requests, making sure rooms, sensors and readings are still available. In a real system, you’d likely need thread safe connections to handle several requests at the same time

### Question 2:

Hypermedia is considered an advanced REST feature because it helps the clients know how to use the API directly without needing external documentation. The client can just start at the root endpoint and follow the links returned by the API.

In this project, the endpoint gives basic API metadata and links to the main resource collections. Making it easier to understand and navigate. 

### Question 3:

Returning IDs is lightweight. Reducing size significantly and thus bandwidth. It’s mainly useful when dealing with a lot of resources. It does mean that the client has to make more requests if they want more room detail however.

Returning full rooms means that the client has all that info immediately available. But makes the bandwidth and size a lot larger. For this project, returning full rooms is probably wiser because it doesn’t contain much data.

### Question 4:

Yes DELETE is idempotent. If a client deletes a room that exists. It is removed. If the same request is given again, the room doesn’t exist so a 404 error is given. Both of the responses may look different. But the server state hasn’t changed after the second delete request is given. So it's idempotent.

### Question 5:

“@Consumes(MediaType.APPLICATION_JSON)” tells JAX-RS to only accept requests in JSON format. So it’ll reject requests made in plain format or application/xml. Which is useful because the API is only designed to handle JSON and so there’s no interpretation errors to do with the request body.

### Question 6:

Using a Query parameter is a better design for filtering because the client is still requesting the same sensor collection, just with a filter applied. The resource hasn’t changed.

This is more flexible than applying the filter to the type like this: /sensors/type/CO2. As Query parameters are easier to expand later. And applying them to the path makes it difficult to search by more than one filter at once like searching for type and room. 

### Question 7:

The sub-resource locator pattern is useful for keeping the API organised. It separates nested logic into different classes rather than putting every sensor reading in one large sensor controller. Making code easy to understand, manage and expand on later. It’s also useful for separating unrelated sensor operations.

### Question 8:

A 422 error is more accurate because the request itself is valid and the endpoint exists, but the data is incorrect. So for example, the client sends a request to create a new sensor for a room, and it’s in valid JSON, but the room ID doesn’t exist. A 404 would suggest the endpoint or resource isn’t real. But that’s not the case. So It’s a 422 error not a 404.

### Question 9:
Exposing internal Java stack traces is a bad idea because they can reveal details about how the application is built internally, exposing details like class names, method names, file structure etc.
Attackers can use that information to understand the application intimately. Making it easier for them to identify weaknesses. So it's better to return generic error information.

### Question 10:

Using JAX-RS filters for logging is better because logging applies to the whole API, not just one endpoint. A filter allows the same logging logic to run automatically for every request and response.
This avoids repeating logging statements in every resource method, which keeps the code cleaner and easier to maintain. It also makes future changes simpler, because the logging behaviour only needs to be updated in one place.
