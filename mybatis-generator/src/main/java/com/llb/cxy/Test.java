package com.llb.cxy;

import com.llb.cxy.mybatis.MyMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * description: Test <br>
 * date: 2020/3/25 15:19 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class Test {
    public void ff() throws IOException {
        // 获取当前项目的目录
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);//注意返回的是反斜杠标识的目录名
// 获取当前类的目录
        URL xmlpath = this.getClass().getClassLoader().getResource("");
        System.out.println(xmlpath);
        File f = new File(this.getClass().getResource("/").getPath());
        System.out.println(f);
// 获取所有的类路径 包括jar包的路径
        System.out.println(System.getProperty("java.class.path"));
    }

    public static void main(String[] args) throws IOException {
        Test t = new Test();
        t.ff();

        /**
         * 获取项目的路径
         */
        // 1st projectPath==E:\Hunger\workspace\twpre
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath==" + projectPath);

        // 2nd projectPath2==E:\Hunger\workspace\twpre
        File dir = new File("");// 参数为空
        String projectPath2 = dir.getCanonicalPath();
        System.out.println("projectPath2==" + projectPath2);

        System.out.println(MyMapper.class.toGenericString());
    }
}
