#!/bin/sh
javac -d . *.java
java EmailExtractor/Server 4444
