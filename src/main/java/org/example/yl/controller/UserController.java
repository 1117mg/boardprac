package org.example.yl.controller;

import org.example.yl.model.UserDto;
import org.example.yl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService service;


    @GetMapping("/login")
    public String loginForm(Model model){
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(String userId, String pw, HttpSession session, Model model){
        UserDto user=null;

        user=service.getLoginUser(userId, pw);

        if (user != null) {
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());

            return "redirect:/main";

        }else{
            model.addAttribute("errorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "user/login";
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        if(session != null) {
            session.invalidate();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "redirect:/main";
    }
}
