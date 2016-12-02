package ru.itpark.soa;

import javax.xml.ws.WebFault;

/**
 * Created by ilavrentev on 28.11.2016.
 */
@WebFault
public class NewsWSFault extends Exception {
    private NewsWSFaultBean fault;

    public NewsWSFault() {

    }

    protected NewsWSFault(NewsWSFaultBean fault) {
        super(fault.getFaultString());
        this.fault = fault;
    }

    public NewsWSFault(String message, NewsWSFaultBean faultInfo){
        super(message);
        this.fault = faultInfo;
    }

    public NewsWSFault(String message, NewsWSFaultBean faultInfo, Throwable cause){
        super(message,cause);
        this.fault = faultInfo;
    }

    public NewsWSFaultBean getFaultInfo(){
        return fault;
    }


    public NewsWSFault(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public NewsWSFault(String code, String message) {
        super(message);
        this.fault = new NewsWSFaultBean();
        this.fault.setFaultString(message);
        this.fault.setFaultCode(code);
    }


    public NewsWSFault(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }


    public NewsWSFault(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
