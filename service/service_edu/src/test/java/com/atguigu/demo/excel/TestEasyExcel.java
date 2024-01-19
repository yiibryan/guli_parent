package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String canonicalPath = null;
        // String projectPath = System.getProperty("user.dir");
        /*String path = "src/main/java";*/
        File file = new File("1.xlsx");
        String absolutePath = file.getAbsolutePath(); //不解析File path里的相对路径
        try {
            canonicalPath = file.getCanonicalPath(); //尝试解析File path里的相对路径
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("projectPath:"+absolutePath);

        // Path resourceDirectory = Paths.get("src","main","java");
        // String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        // System.out.println(absolutePath);


        // System.getProperty(user.dir) 获取的是启动项目的容器位置，用IDEA是项目的根目录，部署在tomcat上是tomcat的启动路径，即tomcat/bin的位置
        // String filePath = System.getProperty("user.dir")+ "/1.xlsx";
        // System.out.println(filePath);

        //第一种：获取类加载的根路径
        // path = this.getClass().getResource("/").getPath();
        // System.out.println(path);

        //获取当前类的所在工程路径,不加“/”  获取当前类的加载目录
        // path = this.getClass().getResource("").getPath();
        // System.out.println(path);

        // 第二种：获取项目路径
        // File file = new File("");
        // path = file.getCanonicalPath();
        // System.out.println(path);

        // 第三种：
        // URL path1 = this.getClass().getResource("");
        // System.out.println(path1);

        // 实现Excel写操作
        // 第一个参数文件路径，第二个实体类的名称
        // EasyExcel.write(filePath, DemoData.class).sheet("学生列表").doWrite(getData());

        //实现excel读操作
        EasyExcel.read(canonicalPath, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    //创建方法返回list集合
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
