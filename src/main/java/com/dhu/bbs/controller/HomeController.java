package com.dhu.bbs.controller;

import com.dhu.bbs.model.Message;
import com.dhu.bbs.model.ViewObject;
import com.dhu.bbs.service.MessageService;
import com.dhu.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    MessageService message_service;

    @Autowired
    UserService userService;

    @RequestMapping(path= {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop",defaultValue = "0") int pop) {
        List<Message> messagesList =  message_service.getLatestNews(0, 0, 10);

        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messagesList){
            ViewObject vo = new ViewObject();
            vo.set("news", message);
            vo.set("user", userService.getUser(message.getUser_id()));
            vos.add(vo);
        }
        model.addAttribute("pop", pop);
        model.addAttribute("vos", vos);
        return "index";
    }

    @RequestMapping("/redirect/{code}")
    public RedirectView redirectView(@PathVariable("code") int code){
        RedirectView red = new RedirectView("/", true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

}
