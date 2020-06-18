package study.xln.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.xln.community.dto.AccessTokenDTO;
import study.xln.community.dto.GithubUser;
import study.xln.community.provider.GithubProvider;

@Controller
public class AuthorizeController {

    @Autowired  //自动的去把String容器里面写好的实例化的一个实例加载到当前使用的上下文
    private GithubProvider githubProvider;

    //注释@Value读取配置文件名称对应的value
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")  String code,
                           @RequestParam(name = "state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //在github的OAuth application settings里面对应Client ID和Cilent Secret
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //在括号里new一个对象时，选中并使用Ctrl+Alt+V 自动创建对象
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        //登录成功之后返回首页
        return "index";
    }
}
