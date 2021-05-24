package cn.kgc.demo.dao;

import cn.kgc.demo.pojo.ReplyDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReplyDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReplyDetail record);

    int insertSelective(ReplyDetail record);

    ReplyDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReplyDetail record);

    int updateByPrimaryKey(ReplyDetail record);

    @Select("select * from reply_detail where invid = #{invId} order by createdate desc")
    List<ReplyDetail> selReplyDetailByInvId(@Param("invId") Integer invId);

    @Delete("delete from reply_detail where invid = #{invId}")
    int deleteReplyDetailByInvId(@Param("invId") Integer invId);
}
