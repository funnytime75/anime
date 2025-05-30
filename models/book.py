from dataclasses import dataclass
from datetime import datetime

@dataclass
class Book:
    id: int | None = None
    title: str | None = None
    summary: str | None = None
    genre: str | None = None
    word_count: int | None = None
    author_username: str | None = None
    create_time: datetime | None = None
    update_time: datetime | None = None
    status: str | None = None
    cover: str | None = None
