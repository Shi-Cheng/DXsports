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
        if(userNameKey.getUsername() == null){  //用来模拟，这样会与
            noteResult.setStatus(1);
            noteResult.setMsg("用户名不存在");
            return noteResult;
        }
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

        UserNameKey userNameKey = new UserNameKey(); //调用查询接口
        if(userNameKey.getUsername() != null ){
            noteResult.setStatus(1);
            noteResult.setMsg("用户名不存在");
            return noteResult;
        }
        //UUIDKey 记录形式
        User user = new User();

        String userUUID = UserUtil.creadid();
        user.setUsername(username);
        String md5_pwd = UserUtil.md5(password);
        user.setPassword(md5_pwd);
        user.setUserID(userUUID);
        //存储为{"userID":userID,"username":username,"password":password } 形式
        JSONObject userJson = JSONObject.fromObject(user);
//        Map<String,User> userMap = new HashMap<>();
//        userMap.put(userUUID,user);
//        //存储为{"userID":userID,"username":username,"password":password } 形式
//        JSONObject userJSON = JSONObject.fromObject(userMap);
        //UserNameKey记录形式{ "userNameKey",{{"userID":userID,"username":username,"password":password }" }
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
        System.out.println("=======UUID为key========="+userJson+"==================");
        System.out.println("=======name为key========="+userNameKeyJSON+"================");
        //调用UserDao进行保存
//        userDao.save(userJSON);//模拟<UUID,Value>上链
//        userDao.save(userNameKeyJSON); //模拟<UserNameKey.Value> 上链
        return noteResult;
    }

    @Override
    public UserReserve ActivityOptions(String activity_id, String user_id, int reserve_status) {

        UserReserve userReserve = new UserReserve();
        String activityUUID = UserUtil.creadid();

        userReserve.setReserve_id(activityUUID); //用户预约的id
        userReserve.setActivity_id(activity_id);//用户预约活动的id  把活动开始时间记录为活动的id 日期形式
        userReserve.setUser_id(user_id);//用户id
        userReserve.setReserve_status(reserve_status);//预约状态
        //第一种上链内容，普通上链
        JSONObject userReserveJson = JSONObject.fromObject(userReserve);
        //第二种上链内容，<日期，value> 通过日期查询相关的信息 每个用户有一个userid，通过userid查询用户信息
        Map<String,UserReserve> userReserveMap = new HashMap<>();
        userReserveMap.put(activity_id,userReserve);
        JSONObject userReserveMapJson = JSONObject.fromObject(userReserveMap);

        System.out.println(userReserveJson);
        System.out.println(userReserveMapJson);
        return userReserve;
    }

}
