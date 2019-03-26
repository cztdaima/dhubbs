package com.dhu.bbs;


import com.dhu.bbs.Util.BbsUtil;
import com.dhu.bbs.dao.LoginTicketDao;
import com.dhu.bbs.dao.MessageDao;
import com.dhu.bbs.dao.UserDao;
import com.dhu.bbs.model.LoginTicket;
import com.dhu.bbs.model.Message;
import com.dhu.bbs.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init_schema.sql")
public class ToutiaoApplicationTests {
	@Autowired
	private UserDao userMapper;
	@Autowired
	private  LoginTicketDao loginTicketDao;

	@Autowired
	private MessageDao messageDao;

	@Test
	public void contextLoads() {
		Random random = new Random();
		for(int i = 0; i < 11; i++){
			User user = new User();
			user.setHead_url(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			user.setName(String.format("user%d",i));
			user.setPassword("password");
			user.setSalt(".");

			user.setUser_limit(0);

			userMapper.userAdd(user);

			user.setPassword(BbsUtil.MD5(user.getPassword()+user.getSalt()));
			userMapper.updatePassword(user);

			LoginTicket ticket = new LoginTicket();
			ticket.setStatus(0);
			ticket.setUser_id(i+1);
			Date date = new Date();
			ticket.setExpire(date);
			ticket.setTicket(String.format("ticket%d", i+1));
			loginTicketDao.addTicket(ticket);

			Message message = new Message();
			message.setComment_count(i);
			date.setTime(date.getTime() + 1000*3600*5);
			message.setCreated_date(date);
			message.setImage(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			message.setLike_count(i);
			message.setTitle("title" + String.format("%d", i+1));
			message.setUser_id(i+1);
			messageDao.messageAdd(message);




		}
		Assert.assertEquals(1,loginTicketDao.selectByTicket("TICKET1").getUser_id());
		System.out.println(messageDao.findByName("title7"));


//		System.out.println(messageDao.selectByUserIdAndOffset(2, 2 ,6).getCreated_date());
		loginTicketDao.updateStatus("TICKET1",1);
	}


}
