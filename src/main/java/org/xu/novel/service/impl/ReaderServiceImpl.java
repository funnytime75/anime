package org.xu.novel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xu.novel.domain.dto.ChapterInfo;
import org.xu.novel.domain.dto.ContentInfo;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;
import org.xu.novel.domain.po.Result;
import org.xu.novel.mapper.ReaderMapper;
import org.xu.novel.service.ReaderService;
import org.xu.novel.utils.UserEnum;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ReaderMapper readerMapper;

    @Override
    public int countBooks(){
        return readerMapper.countBooks();
    }

    @Override
    public List<Book> searchAllBook(int page,int limit) {
        int offset = (page-1)*limit;
        List<Book> bookList =readerMapper.searchAllBook(offset,limit);
        return bookList;
    }

    @Override
    public List<Book> searchByBookName(String bookName,int page,int limit) {
        return readerMapper.searchByBookName(bookName,page,limit);
    }

    @Override
    public List<Book> searchByAuthorName(String authorName) {
        return readerMapper.searchByAuthorName(authorName);
    }

    @Override
    public Result<Reader> login(String account, String password) {
        if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
            return Result.error("账号或密码不能为空");
        }

        Reader reader = readerMapper.searchByAccount(account);
        if (reader == null || !reader.getPassword().equals(password)) {
            return Result.error("账号或密码错误");
        }

        return new Result<>(1, "登录成功", reader);
    }






    @Override
    public Result<Boolean> register(Reader reader) {
        if (!StringUtils.hasText(reader.getPassword()) || !StringUtils.hasText(reader.getAccount())) {
            return new Result<>(0, "账号或密码不能为空", null);
        }

        // 账号验证
        if (!UserEnum.ACCOUNT.isValid(reader.getAccount())) {
            return new Result<>(0, UserEnum.ACCOUNT.getMessage(), null);
        }

        // 密码验证
        if (!UserEnum.PASSWORD.isValid(reader.getPassword())) {
            return new Result<>(0, UserEnum.PASSWORD.getMessage(), null);
        }

        // 验证账号是否已存在
        if (readerMapper.searchByAccount(reader.getAccount()) != null) {
            return new Result<>(0, "账号已存在", null);
        }

        // 验证用户名是否已存在
        if (readerMapper.searchByUsername(reader.getUsername()) != null) {
            return new Result<>(0, "用户名已存在", null);
        }

        // 插入新用户
        Reader newReader = new Reader();
        newReader.setAccount(reader.getAccount());
        newReader.setUsername(reader.getUsername());
        newReader.setPassword(reader.getPassword());

        boolean isInserted = readerMapper.insertByReader(newReader);

        if (!isInserted) {
            return new Result<>(0, "注册失败，未知错误", null);
        }

        // 返回注册成功
        return new Result<>(1, "注册成功", true);
    }

    @Override
    public Book searchById(Integer id) {
        return readerMapper.searchById(id);
    }

    @Override
    public List<Book> searchCompleteBook(int page, int limit) {
        int offset = (page-1)*limit;
        return readerMapper.searchCompleteBook(offset,limit);
    }

    @Override
    public List<Book> searchOngoingBook(int page, int limit) {
        int offset = (page-1)*limit;
        return readerMapper.searchOngoingBook(offset,limit);
    }

    @Override
    public List<Book> searchRankingBook(int page, int limit) {
        int offset = (page-1)*limit;
        return readerMapper.searchRankingBook(offset,limit);
    }

    @Override
    public List<ChapterInfo> readChapter(int id) {
        return readerMapper.readChapter(id);
    }

    @Override
    public ContentInfo readContent(int bookId, int chapterId) {
        return readerMapper.readContent(bookId,chapterId);
    }
}
