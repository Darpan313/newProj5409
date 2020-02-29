# newProj5409

This is REST-API that return fibonacci series/factorial of given number and also requestID, status.

"marathon service" has API code for factorial and fibonacci which can be access:
http://{host-addr}:5557/fibonacci/{number}
http://{host-addr}:5557/factorial/{number}

"simpleclient" is HttpClient, that generates 100 random integers store it in .txt file and hit above two APIs and store response time in .csv file

Both the projects can be run using 'java -jar "build/libs/{name of jar file}"'

Commands to build and install Mesos
Follow: https://dzone.com/articles/getting-started-with-mesos-1

Command to run Mesos and Marathon in Ubuntu 18.04


systemctl start zookeeper

mesos-master --zk=zk://localhost:2181/mesos --work_dir=/tmp/mesos --quorum=1

sudo mesos-slave --master=zk://localhost:2181/mesos --work_dir=/tmp/mesos

sudo mesos-slave --master=zk://localhost:2181/mesos --work_dir=/tmp/mesos --port=5051

export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so

cd ~/Desktop/marathon-1.5.0-96-gf84298d/

./bin/marathon --master zk://localhost:2181/mesos --zk zk://localhost:2181/marathon


