#!/usr/bin/env bash
mvn clean install
cd target/classes
jar -cvfm wsinvoker.jar ./META-INF/MANIFEST.MF com/pearson/registrationassistant/client
cp wsinvoker.jar ../../
cd ../../

