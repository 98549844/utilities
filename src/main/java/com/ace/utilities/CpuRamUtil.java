package com.ace.utilities;

import com.sun.management.OperatingSystemMXBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

/**
 * @Classname: SystemResourceUtil
 * @Date: 2023/2/10 下午 05:52
 * @Author: kalam_au
 * @Description:
 */


public class CpuRamUtil {
    private static final Logger log = LogManager.getLogger(CpuRamUtil.class.getName());


    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            cpuUsage();
            memoryUsage();
            System.out.println();
        }
    }

    public static void cpuAndRamUsage() throws InterruptedException {
        /*while (true) {
            //使用buildin function 获取cpu and ram使用量
            //获取CPU
            double cpuLoad = osmxb.getCpuLoad();
            int percentCpuLoad = (int) (cpuLoad * 100);
            //获取内存
            double totalVirtualMemory = osmxb.getTotalMemorySize();
            double freePhysicalMemorySize = osmxb.getFreeMemorySize();
            double value = freePhysicalMemorySize / totalVirtualMemory;
            int percentMemoryLoad = (int) ((1 - value) * 100);

            log.info("CPU = {}, Mem = {}", percentCpuLoad, percentMemoryLoad);
            Thread.sleep(1000);
        }*/
        while (true) {
            cpuUsage();
            memoryUsage();
            System.out.println();
        }
    }

    public static void memoryUsage() throws InterruptedException {

        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long availableByte = memory.getAvailable();

        log.info("内存大小 = {},内存使用率 ={}", formatByte(totalByte), new DecimalFormat("#.##%").format((totalByte - availableByte) * 1.0 / totalByte));
        Thread.sleep(10000);

    }

    public static void cpuUsage() throws InterruptedException {

        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + ioWait + irq + softIrq + steal;

        int cpuTotal = processor.getLogicalProcessorCount();
        String cpuUsage = new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu));

        log.info("CPU总数 = {}, CPU利用率 = {}", cpuTotal, cpuUsage);
        Thread.sleep(1000);
    }

    private static String formatByte(long byteNumber) {
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

}

