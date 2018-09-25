package imaginers.com.taskfire.Model;

public class Workspace {

    private String wsID;
    private String wsName;
    private String wsType;
    private String uId;


    public Workspace() {
    }

    public Workspace(String wsID, String wsName, String wsType, String uId) {
        this.wsID = wsID;
        this.wsName = wsName;
        this.wsType = wsType;
        this.uId = uId;
    }

    public String getWsName() {
        return wsName;
    }

    public void setWsName(String wsName) {
        this.wsName = wsName;
    }

    public String getWsType() {
        return wsType;
    }

    public void setWsType(String wsType) {
        this.wsType = wsType;
    }

    public String getWsID() {
        return wsID;
    }

    public String getuId() {
        return uId;
    }

    public void setWsID(String wsID) {
        this.wsID = wsID;
    }
}
