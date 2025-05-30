from .db import get_db_connection
from .book import Book
from .reader import Reader
from .chapter_info import ChapterInfo
from .content_info import ContentInfo
from typing import List, Optional

def count_books() -> int:
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT COUNT(*) FROM book")
        result = cursor.fetchone()
        return result[0] if result else 0
    finally:
        cursor.close()
        conn.close()

def search_all_book(offset: int, limit: int) -> List[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    books = []
    try:
        cursor.execute("SELECT * FROM book LIMIT %s OFFSET %s", (limit, offset))
        for row in cursor.fetchall():
            books.append(Book(**row))
        return books
    finally:
        cursor.close()
        conn.close()

def search_by_book_name(book_name: str, offset: int, limit: int) -> List[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    books = []
    try:
        query = "SELECT * FROM book WHERE title LIKE %s LIMIT %s OFFSET %s"
        params = (f"%{book_name}%", limit, offset)
        cursor.execute(query, params)
        for row in cursor.fetchall():
            books.append(Book(**row))
        return books
    finally:
        cursor.close()
        conn.close()

def search_by_author_name(author_name: str) -> List[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    books = []
    try:
        query = "SELECT * FROM book WHERE author_username LIKE %s"
        params = (f"%{author_name}%",)
        cursor.execute(query, params)
        for row in cursor.fetchall():
            books.append(Book(**row))
        return books
    finally:
        cursor.close()
        conn.close()

def search_reader_by_account(account: str) -> Optional[Reader]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    try:
        cursor.execute("SELECT * FROM reader WHERE account = %s", (account,))
        row = cursor.fetchone()
        return Reader(**row) if row else None
    finally:
        cursor.close()
        conn.close()

def search_reader_by_username(username: str) -> Optional[Reader]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    try:
        cursor.execute("SELECT * FROM reader WHERE username = %s", (username,))
        row = cursor.fetchone()
        return Reader(**row) if row else None
    finally:
        cursor.close()
        conn.close()

def insert_reader(reader: Reader) -> bool:
    conn = get_db_connection()
    cursor = conn.cursor()
    try:
        query = "INSERT INTO reader (account, username, password) VALUES (%s, %s, %s)"
        params = (reader.account, reader.username, reader.password)
        cursor.execute(query, params)
        conn.commit()
        return cursor.rowcount > 0
    finally:
        cursor.close()
        conn.close()

def search_book_by_id(book_id: int) -> Optional[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    try:
        cursor.execute("SELECT * FROM book WHERE id = %s", (book_id,))
        row = cursor.fetchone()
        return Book(**row) if row else None
    finally:
        cursor.close()
        conn.close()

def search_complete_book(offset: int, limit: int) -> List[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    books = []
    try:
        cursor.execute("SELECT * FROM book WHERE genre = '无类别' LIMIT %s OFFSET %s", (limit, offset))
        for row in cursor.fetchall():
            books.append(Book(**row))
        return books
    finally:
        cursor.close()
        conn.close()

def search_ongoing_book(offset: int, limit: int) -> List[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    books = []
    try:
        cursor.execute("SELECT * FROM book WHERE status = '连载中' LIMIT %s OFFSET %s", (limit, offset))
        for row in cursor.fetchall():
            books.append(Book(**row))
        return books
    finally:
        cursor.close()
        conn.close()

def search_ranking_book(offset: int, limit: int) -> List[Book]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    books = []
    try:
        cursor.execute("SELECT * FROM book ORDER BY word_count DESC LIMIT %s OFFSET %s", (limit, offset))
        for row in cursor.fetchall():
            books.append(Book(**row))
        return books
    finally:
        cursor.close()
        conn.close()

def read_chapter_info(book_id: int) -> List[ChapterInfo]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    chapters = []
    try:
        sql = """
            SELECT 
                c.name AS chapter_name,
                c.chapter_id AS chapter_id
            FROM 
                book b
            INNER JOIN 
                book_chapters bc ON b.id = bc.book_id
            INNER JOIN 
                chapters c ON bc.chapter_db_id = c.id
            WHERE 
                b.id = %s
            ORDER BY 
                b.title ASC, 
                bc.id ASC;
        """
        cursor.execute(sql, (book_id,))
        for row in cursor.fetchall():
            chapters.append(ChapterInfo(chapter_name=row['chapter_name'], chapter_id=row['chapter_id']))
        return chapters
    finally:
        cursor.close()
        conn.close()

def read_content_info(book_id: int, chapter_id: int) -> Optional[ContentInfo]:
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    try:
        sql = """
            SELECT 
                c.chapter_id AS chapter_id,
                c.name AS chapter_name,
                c.content AS chapter_content
            FROM 
                book b
            INNER JOIN 
                book_chapters bc ON b.id = bc.book_id
            INNER JOIN 
                chapters c ON bc.chapter_db_id = c.id
            WHERE 
                b.id = %s
                AND c.chapter_id = %s
        """
        cursor.execute(sql, (book_id, chapter_id))
        row = cursor.fetchone()
        if row:
            return ContentInfo(
                chapter_id=row['chapter_id'], 
                chapter_name=row['chapter_name'], 
                chapter_content=row['chapter_content']
            )
        return None
    finally:
        cursor.close()
        conn.close()
