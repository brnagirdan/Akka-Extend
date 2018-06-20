package com.berna.akka.actor;


import akka.actor.UntypedActor;
import com.berna.akka.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The WorkerActor is a statefull actor that receives and sends messages
 */
@Component
@Scope("prototype")
public class WorkerActor extends UntypedActor {

    @Autowired
    private BusinessService businessService;

    private int count = 0;


    public void onReceive(Object message) throws Throwable {
        if (message instanceof Request) {
            businessService.perform(this + " " + (++count));
        } else if (message instanceof Response) {
            getSender().tell(count, getSelf());
        } else {
            unhandled(message);
        }
    }

    public static class Request {
    }

    public static class Response {
    }
}
