#!/bin/bash

mvn clean install
cd target/classes
java -Xms1024m -Xmx1024m -XX:+HeapDumpOnOutOfMemoryError com.ak.learning.nio.StreamingLargeFiles
cd ../../

