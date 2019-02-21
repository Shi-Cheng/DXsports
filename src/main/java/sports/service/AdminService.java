package sports.service;
import sports.entity.AdminActivity;
import sports.entity.NoteResult;

import java.security.NoSuchAlgorithmException;

/*
* 管理员接口，校验身份，创建活动，活动查询,活动更新三个功能
* */
public interface AdminService {
    NoteResult checkLogin (String username,String password) throws NoSuchAlgorithmException;
    AdminActivity createActivity(String activityDate,String activityPlace,String activityTime);
    AdminActivity activityQuery(String activityDate);
    AdminActivity activityUpdate(String activityId);
    AdminActivity activityLoading(String currentDate);
}
