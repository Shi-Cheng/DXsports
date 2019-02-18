package sports.service.impl;
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
        //User user = userDao.findByName(username);   根据username为主键进行判断
       // UserNameKey userNameKey = userDao.findByName(username); //调用查询接口
        NoteResult noteResult = new NoteResult();
        UserNameKey userNameKey = new UserNameKey();
        if (userLoginCheck(username, noteResult, userNameKey)) return noteResult;

        String md5_pwd = UserUtil.md5(password);
        if (!userNameKey.getPassword().equals(md5_pwd)){
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
        //根据字符串来判断用户注册状态  并涉及到上链，两条链，主键分别为   UUID 和 nameUUID
        // UserNameKey userNameKey = userDao.findByName(username); //模拟<UserNameKey.Value>链上查询
        NoteResult noteResult = new NoteResult();
        UserNameKey userNameKey = new UserNameKey();
        if (userLoginCheck(username, noteResult, userNameKey)) return noteResult;
        User user = new User();
        String userUUID = UserUtil.creadid();
        user.setUsername(username);
        String md5_pwd = UserUtil.md5(password);
        user.setPassword(md5_pwd);
        user.setUserID(userUUID);
        //{'args':['save',{"password":"lueSGJZetyySpUndWjMBEg==","userID":"d05925b5eb11440db324b5362fc78b3d","username":"111111"}]}
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

    private boolean userLoginCheck(String username, NoteResult noteResult, UserNameKey userNameKey) {
        Map<String,String> userMap = new HashMap<String,String>();
        userMap.put("username",username);
        JSONObject usernameJson = JSONObject.fromObject(userMap);
        String userCheckInfo = "{'args':['queryUser',"+usernameJson+"]}"; //封装
        //调用智能合约，根据返回值来判断用户是否存在
        //下面只是一个模拟
        if(userNameKey.getUsername() != null ){
            noteResult.setStatus(1);
            noteResult.setMsg("用户名不存在");
            return true;
        }
        return false;
    }

    @Override
    public UserReserve ActivityOptions(String activity_id, String user_id, String reserve_status) {

        UserReserve userReserve = new UserReserve();
        String activityUUID = UserUtil.creadid();
        userReserve.setReserve_id(activityUUID); //用户预约的id
        userReserve.setActivity_id(activity_id);//用户预约活动的id  把活动开始时间记录为活动的id 日期形式
        userReserve.setUser_id(user_id);//用户id
        userReserve.setReserve_status(reserve_status);//预约状态
        //第一种上链内容，普通上链
        JSONObject userReserveJson = JSONObject.fromObject(userReserve);
        String createReserve = "{'args':['createReserve',"+userReserveJson+"]}"; //预约活动信息封装

        //第二种上链内容，<日期，value> 通过日期查询相关的信息 每个用户有一个userid，通过userid查询用户信息
        Map<String,UserReserve> userReserveMap = new HashMap<>();
        userReserveMap.put(activity_id,userReserve);
        JSONObject userReserveMapJson = JSONObject.fromObject(userReserveMap);
        String createDateReserve = "{'args':['createReserve',"+userReserveMapJson+"]}"; //预约活动信息封装

        System.out.println("========普通上链的格式======"+userReserveJson+"===================");
        System.out.println("=======UserID上链的格式============"+userReserveMapJson+"===================");
        return userReserve;
    }

}
