from google.protobuf.internal import containers as _containers
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Mapping as _Mapping, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class WarehouseRequest(_message.Message):
    __slots__ = ("id",)
    ID_FIELD_NUMBER: _ClassVar[int]
    id: str
    def __init__(self, id: _Optional[str] = ...) -> None: ...

class WarehouseData(_message.Message):
    __slots__ = ("warehouseID", "warehouseName", "timestamp", "productData")
    WAREHOUSEID_FIELD_NUMBER: _ClassVar[int]
    WAREHOUSENAME_FIELD_NUMBER: _ClassVar[int]
    TIMESTAMP_FIELD_NUMBER: _ClassVar[int]
    PRODUCTDATA_FIELD_NUMBER: _ClassVar[int]
    warehouseID: str
    warehouseName: str
    timestamp: str
    productData: _containers.RepeatedCompositeFieldContainer[Product]
    def __init__(self, warehouseID: _Optional[str] = ..., warehouseName: _Optional[str] = ..., timestamp: _Optional[str] = ..., productData: _Optional[_Iterable[_Union[Product, _Mapping]]] = ...) -> None: ...

class Product(_message.Message):
    __slots__ = ("productID", "productName", "productCategory", "productQuantity")
    PRODUCTID_FIELD_NUMBER: _ClassVar[int]
    PRODUCTNAME_FIELD_NUMBER: _ClassVar[int]
    PRODUCTCATEGORY_FIELD_NUMBER: _ClassVar[int]
    PRODUCTQUANTITY_FIELD_NUMBER: _ClassVar[int]
    productID: str
    productName: str
    productCategory: str
    productQuantity: int
    def __init__(self, productID: _Optional[str] = ..., productName: _Optional[str] = ..., productCategory: _Optional[str] = ..., productQuantity: _Optional[int] = ...) -> None: ...
