Akka ActorSystem can be integrated with Spring ApplicationContext in three steps.

Firstly, the SpringActorProducer is used to create actors by getting them as Spring beans from the ApplicationContext.

Secondly, an Akka Extension is used to add additional functionality to the ActorSystem.
The SpringExtension uses Akka Props to create actors with the SpringActorProducer.

Thirdly, a Spring @Configuration is used to provide the ActorSystem as a Spring bean.


The WorkerActor is a statefull actor that receives and sends messages (they have to be immutable)
 with other actors inside the onReceive method. Don't forget to use the unhandled method
 if the received message doesn't match. Notice that actors have to be defined in Spring prototype scope.



  A Spring Boot CommandLineRunner is used to get a WorkerActor from the ActorSystem inside the ApplicationContext,
   to send sequence of requests and receive a response, and finally to terminate the ActorSystem


   <!-- Reference Link https://www.linkedin.com/pulse/spring-boot-akka-part-1-aliaksandr-liakh/   -->