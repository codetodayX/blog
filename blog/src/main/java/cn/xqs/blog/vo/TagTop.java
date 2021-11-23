package cn.xqs.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该实体类用于完成首页右侧标签top
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagTop {
    private Integer id;
    private String name;
    private Integer count;
}
