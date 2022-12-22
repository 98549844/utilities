package com.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.gson.Gson;
import com.util.entity.TestEntity;
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

    final static String mac_path = "/Users/garlam/IdeaProjects/utilities/src/main/resources/file/output/";
    final static String windows_path = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\output\\";
    final static String fileName = windows_path + "excel.xls";

    private static final int BATCH_COUNT = 3000;


    public void read(String fileName) {
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取3000条数据 然后返回过来 直接调用使用数据就行

        //Gson gson = new Gson();
        //EasyExcel.read(fileName, obj.getClass(), new PageReadListener<Users>(dataList -> {
        //    int size = dataList.size();
        //    for (int i = 0; i < size; i++) {
        //        log.info("row{}: {}", i, gson.toJson(dataList.get(i)));
        //    }
        // })).sheet().doRead();
        EasyExcel.read(fileName, new EasyExcelUtil()).sheet().doRead();
    }


    /**
     * excel读取后的数据处理手段, 重写invoke方法封装数据和保存数据
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        Gson gson = new Gson();
        List<Map<Integer, String>> list = new ArrayList<>();
        log.info("row data: {}", gson.toJson(data));

        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    // 读取header内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Gson gson = new Gson();
        List<Map<Integer, String>> list = new ArrayList<>();
        log.info("header: {}", gson.toJson(headMap));
    }


    public static void main(String[] args) {
        String xls = "C:\\ACE\\aaa.xls";

        EasyExcelUtil easyExcelUtil = new EasyExcelUtil();
        ExcelReaderBuilder l = EasyExcel.read(xls, TestEntity.class, new EasyExcelUtil());

        List<TestEntity> ls = new ArrayList<>();

        TestEntity a = new TestEntity();
        a.setId(10);
        a.setUserName("garlam_1");

        TestEntity b = new TestEntity();
        b.setId(11);
        b.setUserName("garlam_2");


        TestEntity c = new TestEntity();
        c.setId(13);
        c.setUserName("garlam_3");

        ls.add(a);
        ls.add(b);
        ls.add(c);

        //   easyExcelUtil.write(xls, ls, new TestEntity());
    }


    public void write(String saveLocation, List objList, Object obj) {
        // 写法1
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // EasyExcel.write(fileName, Users.class).sheet("模板").doWrite(objList);

        // 写法2
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(saveLocation, obj.getClass()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("template").build();
        excelWriter.write(objList, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }
}