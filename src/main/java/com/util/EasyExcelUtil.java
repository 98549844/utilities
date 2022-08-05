package com.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname: EasyExcelUtil
 * @Date: 6/12/2021 3:09 上午
 * @Author: garlam
 * @Description:
 */


public class EasyExcelUtil extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger log = LogManager.getLogger(EasyExcelUtil.class.getName());

    final static String path = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/output/";
    final static String fileName = path + "excel.xls";

    private static final int BATCH_COUNT = 3000;


    public void read(String fileName) {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行

//        Gson gson = new Gson();
//        EasyExcel.read(fileName, obj.getClass(), new PageReadListener<Users>(dataList -> {
//            int size = dataList.size();
//            for (int i = 0; i < size; i++) {
//                log.info("row{}: {}", i, gson.toJson(dataList.get(i)));
//            }
//        })).sheet().doRead();
        EasyExcel.read(fileName, new EasyExcelUtil()).sheet().doRead();
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        Gson gson = new Gson();
        List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        log.info("row data: {}", gson.toJson(data));

        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    public static void main(String[] args) {
        EasyExcelUtil easyExcelUtil = new EasyExcelUtil();
        easyExcelUtil.read(fileName);
    }


    public void write(String saveLocation, List objList, Object obj) {
        // 写法1
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // EasyExcel.write(fileName, Users.class).sheet("模板").doWrite(objList);

        // 写法2
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(saveLocation, obj.getClass()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        excelWriter.write(objList, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }
}