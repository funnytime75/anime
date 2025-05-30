from dataclasses import dataclass

@dataclass
class LoginDto:
    account: str | None = None
    password: str | None = None
    captcha: str | None = None
