package com.berna.akka.di;

import akka.actor.Extension;
import akka.actor.Props;
import com.berna.akka.di.SpringActorProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

// Actor systeme extension ile yeni özellikler ekliyoruz.
@Component
public class SpringExtension implements Extension {
    private ApplicationContext applicationContext;

    public void initialize(ApplicationContext applicationContext){
        this.applicationContext=applicationContext;
    }


    // actor procuder classını alıp, actor üretiyoruz.
    public Props props(String actorBeanName){
      return Props.create(SpringActorProducer.class,applicationContext, actorBeanName);
    }
}
