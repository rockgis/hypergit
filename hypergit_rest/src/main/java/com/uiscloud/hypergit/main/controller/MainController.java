package com.uiscloud.hypergit.main.controller;

import com.uiscloud.hypergit.appmng.dto.Gittb0001Dto;
import com.uiscloud.hypergit.appmng.service.Gittb0001Service;

import  com.uiscloud.hypergit.common.util.NetUtil;


import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private Gittb0001Service gittb0001Service;


    @GetMapping("/")
    public String main(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        return "redirect:/admin";
    }


    /* Main Page */
    @GetMapping("/admin")
    public String admin(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {

        return "main/index.html";
    }

    @GetMapping("/help")
    public String gethelp(Model model) {

        return "redirect:/swagger-ui.html";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {

        return "redirect:/oauth2/authorization/wso2";
    }

}
