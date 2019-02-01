package sports.controller;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sports.entity.NoteResult;
import sports.entity.User;
import sports.entity.UserReserve;
import sports.service.UserService;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;
    /*
    *  用户注册模块
    *  包含用户注册信息判断，为用户生成身份唯一标识符
    * */
    @RequestMapping(value = "/loginInfo",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public NoteResult loginInfo(@RequestBody User user,HttpServletResponse response) throws NoSuchAlgorithmException {
        String name = user.getUsername();
        String password = user.getPassword();
        NoteResult noteResult =  userService.register(name,password);

        String userUUID = noteResult.getUserID();
        Cookie userID = new Cookie("uid",userUUID);
        response.addCookie(userID);

        String noteResultJson = JSON.toJSONString(noteResult);

        System.out.println("========================"+noteResultJson+"======================");
        return noteResult;
    }
    @RequestMapping(value = "/checkLogin",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public NoteResult checkLogin(@RequestBody User user,@CookieValue("uid") String uid) throws NoSuchAlgorithmException {
        System.out.println("===================="+uid+"======================");
        String username = user.getUsername();
        String password = user.getPassword();
        NoteResult noteResult = userService.login(username,password);
        String noteResultJson = JSON.toJSONString(noteResult);
        System.out.println("========================"+noteResultJson+"======================");
        return noteResult;
    }

    @RequestMapping(value = "/activityOrder",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public UserReserve activityOrder(@RequestBody String activityDate, int activityStatus, @CookieValue("uid") String uid){
        System.out.println("===============this is activityOrder=================");
        UserReserve userReserve = userService.ActivityOptions(activityDate,uid,activityStatus);
        System.out.println(userReserve);
        return userReserve;
    }


}
