package org.xu.novel.mapper;

import org.apache.ibatis.annotations.*;
import org.xu.novel.domain.dto.ChapterInfo;
import org.xu.novel.domain.dto.ContentInfo;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;

import java.util.List;

@Mapper
public interface ReaderMapper {

    @Select("SELECT COUNT(*) FROM book")
    int countBooks();

    @Select("select * from book limit #{offset} , #{limit}")
    List<Book> searchAllBook(@Param("offset") int offset, @Param("limit") int limit) ;

    @Select("select * from book where title like concat('%',#{bookName},'%') limit #{offset} ,#{limit}")
    List<Book> searchByBookName(@Param("bookName") String bookName,int offset, int limit) ;

    @Select("select * from book where author_username like concat('%',#{authorName},'%')")
    List<Book> searchByAuthorName(String authorName);

    @Select("select * from reader where account = #{account}")
    Reader searchByAccount(String account);

    @Select("select * from reader where username = #{username}")
    Reader searchByUsername(String username);

    @Insert("INSERT INTO reader (account, username, password) VALUES (#{account}, #{username}, #{password})")
    boolean insertByReader(Reader reader);

    @Select("select * from book where id = #{id}")
    Book searchById(Integer id);

    @Select("select * from book where genre = '无类别' limit #{offset} ,#{limit}")
    List<Book> searchCompleteBook(int offset, int limit);

    @Select("select * from book where status = '连载中' limit #{offset} ,#{limit}")
    List<Book> searchOngoingBook(int offset, int limit);

    @Select("SELECT * FROM book" + " ORDER BY word_count DESC limit #{offset} ,#{limit};")
    List<Book> searchRankingBook(int offset, int limit);

    @Select("""
            
            SELECT\s
                c.name AS chapter_name,
                c.chapter_id AS chapter_id
            FROM\s
                book b
            INNER JOIN\s
                book_chapters bc ON b.id = bc.book_id
            INNER JOIN\s
                chapters c ON bc.chapter_db_id = c.id
            WHERE\s
                b.id = ${bookId}
            ORDER BY\s
                b.title ASC,\s
                bc.id ASC;
            """)
    @Results({
            @Result(column = "chapter_name", property = "chapterName"),
            @Result(column = "chapter_id", property = "chapterId")
    })
    List<ChapterInfo> readChapter(@Param("bookId") int id);

    @Select("""
            
            SELECT\s
                c.chapter_id AS chapter_id,
                c.name AS chapter_name,
                c.content AS chapter_content
            FROM\s
                book b
            INNER JOIN\s
                book_chapters bc ON b.id = bc.book_id
            INNER JOIN\s
                chapters c ON bc.chapter_db_id = c.id
            WHERE\s
                b.id = ${bookId}
                AND c.chapter_id = ${chapterId}
            """)
    @Results({
            @Result(column = "chapter_name", property = "chapterName"),
            @Result(column = "chapter_id", property = "chapterId"),
            @Result(column = "chapter_content", property = "chapterContent"),

    })
    ContentInfo readContent(int bookId, int chapterId);
}
