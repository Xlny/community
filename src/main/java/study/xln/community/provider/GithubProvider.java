package study.xln.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import study.xln.community.dto.AccessTokenDTO;
import study.xln.community.dto.GithubUser;

import java.io.IOException;


//注解@Controller是把类作为路由API的一个承载者
//注解@Component仅仅把当前类初始化到Spring的容器的上下文，使用这个注解就不需要再去实例化这个对象了
@Component
public class GithubProvider {
    //如果参数超过两个，就要把它封装成对象去做

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        //已经封装好方法了，把它定义成局部变量
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

//        String json = "";
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];

            return token;
//            System.out.println(string);
//            return string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //把string的json对象自动转化解析成java的类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
