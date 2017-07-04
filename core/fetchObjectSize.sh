#!/bin/bash

mvn clean install
cd target/classes
java -javaagent:../../ObjectSizeFetcher.jar com.ak.learning.core.TestObjectSizeFetcher
cd ../../

