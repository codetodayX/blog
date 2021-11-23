package cn.xqs.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类用来帮助我们完成首页右侧分类Top
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeTop {
    private Integer id;
    private String name;
    private Integer bId;
    private Integer count;
}
