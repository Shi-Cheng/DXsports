package sports.service.impl;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
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
    public NoteResult createActivity(String activityDate, String activityPlace, String activityTime) {
        return null;
    }

    @Override
    public NoteResult activityQuery(String activityDate) {
        return null;
    }
}
