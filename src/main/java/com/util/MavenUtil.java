package com.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/*import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.cli.MavenCli;*/

/**
 * @Classname: MavenUtil
 * @Date: 2023/2/16 上午 10:38
 * @Author: kalam_au
 * @Description:
 */


public class MavenUtil {
    private static final Logger log = LogManager.getLogger(MavenUtil.class.getName());

    // static final String mavenWindowsHome = "C:\\Users\\Kalam_au\\AppData\\Local\\JetBrains\\Toolbox\\apps\\IDEA-U\\ch-0\\223.8214.52\\plugins\\maven\\lib\\maven3\\bin\\mvn.cmd";
    static final String mavenWindowsHome = "C:\\maven\\apache-maven-3.9.0\\bin\\mvn.cmd";
    private static final String space = " ";
    private static final String groupId = "com.ace";
    private static final String version = "1.0";
    private static final String bootDir = PathUtil.getSystemPath();

    public static void main(String[] args) throws IOException {
        System.out.println(PathUtil.getSystemPath());
        PathUtil p = new PathUtil();
        System.out.println(p.getResourcePath("maven/installedDependency.txt"));

        //  getMavenVersion();
        //  getMavenRepositoryLocation();

        // String ExternalJarPath = "C:\\ideaPorject\\mpfa-core2\\src\\lib\\plugins\\com.ibm.ws.runtime.jar";
        // installExternalJar(ExternalJarPath);

        // logInstalledDependency();
    }

    private static void installExternalJar(String ExternalJarPath) throws IOException {
        if (new File(ExternalJarPath).isDirectory()) {
            log.error("this is directory, please check !!!");
            return;
        }

        String jarName = FileUtil.getFileNames(ExternalJarPath).get(0);
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
     * install jar
     *
     * @param dependency
     */
    private static void logInstalledDependency(List<StringBuilder> dependency) {
        String installedDependency = "C:\\ideaPorject\\utilities\\src\\main\\resources\\file\\maven\\installedDependency.txt";
        if (!FileUtil.exist(installedDependency)) {
            FileUtil.create(installedDependency);
        }
        log.info("logging installed maven dependency ...");
        FileUtil.write(FileUtil.convertToPath(installedDependency), "installedDependency.txt", dependency, FileUtil.exist(installedDependency));

        log.info("Installed maven dependency log complete !!!");
    }


    /**
     * 装安文件夹里所有jars
     *
     * @param ExternalJarPath
     * @throws IOException
     */
    private static void installExternalJars(String ExternalJarPath) throws IOException {
        if (new File(ExternalJarPath).isFile()) {
            log.error("this is file, please check !!!");
            return;
        }

        List<String> jarNames = FileUtil.getFileNames(ExternalJarPath);

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
                Console.println(line, Console.BOLD);
                if (line.contains("<localRepository>")) {
                    result = "LocalRepository: " + line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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

