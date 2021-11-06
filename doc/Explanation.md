### gRPC Client
The gRPC client formats a TimeRequest proto using query inputs from the config file. It then conducts a SearchRequest RPC
call to a gRPC Server. The gRPC server returns the results from the AWS lambda as a LogResponse proto. The proto is
parsed and the final results are logged to the terminal screen.

### gRPC Server
The gRPC server parses a TimeRequest proto, and constructs an HTTP url with the user specified paramters. The Server
makes a GET request to the AWS lambda at the designated end point. The Server reads the results, and constructs a
LogResponse proto, and returns this to a gRPC client.

### AWS LAMBDA
I designed the AWS lambda as 4 step pipeline: parsing of request, search for timestamp, find all logs within boundrary, 
and finally returning logs with injection. Parsing  simply involves collecting the HTTP query parameters and forming 
two inputs: interval start time, and duration. Then a log file is read from AWS s3 as a string, and a binary search is
conducted using log times to find the first log time within the time interval within the file in log(N) time. If no time
stamp exists, then the pipeline ends and "No Logs Found" is returned to the client. If a log file does exist, using the
index of the found timestamp, the all log timestamps within the query duration are computed. Finally, of these timestamps,
those containing the injected string as specificed in the config file are collected as a single string and returned to 
the client.