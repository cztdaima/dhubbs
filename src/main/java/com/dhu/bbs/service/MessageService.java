package com.dhu.bbs.service;

import com.dhu.bbs.Util.BbsUtil;
import com.dhu.bbs.dao.MessageDao;
import com.dhu.bbs.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    public List<Message> getLatestNews(int user_id, int offset, int limit){
        return messageDao.selectByUserIdAndOffset(user_id, offset, limit);
    }

    public void addMessage(Message message){
        messageDao.messageAdd(message);
    }

    public Message getDetail(int id){
        return messageDao.selectById(id);
    }

}
