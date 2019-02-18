package sports.entity;
    /*
    * 此用户实例是对活动信息进行操作
    * */
public class AdminActivity {
    private String adminID;
    private String adminName;
    private String placeName;
    private String activityDate;//活动日期
    private String activityTime;//活动具体时间，通过下拉框的格式进行选择
    private String activityStatus;

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }
    @Override
    public String toString() {
        return "AdminActivity{" +
                "adminID='" + adminID + '\'' +
                ", adminName='" + adminName + '\'' +
                ", placeName='" + placeName + '\'' +
                ", activityDate='" + activityDate + '\'' +
                ", activityTime='" + activityTime + '\'' +
                ", activityStatus='" + activityStatus + '\'' +
                '}';
    }
}
