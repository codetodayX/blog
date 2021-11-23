package cn.xqs.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_comment")
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;                        //id
    @TableField("nickName")
    private String nickName;                //评论用户的昵称
    private String email;                   //邮箱
    @TableField("createTime")
    private Date createTime;                //评论创建时间
    private String info;                    //评论信息
    @TableField("headPicture")
    private String headPicture;             //头像
    @TableField(exist = false)
    private Blog blog;                      //一条评论只能包含一条博客
    @TableField(exist = false)
    private List<Comment> replyComments;    //一个评论对象可以包含多个评论
    @TableField(exist = false)
    private Comment parentComment;          //一条评论只能有一个父类评论
    @TableField("parentId")
    private Integer parentId;              //评论的父评论ID
    @TableField("blog_id")
    private Integer blogId;               //评论对应的博客ID
    private String type;                   //当前评论是属于游客评论还是管理员评论

}
