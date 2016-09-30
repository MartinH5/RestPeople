
package util;

import com.google.gson.Gson;
import entities.Person;
import java.util.List;

/**
 *
 * @author Martin
 */
public class JSONtranslate{
    
    private Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();


    public String PersonToJson(Person p) {
        return gson.toJson(p);
    }
    public Person JsonToPerson(String json) {
        return gson.fromJson(json, Person.class);
    }

    public String ListToJson(List list) {
        return gson.toJson(list);
    }
    
}