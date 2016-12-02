package ru.itpark.soa;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
public interface NewsWS {

    @WebMethod
    @SOAPBinding(style = SOAPBinding.Style.RPC)
    public String doCall(
            @WebParam(name="login") String login,
            @WebParam(name="password") String password,
            @WebParam(name="commandname") String commandname,
            @WebParam(name="commanddata") String commanddata
    )throws NewsWSFault;
}
