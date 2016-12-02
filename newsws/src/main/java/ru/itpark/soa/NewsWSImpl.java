package ru.itpark.soa;

import javax.annotation.PreDestroy;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@WebService(endpointInterface="ru.itpark.soa.NewsWS")
public class NewsWSImpl implements NewsWS {

    @PreDestroy
    private void onBeforeDestroy(){
        DataInterface.tearDown();
    }


    @WebMethod
    public String doCall(
            @WebParam(name = "login") String login,
            @WebParam(name = "password") String password,
            @WebParam(name = "commandname") String commandname,
            @WebParam(name = "commanddata") String commanddata
    ) throws NewsWSFault {

        if(commandname!=null && !commandname.equals("")){
            Command cmd = CommandRegistry.lookupCommand(commandname);
            if(commanddata==null || "".equals(commanddata)){
                commanddata = "{}";
            }
            Map<String, Object> in = JSONUtils.parseJSON(commanddata);
            CommandContext cntx = new CommandContext();
            cntx.setLogin(login);
            cntx.setPassword(password);
            cntx.setData(in);
            Map<String, Object> out = new HashMap<String, Object>();
            try {
                Connection conn = DataInterface.getConnection();
                cntx.setConn(conn);
                boolean callFullyPerformed = false;
                try {
                    out = cmd.execute(cntx);
                    callFullyPerformed = true;
                } catch (Exception x) {
                    try {
                        conn.rollback();
                    } catch (Exception xx) {
                        throw new NewsWSFault("Unhandled exception", x);
                    }
                } finally {
                    if (callFullyPerformed) {
                        try {
                            conn.commit();
                        } catch (Exception xx) {
                            throw new NewsWSFault("Unhandled exception", xx);
                        }
                    }
                    try {
                        conn.close();
                    } catch (Exception xx) {
                        throw new NewsWSFault("Unhandled exception", xx);
                    }
                }
            } catch (Exception x){
                throw new NewsWSFault("Unhandled exception", x);
            }

            return JSONUtils.createJSON(out);
        }
        return "{}";
    }
}
