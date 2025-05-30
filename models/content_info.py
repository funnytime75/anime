from dataclasses import dataclass

@dataclass
class ContentInfo:
    chapter_name: str | None = None
    chapter_id: int | None = None
    chapter_content: str | None = None
