import org.flywaydb.core.Flyway;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args){
        parseInfo(args);

        Flyway fw = new Flyway();
        fw.setDataSource(args[0], args[1], args[2]);



        fw.migrate();

    }


    private static void parseInfo(String[] v){
        if(v.length < 3){
            throw new IllegalArgumentException("Incorrect dadabase info");
        }
    }
}
