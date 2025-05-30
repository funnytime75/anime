package org.xu.novel.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xu.novel.domain.po.Admin;
import org.xu.novel.domain.po.Book;
import org.xu.novel.domain.po.Reader;
import org.xu.novel.domain.po.Result;
import org.xu.novel.mapper.AdminMapper;
import org.xu.novel.service.AdminService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;

    @Override
    public List<Reader> selectAll() {
        return adminMapper.selectAll();
    }

    @Override
    public List<Book> selectAllBook() {
        return adminMapper.selectAllBook();
    }

    @Override
    public boolean login(String account, String password, String captcha, HttpSession session) {
        if(!StringUtils.hasText(account) || !StringUtils.hasText(password)){
            throw new IllegalArgumentException("账号或者密码不能为空");
        }

        String sessionCaptcha = (String) session.getAttribute("captcha");

        if(!StringUtils.hasText(captcha) || !StringUtils.hasText(sessionCaptcha) ||
                !captcha.equalsIgnoreCase(sessionCaptcha)) {
            throw new RuntimeException("验证码错误");
        }
        session.removeAttribute("captcha");

        Admin admin = adminMapper.selectByUsername(account);
        return admin != null && admin.getPassword().equals(password);
    }

    @Override
    public void deleteReaderById(int id) {
        adminMapper.deleteReaderById(id);
    }

    @Override
    public Result<Boolean> addReader(Reader reader) {
        // 验证账号是否已存在
        if (adminMapper.searchByAccount(reader.getAccount()) != null) {
            return new Result<>(0, "账号已存在", null);
        }

        // 验证用户名是否已存在
        if (adminMapper.searchByUsername(reader.getUsername()) != null) {
            return new Result<>(0, "用户名已存在", null);
        }
        adminMapper.addReader(reader);
        return  new Result<>(1, "用户添加成功", true);
    }

    @Override
    public void updateReader(Reader reader) {
        adminMapper.updateReader(reader);
    }

    @Override
    public void addBook(Book book) {
        adminMapper.addBook(book);
    }

    @Override
    public void deleteBookById(int id) {
        adminMapper.deleteBookById(id);
    }

    @Override
    public void updateBook(Book book) {
        adminMapper.updateBook(book);
    }
}
