package cn.kgc.demo.service.impl;

import cn.kgc.demo.dao.InvitationMapper;
import cn.kgc.demo.dao.ReplyDetailMapper;
import cn.kgc.demo.pojo.Invitation;
import cn.kgc.demo.pojo.ReplyDetail;
import cn.kgc.demo.service.DemoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName DemoServiceImpl
 * @Description TODO
 * @Author zhaojing
 * @Date 2021/4/26 15:23
 * @Version 1.0
 */
@Service
@Transactional(readOnly = false)  //readOnly = false: 表示事务以非只读的方式运行。
public class DemoServiceImpl implements DemoService {

    /*引入帖子的mapper*/
    @Autowired
    private InvitationMapper invitationMapper;

    /*引入回复信息的mapper*/
    @Autowired
    private ReplyDetailMapper replyDetailMapper;

    @Override
    public PageInfo findInvitationByPage(String searchName, Integer pageNum, Integer pageSize) {
        //1.开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //2.查询数据
        //根据搜索条件模糊查询帖子列表
        List<Invitation> list = invitationMapper.selInvitationBySearchName(searchName);
        //3.封装pageInfo
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo findReplyDetailByPage(Integer invId, Integer pageNum, Integer pageSize) {
        //1.开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //2.查询数据
        //根据帖子id查询回复信息列表
        List<ReplyDetail> list = replyDetailMapper.selReplyDetailByInvId(invId);
        //3.封装pageInfo
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public boolean saveReplyDetail(ReplyDetail replyDetail) {
        if(replyDetailMapper.insert(replyDetail) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean removeInvAndReplayDetailById(Integer invId) {
        try{
            //1. 根据帖子id删除回复信息
            replyDetailMapper.deleteReplyDetailByInvId(invId);

            //2.根据帖子id删除帖子信息
            invitationMapper.deleteByPrimaryKey(invId);
            return true;
           }catch (Exception e){
               e.printStackTrace();
           }
        return false;
    }
}
