package cn.xqs.blog.controller.admin;

import cn.xqs.blog.pojo.Type;
import cn.xqs.blog.service.TypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/admin")
public class TypesController {

    @Autowired
    TypeService typeService;


    //跳转到分类列表页面
    @RequestMapping(path = "/types")
    public String types(Model model,
                        @RequestParam(defaultValue = "1") int curPage,
                        @RequestParam(value = "typeMsg", defaultValue = "null") String typeMsg) {
        Page<Type> page = typeService.getList(curPage);
        model.addAttribute("page", page);
        if (!typeMsg.equals("null")) {
            model.addAttribute("typeMsg", typeMsg);
        } else {
            model.addAttribute("typeMsg", "");
        }
        return "admin/types";
    }


    //跳转到分类新增页面
    @RequestMapping(path = "/types/input")
    public String typesInput() {
        return "admin/types-input";
    }


    //分类新增
    @RequestMapping(path = "/types/inputInvoke")
    public String typesInputInvoke(Type type,
                                   RedirectAttributes attributes) {
        System.out.println(type.getId());
        boolean flag = typeService.saveType(type);

        if (flag == true) {
            //如果flag为true，说明新增成功
            attributes.addAttribute("typeMsg", "新增成功!");
            return "redirect:/admin/types";
        } else {
            attributes.addAttribute("typeMsg", "添加失败,分类名字重复!");
            return "redirect:/admin/types";
        }
    }


    //分类删除
    @RequestMapping(path = "/types/remove")
    public String typesRemove(@RequestParam Integer id,
                              RedirectAttributes attributes) {
        typeService.remove(id);
        attributes.addAttribute("typeMsg", "删除成功!");
        return "redirect:/admin/types";
    }


    //跳转到分类编辑页面
    @RequestMapping(path = "/types/editor")
    public String typeEditor(Type type,
                             Model model) {
        model.addAttribute("type", type);
        return "admin/types-editor";

    }


    //分类编辑逻辑
    @RequestMapping(path = "/types/editorInvoke")
    public String typesEditorInvoke(Type type,
                                    RedirectAttributes attributes) {
        typeService.update(type);
        attributes.addAttribute("typeMsg", "修改成功!");
        return "redirect:/admin/types";
    }
}
