package ru.itpark.soa.api;

import ru.itpark.soa.Command;
import ru.itpark.soa.CommandContext;
import ru.itpark.soa.DataInterface;
import ru.itpark.soa.Exceptions.AccountLoginExist;
import ru.itpark.soa.MapConvertUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class NewsWSUserAccountCreate implements Command {




    private static final String Q_INS =
            "INSERT INTO USERACCOUNT (ACCOUNTID, LOGIN, PASSWORD, NICKNAME)" +
                    " VALUES (?, ?, ?, ?)";
    private static final String Q_CH = "SELECT USERACCOUNT, LOGIN FROM USERACCOUNT";

    public Map<String, Object> execute(CommandContext cntx) throws SQLException, AccountLoginExist {

        Map<String, Object> result = new HashMap<String, Object>();
        MapConvertUtils.generateCallStatus(result, 0);
        Map<String, Object> data = cntx.getData();
        MapConvertUtils.truncateEmptyParams(data);
        if (!checkInput(data)) {
            MapConvertUtils.generateCallStatus(result, 1);
            return result;
        }
        String login = MapConvertUtils.asString(data, "Login");
        String password = MapConvertUtils.asString(data, "Password");
        String nickname = "";
        if (data.get("Nickname") != null) {
            nickname = MapConvertUtils.asString(data, "Nickname");
        }
        //TODO check existance_
        Connection conn = cntx.getConn();
        PreparedStatement st_check = conn.prepareStatement(Q_CH);
        ResultSet rs = st_check.executeQuery();
        while (rs.next()) {
            String uslogin = rs.getString("LOGIN");
            if (uslogin.equals(login)) {
                throw new AccountLoginExist("Login is busy");
            }
            st_check.close();
        }
            PreparedStatement st = conn.prepareStatement(Q_INS);
            Long id = DataInterface.getNewId("USERACCOUNT");
            st.setLong(1, id);
            st.setString(2, login);
            st.setString(3, "" + password.hashCode());
            st.setString(4, nickname);
            st.execute();
            result.put("UserAccountID", id);
            st.close();
            return result;
        }


    private boolean checkInput(Map<String, Object> data){
        return !MapConvertUtils.isParamEmpty(data, "Login")
                &&
                !MapConvertUtils.isParamEmpty(data, "Password");
    }
}
