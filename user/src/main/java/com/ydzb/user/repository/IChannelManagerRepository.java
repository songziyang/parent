package com.ydzb.user.repository;


import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.ChannelManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IChannelManagerRepository extends IBaseRepository<ChannelManager, Long> {


    @Query(" SELECT cm FROM ChannelManager as cm  where cm.type = :ptype and cm.status = 1 ")
    public ChannelManager findChannelManagerByType(@Param("ptype") Integer ptype);


}
