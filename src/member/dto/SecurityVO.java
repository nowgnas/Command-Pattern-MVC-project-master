package member.dto;

public class SecurityVO {
    String userId, uuid, salt;

    public SecurityVO(String userId, String uuid, String salt) {
        this.userId = userId;
        this.uuid = uuid;
        this.salt = salt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
