package tgm.aahuja.warehouse.controller;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import tgm.aahuja.warehouse.controller.WarehouseServiceGrpc.WarehouseServiceImplBase;
import tgm.aahuja.warehouse.model.Warehouse.WarehouseData;
import tgm.aahuja.warehouse.model.Warehouse.WarehouseRequest;

@GrpcService
public class WarehouseService extends WarehouseServiceImplBase {

  @Override
  public void getData(WarehouseRequest request,
                      StreamObserver<WarehouseData> responseObserver) {
    WarehouseData response = new WarehouseSimulation().getData(request.getId());
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
