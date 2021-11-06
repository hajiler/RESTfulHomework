Sbt Version : 1.52 Scala Version : 3.0.2 Java Version : 11.0.11

###Installation Instructions
Clone github repository on local machine using IntelliJ import from version control feature.

###Input
The input for the start time should be in the format:
    
    hour.minutes.seconds.milliseconds

The input for the duration should be a single integer greater than 0

###Execution Instructions
Run a gRPC server process. Then run a gRPC client.

###gRPC Server Instructions:

     sbt "runMain Homework3.gRPCServer"

###gRPC Client Instructions:

    sbt "runMain Homework3.gRPCClient"

