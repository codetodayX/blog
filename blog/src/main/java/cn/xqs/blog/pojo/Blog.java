package cn.xqs.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 博客详情实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_blog")
public class Blog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;                        //id
    private String title;                   //标题
    @TableField("createTime")           //mybatis-plus默认会将驼峰命名用下划线区分大小写 例如:createTime =>create_time
    private Date createTime;                //博客创建时间
    @TableField("updateTime")
    private Date updateTime;                //博客更新时间
    private Long views;                     //浏览次数
    @TableField("firstPicture")
    private String firstPicture;            //首图
    private String info;                    //博客内容
    private String flag;                    //博客标记(原创,转载...)
    private Boolean appreciation = false;           //是否开启赞赏
    @TableField("shareStatement")
    private Boolean shareStatement = false;         //是否开启版权声明
    @TableField("commentEnabled")
    private Boolean commentEnabled = false;         //是否开启评论
    private Boolean published = false;           //博客状态(发布,保存)
    private Boolean recommend = false;               //是否推荐
    @TableField(exist = false)
    private Type type;                      //博客所属分类
    @TableField(exist = false)
    private List<Tag> tags;                 //博客所含标签
    @TableField(exist = false)
    private List<Comment> comments;         //博客中的评论
    @TableField(exist = false)
    private User user;                      //用户
    private String description;             //博客描述
    @TableField(exist = false)
    private String tagIds;                  //含有标签对应的ID组成的字符串
    @TableField("u_id")
    private Integer userId;                     //用户ID,插入数据库时使用
    @TableField("type_id")
    private Integer typeId;                    //分类ID,插入数据时使用


    public Date parseDate() {
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD hh-mm-ss");
        return null;
    }


    /**
     * 通过序列化将对象深拷贝
     *
     * @return
     */
    public Blog myClone() {
        Blog b = null;
        try {
            //1.串行化(将对象输出到流里面，需要注意的是，此时原对象任然还存在与JVM中)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);

            //2.并行化(从流中读取对象，该对象与原对象地址值不一样，即深拷贝)
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            b = (Blog) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }


}
