package cn.kgc.demo.service;

import cn.kgc.demo.pojo.ReplyDetail;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName DemoService
 * @Description TODO
 * @Author zhaojing
 * @Date 2021/4/26 15:22
 * @Version 1.0
 */
public interface DemoService {

    //根据搜索条件进行分页查询帖子列表
    PageInfo findInvitationByPage(String searchName, Integer pageNum, Integer pageSize);

    //根据帖子id分页查看回复信息列表
    PageInfo findReplyDetailByPage(Integer invId, Integer pageNum, Integer pageSize);

    boolean saveReplyDetail(ReplyDetail replyDetail);

    boolean removeInvAndReplayDetailById(Integer invId);
}
