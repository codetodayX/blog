package cn.xqs.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.rmi.runtime.Log;

import java.io.Serializable;
import java.util.List;

/**
 * 分类实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_type")
public class Type implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;                    //id
    private String name;                //分类名
    private List<Blog> blogs;           //一个分类对应多条博客
}
