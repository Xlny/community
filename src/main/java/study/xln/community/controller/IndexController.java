package study.xln.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller自动扫描识别控制器，允许这个类接收前端请求
@Controller
public class IndexController {

    //@GetMapping注释确保Http get请求到/hello, 然后被映射到hello()方法
    @GetMapping("/")  //匹配路径
    //@RequestParam将查询字符串参数的值绑定name到hello()方法的name参数中
    //@RequestParam(快捷键Ctrl+P)
    public String index(){
//       model.addAttribute("name",name);
       //自动去找index这个模板，在templates里面
       return "index";
    }
}
