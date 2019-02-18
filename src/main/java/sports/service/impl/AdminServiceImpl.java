package sports.service.impl;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import sports.entity.AdminActivity;
import sports.entity.AdminLogin;
import sports.entity.NoteResult;
import sports.service.AdminService;
import sports.util.UserUtil;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Override
    public NoteResult checkLogin(String username, String password) throws NoSuchAlgorithmException {
        AdminLogin adminLogin = new AdminLogin();
        NoteResult result = new NoteResult();

        Map<String,String> adminMap = new HashMap<String,String>();
        adminMap.put("username",username);
        JSONObject jsonObj = JSONObject.fromObject(adminMap);
        String jsonAdmin = "{'args':['queryAdmin']},"+jsonObj+"]}";
        //调用智能合约，进行管理员身份校验   根据用户名查询，如果存在则返回密码，如果不存在则返回null

        if(adminLogin.getUsername() != null){
            result.setStatus(1);
            result.setMsg("非法用户");
            return result;
        }
//        String md5_adminPassWord = UserUtil.md5(password);
//        if (adminLogin.getPassword().equals(md5_adminPassWord)){
//            result.setStatus(2);
//            result.setMsg("密码有误");
//            return result;
//        }
        result.setStatus(0);
        result.setMsg("账号信息正确");
        return result;
    }

    @Override
    public String createActivity(String activityDate, String activityPlace, String activityTime) {
        AdminActivity adminActivity = new AdminActivity();
        String activityUUID = UserUtil.creadid();
        adminActivity.setActivityUUID(activityUUID);
        adminActivity.setActivityPlace(activityPlace);
        adminActivity.setActivityDate(activityDate);
        adminActivity.setActivityTime(activityTime);
        adminActivity.setActivityStatus("1"); //创建活动初始状态为执行
        JSONObject jsonObject = JSONObject.fromObject(adminActivity);
        String activityJson = "{'args':['createActivity']},"+jsonObject+"]}";
        //调用智能合约，根据返回值来判断活动信息是否上链成功，智能合约的返回值 success 和 null
        /*
        * 调用智能合约接口
        * */
        return "success";
    }

    @Override
    public NoteResult activityQuery(String activityDate) {
        return null;
    }
}
