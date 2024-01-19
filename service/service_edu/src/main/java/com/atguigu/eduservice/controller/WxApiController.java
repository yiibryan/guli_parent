/*
package com.atgugui.user.controller;

import com.atgugui.commonutils.DateFormat;
import com.atgugui.commonutils.JwtUtils;
import com.atgugui.servicebase.exceptionhandler.GuliException;
import com.atgugui.user.entity.UcenterMember;
import com.atgugui.user.service.UcenterMemberService;
import com.atgugui.user.utils.ConstantPropertiesUtil;
import com.atgugui.user.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("/callback")
    public String callback(String code, String state) {
        //第一步 根据code向微信发送请求 获取用户的openid
        //拼接参数
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl=String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
        );
        //使用httpclient模拟发送请求
        try {
            String s = HttpClientUtils.get(accessTokenUrl);
            //将获取的信息转换成map
            Gson gson=new Gson();
            HashMap hashMap = gson.fromJson(s, HashMap.class);
            //获取map里面的内容
            String access_token = (String)hashMap.get("access_token");
            String openid = (String)hashMap.get("openid");
            //第二步 根据appid查询数据库是否有用户信息
            UcenterMember userInfoByOpenId = ucenterMemberService.getUserInfoByOpenId(openid);
            if (userInfoByOpenId==null){
                //第三步 根据openid获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl=String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取返回userinfo字符串扫描人信息
                HashMap map = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)map.get("nickname");//昵称
                String headimgurl = (String)map.get("headimgurl");//头像
                userInfoByOpenId = new UcenterMember();
                String id=UUID.randomUUID().toString().replaceAll("-","");
                userInfoByOpenId.setId(id);
                userInfoByOpenId.setIsDisabled(false);
                userInfoByOpenId.setIsDeleted(false);
                DateFormat dateFormat = new DateFormat();
                Date date = dateFormat.dateFormat(new Date());
                userInfoByOpenId.setGmtCreate(date);
                userInfoByOpenId.setGmtModified(date);
                userInfoByOpenId.setOpenid(openid);
                userInfoByOpenId.setNickname(nickname);
                userInfoByOpenId.setAvatar(headimgurl);
                ucenterMemberService.saveUserInfo(userInfoByOpenId);
            }
            //第三步 生成token重定向页面
            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(userInfoByOpenId.getId(), userInfoByOpenId.getNickname());
            //最后：返回首页面，通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw  new GuliException(20001,"登陆失败");
        }

    }


    @GetMapping("/login")
    public String login() {
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_uri进行URLEncoder编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        System.out.println(redirectUrl + "++++++++++++++++++++++++++++++++++++++++++++");
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s里面的值
        String url = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        //进行重定向到微信请求里面
        return "redirect:" + url;
    }
}
*/
