package org.xu.novel.service;

import jakarta.servlet.http.HttpSession;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;
import org.xu.novel.domain.po.Result;

import java.util.List;

public interface AdminService {
    List<Reader> selectAll();

    List<Book> selectAllBook();

    boolean login(String account, String password, String captcha, HttpSession session);

    void deleteReaderById(int id);



    void updateReader(Reader reader);

    void addBook(Book book);

    void deleteBookById(int id);

    void updateBook(Book book);

    Result<Boolean> addReader(Reader reader);
}
