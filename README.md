
# Welcome to EnergyHub Coding Sample!

This is my code challenge project, It's made in maven and Java 8
Based in:https://gist.github.com/harthur/4db5df3fed5e25a47aa6c3b9f01ab8ca

# How build it

You need java & maven.

Inside of EnergyHub directory write:

mvn clean

mvn install


Now you have your project ready, change to target directory

cd target


And now you have a jar file: EnergyHubReplay-1.0-jar-with-dependencies.jar


## Run it
write:

java -jar EnergyHubReplay-1.0-jar-with-dependencies.jar  variable file  timestamp


Where:
**variable** is the field to find. Can be schedule, ambientTemp, lastAlertTs etc.
**file** the data file path, can be jsonl or gz (I added 2 files in "files" folder)
** timestamp** the timestamp to find

Example:
java -jar EnergyHubReplay-1.0-jar-with-dependencies.jar  ambientTemp ..\files\thermostat-data.jsonl.gz 2016-01-01T03:00

# Note:
The project has test units, I added some test for file testing, but there are commented because for a unit test can't access to file. It's only for integration test in development phase.
