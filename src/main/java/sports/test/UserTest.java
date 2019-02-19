package sports.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sports.entity.AdminActivity;
import sports.service.UserService;

public class UserTest {

    @Test
    public void run1(){
        //加载配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //获取对象
        UserService  userService = (UserService) ac.getBean("userService");
        //调方法
    }

    @Test
    public void jsonToObject(){
//        String result = "{'activity_id':'7f17a4c2303711e9b210d663bd873d93','activity_place':'centralpark','activity_period':'6:00-8:00','activity_date':'2019-1-28','activity_status':'1'}";
//        AdminActivity adminActivity = JSON.parseObject(result, AdminActivity.class);
//        String activity_status = adminActivity.getActivity_status();
//        int i = Integer.parseInt(activity_status);
//        if(i < 3){
//            System.out.println("这是整数类型");
//        }
        //System.out.println("================="+i+"==================");

    }
}
