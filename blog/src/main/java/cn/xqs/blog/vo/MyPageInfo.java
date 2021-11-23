package cn.xqs.blog.vo;

import cn.xqs.blog.pojo.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyPageInfo {
    private List<Message> list;
    private boolean hasPreviousPage = false;
    private boolean hasNextPage = false;
    private int total;
    private int pageNum;
    private int pages;

}
