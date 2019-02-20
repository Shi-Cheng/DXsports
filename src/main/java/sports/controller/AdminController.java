package sports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sports.entity.AdminActivity;
import sports.entity.AdminLogin;
import sports.entity.NoteResult;
import sports.service.AdminService;

import java.security.NoSuchAlgorithmException;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/checkLogin",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public NoteResult adminLoginCheck(@RequestBody AdminLogin adminLogin) throws NoSuchAlgorithmException {
        String adminName = adminLogin.getUsername();
        String adminPassWord = adminLogin.getPassword();
        NoteResult noteResult = adminService.checkLogin(adminName,adminPassWord);
        return noteResult;
    }
    @RequestMapping(value = "/activityCreate", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AdminActivity activityCreate(@RequestBody AdminActivity adminActivity){
        //没有传入值
        String activityDate = adminActivity.getActivity_date();
        String activityPlace = adminActivity.getActivity_place();
        String activityTime = adminActivity.getActivity_period();

        System.out.println("======================="+activityTime+"=========================");
        AdminActivity createStatus = adminService.createActivity(activityDate,activityPlace,activityTime);
        System.out.println("======================="+createStatus.getActivity_id()+"=========================");
        return createStatus;
    }
    @RequestMapping(value = "/activityQuery", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AdminActivity activityQuery(@RequestBody AdminActivity admin){
        String activityDate = admin.getActivity_date();
        AdminActivity adminActivity = adminService.activityQuery(activityDate);
        return adminActivity;
    }
    @RequestMapping(value = "/activityUpdate", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AdminActivity activityUpdate(@RequestBody AdminActivity admin){
        String activityId = admin.getActivity_id();
        AdminActivity status = adminService.activityUpdate(activityId);
        if (status != null){
            return status;
        }
        return null;
    }
}
