package ru.itpark.soa;

/**
 * Created by ilavrentev on 28.11.2016.
 */
public class NewsWSFaultBean {
    private String faultCode;
    private String faultString;

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
}
