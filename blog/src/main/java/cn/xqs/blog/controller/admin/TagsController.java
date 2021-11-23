package cn.xqs.blog.controller.admin;

import cn.xqs.blog.pojo.Tag;
import cn.xqs.blog.pojo.Type;
import cn.xqs.blog.service.TagService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagsController {

    @Autowired
    TagService tagService;


    //跳转到标签列表页面
    @RequestMapping(path = "/tags")
    public String tags(@RequestParam(defaultValue = "1") Long curPage,
                       Model model,
                       @RequestParam(value = "tagMsg", defaultValue = "null") String tagMsg) {
        Page<Tag> page = tagService.getList(curPage);
        if (!tagMsg.equals("null")) {
            model.addAttribute("tagMsg", tagMsg);
        } else {
            model.addAttribute("tagMsg", "");
        }
        model.addAttribute("page", page);
        return "admin/tags";
    }


    //跳转到标签新增页面
    @RequestMapping(path = "/tags/input")
    public String tagsInput() {
        return "admin/tags-input";
    }


    //标签新增逻辑
    @RequestMapping(path = "/tags/inputInvoke")
    public String tagsInputInvoke(Tag tag,
                                  RedirectAttributes attributes) {

        boolean flag = tagService.saveTag(tag);
        if (flag == true) {
            attributes.addAttribute("tagMsg", "添加成功!");
            return "redirect:/admin/tags";
        } else {
            attributes.addAttribute("tagMsg", "添加失败,标签名重复!");
            return "redirect:/admin/tags";
        }
    }


    //标签删除逻辑
    @RequestMapping(path = "/tags/remove")
    public String tagsRemove(@RequestParam Long id,
                             RedirectAttributes attributes) {
        tagService.remove(id);
        attributes.addAttribute("tagMsg", "删除成功!");
        return "redirect:/admin/tags";
    }


    //跳转标签编辑页面
    @RequestMapping(path = "/tags/editor")
    public String tagsEditor(Tag tag,
                             Model model) {
        model.addAttribute("tag", tag);
        return "admin/tags-editor";
    }


    //标签编辑逻辑
    @RequestMapping(path = "/tags/editorInvoke")
    public String tagsEditorInvoke(Tag tag,
                                   RedirectAttributes attributes) {
        tagService.update(tag);

        attributes.addAttribute("tagMsg", "修改成功!");
        return "redirect:/admin/tags";
    }
}
