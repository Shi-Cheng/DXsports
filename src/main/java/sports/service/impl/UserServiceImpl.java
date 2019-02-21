package sports.service.impl;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import sports.entity.NoteResult;
import sports.entity.User;
import sports.entity.UserNameKey;
import sports.entity.UserReserve;
import sports.service.UserService;
import sports.util.UserUtil;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public NoteResult login(String username, String password) throws NoSuchAlgorithmException {
        NoteResult noteResult = new NoteResult();
        // 33 111111
        //if (userCheck(username, noteResult)) return noteResult;
        Map<String, String> userMap = new HashMap<>();
        userMap.put("user_name", username);
        JSONObject usernameJson = JSONObject.fromObject(userMap);
        String userCheckInfo = "{'args':['userLogin'," + usernameJson + "]}";
        //对于用户登陆  存在用户名则返回user对象，如果不存在返回  fail
        String result = "{'user_id':'7f17a4c2303711e9b210d663bd873d93','user_name':'33','user_pwd':'lueSGJZetyySpUndWjMBEg=='}";
        //String result = "fail";
        System.out.println("============="+username+"========");
        if (result.equals("fail")) {
            System.out.println("==============fail===========");
            noteResult.setStatus(1);
            noteResult.setMsg("用户名不存在");
            return noteResult;
        }

        String userCheckResult = "{'user_id':'7f17a4c2303711e9b210d663bd873d93','user_name':'33','user_pwd':'lueSGJZetyySpUndWjMBEg=='}";//检测的密码111111
        User user = JSON.parseObject(userCheckResult,User.class);
        String md5_pwd = UserUtil.md5(password);
        if(!user.getUser_pwd().equals(md5_pwd)){
            noteResult.setStatus(2);
            noteResult.setMsg("密码不正确");
            return noteResult;
        }
        noteResult.setStatus(0);
        noteResult.setMsg("用户名和密码正确");
        return noteResult;
    }
    @Override
    public NoteResult register(String username, String password) throws NoSuchAlgorithmException {
        NoteResult noteResult = new NoteResult();
        User user = new User();
        //注册用户不能重名，注册前进行校验

        Map<String, String> userMap = new HashMap<>();
        userMap.put("user_name", username);
        JSONObject usernameJson = JSONObject.fromObject(userMap);
        String userCheckInfo = "{'args':['userLogin'," + usernameJson + "]}";
        //对于用户登陆  存在用户名则返回user对象，如果不存在返回  fail
        String result = "{'user_id':'7f17a4c2303711e9b210d663bd873d93','user_name':'33','user_pwd':'lueSGJZetyySpUndWjMBEg=='}";
        //String result = "fail";
        System.out.println("============="+username+"========");
        if (result.equals("fail")) {
            System.out.println("==============fail===========");
            noteResult.setStatus(1);
            noteResult.setMsg("用户名不存在");
            return noteResult;
        }
        String userUUID = UserUtil.creadid();
        user.setUser_name(username);
        String md5_pwd = UserUtil.md5(password);
        user.setUser_pwd(md5_pwd);
        user.setUser_id(userUUID);
        JSONObject userJson = JSONObject.fromObject(user);
        String userJsonSave = "{'args':['createUser',"+userJson+"]}";

        UserNameKey unk = new UserNameKey();
        unk.setUsername(username);
        unk.setPassword(md5_pwd);
        unk.setUUID(userUUID);

        noteResult.setStatus(0);
        noteResult.setUsername(username);
        noteResult.setPassword(password);
        noteResult.setMsg("注册成功");
        noteResult.setUserID(userUUID);

        Map<String,UserNameKey> userNameKeyMap = new HashMap<>();
        userNameKeyMap.put(username,unk);
        JSONObject userNameKeyJSON = JSONObject.fromObject(userNameKeyMap);
        String usernameKeySave = "{'args':['save',"+userNameKeyJSON+"]}";
        System.out.println("=======UUID为key========="+userJson+"==================");
        System.out.println("=======UUID为key========="+userJsonSave+"==================");
        System.out.println("=======name为key========="+usernameKeySave+"================");
        //调用智能合约save的方法
        return noteResult;
    }
    @Override
    public UserReserve ActivityOptions(String activity_id, String user_id) {
        UserReserve userReserve = new UserReserve();
        String activityUUID = UserUtil.creadid();
        userReserve.setReserve_id(activityUUID);
        userReserve.setActivity_id(activity_id);
        userReserve.setUser_id(user_id);
        userReserve.setReserve_status("1");
        //第一种上链内容，普通上链
        JSONObject userReserveJson = JSONObject.fromObject(userReserve);
        String createReserve = "{'args':['createReserve',"+userReserveJson+"]}";

        //第二种上链内容，<日期，value> 通过日期查询相关的信息 每个用户有一个userid，通过userid查询用户信息
        Map<String,UserReserve> userReserveMap = new HashMap<>();
        userReserveMap.put(activity_id,userReserve);
        JSONObject userReserveMapJson = JSONObject.fromObject(userReserveMap);
        String createDateReserve = "{'args':['createReserve',"+userReserveMapJson+"]}"; //预约活动信息封装

        System.out.println("========普通上链的格式======"+userReserveJson+"===================");
        System.out.println("=======UserID上链的格式============"+userReserveMapJson+"===================");
        return userReserve;
    }
    /*
    * 用户根据活动id取消活动，用户先查询活动id的相关数据，获取当前参加状态，然后对状态进行修改，再进行
    * */
    @Override
    public UserReserve CancelActivity(String activityId,String activityStatus,String userId) {
        UserReserve userReserve = new UserReserve();
        userReserve.setReserve_id(UserUtil.creadid());
        userReserve.setReserve_status(activityStatus);
        userReserve.setActivity_id(activityId);
        userReserve.setUser_id(userId);
        JSONObject jsonObj = JSONObject.fromObject(userReserve);
        /*
         * 用户对活动的取消操作相当于把Status修改后再重新上链
         * */
        String activityJson = "{'args':['createReserve',"+jsonObj+"]}";
        //{"activity_id":"7f17a4c2303711e9b210d663bd873d93","reserve_id":"71da95b9f8af485387ab974539d36cd1","reserve_status":"1","user_id":"2e9a4c10bea74e7593c4f7f58aed2b87"}
        String test = "{'activity_id':'7f17a4c2303711e9b210d663bd873d93','reserve_id':'71da95b9f8af485387ab974539d36cd1','reserve_status':'1','user_id':'2e9a4c10bea74e7593c4f7f58aed2b87'}";
        UserReserve userReserveTest = JSON.parseObject(test, UserReserve.class);
        System.out.println("=====userReserveTest==========="+userReserveTest.getReserve_status()+"==============");
        return userReserve;
    }

}
