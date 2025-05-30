from dataclasses import dataclass
from datetime import datetime

@dataclass
class Admin:
    id: int | None = None
    account: str | None = None
    username: str | None = None
    password: str | None = None
    create_time: datetime | None = None
    update_time: datetime | None = None
