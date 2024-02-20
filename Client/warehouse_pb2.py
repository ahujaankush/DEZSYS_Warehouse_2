# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: warehouse.proto
# Protobuf Python Version: 4.25.2
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0fwarehouse.proto\"\x1e\n\x10WarehouseRequest\x12\n\n\x02id\x18\x01 \x01(\t\"m\n\rWarehouseData\x12\x13\n\x0bwarehouseID\x18\x01 \x01(\t\x12\x15\n\rwarehouseName\x18\x02 \x01(\t\x12\x11\n\ttimestamp\x18\x03 \x01(\t\x12\x1d\n\x0bproductData\x18\x04 \x03(\x0b\x32\x08.Product\"c\n\x07Product\x12\x11\n\tproductID\x18\x01 \x01(\t\x12\x13\n\x0bproductName\x18\x02 \x01(\t\x12\x17\n\x0fproductCategory\x18\x03 \x01(\t\x12\x17\n\x0fproductQuantity\x18\x04 \x01(\x05\x32\x42\n\x10WarehouseService\x12.\n\x07getData\x12\x11.WarehouseRequest\x1a\x0e.WarehouseData\"\x00\x62\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'warehouse_pb2', _globals)
if _descriptor._USE_C_DESCRIPTORS == False:
  DESCRIPTOR._options = None
  _globals['_WAREHOUSEREQUEST']._serialized_start=19
  _globals['_WAREHOUSEREQUEST']._serialized_end=49
  _globals['_WAREHOUSEDATA']._serialized_start=51
  _globals['_WAREHOUSEDATA']._serialized_end=160
  _globals['_PRODUCT']._serialized_start=162
  _globals['_PRODUCT']._serialized_end=261
  _globals['_WAREHOUSESERVICE']._serialized_start=263
  _globals['_WAREHOUSESERVICE']._serialized_end=329
# @@protoc_insertion_point(module_scope)