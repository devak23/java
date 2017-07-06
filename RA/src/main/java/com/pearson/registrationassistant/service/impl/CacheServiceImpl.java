package com.pearson.registrationassistant.service.impl;

import com.pearson.registrationassistant.service.CacheService;
import com.pearson.registrationassistant.util.AppConstantsEnum;
import com.pearson.registrationassistant.util.PropertiesHelper;
import org.apache.log4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of CacheService
 *
 * Created by abhaykulkarni on 08/12/16.
 */
@ApplicationScoped
public class CacheServiceImpl implements CacheService {
  private Logger logger = Logger.getLogger(CacheServiceImpl.class);
  private final Map<String, String> clientMap = new HashMap<>();

  @Inject
  private PropertiesHelper propertiesHelper;

  @Override
  public void init() {
    String csv = propertiesHelper.getProperty(AppConstantsEnum.CLIENT_ABBR.getValue());
    Arrays.asList(csv.split(",")).forEach(element -> {
      String[] kv = element.split(":");
      clientMap.put(kv[0],kv[1]);
    });

    logger.info("cache: " + clientMap);
  }

  public String getClientAbbrv(String key) {
    String s = clientMap.get(key);
    logger.info("cacheMap: " + clientMap + " returning value for key = " + key + "... value = " + s);
    return s;
  }
}
