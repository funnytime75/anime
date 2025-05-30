package org.xu.novel.service;

import org.xu.novel.domain.dto.ChapterInfo;
import org.xu.novel.domain.dto.ContentInfo;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;
import org.xu.novel.domain.po.Result;

import java.util.List;

public interface ReaderService {
    int countBooks();

    List<Book> searchAllBook(int page,int limit);

    List<Book> searchByBookName(String bookName,int page,int limit);

    List<Book> searchByAuthorName(String authorName);

    Result<Reader> login(String account, String password);


    Result<Boolean> register(Reader reader);

    Book searchById(Integer id);

    List<Book> searchCompleteBook(int page, int limit);

    List<Book> searchOngoingBook(int page, int limit);

    List<Book> searchRankingBook(int page, int limit);

    List<ChapterInfo> readChapter(int id);

    ContentInfo readContent(int bookId, int chapterId);
}
