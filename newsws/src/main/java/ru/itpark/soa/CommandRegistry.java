package ru.itpark.soa;

import ru.itpark.soa.Exceptions.CommandException;
import ru.itpark.soa.api.NewsWSUserAccountCreate;
import ru.itpark.soa.api.NewsWSUserBrowseListByParams;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private static Map<String, Command> reg = new HashMap<String, Command>();


    static {
        //reg.put...
        reg.put("newsWSUserAccountCreate", new NewsWSUserAccountCreate());
        reg.put("newsWSUserBrowseListByParams", new NewsWSUserBrowseListByParams());
    }

    public static Command lookupCommand(String cn) throws CommandException{
        try{
            if(reg.get(cn)!=null) {
                return reg.get(cn);
            } else {throw new CommandException("No such command!");
                //TODO _
            }
        }
        finally {return null;}
    }
}
