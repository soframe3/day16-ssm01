package cn.kgc.demo.controller;

import cn.kgc.demo.pojo.ReplyDetail;
import cn.kgc.demo.service.DemoService;
import cn.kgc.utils.Consants;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author zhaojing
 * @Date 2021/4/26 15:14
 * @Version 1.0
 */
@Controller
@RequestMapping("invitation")
public class TestController {

    @Autowired
    private DemoService demoService;

    /**
     *  根据搜索条件进行分页查询帖子列表
     * @param searchName  搜索条件
     * @param model  封装数据
     * @param pageNum 当前页码
     * @return list.jsp
     */
    @RequestMapping("list")
    public String list(@RequestParam(required = false) String searchName,
                       Model model,
                       @RequestParam(defaultValue = "1") Integer pageNum){
        //分页查询
        PageInfo pageInfo = demoService.findInvitationByPage(searchName,pageNum, Consants.PAGE_SIZE);
        //封装数据
        model.addAttribute("pageInfo",pageInfo);
        //回显查询条件
        model.addAttribute("searchName",searchName);
        return "list";
    }

    //根据帖子id分页查看回复信息列表
    @RequestMapping("cat/{invId}")
    public String catReplyDetail(
            @PathVariable("invId") Integer invId,
            Model model,
            @RequestParam(defaultValue = "1") Integer pageNum){
        //根据帖子id分页查看回复信息列表
        PageInfo pageInfo = demoService.findReplyDetailByPage(invId, pageNum, Consants.PAGE_SIZE);
        model.addAttribute("pageInfo",pageInfo);
        //回显invId
        model.addAttribute("id",invId);
        return "replyDetail";
    }

    /*携带invid跳转到添加回复页面*/
    @RequestMapping("addUI/{invId}")
    public String addUI(@PathVariable("invId") Integer invId,Model model){
        //封装invId
        model.addAttribute("id",invId);
        return "add";
    }

    //添加帖子回复信息
    @RequestMapping("add/{invId}")
    public String add(
            @PathVariable("invId") Integer invId,
            ReplyDetail replyDetail,
            Model model){
        //封装replyDetail实体信息
        replyDetail.setCreatedate(new Date());
        //设置当前回复信息的帖子id
        replyDetail.setInvid(invId);
        //保存
        boolean b =  demoService.saveReplyDetail(replyDetail);
        //判断提示消息
        if(b){
            model.addAttribute("saveMsg","添加成功！");
        }else{
            model.addAttribute("saveMsg","添加失败！");
        }
        //转发
        return "forward:/invitation/cat/" + invId;
    }

    //根据帖子id，删除帖子列表和回复信息
    @RequestMapping("del/{invId}")
    public String delInvAndReplayDetail(@PathVariable("invId") Integer invId,Model model){
        boolean b = demoService.removeInvAndReplayDetailById(invId);
        //判断提示消息
        if(b){
            model.addAttribute("delMsg","删除成功！");
        }else{
            model.addAttribute("delMsg","删除失败！");
        }
        return "forward:/invitation/list";
    }

}
