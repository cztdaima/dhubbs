package com.dhu.bbs.controller;


import com.dhu.bbs.Util.BbsUtil;
import com.dhu.bbs.model.Message;
import com.dhu.bbs.model.ViewObject;
import com.dhu.bbs.service.MessageService;
import com.dhu.bbs.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(path = "/image", method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response){
        System.out.println(imageName);
        response.setContentType("image/jepg");
        System.out.println(BbsUtil.IMAGE_DIR + imageName);
        try{
            StreamUtils.copy(new FileInputStream(
                            new File(BbsUtil.IMAGE_DIR + imageName)),
                    response.getOutputStream());
        } catch (Exception e){
            System.out.println("解析图片错误");
        }
    }


    @RequestMapping(value = {"title/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String messageDetail(@PathVariable("id") int id, Model model){
        Message message =  messageService.getDetail(id);
        ViewObject vos = new ViewObject();
        vos.set("user", userService.getUser(message.getUser_id()));
        vos.set("news", message);


        model.addAttribute("vo", vos);
        return "message_detail";
    }

}
