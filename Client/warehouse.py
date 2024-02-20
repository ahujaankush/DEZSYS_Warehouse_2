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
