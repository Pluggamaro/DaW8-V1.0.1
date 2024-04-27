package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
public class StaticInfo {
    private final StringProperty schedule = new SimpleStringProperty();
    private final StringProperty cardNo = new SimpleStringProperty();
    private final StringProperty item = new SimpleStringProperty();
    private final StringProperty strength = new SimpleStringProperty();
    private final StringProperty unitOfIssue = new SimpleStringProperty();
    private final StringProperty code = new SimpleStringProperty();
    private final StringProperty icn = new SimpleStringProperty();
    private final StringProperty price = new SimpleStringProperty();
    private final StringProperty rol = new SimpleStringProperty();
    private final StringProperty size = new SimpleStringProperty();
    private final StringProperty binCard = new SimpleStringProperty();
    
    private final IntegerProperty id = new SimpleIntegerProperty();

    public StaticInfo() {
    }

    public StaticInfo(String schedule, String cardNo, String item, String strength, String unitOfIssue, String code, String icn, String price, String rol, String size) {
        setSchedule(schedule);
        setCardNo(cardNo);
        setItem(item);
        setStrength(strength);
        setUnitOfIssue(unitOfIssue);
        setCode(code);
        setIcn(icn);
        setPrice(price);
        setRol(rol);
        setSize(size);
    }
    
    public String getBinCard() {
    	return binCard.get();
    }
    
    public void setBinCard(String value) {
    	binCard.setValue(value);
    }
    
    public StringProperty binCardProperty() {
    	return binCard;
    }
    
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    // Schedule property
    public String getSchedule() {
        return schedule.get();
    }

    public void setSchedule(String schedule) {
        this.schedule.set(schedule);
    }

    public StringProperty scheduleProperty() {
        return schedule;
    }

    // CardNo property
    public String getCardNo() {
        return cardNo.get();
    }

    public void setCardNo(String cardNo) {
        this.cardNo.set(cardNo);
    }

    public StringProperty cardNoProperty() {
        return cardNo;
    }

    // Item property
    public String getItem() {
        return item.get();
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public StringProperty itemProperty() {
        return item;
    }

    // Strength property
    public String getStrength() {
        return strength.get();
    }

    public void setStrength(String strength) {
        this.strength.set(strength);
    }

    public StringProperty strengthProperty() {
        return strength;
    }

    // UnitOfIssue property
    public String getUnitOfIssue() {
        return unitOfIssue.get();
    }

    public void setUnitOfIssue(String unitOfIssue) {
        this.unitOfIssue.set(unitOfIssue);
    }

    public StringProperty unitOfIssueProperty() {
        return unitOfIssue;
    }

    // Code property
    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public StringProperty codeProperty() {
        return code;
    }

    // ICN property
    public String getIcn() {
        return icn.get();
    }

    public void setIcn(String icn) {
        this.icn.set(icn);
    }

    public StringProperty icnProperty() {
        return icn;
    }

    // Price property
    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public StringProperty priceProperty() {
        return price;
    }

    // ROL property
    public String getRol() {
        return rol.get();
    }

    public void setRol(String rol) {
        this.rol.set(rol);
    }

    public StringProperty rolProperty() {
        return rol;
    }

    // Size property
    public String getSize() {
        return size.get();
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public StringProperty sizeProperty() {
        return size;
    }

}
