package com.atguigu.eduservice.controller;

import com.atgugui.commonutils.R;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        Map<String, Object> map = new HashMap<String, Object>();
        String[] roleArray = {"admin"};
        map.put("role", roleArray);
        map.put("name", "Super Admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.ok().data(map);
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

}
