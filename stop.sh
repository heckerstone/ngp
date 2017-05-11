#!/bin/sh
if [ -e ~/.ngp/ngp.pid ]; then
    PID=`cat ~/.ngp/ngp.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    echo "stopping"
    while [ $STATUS -eq 0 ]; do
        kill `cat ~/.ngp/ngp.pid` > /dev/null
        sleep 5
        ps -p $PID > /dev/null
        STATUS=$?
    done
    rm -f ~/.ngp/ngp.pid
    echo "Ngp server stopped"
fi

