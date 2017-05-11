#!/bin/sh
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dngp.runtime.mode=desktop -Dngp.runtime.dirProvider=ngp.env.DefaultDirProvider ngp.NGP
