package com.d2112.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    public static final String CLIENT_NAME_ATTRIBUTE = "clientName";

    @RequestMapping(value = "/")
    public String startChat(boolean chatStart, Model model, HttpServletRequest req) {
        if (chatStart) {
            Object clientName = req.getParameter(CLIENT_NAME_ATTRIBUTE);
            model.addAttribute("clientName", clientName);
            return "chat";
        }
        return "home";
    }
}