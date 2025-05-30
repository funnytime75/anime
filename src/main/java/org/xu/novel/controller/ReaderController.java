package org.xu.novel.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xu.novel.domain.dto.ChapterInfo;
import org.xu.novel.domain.dto.ContentInfo;
import org.xu.novel.domain.dto.LoginDto;
import org.xu.novel.domain.dto.PageResponse;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;
import org.xu.novel.domain.po.Result;
import org.xu.novel.service.ReaderService;

import java.util.List;

@RequestMapping("/reader")
@RestController
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    /*
    读者查看网站所有书籍(已经实现分页查询)
    前端为同人小说
     */
    @GetMapping("/searchAllBook")
    public PageResponse<Book> SearchAllBook(@RequestParam int page, @RequestParam int limit) {
        List<Book> books = readerService.searchAllBook(page, limit);

        int totalItems = readerService.countBooks();

        int totalPages = (int) Math.ceil((double) totalItems / (double) limit);

        PageResponse<Book> response = new PageResponse<>();
        response.setData(books);
        response.setTotalItems(totalItems);
        response.setTotalPages(totalPages);
        response.setCurrentPage(page);
        response.setPageSize(limit);
        return response;
    }

    /*
    读者查看网站全本同人
     */
    @GetMapping("/searchCompleteBook")
    public PageResponse<Book> SearchCompleteBook(@RequestParam int page, @RequestParam int limit) {
        List<Book> books = readerService.searchCompleteBook(page, limit);

        int totalItems = readerService.countBooks();

        int totalPages = (int) Math.ceil((double) totalItems / (double) limit);

        PageResponse<Book> response = new PageResponse<>();
        response.setData(books);
        response.setTotalItems(totalItems);
        response.setTotalPages(totalPages);
        response.setCurrentPage(page);
        response.setPageSize(limit);

        return response;
    }

    /*
    读者查看网站连载同人
     */
    @GetMapping("/searchOngoingBook")
    public PageResponse<Book> searchOngoingBook(@RequestParam int page, @RequestParam int limit) {
        List<Book> books = readerService.searchOngoingBook(page, limit);

        int totalItems = readerService.countBooks();

        int totalPages = (int) Math.ceil((double) totalItems / (double) limit);

        PageResponse<Book> response = new PageResponse<>();
        response.setData(books);
        response.setTotalItems(totalItems);
        response.setTotalPages(totalPages);
        response.setCurrentPage(page);
        response.setPageSize(limit);

        return response;
    }

    /*
    读者查看网站排行列表（虽然但是没这个功能，按字数排序）
     */
    @GetMapping("/searchRankingBook")
    public PageResponse<Book> searchRankingBook(@RequestParam int page, @RequestParam int limit) {
        List<Book> books = readerService.searchRankingBook(page, limit);

        int totalItems = readerService.countBooks();

        int totalPages = (int) Math.ceil((double) totalItems / (double) limit);

        PageResponse<Book> response = new PageResponse<>();
        response.setData(books);
        response.setTotalItems(totalItems);
        response.setTotalPages(totalPages);
        response.setCurrentPage(page);
        response.setPageSize(limit);

        return response;
    }
    /*
    读者根据书籍名查询书籍(模糊匹配)
     */

    @GetMapping("/searchByBookName")
    public PageResponse<Book> searchByBookName(@RequestParam String bookName,@RequestParam int page, @RequestParam int limit) {
        List<Book> books = readerService.searchByBookName(bookName,page, limit);

        int totalItems = readerService.countBooks();

        int totalPages = (int) Math.ceil((double) totalItems / (double) limit);

        PageResponse<Book> response = new PageResponse<>();
        response.setData(books);
        response.setTotalItems(totalItems);
        response.setTotalPages(totalPages);
        response.setCurrentPage(page);
        response.setPageSize(limit);

        return response;

    }

    @GetMapping("/searchById")
    public Book SearchById(Integer id) {
        return readerService.searchById(id);
    }


    /*
    读者根据作者名查询书籍(模糊匹配)
     */
    @GetMapping("/searchByAuthorName")
    public List<Book> SearchByAuthorName(@RequestParam String authorName) {
        return readerService.searchByAuthorName(authorName);
    }

    /*
    读者登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDto loginDto, HttpSession session) {
        String account = loginDto.getAccount();
        String password = loginDto.getPassword();
        String captcha = loginDto.getCaptcha();
        // 获取验证码并移除（防止复用）
        String sessionCaptcha = (String) session.getAttribute("captcha");
        session.removeAttribute("captcha");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            return Result.error("验证码错误");
        }
        // 验证账号密码
        Result<Reader> reader = readerService.login(account, password);
        if (reader == null) {
            return Result.error("账号或密码错误");
        }

        // 登录成功，存入 session
        session.setAttribute("reader", reader);
        System.out.println(session.getAttribute("reader"));
        return Result.success("登录成功");
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {
        session.removeAttribute("reader");
        return Result.success("退出成功");
    }

    /*
    读者注册
     */
    @PostMapping("/register")
    public Result<Boolean> Register(@RequestBody Reader reader) {
        return readerService.register(reader);
    }

    /*
    小说阅读界面的数据库查询
     */
    @PostMapping("/chapter")
    public List<ChapterInfo> readChapter(@RequestParam int id) {
        return readerService.readChapter(id);
    }

    @GetMapping("/content")
    public ContentInfo readContent(@RequestParam int bookId, @RequestParam int chapterId) {
        return readerService.readContent(bookId, chapterId);
    }
}
