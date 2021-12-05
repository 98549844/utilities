package util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.entity.Users;

import java.io.File;

/**
 * @Classname: EasyExcelUtil
 * @Date: 6/12/2021 3:09 上午
 * @Author: garlam
 * @Description:
 */


public class EasyExcelUtil {
    private static Logger log = LogManager.getLogger(EasyExcelUtil.class.getName());

    final static String path = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/output/";
    final static String fileName = path + "excel.xls";


    public void Read() {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行

        Gson gson = new Gson();
        EasyExcel.read(fileName, Users.class, new PageReadListener<Users>(dataList -> {
            for (Users users : dataList) {
                log.info("读取到一条数据{}", gson.toJson(users));
            }
        })).sheet().doRead();

    }

    public static void main(String[] args) {
        EasyExcelUtil easyExcelUtil = new EasyExcelUtil();
        easyExcelUtil.Read();
    }

}