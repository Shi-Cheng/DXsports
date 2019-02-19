package sports.entity;
    /*
    * 此用户实例是对活动信息进行操作
    * */
public class AdminActivity {
        private String activity_id;
        private String activity_place;
        private String activity_date;//活动日期
        private String activity_period;//活动具体时间，通过下拉框的格式进行选择
        private String activity_status;

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }
        public String getActivity_place() {
            return activity_place;
        }

        public void setActivity_place(String activity_place) {
            this.activity_place = activity_place;
        }

        public String getActivity_date() {
            return activity_date;
        }

        public void setActivity_date(String activity_date) {
            this.activity_date = activity_date;
        }

        public String getActivity_period() {
            return activity_period;
        }

        public void setActivity_period(String activity_period) {
            this.activity_period = activity_period;
        }

        public String getActivity_status() {
            return activity_status;
        }

        public void setActivity_status(String activity_status) {
            this.activity_status = activity_status;
        }

        @Override
        public String toString() {
            return "AdminActivity{" +
                    "activity_id='" + activity_id + '\'' +
                    ", activity_place='" + activity_place + '\'' +
                    ", activity_date='" + activity_date + '\'' +
                    ", activity_period='" + activity_period + '\'' +
                    ", activity_status='" + activity_status + '\'' +
                    '}';
        }
    }
