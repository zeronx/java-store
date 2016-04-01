echo off
set filepath=%cd%
echo %filepath%
cd %filepath%

call mvn install:install-file -DgroupId=third.jar -DartifactId=cas-client-core -Dversion=3.2.1 -Dpackaging=jar -Dfile=cas-client-core-3.2.1.jar

echo ------------ install local file end ------------
pause
