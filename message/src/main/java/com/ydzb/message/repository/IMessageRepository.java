package com.ydzb.message.repository;


import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.sms.entity.Message;
import com.ydzb.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface IMessageRepository extends IBaseRepository<Message, Long> {

	@Query("from User u where u.username = :username and u.status = 0 ")
	public User findUserByUsername(@Param("username") String username);

}
