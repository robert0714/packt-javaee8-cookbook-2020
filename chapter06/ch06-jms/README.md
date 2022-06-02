# Steps
1. We will begin by configuring our messaging service in GlassFish/Payara 5. Once the server is up and running, go to ``http://localhost:8080``.
2. Now click on the Go to the **Administration Console** link, or, if you prefer, go straight to ``http://localhost:4848/``.   
Then, follow this path in the left menu:   
**Resources | JMS Resources | Connection Factories**
3. Click on the New button. When the page is open, fill in the **General Settings** section fields like this:  
  * **JNDI Name**: jms/JmsFactory  
  * **Resource Type**: javax.jms.ConnectionFactory  
    We will not touch the **Pool Settings** section here, so just click on the **OK** button to register your new factory.
4. Now follow this path in the left menu:   
   **Resources | JMS Resources | Destination Resources**
5. Click on the New button. When the page is open, fill in the section fields like this:  
  * **JNDI Name**: jms/JmsQueue
  * **Physical Destination Name**: JmsQueue
  * **ResourceType**: javax.jms.Queue
  
## Test
To try it, just open this URL in your browser:

```shell
http://localhost:8080/ch06-jms/QueueSenderServlet
```