#!/bin/bash

if [ $# -ne 1 ]
then
    echo "Usage: run.sh <program_name with package information>"
    exit 1
fi

mvn clean install
cd target/classes 
java -XX:+HeapDumpOnOutOfMemoryError $1
cd ../../

