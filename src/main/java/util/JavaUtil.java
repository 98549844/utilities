package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.impl.JdkVersion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaUtil {
    static private final Log log = LogFactory.getLog(JavaUtil.class);

    public static void main(String[] args) throws IOException {
        getCurrentJdkInfo();
        showJavaInfo();
    }

    @SuppressWarnings("static-access")
    public static List getCurrentJdkInfo() throws IOException {
        List<String> jdkInfo = new ArrayList<>();
        String Command = "java -version";
        log.info("Current Java Version On Mac Os");
        Process process = Runtime.getRuntime().exec(Command);
        log.info("PID:  " + process.pid());
        String[] infos = process.info().toString().split(",");
        for (String s : infos) {
            log.info(s.trim());
            jdkInfo.add(s.trim());
        }

        jdkInfo.add(getJdkVersion());
        return jdkInfo;
    }


    @SuppressWarnings("static-access")
    public static String getJdkVersion() {
        JdkVersion jdkVersion = new JdkVersion() {
        };
        String jdkVer = jdkVersion.getJavaVersion();
        log.info("JKD version:  " + jdkVer);
        return jdkVer;
    }


    public static void showJavaInfo() {
        Console.print("Java版本号: \t", Console.BLUE);
        Console.println(System.getProperty("java.version"), Console.BLACK); // java版本号
        Console.print("Java提供商名称: \t", Console.BLUE);
        Console.println(System.getProperty("java.vendor"), Console.BLACK); // Java提供商名称
        Console.print("Java提供商网站: \t", Console.BLUE);
        Console.println(System.getProperty("java.vendor.url"), Console.BLACK); // Java提供商网站
        Console.print("jre目录: \t", Console.BLUE);
        Console.println(System.getProperty("java.home"), Console.BLACK); // Java，哦，应该是jre目录
        Console.print("Java虚拟机规范版本号: \t", Console.BLUE);
        Console.println(System.getProperty("java.vm.specification.version"), Console.BLACK); // Java虚拟机规范版本号
        Console.print("Java虚拟机规范提供商: \t", Console.BLUE);
        Console.println(System.getProperty("java.vm.specification.vendor"), Console.BLACK); // Java虚拟机规范提供商
        Console.print("Java虚拟机规范名称: \t", Console.BLUE);
        Console.println(System.getProperty("java.vm.specification.name"), Console.BLACK); // Java虚拟机规范名称
        Console.print("Java虚拟机版本号: \t", Console.BLUE);
        Console.println(System.getProperty("java.vm.version"), Console.BLACK); // Java虚拟机版本号
        Console.print("Java虚拟机提供商: \t", Console.BLUE);
        Console.println(System.getProperty("java.vm.vendor"), Console.BLACK); // Java虚拟机提供商
        Console.print("Java虚拟机名称: \t", Console.BLUE);
        Console.println(System.getProperty("java.vm.name"), Console.BLACK); // Java虚拟机名称
        Console.print("Java规范版本号: \t", Console.BLUE);
        Console.println(System.getProperty("java.specification.version"), Console.BLACK); // Java规范版本号
        Console.print("Java规范提供商: \t", Console.BLUE);
        Console.println(System.getProperty("java.specification.vendor"), Console.BLACK); // Java规范提供商
        Console.print("Java规范名称: \t", Console.BLUE);
        Console.println(System.getProperty("java.specification.name"), Console.BLACK); // Java规范名称
        Console.print("Java类版本号: \t", Console.BLUE);
        Console.println(System.getProperty("java.class.version"), Console.BLACK); // Java类版本号
        Console.print("Java类路径: \t", Console.BLUE);
        Console.println(System.getProperty("java.class.path"), Console.BLACK); // Java类路径
        Console.print("Java lib路径: \t", Console.BLUE);
        Console.println(System.getProperty("java.library.path"), Console.BLACK); // Java lib路径
        Console.print("Java输入输出临时路径: \t", Console.BLUE);
        Console.println(System.getProperty("java.io.tmpdir"), Console.BLACK); // Java输入输出临时路径
        Console.print("Java编译器: \t", Console.BLUE);
        Console.println(System.getProperty("java.compiler"), Console.BLACK); // Java编译器
        Console.print("Java执行路径: \t", Console.BLUE);
        Console.println(System.getProperty("java.ext.dirs"), Console.BLACK); // Java执行路径

    }
}
