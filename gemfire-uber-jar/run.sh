#!/usr/bin/env bash
export CLASSPATH=$CLASSPATH:/apps/gemfire/gemfire-app.jar

java org.apache.geode.distributed.LocatorLauncher start locator1
echo "Locator started"

