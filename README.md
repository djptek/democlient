Demo code to make HTTP Requests with Elastic APM Distributed Tracing header properties  

Build with 

./gradlew build shadow

using https://github.com/johnrengelman/shadow plugin to create a fat jar

Download the Elastic Java APM Agent with e.g.

curl -OL https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.15.0/elastic-apm-agent-1.15.0.jar

Use version >= 1.14 to ensure Distrubuted Tracing available

start the client service with e.g 

$ java -javaagent:elastic-apm-agent-1.15.0.jar 
    -Delastic.apm.service_name=myHttpRequest 
    -Delastic.apm.server_url=http://localhost:8200 
    -Delastic.apm.application_packages=com.example.democlient 
    -Delastic.apm.trace_methods=com.example.democlient.HttpClient.callServlet 
    -Delastic.apm.use_elastic_traceparent_header=true -jar git/myHTTPRequest/build/libs/HttpClient.jar

$ java -javaagent:elastic-apm-agent-1.15.0.jar -Delastic.apm.service_name=myHttpRequest -Delastic.apm.server_url=http://localhost:8200 -Delastic.apm.application_packages=com.example.democlient -Delastic.apm.trace_methods=com.example.democlient.HttpClient.callServlet -Delastic.apm.use_elastic_traceparent_header=true -jar git/myHTTPRequest/build/libs/HttpClient.jar
