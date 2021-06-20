package util;

public class SystemUtil {
    public static void getSystemInfo() {
        Console.print("Java版本号:\t", Console.BLUE);
        Console.println(System.getProperty("java.version"), Console.BLACK); // java版本号
        Console.print("Java提供商名称:\t", Console.BLUE);
        Console.println(System.getProperty("java.vendor"), Console.BLACK); // Java提供商名称
        Console.print("Java提供商网站:\t", Console.BLUE);
        Console.println(System.getProperty("java.vendor.url"), Console.BLACK); // Java提供商网站
        Console.print("jre目录:\t", Console.BLUE);
        Console.println(System.getProperty("java.home"), Console.BLACK); // Java，哦，应该是jre目录
        Console.print("Java虚拟机规范版本号:\t", Console.BLUE);
        Console.println(System.getProperty("java.vm.specification.version"), Console.BLACK); // Java虚拟机规范版本号
        Console.print("Java虚拟机规范提供商:\t", Console.BLUE);
        Console.println(System.getProperty("java.vm.specification.vendor"), Console.BLACK); // Java虚拟机规范提供商
        Console.print("Java虚拟机规范名称:\t", Console.BLUE);
        Console.println(System.getProperty("java.vm.specification.name"), Console.BLACK); // Java虚拟机规范名称
        Console.print("Java虚拟机版本号:\t", Console.BLUE);
        Console.println(System.getProperty("java.vm.version"), Console.BLACK); // Java虚拟机版本号
        Console.print("Java虚拟机提供商:\t", Console.BLUE);
        Console.println(System.getProperty("java.vm.vendor"), Console.BLACK); // Java虚拟机提供商
        Console.print("Java虚拟机名称:\t", Console.BLUE);
        Console.println(System.getProperty("java.vm.name"), Console.BLACK); // Java虚拟机名称
        Console.print("Java规范版本号:\t", Console.BLUE);
        Console.println(System.getProperty("java.specification.version"), Console.BLACK); // Java规范版本号
        Console.print("Java规范提供商:\t", Console.BLUE);
        Console.println(System.getProperty("java.specification.vendor"), Console.BLACK); // Java规范提供商
        Console.print("Java规范名称:\t", Console.BLUE);
        Console.println(System.getProperty("java.specification.name"), Console.BLACK); // Java规范名称
        Console.print("Java类版本号:\t", Console.BLUE);
        Console.println(System.getProperty("java.class.version"), Console.BLACK); // Java类版本号
        Console.print("Java类路径:\t", Console.BLUE);
        Console.println(System.getProperty("java.class.path"), Console.BLACK); // Java类路径
        Console.print("Java lib路径:\t", Console.BLUE);
        Console.println(System.getProperty("java.library.path"), Console.BLACK); // Java lib路径
        Console.print("Java输入输出临时路径:\t", Console.BLUE);
        Console.println(System.getProperty("java.io.tmpdir"), Console.BLACK); // Java输入输出临时路径
        Console.print("Java编译器:\t", Console.BLUE);
        Console.println(System.getProperty("java.compiler"), Console.BLACK); // Java编译器
        Console.print("Java执行路径:\t", Console.BLUE);
        Console.println(System.getProperty("java.ext.dirs"), Console.BLACK); // Java执行路径
        Console.print("操作系统名称:\t", Console.BLUE);
        Console.println(System.getProperty("os.name"), Console.BLACK); // 操作系统名称
        Console.print("操作系统的架构:\t", Console.BLUE);
        Console.println(System.getProperty("os.arch"), Console.BLACK); // 操作系统的架构
        Console.print("操作系统版本号:\t", Console.BLUE);
        Console.println(System.getProperty("os.version"), Console.BLACK); // 操作系统版本号
        Console.print("文件分隔符:\t", Console.BLUE);
        Console.println(System.getProperty("file.separator"), Console.BLACK); // 文件分隔符
        Console.print("路径分隔符:\t", Console.BLUE);
        Console.println(System.getProperty("path.separator"), Console.BLACK); // 路径分隔符
        Console.print("直线分隔符:\t", Console.BLUE);
        Console.println(System.getProperty("line.separator"), Console.BLACK); // 直线分隔符
        Console.print("操作系统用户名:\t", Console.BLUE);
        Console.println(System.getProperty("user.name"), Console.BLACK); // 用户名
        Console.print("操作系统用户的主目录:\t", Console.BLUE);
        Console.println(System.getProperty("user.home"), Console.BLACK); // 用户的主目录
        Console.print("当前程序所在目录:\t", Console.BLUE);
        Console.println(System.getProperty("user.dir"), Console.BLACK); // 当前程序所在目录
    }

    public static void main(String[] args) {
        getSystemInfo();
    }
}
