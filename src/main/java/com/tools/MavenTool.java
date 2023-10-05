package com.tools;

import com.util.Console;
import com.util.FileUtil;
import com.util.PathUtil;
import com.util.SystemUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * @Classname: MavenUtil
 * @Date: 2023/2/16 上午 10:38
 * @Author: kalam_au
 * @Description:
 */


public class MavenTool {
    private static final Logger log = LogManager.getLogger(MavenTool.class.getName());

    public static final String mavenWindowsHome = "C:\\maven\\apache-maven-3.9.0\\bin\\mvn.cmd";
    private static final String space = " ";
    private static final String groupId = "com.ace";
    private static final String version = "1.0";
    private static final String bootDir = PathUtil.getSystemPath();
    private static final String localDependencyRecord = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\maven\\localDependencyRecord.txt";


    public static void main(String[] args) throws IOException {
        String p = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\maven\\";
        String icefaces_ee_ace = p + "icefaces-ee-ace.jar";
        String runtime = p + "com.ibm.ws.runtime.jar";
        String admin_core = p + "com.ibm.ws.admin.core.jar";
        String wlm = p + "com.ibm.ws.wlm.jar";
        String web2 = "C:\\Users\\Kalam_au\\Downloads\\20230306\\mpfa-web2.jar";
        String tools = "C:\\Users\\Kalam_au\\.jdks\\azul-1.8.0_362\\lib\\tools.jar";

        installExternalJar(tools);
    }

    private static void installExternalJar(String ExternalJarPath) throws IOException {
        if (new File(ExternalJarPath).isDirectory()) {
            log.error("this is directory, please check !!!");
            return;
        }

        String jarName = FileUtil.getFileNameWithExt(PathUtil.space(ExternalJarPath));
        String jar = FileUtil.getName(jarName);

        log.info("Maven install starting ...");
        log.info("installing " + jarName + " ...");
        List<String> commands = new ArrayList<>();
        commands.add(mavenWindowsHome);
        commands.add("install:install-file");
        commands.add("-Dfile=" + ExternalJarPath);
        commands.add("-DgroupId=" + groupId);
        commands.add("-DartifactId=" + jar);
        commands.add("-Dversion=" + version);
        commands.add("-Dpackaging=jar");
        run(preparedCommands(commands));
        log.info("Maven jar installation complete !!!");

        StringBuilder dependencyLog = new StringBuilder("<dependency>");
        StringBuilder groupIdLogLog = new StringBuilder("    <groupId>" + groupId + "</groupId>");
        StringBuilder artifactIdLog = new StringBuilder("    <artifactId>" + jar + "</artifactId>");
        StringBuilder versionLog = new StringBuilder("    <version>" + version + "</version>");
        StringBuilder dependency2Log = new StringBuilder("</dependency>");
        System.out.println(dependencyLog);
        System.out.println(groupIdLogLog);
        System.out.println(artifactIdLog);
        System.out.println(versionLog);
        System.out.println(dependency2Log);

        List<StringBuilder> dependency = new ArrayList<>();
        dependency.add(new StringBuilder(SystemUtil.separator()));
        dependency.add(dependencyLog.append(SystemUtil.separator()));
        dependency.add(groupIdLogLog.append(SystemUtil.separator()));
        dependency.add(artifactIdLog.append(SystemUtil.separator()));
        dependency.add(versionLog.append(SystemUtil.separator()));
        dependency.add(dependency2Log.append(SystemUtil.separator()));
        dependency.add(new StringBuilder(SystemUtil.separator()));
        logInstalledDependency(dependency);
    }

    /**
     * 装安文件夹里所有jars
     *
     * @param ExternalJarPath
     */
    private static void installExternalJars(String ExternalJarPath) throws IOException {
        if (new File(ExternalJarPath).isFile()) {
            log.error("this is file, please check !!!");
            return;
        }

        List<String> jarNames = FileUtil.getFileNames(PathUtil.space(ExternalJarPath));

        log.info("Maven install starting ...");
        for (String jarName : jarNames) {
            log.info("installing " + jarName + " ...");
            String jar = jarName.split("\\.")[0];
            List<String> commands = new ArrayList();
            commands.add(mavenWindowsHome);
            commands.add("install:install-file");
            commands.add("-Dfile=" + ExternalJarPath + jarName);
            commands.add("-DgroupId=" + groupId);
            commands.add("-DartifactId=" + jar);
            commands.add("-Dversion=" + version);
            commands.add("-Dpackaging=jar");
            run(preparedCommands(commands));
            // process(commands);
        }
        log.info("Maven jar installation complete !!!");

    }


    private static void getMavenVersion() {
        String command = "-v";
        List<String> commands = new ArrayList<>();
        commands.add(mavenWindowsHome);
        commands.add(command);
        run(preparedCommands(commands));
    }

    private static String getMavenRepositoryLocation() {
        String command = "help:effective-settings";
        List<String> commands = new ArrayList();
        commands.add(mavenWindowsHome);
        commands.add(command);
        return run(preparedCommands(commands));
    }


    public static String run(String commands) {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec(commands);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                //  Console.println(line, Console.BOLD);
                if (line.contains("<localRepository>")) {
                    result = "LocalRepository: " + line;
                }
                if (line.contains("[ERROR]")) {
                    Console.println(line, Console.RED, Console.BOLD);
                } else {
                    Console.println(line, Console.BOLD);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * write install jar dependency record into txt file
     */
    private static void logInstalledDependency(List<StringBuilder> dependency) {
        if (!FileUtil.exist(localDependencyRecord)) {
            FileUtil.create(localDependencyRecord);
        }
        log.info("logging installed maven dependency ...");
        FileUtil.write(FileUtil.convertToPath(localDependencyRecord), "localDependencyRecord.txt", dependency, FileUtil.exist(localDependencyRecord));
        log.info("Installed maven dependency log complete !!!");
    }

    private static void process(List<String> commands) throws IOException {
        Process p = new ProcessBuilder(commands).start();
    }

    public static String preparedCommands(String... commands) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : commands) {
            stringBuilder.append(s).append(space);
        }
        return stringBuilder.toString().trim();
    }

    public static String preparedCommands(List<String> commands) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : commands) {
            stringBuilder.append(s).append(space);
        }
        String c = stringBuilder.toString().trim();
        Console.println(c, Console.BOLD, Console.CYAN);
        return c;
    }


}

