package cn.xqs.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分页查询对象
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery {
    private String title;
    private Integer typeId;
    private Boolean recommend;
    private Integer curPage;
}
