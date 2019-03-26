package com.dhu.bbs.controller;


import com.dhu.bbs.model.Message;
import com.dhu.bbs.model.ViewObject;
import com.dhu.bbs.service.MessageService;
import com.dhu.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService message_service;

    @RequestMapping(value={"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headName = request.getHeaderNames();
        while(headName.hasMoreElements()){
            String name = headName.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        return sb.toString();
    }


    @RequestMapping(value = {"profile/{user_id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(@PathVariable("user_id") int user_id, Model model){
        List<Message> messagesList =  message_service.getLatestNews(user_id, 0, 10);

        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messagesList){
            ViewObject vo = new ViewObject();
            vo.set("user", userService.getUser(message.getUser_id()));
            vo.set("news", message);
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "profile";
    }

    @RequestMapping(value = "inform",method = RequestMethod.GET)
    public String getInform() {
        return "inform";
    }


    @RequestMapping(value = {"inform_page"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String inform(Model model, @RequestParam("select2") String select2,
                        @RequestParam("url") String url,
                        @RequestParam("describe") String describe,
                        @RequestParam("email") String email
    ) {

        try{
           userService.inform(select2, url, describe, email);
            model.addAttribute("code","0") ;
            return "inform";
        } catch (Exception e){
            model.addAttribute("code","1") ;
            return "inform";
        }
    }

    @RequestMapping(value = "post",method = RequestMethod.GET)
    public String getPost() {
        return "post";
    }

    @RequestMapping(value = {"post_page"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String post(Model model, @RequestParam("select2") String select2,
                       @RequestParam("file")MultipartFile file,
                       @RequestParam("describe") String describe,
                       @RequestParam("title") String title,
                       @CookieValue("ticket") String ticket
    ) {

        try{
            Map<String, Object> map = userService.addMessage(select2, file, describe, title, ticket);
            if( map.isEmpty()){
                model.addAttribute("code","0") ;
                return "post";
            } else {
                model.addAttribute("code","1") ;
                return "post";
            }

        } catch (Exception e){
            model.addAttribute("code","1") ;
            return "post";
        }
    }

}
