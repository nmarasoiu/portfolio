#!/bin/bash
export JAVA_HOME=`/usr/libexec/java_home -v 12`
if [ "$#" -eq 0 ]; then
  echo "you did not pass any parameter: the file path is needed as the only parameter"
  exit 1
fi
if test -f "${1}"; then
  mvn exec:java -Dexec.mainClass="demo.PortfolioRunner" -Dexec.args="${1}"
else
  echo "the argument ${1} does not seem to be a valid file path"
fi