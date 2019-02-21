package sports.service.impl;
import com.alibaba.fastjson.JSON;
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
        NoteResult result = new NoteResult();
        Map<String,String> adminMap = new HashMap<String,String>();
        adminMap.put("username",username);
        JSONObject jsonObj = JSONObject.fromObject(adminMap);
        String jsonAdmin = "{'args':['queryAdmin',"+jsonObj+"]}";
        //调用智能合约，进行管理员身份校验   根据用户名查询，如果存在则返回密码，如果不存在则返回null
        //需要智能合约返回AdminLogin json数组

        String adminResult = "{'username':'admin','password':'ISMvKXpXpadDiUoOSoAfww=='}";
        AdminLogin adminLogin = JSON.parseObject(adminResult,AdminLogin.class);
        if(adminLogin.getUsername() == null){
            result.setStatus(1);
            result.setMsg("非法用户");
            return result;
        }
        String md5_adminPassWord = UserUtil.md5(password);
        if (adminLogin.getPassword().equals(md5_adminPassWord)){
            result.setStatus(2);
            result.setMsg("密码有误");
            return result;
        }
        result.setStatus(0);
        result.setMsg("账号信息正确");
        return result;
    }
    @Override
    public AdminActivity createActivity(String activityDate, String activityPlace, String activityTime) {
        AdminActivity adminActivity = new AdminActivity();
        String activityUUID = UserUtil.creadid();
        adminActivity.setActivity_id(activityUUID);
        adminActivity.setActivity_place(activityPlace);
        adminActivity.setActivity_date(activityDate);
        adminActivity.setActivity_period(activityTime);
        adminActivity.setActivity_status("1"); //创建活动初始状态为执行
        System.out.println("============"+adminActivity.getActivity_place());
        JSONObject jsonObject = JSONObject.fromObject(adminActivity);
        String activityJson = "{'args':['createActivity',"+jsonObject+"]}";
        //调用智能合约，根据返回值来判断活动信息是否上链成功，智能合约的返回值 success 和 null
        /*
         * 调用智能合约接口
         * */
        return adminActivity;
    }
    @Override
    public AdminActivity activityQuery(String activityDate) {
        AdminActivity adminActivity = new AdminActivity();
        Map<String,String> map = new HashMap<>();
        map.put("activityDate",activityDate);
        JSONObject jsonObj = JSONObject.fromObject(map);
        String activityJson = "{'args':['queryActivity',"+jsonObj+"]}";
        //调用智能合约接口，依据活动的时间进行查询，返回查询所得的信息 返回的是 AdminActivity 对象，或者是一个json字符串
        String result = "{'activity_id':'7f17a4c2303711e9b210d663bd873d93','activity_place':'centralpark','activity_period':'6:00-8:00','activity_date':'2019-1-28','activity_status':'1'}";
        AdminActivity activity = JSON.parseObject(result, AdminActivity.class); //返回一个活动对象
        return activity;
    }
    /*
    * 活动状态的更新原理
    * 1：获取所要更新的活动信息，再对信息进行修改，重新上链
    * 2：把活动id传到智能合约，合约进行修改，存储
    * */
    @Override
    public AdminActivity activityUpdate(String activityId) {
        Map<String,String> map = new HashMap<>();
        map.put("activityId",activityId);
        JSONObject jsonObj = JSONObject.fromObject(map);
        String activityJson = "{'args':['cancelActivity',"+jsonObj+"]}";
        //根据日期进行查询 活动信息的更新   根据活动信息的id，更改活动的状态
        /*
        * 智能合约输出的值是一个json的对象，将json对象解析并返回前端
        * */
        String result = "{'activity_id':'7f17a4c2303711e9b210d663bd873d93','activity_place':'centralpark','activity_period':'6:00-8:00','activity_date':'2019-1-28','activity_status':'1'}";
        AdminActivity adminActivity = JSON.parseObject(result, AdminActivity.class);
        String activity_status = adminActivity.getActivity_status();
        String activity_palce = adminActivity.getActivity_place();
        String activity_date = adminActivity.getActivity_date();
        String activity_period = adminActivity.getActivity_period();
        int status = Integer.parseInt(activity_status);
        if (status == 1){
            status = 0;
            return getAdminActivity(adminActivity, activity_palce, activity_date, activity_period, status);
        }else {
            status = 1;
            return getAdminActivity(adminActivity, activity_palce, activity_date, activity_period, status);
        }
    }

    @Override
    public AdminActivity activityLoading(String currentDate) {
        Map<String,String> map = new HashMap<>();
        map.put("currentDate",currentDate);
        JSONObject  obj = JSONObject.fromObject(map);
        /*
        * 由系统时间来判断，存在的最新数据
        * */
        String currentDateJson = "{'args':['activityLoad',"+obj+"]}";

        String Test = "{'activity_id':'7f17a4c2303711e9b210d663bd873d93','activity_place':'centralpark','activity_period':'6:00-8:00','activity_date':'2019-1-28','activity_status':'1'}";
        AdminActivity activityInfo = JSON.parseObject(Test, AdminActivity.class); //返回一个活动对象
        return activityInfo;

    }

    private AdminActivity getAdminActivity(AdminActivity adminActivity, String activity_palce, String activity_date, String activity_period, int status) {
        String status_str = "" + status;
        adminActivity.setActivity_period(activity_period);
        adminActivity.setActivity_status(status_str);
        adminActivity.setActivity_date(activity_date);
        adminActivity.setActivity_place(activity_palce);
        adminActivity.setActivity_id(UserUtil.creadid());//将修改后的id更换，再上链
        JSONObject activityUpdate_json = JSONObject.fromObject(adminActivity);
        String activityUpdateJson = "{'args':['createActivity'," + activityUpdate_json + "]}";
        /*
         * 调用智能合约
         * */
        return adminActivity;
    }
}
