package sports.service;
import sports.entity.NoteResult;
import sports.entity.UserReserve;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    //通过用户名及密码核查用户登陆
    NoteResult login(String username , String password) throws NoSuchAlgorithmException;
    //添加用户
    NoteResult  register(String username , String password) throws NoSuchAlgorithmException;
    //用户预约状态
    UserReserve ActivityOptions (String activity_id, String user_id);
    //用户取消预约
    UserReserve CancelActivity(String activityId,String activityStatus,String userId);
}
