package sports.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        String name = user.getUser_name();
        String password = user.getUser_pwd();
        NoteResult noteResult =  userService.register(name,password);
        String userUUID = noteResult.getUserID();
        Cookie userID = new Cookie("uid",userUUID);
        response.addCookie(userID);
        String noteResultJson = JSON.toJSONString(noteResult);
        return noteResult;
    }
    @RequestMapping(value = "/checkLogin",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public NoteResult checkLogin(@RequestBody User user,@CookieValue("uid") String uid) throws NoSuchAlgorithmException {
        String username = user.getUser_name();
        String password = user.getUser_pwd();
        NoteResult noteResult = userService.login(username,password);
        String noteResultJson = JSON.toJSONString(noteResult);
        System.out.println("==============noteresultjeson"+noteResultJson+"=====================");
        return noteResult;
    }
    @RequestMapping(value = "/activityOrder",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public UserReserve activityOrder(@RequestBody String activityBody, @CookieValue("uid") String uid){
        JSONObject jsonObject = JSONObject.parseObject(activityBody);
        String activityDate = jsonObject.get("activity_id").toString();
        UserReserve userReserve = userService.ActivityOptions(activityDate,uid);
        return userReserve;
    }
    //userCancelActivity
    @RequestMapping(value = "/activityCancel",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public UserReserve activityCancel(@RequestBody String activityBody, @CookieValue("uid") String uid){
        JSONObject jsonObject = JSONObject.parseObject(activityBody);
        String activityId = jsonObject.get("activity_id").toString();
        String activityStatus = jsonObject.get("reserve_status").toString();
        UserReserve userReserve = userService.CancelActivity(activityId,activityStatus,uid);
        return userReserve;
    }

}
