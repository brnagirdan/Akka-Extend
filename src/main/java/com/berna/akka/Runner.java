package com.berna.akka;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.berna.akka.actor.WorkerActor;
import com.berna.akka.di.SpringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

/**
 *  A Spring Boot CommandLineRunner is used to get a WorkerActor from the ActorSystem
 *  inside the ApplicationContext, to send sequence of requests and receive a response,
 *  and finally to terminate the ActorSystem
 */
@Component
public class Runner implements CommandLineRunner {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    public void run(String... strings) throws Exception {
        try{
            ActorRef workerActor= actorSystem.actorOf(springExtension.props("workerActor"), "worker-actor");

            workerActor.tell(new WorkerActor.Request(), null);
            workerActor.tell(new WorkerActor.Request(),null);
            workerActor.tell(new WorkerActor.Request(),null);

            FiniteDuration duration=FiniteDuration.create(1, TimeUnit.SECONDS);
            Future<Object> awaitable= Patterns.ask(workerActor, new WorkerActor.Response(), Timeout.durationToTimeout(duration));

            logger.info("Response: " + Await.result(awaitable,duration));

        }finally {
            actorSystem.terminate();
            Await.result(actorSystem.whenTerminated(), Duration.Inf());

        }

    }
}
