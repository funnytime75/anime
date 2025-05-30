package org.xu.novel.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xu.novel.domain.dto.LoginDto;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;
import org.xu.novel.domain.po.Result;
import org.xu.novel.service.AdminService;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /*
    管理员登录
     */
    @PostMapping("/login")
    public boolean login(
            @RequestBody LoginDto loginDto,
            HttpSession session) {
        String account = loginDto.getAccount();
        String password = loginDto.getPassword();
        String captcha = loginDto.getCaptcha();
        return adminService.login(account,password,captcha,session);
    }

    // 查询所有读者
    @GetMapping("/selectAll")
    public List<Reader> selectAll() {
        return adminService.selectAll();
    }

    /*
     * 接下来是增删改用户的数据
     */

    // 添加读者
    @PostMapping("/addReader")
    public Result<Boolean> addReader(@RequestBody Reader reader) {
        reader.setCreateTime(LocalDateTime.now());
        reader.setUpdateTime(LocalDateTime.now());
        return adminService.addReader(reader);
    }

    // 根据 ID 删除读者
    @GetMapping("/deleteReaderById")
    public Result<String> deleteReaderById(@RequestParam int id) {
        adminService.deleteReaderById(id);
        return Result.success("删除读者成功");
    }

    // 更新读者信息
    @PostMapping("/updateReader")
    public Result<String> updateReader(@RequestBody Reader reader) {
        reader.setUpdateTime(LocalDateTime.now());
        adminService.updateReader(reader);
        return Result.success("更新读者成功");
    }



    /*
     * 查看当前所有书籍
     */
    @GetMapping("/selectAllBook")
    public List<Book> selectAllBook() {
        return adminService.selectAllBook();
    }

    /*
     * 添加书籍
     */
    @PostMapping("/addBook")
    public Result<String> addBook(@RequestBody Book book) {
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        adminService.addBook(book);
        return Result.success("添加图书成功");
    }

    /*
     * 根据 ID 删除书籍
     */
    @GetMapping("/deleteBookById")
    public Result<String> deleteBookById(@RequestParam int id) {
        adminService.deleteBookById(id);
        return Result.success("删除图书成功");
    }

    /*
     * 更新书籍信息
     */
    @PostMapping("/updateBook")
    public Result<String> updateBook(@RequestBody Book book) {
        book.setUpdateTime(LocalDateTime.now());
        adminService.updateBook(book);
        return Result.success("更新图书成功");
    }

    /*
    章节列表略
    太麻烦
     */

}
