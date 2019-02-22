package sports.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class UserUtil {

    public static String creadid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","");
    }
    public static String md5(String msg) throws NoSuchAlgorithmException{
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] input = msg.getBytes();
        byte[] output = messageDigest.digest(input);
        String result_MD5 = Base64.encodeBase64String(output);
        return  result_MD5;
    }
    public  static  void main (String [] args) throws NoSuchAlgorithmException {
//        System.out.println(md5("567"));//测试
//        System.out.println(md5("admin"));
//        if("ISMvKXpXpadDiUoOSoAfww==".equals(UserUtil.md5("admin"))){
//            System.out.println("they are equal!");
//            //ISMvKXpXpadDiUoOSoAfww==
//        }else {
//            System.out.println("不想等");
//        }

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(new Date()));

    }
}
