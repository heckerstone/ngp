#!/bin/sh
java -cp classes ngp.tools.ManifestGenerator
/bin/rm -f ngp.jar
jar cfm ngp.jar resource/ngp.manifest.mf -C classes . || exit 1
/bin/rm -f ngpservice.jar
jar cfm ngpservice.jar resource/ngpservice.manifest.mf -C classes . || exit 1

echo "jar files generated successfully"