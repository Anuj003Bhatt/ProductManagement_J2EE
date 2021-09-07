# ProductManagement_J2EE

Preface
-------
The aim of this project is to provide with the necessary standards that needs to be followed while working on a Java specific code.

A lot of times it is seen that the developer do not have a clear idea of what is going underneath the platform on which they are working and hence they make code which is error prone.

For example,
In a general scenario, when working with databases or other OS resources like files, often it is seen that the resource are not managed properly. Ideally, every resource must be acquired from OS and locked with proper locks and then used. And eventually and most importantly these resources must be released.

What happens in most scenarios is that the developer do only part of what is mentioned above and sometime none.

Here is what can happen
1. If resources are not locked properly some multithreaded change might either corrupt resources or might throw concurrent modification exception. Although this does depend on the platform developer is working but in mordern time the multithreading is mostly in all platforms.
2. If resources are not closed properly there will be memory leaks. Now this is crtical and it does not even depend on the platform (i.e., even if the platform is not mutithreaded resources must be without exception closed after user.)

Let us take an example on the criticality of the second statement.

Suppose one is working with database and whenever user queries for data the code establishes a connection gets data and returns it to user. (without closing)

Now what will happen is,
The DB is keeping the connection open because we have not closed it.
Now after a number of user queries, the list of open connections will cross a threshold (depending on the database) and the database will no longer be able to accept further connections.

Now the impact this has on your application is this,

**Application is DOWN !!
With dangling connections to DB which cannot be closed !!
**

This is unacceptable in any case. So extreme precautions needs to be take.

There can be a lot more examples like above. 

Also there might be times when we need to keep a pool of connections open because establishing a new connection for each query is not at all a very good approach. I took the above example to make the reader understand the impact of open connections which can easily occur in a enterprise grade application where we have 1000s of modules all interacting with DB for their specific use case.


This open source project was created keeping the above points and a lot more...

It implements a database connection interface in layers where there is a:
1. Bottom layer managing connection pools, 
2. Middle layer interacting with the database queries, 
3. Higher lever interacting wtih functional objects required for business logic

It also implements proper exception handling mechanishm and logging modules which are very critical part of any enterprise grade project.


