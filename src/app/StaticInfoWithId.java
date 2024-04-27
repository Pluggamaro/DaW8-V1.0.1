package app;

public class StaticInfoWithId {
    private StaticInfo staticInfo;
    private int id;

    public StaticInfoWithId(StaticInfo staticInfo, int id) {
        this.staticInfo = staticInfo;
        this.id = id;
    }
    
    public StaticInfo getStaticInfo() {
        return staticInfo;
    }

    public void setStaticInfo(StaticInfo staticInfo) {
        this.staticInfo = staticInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

