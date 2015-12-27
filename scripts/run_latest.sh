#!/bin/bash

# get latest file in build/libs meeting pattern exercise-log-dropwizard-*-all.jar
latest=$(ls -dt build/libs/exercise-log-dropwizard-*-all.jar | head -1)

# run it with the same args passed to this script
echo "java -jar $latest $@"
java -jar $latest $@