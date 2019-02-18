package sports.service;
import sports.entity.NoteResult;

import java.security.NoSuchAlgorithmException;

/*
* 管理员接口，校验身份，创建活动，活动查询三个功能
* */
public interface AdminService {
    NoteResult checkLogin (String username,String password) throws NoSuchAlgorithmException;
    String createActivity(String activityDate,String activityPlace,String activityTime);
    NoteResult activityQuery(String activityDate);
}
