from dataclasses import dataclass
from datetime import datetime

@dataclass
class Reader:
    id: int | None = None
    account: str | None = None
    username: str | None = None
    password: str | None = None
    registration_duration: int | None = None # Assuming this maps to an int
    create_time: datetime | None = None
    update_time: datetime | None = None
