package ru.itpark.soa.api;

import ru.itpark.soa.Command;
import ru.itpark.soa.CommandContext;

import java.util.HashMap;
import java.util.Map;

public class NewsWSUserBrowseListByParams implements Command {
    public Map<String, Object> execute(CommandContext cntx) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("OutRes2", "Hello from NewsWSUserBrowseListByParams!");
        return result;
    }
}
