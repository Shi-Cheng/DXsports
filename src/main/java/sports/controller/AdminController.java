package sports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sports.entity.AdminLogin;
import sports.entity.NoteResult;
import sports.service.AdminService;

import java.security.NoSuchAlgorithmException;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/checkLogin",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public NoteResult adminLoginCheck(@RequestBody AdminLogin adminLogin) throws NoSuchAlgorithmException {
        String adminName = adminLogin.getUsername();
        String adminPassWord = adminLogin.getPassword();
        NoteResult noteResult = adminService.checkLogin(adminName,adminPassWord);
        System.out.println("=====adminName====="+adminName+"==========");
        System.out.println("=====adminPassWord====="+adminPassWord+"========");
        System.out.println("=================== this is a test =====================");

        return noteResult;
    }
}
