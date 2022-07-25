package com.onlinechat.controller;


import com.onlinechat.base.RestResponse;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.vo.UserVO;
import com.onlinechat.exception.OnlineChatException;
import com.onlinechat.service.TUserService;
import com.onlinechat.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-15
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class TUserController {

    @Autowired
    private TUserService userService;

    @PutMapping("/updateUserMsg")
    public RestResponse<String> updateUserMsg(@RequestBody UserVO userVO){
        try {
            userService.updateUser(userVO);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PostMapping("/register")
    public RestResponse<String> register(@RequestBody TUser user){

        try {
            TUser tuser = userService.getUserByUserName(user.getUsername());
            if(tuser != null){
                throw new OnlineChatException(SystemCode.REGISTERERROR.getCode(), SystemCode.REGISTERERROR.getMessage());
            }
            String password = MD5.encrypt(user.getPassword());
            user.setPassword(password);
            userService.save(user);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok();
    }

    @PutMapping("/editProfile")
    public RestResponse<String> editProfile(@RequestBody TUser user){
        try {
            userService.editProfile(user);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/getUserInfo")
    public RestResponse<UserVO> getUserInfo(){
        UserVO userVO = null;
        try {
            userVO = userService.getUserInfo();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(userVO);
    }

    @PostMapping("/login")
    public RestResponse<String> login(@RequestBody TUser user){

        String userName = user.getUsername();
        String password = user.getPassword();
        String token = "";
        try {
            token = userService.login(userName,password);
        } catch (OnlineChatException e) {
            return RestResponse.fail(e.getCode(),e.getMsg());
        }
        return RestResponse.ok(token);
    }

    // 为了避免跨域写一个空的~
    @PostMapping("/loginout")
    public RestResponse<String> loginout() {
        return null;
    }



    @GetMapping("/searchFriend")
    public RestResponse<UserVO> searchFriend(@RequestParam("userName") String username){
        UserVO userVO = null;
        try {
            userVO = userService.searchFriend(username);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(userVO);
    }

}

