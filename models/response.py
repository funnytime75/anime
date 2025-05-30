from dataclasses import dataclass, field
from typing import Generic, TypeVar, List

T = TypeVar('T')

@dataclass
class Result(Generic[T]):
    code: int
    message: str
    data: T | None = None

    @classmethod
    def success(cls, data: T | None = None, message: str = "success") -> 'Result[T]':
        return cls(code=1, message=message, data=data)

    @classmethod
    def error(cls, message: str = "error") -> 'Result[T]':
        return cls(code=0, message=message, data=None)

@dataclass
class PageResponse(Generic[T]):
    data: List[T] = field(default_factory=list)
    total_items: int = 0
    total_pages: int = 0
    current_page: int = 0
    page_size: int = 0
