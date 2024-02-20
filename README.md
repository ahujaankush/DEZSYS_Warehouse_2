# MidEng GK861 gRPC Framework

## Questions

- What is gRPC and why does it work across languages and platforms?
gRPC is an RPC framework that essentially allows one to define type definitions and work with them as if they were local objects. As it transpiles the objects in the background through its own interface, it works across languages.

- Describe the RPC life cycle starting with the RPC client?
    - Initiate the remote call as if it were a local method.
    - Method arguments are serialized by the generated interface.
    - Request is sent to the server.
    - Server receives the request, deserializes it, and invokes the required methods.
    - The server response is serialized and sent to the client.
    - The client receives the response and processes it accordingly.

- Describe the workflow of Protocol Buffers?
    - Define a .proto file containing the type definitions.
    - Compile the .proto file (with protoc).
    - Use the generated code within your own application.

- What are the benefits of using Protocol Buffers?
    - Serialized objects are very easy to read and smaller in size.
    - Language independence.

- When is the use of Protocol Buffers not recommended?
    - When working with languages that do not support working with objects. Also, it doesn't make sense to use gRPC in smaller projects that only require the usage of one language.

- List 3 different data types that can be used with Protocol Buffers?
    - String
    - Arrays
    - Integers/Numbers

## Proto File
The Proto File specifies the Serializable Interface. Thus, data can be sent through a defined interface.
```proto
syntax = "proto3";

service WarehouseService {
rpc getData(WarehouseRequest) returns (WarehouseData) {}
}

message WarehouseRequest { string id = 1; }

message WarehouseData {
string warehouseID = 1;
string warehouseName = 2;
string timestamp = 3;
repeated Product productData = 4;
}

message Product {
string productID = 1;
string productName = 2;
string productCategory = 3;
int32 productQuantity = 4;
}
```

The classes previously present in the model have simply been replaced by the Proto specification. Java obviously cannot read Proto data, so these must be compiled into `.java` files.
There are 2 options for this.
A. You can manually compile the Proto file, which can be done with ```protoc -I=$PROTO_SOURCE_DIR --java_out=$DESTINATION_DIR $PROTO SOURCE_FILE```
B. Maven automates this, for which something needs to be added to the `pom.xml`.

```xml
 <build>
     <extensions>
         <extension>
             <groupId>kr.motd.maven</groupId>
             <artifactId>os-maven-plugin</artifactId>
             <version>1.7.1</version>
             </extension>
         </extensions>

     <plugins>
         <plugin>
             <groupId>org.xolstice.maven.plugins</groupId>
             <artifactId>protobuf-maven-plugin</artifactId>
             <version>0.6.1</version>
             <configuration>
                 <protocArtifact>com.google.protobuf:protoc:3.25.1:exe:${os.detected.classifier}</protocArtifact>
                 <pluginId>grpc-java</pluginId>
                 <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.61.0:exe:${os.detected.classifier}</pluginArtifact>
                 </configuration>
             <executions>
                 <execution>
                     <goals>
                         <goal>compile</goal>
                         <goal>compile-custom</goal>
                         </goals>
                     </execution>
                 </executions>
             </plugin>
         <plugin>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-maven-plugin</artifactId>
             </plugin>
         </plugins>
     </build>
```

## Server
The server builds on the previous Warehouse applications, using Spring Boot as the server and also using the Simulation class. Java files can then be compiled from the Proto file. The Simulation class must now work with the Proto files, for which slight changes in object creation need to be made. The interface itself offers a way to create and modify objects.
What's new is the Service.

```java
public class WarehouseService extends WarehouseServiceImplBase {

    @Override
    public void getData(WarehouseRequest request,
        StreamObserver<WarehouseData> responseObserver) {
        WarehouseData response = new WarehouseSimulation().getData(request.getId());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
```
The service inherits from a Proto-generated class and simply sends the result of the Simulation class to incoming requests.

## Client

The client is written in Python and builds on the official example. Here too, the Python interface is generated from the Proto files.
Now only the port needs to be specified.
``` python
import logging
import grpc
import warehouse_pb2 
import warehouse_pb2_grpc 

def run():
    with grpc.insecure_channel('localhost:9090') as channel:
        stub = warehouse_pb2_grpc.WarehouseServiceStub(channel)
        response = stub.getData(warehouse_pb2.WarehouseRequest(id=input()))
        print(response)


if __name__ == "__main__":
    logging.basicConfig()
    run()
```
