package cn.xqs.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 标签实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_tag")     //指定实体类应对的表名
public class Tag implements Serializable {
    @TableId(type = IdType.AUTO)            //标注主键自增
    private Integer id;                        //id
    private String name;                    //标签名
    @TableField(exist = false)
    private List<Blog> blogs;               //一个标签可以包含多条博客
}
