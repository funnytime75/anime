package org.xu.novel.mapper;

import org.apache.ibatis.annotations.*;
import org.xu.novel.domain.po.Admin;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from reader")
    List<Reader> selectAll();

    @Select("select * from book")
    List<Book> selectAllBook();

    @Select("select * from admin where account = #{account}")
    Admin selectByUsername(String username);

    @Insert("INSERT INTO reader (account, username, password, registration_duration, create_time, update_time) " +
            "VALUES (#{account}, #{username}, #{password}, #{registrationDuration}, #{createTime}, #{updateTime})")
    void addReader(Reader reader);

    @Delete("DELETE FROM reader WHERE id = #{id}")
    void deleteReaderById(int id);

    @Update("UPDATE reader SET account = #{account}, username = #{username}, password = #{password}, " +
            "registration_duration = #{registrationDuration}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    void updateReader(Reader reader);

    @Insert("INSERT INTO book (title, summary, genre, word_count, author_username, create_time, update_time, status, cover) " +
            "VALUES (#{title}, #{summary}, #{genre}, #{wordCount}, #{authorUsername}, #{createTime}, #{updateTime}, #{status}, #{cover})")
    void addBook(Book book);

    @Delete("DELETE FROM book WHERE id = #{id}")
    void deleteBookById(int id);

    @Update("UPDATE book SET " +
            "title = #{title}, " +
            "summary = #{summary}, " +
            "genre = #{genre}, " +
            "word_count = #{wordCount}, " +
            "author_username = #{authorUsername}, " +
            "update_time = #{updateTime}, " +
            "status = #{status}, " +
            "cover = #{cover} " +
            "WHERE id = #{id}")
    void updateBook(Book book);

    @Select("select * from reader where account = #{account}")
    Reader searchByAccount(String account);

    @Select("select * from reader where username = #{username}")
    Reader searchByUsername(String username);
}
