package sports.entity;
    /*
    * 管理员登陆信息判断，单独进行上链，用来保证数据的安全性
    * 同时，管理员信息是由系统给定
    * */
public class AdminLogin {
    private String username;
    private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "AdminLogin{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
