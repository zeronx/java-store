#!/bin/bash 
echo "----------install start"
buildPath=$PWD
echo $buildPath

for i in `cat jarfiles.txt` 
do 
cd $i
echo "------------ install in [$PWD] ------------"
mvn clean source:jar install
cd $buildPath

done

echo "----------install end"