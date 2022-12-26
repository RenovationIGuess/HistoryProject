package history.era;

import com.fasterxml.jackson.core.type.TypeReference;
import json.JSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Eras {
    public static List<Era> data = new ArrayList<>();
    public static void loadJSON(){
        try{
            int i = 1;
            while(true){
                Era era = JSON.MAPPER.readValue(new File(JSON.PREFIX_URL + "\\Era" + i+".json"), new TypeReference<>() {});
                data.add(era);
                i++;
            }
        } catch(IOException e){
            try{
                System.out.println(JSON.MAPPER.writeValueAsString(data));
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }
}
