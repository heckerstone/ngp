#!/bin/sh
if [ -e ~/.ngp/ngp.pid ]; then
    PID=`cat ~/.ngp/ngp.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    if [ $STATUS -eq 0 ]; then
        echo "Ngp server already running"
        exit 1
    fi
fi
mkdir -p ~/.ngp/
DIR=`dirname "$0"`
cd "${DIR}"
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
nohup ${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dngp.runtime.mode=desktop ngp.NGP > /dev/null 2>&1 &
echo $! > ~/.ngp/ngp.pid
cd - > /dev/null
