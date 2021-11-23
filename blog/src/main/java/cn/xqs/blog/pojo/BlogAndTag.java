package cn.xqs.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 维护博客和标签关系的类,对应数据库中的t_blog_tag表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_blog_tag")
public class BlogAndTag implements Serializable {
    @TableField("tag_id")
    private Integer tagId;
    @TableField("blog_id")
    private Integer blogId;
}
