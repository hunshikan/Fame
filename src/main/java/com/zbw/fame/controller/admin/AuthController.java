package com.zbw.fame.controller.admin;

import com.zbw.fame.controller.BaseController;
import com.zbw.fame.model.Users;
import com.zbw.fame.service.UsersService;
import com.zbw.fame.util.FameConsts;
import com.zbw.fame.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 后台用户验证 Controller
 *
 * @auther zbw
 * @create 2017/7/11 20:15
 */
@RestController
@RequestMapping("/admin")
public class AuthController extends BaseController {

    @Autowired
    private UsersService usersService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(HttpServletResponse response, @RequestParam String username, @RequestParam String password, String remember_me) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return RestResponse.fail("用户名和密码不能为空");
        }
        Users user = usersService.login(username, password);
        request.getSession().setAttribute(FameConsts.USER_SESSION_KEY, user);

        return RestResponse.ok();
    }
}
