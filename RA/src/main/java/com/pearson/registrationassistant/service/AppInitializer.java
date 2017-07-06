package com.pearson.registrationassistant.service;

import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * The class that loads the first when the application starts
 *
 * Created by abhaykulkarni on 08/12/16.
 */

@ApplicationScoped
public class AppInitializer {
  private Logger logger = Logger.getLogger(AppInitializer.class);

  @Inject
  private CacheService cacheService;

  public void init(@Observes @Initialized(ApplicationScoped.class) Object obj) {
    cacheService.init();
  }
}
