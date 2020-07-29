package com.wucfu.helo.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuchangfu
 * @description
 * @date 2020-07-16 19:32
 */
public class FreeMarkerHelo {

    public static void main(String[] args) {
        // 创建插值的map
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user", "mk");
        map.put("url", "http://www.baidu.com/");
        map.put("name", "百度");
        String temp = "用户名：${user} --- URL:${url} --- 姓名:${name}";

        System.out.println(createTemp1(temp, map));;

        createTemp2();
    }


    public static String createTemp1(String temp, Object o) {
        try {
            // 创建一个模板对象
            Template t = new Template(null, new StringReader(temp), null);

            // 执行插值，并输出到指定的输出流中
            StringWriter sw = new StringWriter();

            t.process(o, sw);

            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static void createTemp2(){
        Animal a1 = new Animal();
        a1.setName("小狗");
        a1.setPrice(88);
        Animal a2 = new Animal();
        a2.setName("小喵");
        a2.setPrice(80);

        List<Animal> list = new ArrayList<Animal>();
        list.add(a1);
        list.add(a2);

        Map<String,Object> sexMap=new HashMap<String,Object>();
        sexMap.put("1", "男");
        sexMap.put("0","女");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user", "jack");
        map.put("score", 13);
        map.put("team", "一班,二班");
        map.put("animals", list);
        map.put("sexMap",sexMap);
        try {
            Configuration config = new Configuration(new Version("2.3.29"));

            config.setDefaultEncoding("UTF-8");

            //File file = new File(FreeMarkerHelo.class.getResource("helo.ftl").getPath());

            config.setDirectoryForTemplateLoading(new File(FreeMarkerHelo.class.getResource("/").getPath()));

            Template template = config.getTemplate("helo.ftl");

            template.process(map,new FileWriter("D:\\helo.html"));

            StringWriter sw = new StringWriter();

            template.process(map, sw);

            System.out.println(sw.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
