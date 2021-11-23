package cn.xqs.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user")
public class User implements Serializable {

    private Integer id;                        //id
    @TableField("nickName")
    private String nickName;                //用户昵称
    private String username;                //登录账号
    private String password;                //登录密码
    @TableField("headPicture")
    private String headPicture;             //头像
    @TableField("createTime")
    private Date createTime;                //用户创建时间
    @TableField("updateTime")
    private Date updateTime;                //用户更新时间
    private String type;                    //用户类型
    private String email;                   //用户邮箱
    @TableField(exist = false)
    private List<Blog> blogs;               //一个用户可以有多条博客
}
