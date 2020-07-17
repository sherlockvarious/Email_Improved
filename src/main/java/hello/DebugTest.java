package hello;

/*

    @author sunchao
    @create 2020/6/23 --9:27
   
*/

import java.util.HashMap;


public class DebugTest {

    public static void main(String[] args) {

        HashMap<String, String> map = new HashMap<>();

        map.put("age","2");
        map.put("school","chuanda");
        map.put("name","tom");
        map.put("region","anhui");

        System.out.println(map.get("age"));
        map.remove("name");
        System.out.println("map = " + map);

    }

}
