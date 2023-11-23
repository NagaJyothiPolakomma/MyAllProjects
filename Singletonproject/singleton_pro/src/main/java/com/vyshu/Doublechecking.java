package com.vyshu;

import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import net.minidev.json.JSONObject;

//public class Doublechecking {

//public static void main(String args[])
//{
//Singleton_class sng1= Singleton_class.instatnce();
//System.out.println(sng1);
// 
//Singleton_class sng2= Singleton_class.instatnce();
//		 System.out.println(sng2);
// Singleton_class sng3= Singleton_class.instatnce();
//		 System.out.println(sng3);
//}
public class Doublechecking 
{
    public static void main(String[] args) throws JsonProcessingException 
    {
        ListMultimap<Object, Object> multimap = ArrayListMultimap.create();

        // Adding values for the key "one"
        multimap.put("one", 1);
        multimap.put("one", "jyothi");
        multimap.put("one", 5);

        // Adding values for the key "two"
       
       multimap.put("one",  Arrays.asList(1, "jyothi", 5));
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(multimap)
       JSONObject jsonObject = new JSONObject();
       String json = jsonObject.toJSONString();

       
        // Getting values for a key
        System.out.println(multimap); // [1, 11]
        System.out.println(json); // [1, 11]
      //  System.out.println(multimap.get("two")); // [2]
    }
}

