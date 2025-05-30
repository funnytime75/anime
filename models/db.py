import mysql.connector
import os
from dotenv import load_dotenv

load_dotenv() # Load environment variables from .env

def get_db_connection():
    connection = mysql.connector.connect(
        host=os.getenv('MYSQL_HOST', 'localhost'),
        user=os.getenv('MYSQL_USER', 'root'),
        password=os.getenv('MYSQL_PASSWORD', ''),
        database=os.getenv('MYSQL_DB'),
        port=int(os.getenv('MYSQL_PORT', 3306))
    )
    return connection

# Example usage (will be used in repositories):
# conn = get_db_connection()
# cursor = conn.cursor(dictionary=True) # dictionary=True to get results as dicts
# cursor.execute("SELECT * FROM your_table")
# result = cursor.fetchall()
# conn.close()
