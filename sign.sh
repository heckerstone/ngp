#!/bin/sh
java -cp "classes:lib/*:conf" ngp.tools.SignTransactionJSON $@
exit $?
