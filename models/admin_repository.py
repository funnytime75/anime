from .db import get_db_connection
from .book import Book
from .reader import Reader
from .admin import Admin
from typing import List, Optional
from datetime import datetime

def select_all_readers() -> List[Reader]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    readers = []
    try:
        cursor.execute("SELECT * FROM reader")
        for row in cursor.fetchall():
            readers.append(Reader(**row))
        return readers
    finally:
        cursor.close()
        conn.close()

def select_all_books() -> List[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    books = []
    try:
        cursor.execute("SELECT * FROM book")
        for row in cursor.fetchall():
            books.append(Book(**row))
        return books
    finally:
        cursor.close()
        conn.close()

def select_admin_by_account(account: str) -> Optional[Admin]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    try:
        cursor.execute("SELECT * FROM admin WHERE account = %s", (account,))
        row = cursor.fetchone()
        return Admin(**row) if row else None
    finally:
        cursor.close()
        conn.close()

def add_reader_from_admin(reader: Reader) -> None:
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        query = """
            INSERT INTO reader 
            (account, username, password, registration_duration, create_time, update_time) 
            VALUES (%s, %s, %s, %s, %s, %s)
        """
        params = (
            reader.account, 
            reader.username, 
            reader.password, 
            reader.registration_duration,
            reader.create_time if reader.create_time else datetime.now(),
            reader.update_time if reader.update_time else datetime.now()
        )
        cursor.execute(query, params)
        conn.commit()
    finally:
        cursor.close()
        conn.close()

def delete_reader_by_id(reader_id: int) -> None:
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        cursor.execute("DELETE FROM reader WHERE id = %s", (reader_id,))
        conn.commit()
    finally:
        cursor.close()
        conn.close()

def update_reader_details(reader: Reader) -> None: 
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        query = """
            UPDATE reader SET 
            account = %s, username = %s, password = %s, 
            registration_duration = %s, update_time = %s 
            WHERE id = %s
        """
        params = (
            reader.account, 
            reader.username, 
            reader.password, 
            reader.registration_duration, 
            datetime.now(), 
            reader.id
        )
        cursor.execute(query, params)
        conn.commit()
    finally:
        cursor.close()
        conn.close()

def add_book_from_admin(book: Book) -> None:
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        query = """
            INSERT INTO book 
            (title, summary, genre, word_count, author_username, create_time, update_time, status, cover) 
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
        params = (
            book.title, book.summary, book.genre, book.word_count, book.author_username,
            book.create_time if book.create_time else datetime.now(),
            book.update_time if book.update_time else datetime.now(),
            book.status, book.cover
        )
        cursor.execute(query, params)
        conn.commit()
    finally:
        cursor.close()
        conn.close()

def delete_book_by_id(book_id: int) -> None:
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        cursor.execute("DELETE FROM book WHERE id = %s", (book_id,))
        conn.commit()
    finally:
        cursor.close()
        conn.close()

def update_book_details(book: Book) -> None: 
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        query = """
            UPDATE book SET 
            title = %s, summary = %s, genre = %s, word_count = %s, 
            author_username = %s, update_time = %s, status = %s, cover = %s 
            WHERE id = %s
        """
        params = (
            book.title, book.summary, book.genre, book.word_count, book.author_username,
            datetime.now(), 
            book.status, book.cover, book.id
        )
        cursor.execute(query, params)
        conn.commit()
    finally:
        cursor.close()
        conn.close()
