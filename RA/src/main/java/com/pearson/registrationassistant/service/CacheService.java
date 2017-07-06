package com.pearson.registrationassistant.service;

/**
 * CacheService interface specifications.
 *
 * Created by abhaykulkarni on 08/12/16.
 */

public interface CacheService {
  void init();

  String getClientAbbrv(String client);
}
