package com.zbw.fame.controller.admin;

import com.zbw.fame.controller.BaseController;
import com.zbw.fame.dto.RestResponse;
import com.zbw.fame.model.Users;
import com.zbw.fame.service.UsersService;
import com.zbw.fame.util.FameConsts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 后台用户验证 Controller
 *
 * @author zbw
 * @create 2017/7/11 20:15
 */
@Api(value = "Auth Controller", description = "后台验证接口", tags = "后台验证接口")
@RestController
@RequestMapping("/api/admin")
public class AuthController extends BaseController {

    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "后台登录", notes = "后台登录", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataTypeClass = String.class)
    })
    @PostMapping("login")
    public RestResponse login(HttpServletResponse response, @RequestParam String username, @RequestParam String password, String rememberMe) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return RestResponse.fail("用户名和密码不能为空");
        }
        Users user = usersService.login(username, password);
        request.getSession().setAttribute(FameConsts.USER_SESSION_KEY, user);

        return RestResponse.ok();
    }

    @ApiOperation(value = "后台登出", notes = "后台登出", response = String.class)
    @PostMapping("logout")
    public RestResponse logout() {
        Users user = this.user();
        if (null == user) {
            return RestResponse.fail("没有用户登陆");
        }

        request.getSession().removeAttribute(FameConsts.USER_SESSION_KEY);
        return RestResponse.ok();
    }

    @ApiOperation(value = "重置密码", notes = "重置密码", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataTypeClass = String.class)
    })
    @PostMapping("reset")
    public RestResponse resetPassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        if (!username.equals(this.user().getUsername())) {
            return RestResponse.fail("用户名与登陆的不符合");
        }

        boolean result = usersService.reset(username, oldPassword, newPassword);
        return RestResponse.ok(result);
    }

    @ApiOperation(value = "获取登录的用户名", notes = "获取登录的用户名", response = String.class)
    @GetMapping("username")
    public RestResponse username() {
        Users user = this.user();
        if (null == user) {
            return RestResponse.fail("没有用户登陆");
        }

        return RestResponse.ok(user.getUsername());
    }

}
