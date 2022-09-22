package com.util;

import com.util.constant.CONSTANT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.core.io.FileSystemResource;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.*;

@SuppressWarnings("unchecked")
public class FileUtil {
    private static final Logger log = LogManager.getLogger(FileUtil.class.getName());

    public static final String FOLDERLIST = "FOLDERLIST";
    public static final String FILELIST = "FILELIST";
    public static final String ORIGINAL = "ORIGINAL";
    public static final String ONE_LINE = "ONE_LINE";
    public static final String LIST = "LIST";


    /**
     * 转半角的函数(DBC case)<br/><br/>
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input 任意字符串
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是:均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    public static boolean exist(String path) {
        return new File(path).exists();
    }


    public static Map getCurrentFolderList(String path) {
        log.info("get current file and folder list !!!");

        File file = new File(path);
        if (exist(path) || !file.isDirectory()) {
            log.warn("Path  incorrect, please check !");
        }
        File[] files = file.listFiles();
        List folderList = new ArrayList();
        List fileList = new ArrayList();
        for (File f : files) {
            if (f.isDirectory()) {
                folderList.add(f.getAbsolutePath());
            } else if (f.isFile()) {
                fileList.add(f.getAbsolutePath());
            }
        }
        Map map = new HashMap();
        map.put(FOLDERLIST, folderList);
        map.put(FILELIST, fileList);
        return map;
    }

    /**
     * current folder and subfolder
     *
     * @param path
     * @return
     */
    public static LinkedList getAllFolderList(String path) {
        LinkedList<String> list = new LinkedList<>();
        path = convertToPath(path);
        //考虑到会打成jar包发布 new File()不能使用改用FileSystemResource
        File file = new FileSystemResource(path).getFile();
        // 获取路径下的所有文件及文件夹
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                //add dir into list
                list.add(files[i].getPath());
                list.addAll(getAllFolderList(files[i].getPath()));
            }
        }
        return list;
    }


    /**
     * 用缓冲区读写，来提升读写效率。
     */
    public static void CopyFileWithNewExt(String path, List<String> fileNames, String ext, Boolean delFile) {
        if (!exist(path)) {
            log.error("Directory not exist or incorrect !!!");
            return;
        }

        path = convertToPath(path);
        log.info("Start copying file ...");
        for (String fileName : fileNames) {
            FileWriter fw = null;
            FileReader fr = null;
            String newFileName = "";
            if (!fileName.substring(fileName.length() - 4).equals(ext)) {
                newFileName = fileName.substring(0, fileName.length() - 4) + ext;
            } else {
                newFileName = "checked_" + fileName;
            }
            try {
                fr = new FileReader(path + fileName);//读
                fw = new FileWriter(path + newFileName);//写
                char[] buf = new char[1024];//缓冲区
                int len;
                while ((len = fr.read(buf)) != -1) {
                    fw.write(buf, 0, len);//读几个写几个
                }
                File file = new File(path + fileName);
                if (NullUtil.isNotNull(delFile) && delFile && file.exists()) {
                    file.delete();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                if (NullUtil.isNotNull(fr)) {
                    try {
                        fr.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (NullUtil.isNotNull(fw)) {
                    try {
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        log.info("File writing complete !!!");
    }


    public static void renameFilesName(String path, List<String> newNameList) throws Exception {
        log.info("Start rename file");
        path = convertToPath(path);
        ArrayList<String> fileList = FileUtil.getFileNames(path);
        if (fileList.size() != newNameList.size()) {
            log.error("List size not equal");
            return;
        }
        for (int i = 0; i < newNameList.size(); i++) {
            //   path = slash(path);
            File oldFile = new File(path + fileList.get(i));
            File newFile = new File(path + newNameList.get(i));
            if (newFile.exists()) {
                newFile = new File(path + "_" + newNameList.get(i));
            }
            oldFile.renameTo(newFile);
        }
        log.info("Finish rename file");
    }


    private static String addDotIfMissing(String ext) throws Exception {
        boolean isExist = NullUtil.isNotNull(ext);
        if (isExist) {
            if (ext.startsWith(".")) {
                return ext;
            } else {
                return "." + ext;
            }
        } else {
            throw new Exception();
        }
    }

    public static void renameFilesExt(String path, String newExt) throws Exception {
        log.info("Start rename ext");
        path = convertToPath(path);
        ArrayList<String> fileList = FileUtil.getFileNames(path);
        Integer i = 1;
        for (String s : fileList) {
            String[] spiltFileName = s.split("\\.");
            newExt = addDotIfMissing(newExt);

            String filePath = path + s;
            File oldFile = new File(filePath);

            File newFile = new File(path + spiltFileName[0] + newExt);

            if (newFile.exists()) {
                ++i;
                String t = "_" + i;
                newFile = new File(path + spiltFileName[0] + t + newExt);
            }
            oldFile.renameTo(newFile);
        }
        log.info("Rename success, total renamed files:" + fileList.size());
        log.info("Finish rename ext");
    }


    /**
     * 根据file获取文件路径
     *
     * @param file
     * @return
     */
    public static String convertToPath(String file) {
        if (isDir(file)) {
            return file;
        }
        String path = "";
        String osType = getOsType(file);
        if (new File(file).isAbsolute()) {
            if (CONSTANT.WINDOWS.equals(osType)) {
                String[] fileSet = file.split("\\\\");
                path = file.replace(fileSet[fileSet.length - 1], "");
                log.info("File path: " + path);
            } else if (CONSTANT.MAC_OS.equals(osType)) {
                String[] fileSet = file.split("/");
                path = file.replace(fileSet[fileSet.length - 1], "");
            }
        }
        return path;
    }

    public static ArrayList<String> getFileNames(String path) throws IOException {
        path = convertToPath(path);
        log.info("Directory: => {}", path);

        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempLists = file.listFiles();
        for (int i = 0; i < tempLists.length; i++) {
            if (tempLists[i].isFile() && !tempLists[i].getName().equals(".DS_Store")) {
                files.add(tempLists[i].getName());//file name
            }
        }
        return files;
    }

    public static ArrayList<String> getFullFileNames(String path) throws IOException {
        path = convertToPath(path);
        log.info("Directory: => {}", path);

        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempLists = file.listFiles();
        for (int i = 0; i < tempLists.length; i++) {
            if (tempLists[i].isFile() && !tempLists[i].getName().equals(".DS_Store")) {
                files.add(tempLists[i].toString());//full path
            }
        }
        return files;
    }


    public static String getText(String src) throws IOException {
        String path = src;//文件路径
        File file = new File(path); // 要读取以上路径的test.txt文件
        System.out.println(file.getName());
        byte[] bytes = new byte[1024];
        StringBuffer sb = new StringBuffer();
        FileInputStream input = new FileInputStream(file);
        int len;
        while ((len = input.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len));
        }
        System.out.println(sb);
        input.close();
        return sb.toString();

    }

    public static Map<String, Object> read(String path) throws IOException {
        if (!isFile(path)) {
            log.warn("CANT read this file, please check !");
            return null;
        }
        File f = new File(path);
        // 建立一个输入流对象reader
        InputStreamReader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
        // 建立一个对象，它把文件内容转成计算机能读懂的语言
        BufferedReader br = new BufferedReader(reader);
        String line;
        line = br.readLine();
        int i = 1;

        StringBuilder content1 = new StringBuilder();
        StringBuilder content2 = new StringBuilder();
        List<StringBuilder> content3 = new LinkedList<>();
        while (NullUtil.isNotNull(line) || "".equals(line)) {
            // 一次读入一行数据,并显示行数
            // content1.append(i + ". ");
            content1.append(line + System.getProperty("line.separator"));
            content2.append(line);
            content3.add(new StringBuilder(line));
            i++;
            // 把所有内容在一行显示
            line = br.readLine();
        }
        br.close();
        reader.close();

        Map<String, Object> map = new HashMap();
        map.put(FileUtil.ORIGINAL, content1.toString());
        map.put(FileUtil.ONE_LINE, content2.toString());
        map.put(FileUtil.LIST, content3);

        return map;
    }

    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    public static boolean isDir(String path) {
        return new File(path).isDirectory();
    }


    /**
     * accord path to get contented file name
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Map getFileNamesMap(String path) throws IOException {
        List<String> ls = getAllFolderList(path);
        Map<String, List<String>> result = new HashMap();
        for (int i = 0; i < ls.size(); i++) {
            String folder = ls.get(i);
            List<String> files = getFileNames(folder);
            result.put(folder, files);
        }
        return result;
    }


    public static LinkedHashMap getFullPathDirTree(String path) throws IOException {
        //   LinkedList<String> list = new LinkedList<>();
        LinkedHashMap m = new LinkedHashMap();
        path = convertToPath(path);
        File file = new FileSystemResource(path).getFile();
        // 获取路径下的所有文件及文件夹
        File[] files = file.listFiles();
        int size = files.length;
        for (int i = 0; i < size; i++) {
            if (files[i].isDirectory()) {
                String folder = files[i].getPath();
                Map t = getFullPathDirTree(folder);
                m.put(folder, t);
            } else {
                String fPath = files[i].getPath();
                m.put(i, fPath);
            }
        }
        return m;
    }

    public static void main(String[] args) throws IOException {
        Map a = getFullPathDirTree("src/main/java/com/entity");
        System.out.println();

        Map b = (Map) a.get("src\\main\\java\\com\\entity\\dao");
        Map c = (Map) b.get("src\\main\\java\\com\\entity\\dao\\hibernate");

        List d = MapUtil.getValueSet(c);

        for (Object t : d) {
            System.out.println(t.toString());
        }
        System.out.println("-------------------");

        Map b1 = (Map) a.get("src\\main\\java\\com\\entity\\vo");
        List d1 = MapUtil.getValueSet(b1);

        for (Object t : d1) {
            System.out.println(t.toString());
        }
    }


    /**
     * 获取路径下所有文件夹
     *
     * @param path
     * @return
     */
    public static LinkedList<String> getFullPathDirList(String path) {
        LinkedList<String> list = new LinkedList<>();
        path = convertToPath(path);
        //考虑到会打成jar包发布 new File()不能使用改用FileSystemResource
        File file = new FileSystemResource(path).getFile();
        // 获取路径下的所有文件及文件夹
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                //add dir into list
                list.add(files[i].getPath());
                list.addAll(getFullPathDirList(files[i].getPath()));
            } else {
                list.add(files[i].getPath());
            }
        }
        return list;
    }

    /**
     * 删除line 0 到 lineNum的内容
     * 主要用于log太大, 把多余的log删除
     *
     * @param fullPath
     * @param lineNum
     * @return
     * @throws IOException
     */
    public static List<String> removeLinesByLineNum(String fullPath, int lineNum) throws IOException {
        File file = new File(fullPath);
        List<String> strList = new ArrayList<String>();
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "rw");
            //Initial write position
            long writePosition = raf.getFilePointer();
            for (int i = 0; i < lineNum; i++) {
                String line = raf.readLine();
                if (NullUtil.isNull(line)) {
                    break;
                }
                strList.add(line);
            }
            // Shift the next lines upwards.
            long readPosition = raf.getFilePointer();
            byte[] buff = new byte[1024];
            int n;
            while (-1 != (n = raf.read(buff))) {
                raf.seek(writePosition);
                raf.write(buff, 0, n);
                readPosition += n;
                writePosition += n;
                raf.seek(readPosition);
            }
            raf.setLength(writePosition);
        } catch (IOException e) {
            log.error("Remove Lines error", e);
            throw e;
        } finally {
            try {
                if (NullUtil.isNotNull(raf)) {
                    raf.close();
                }
            } catch (IOException e) {
                log.error("close Random Access File error", e);
                throw e;
            }
        }
        return strList;
    }

    /**
     * folder not exist, create folders
     *
     * @param fullPath
     */
    public static void mkDirs(String fullPath) {
        File f = new File(fullPath);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * according path to defined os type
     *
     * @param s
     * @return
     */
    private static String getOsType(String s) {
        String osType;
        if (OsUtil.getOsName().contains("MAC")) {
            osType = CONSTANT.MAC_OS;
        } else if (OsUtil.getOsName().contains("WINDOWS")) {
            osType = CONSTANT.WINDOWS;
        } else {
            osType = CONSTANT.UNKNOWN;
        }
        return osType;
    }


    public static boolean rewrite(String filepath, String Content) {
        boolean flag = false;
        try {
            //写入的txt文档的路径
            PrintWriter pw = new PrintWriter(filepath);
            //写入的内容
            pw.write(Content);
            pw.flush();
            pw.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    public static void write(String filePath, String fileName, Object obj) {
        if (NullUtil.isNull(obj)) {
            log.info("Object is null, File writing exist !");
            return;
        }
        //boolean isOk = false;
        String type = "";
        StringBuilder content = null;
        List<StringBuilder> contentList = new ArrayList<>();
        if (obj instanceof String) {
            content = new StringBuilder((String) obj);
            type = "String";
        }
        if (obj instanceof List) {
            contentList = (List) obj;
            type = "List";
        } else {
            log.info("un-default type");
            return;
        }

        if (fileStatus(filePath, fileName)) {
            FileOutputStream fop = null;

            try {
                log.info("File Path : " + filePath + fileName);
                File file = new File(filePath + fileName);
                //fop = new FileOutputStream(file);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_16LE);
                // get the content in bytes
                String contentInBytes = null;
                if (type.equals("String")) {
                    contentInBytes = content.toString();
                    outputStreamWriter.append(contentInBytes);
                    outputStreamWriter.flush();
                } else if (type.equals("List")) {
                    for (int i = 0; i < contentList.size(); i++) {
                        // contentInBytes = contentList.get(i).toString().getBytes();
                        // fop.write(contentInBytes);
                        contentInBytes = contentList.get(i).toString();
                        outputStreamWriter.append(contentInBytes);
                        outputStreamWriter.flush();
                    }

                } else {
                    log.info("contentInBytes: " + contentInBytes);
                }

                outputStreamWriter.close();
                // fop.flush();
                // fop.close();
                log.info("File writing complete");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (NullUtil.isNotNull(fop)) {
                        fop.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //check file and dir status
    public static boolean fileStatus(String path, String fileName) {
        path = convertToPath(path);
        //check dir exist
        File folder = new File(path);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
            log.info("Directory created");
        } else {
            log.info("Directory is exist");
        }
        //check file exist
        boolean isOK = false;
        File file = new File(path + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                file.setWritable(true);
                log.info("File created");
            } catch (IOException e) {
                e.printStackTrace();
            }
            isOK = true;
        } else {
            log.info("File is exist");
            if (file.exists() && file.length() == 0) {
                log.info("File is empty");
            } else {
                log.info("File is not empty, please check !");

            }
            if (file.canWrite()) {
                log.info("File can write");
                isOK = true;
            } else {
                log.info("File can't write");
                isOK = false;
            }
        }
        return isOK;
    }

    /**
     * 读取文件并返回encoding
     *
     * @param f
     * @return
     */
    public static String getEncoding(String f) {
        File file = new File(f);
        String ENCODING = "GBK";
        if (!file.exists()) {
            System.err.println("get File In code: file not exists!");
            return ENCODING;
        }
        byte[] buf = new byte[4096];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // (1)
            UniversalDetector detector = new UniversalDetector(null);
            // (2)
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            // (3)
            detector.dataEnd();
            // (4)
            String encoding = detector.getDetectedCharset();
            if (NullUtil.isNotNull(encoding)) {
                log.info("Detected encoding = " + encoding);
            } else {
                log.info("No encoding detected.");
            }
            // (5)
            detector.reset();
            fis.close();
            if (NullUtil.isNull(encoding) || encoding.equals("IBM855") || encoding.equals("WINDOWS-1252"))
                encoding = ENCODING;
            return encoding;
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("ENCODING: " + ENCODING);
        return ENCODING;
    }

    public static boolean compareFile(String file1, String file2) {
        File fileFirst = new File(file1);
        File fileSecond = new File(file2);
        if (!fileFirst.isFile() || !fileSecond.isFile()) {
            log.error("File not exist, please check !!!");
            return false;
        }
        String firstMD5 = getFileMD5(fileFirst);
        String secondMD5 = getFileMD5(fileSecond);
        boolean isEquals = firstMD5.equals(secondMD5);
        log.info("Compare result: " + isEquals);
        return isEquals;
    }

    private static String getFileMD5(File file) {
        MessageDigest digest;
        FileInputStream in = null;
        byte[] buffer = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static long getFileSize(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            log.info("File not exist");
            return -1;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        if (file.length() / (1024l * 1024l) > 0) {
            double r = (double) file.length() / (1024l * 1024l);
            log.info(df.format(r) + " mb");
        } else if (file.length() / (1024l) > 0) {
            double r = (double) file.length() / (1024l);
            log.info(df.format(r) + " kb");
        } else {
            log.info(file.length() + " bytes");
        }
        return file.length();
    }


    /**
     * Used to extract and return the extension of a given file.
     *
     * @param f Incoming file to get the extension of
     * @return <code>String</code> representing the extension of the incoming
     * file.
     */
    public static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {
            ext = f.substring(i + 1);
        }
        return ext;
    }

    /**
     * Used to extract the filename without its extension.
     *
     * @param f Incoming file to get the filename
     * @return <code>String</code> representing the filename without its
     * extension.
     */
    public static String getFileName(String f) {
        String fName = "";
        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {
            fName = f.substring(0, i);
        }
        return fName;
    }

    public static boolean delete(String file) {
        boolean isSuccess = false;
        File f = new File(file);
        if (!f.isFile()) {
            log.error("This is not a file");
        }
        if (f.exists()) {
            isSuccess = f.delete();
            log.info("File deleted");
        } else {
            log.info("File not exist");
        }
        return isSuccess;
    }


    private static final ArrayList<Object> scanFiles = new ArrayList<>();

    /**
     * TODO:递归扫描指定文件夹下面的文件全路径
     *
     * @return ArrayList<Object>
     * @time 2017年11月3日
     */
    public static ArrayList<Object> getFilesLocation(String folderPath) {
        //   ArrayList<Object> scanFiles = new ArrayList<Object>();
        ArrayList<String> directories = new ArrayList<>();
        File directory = new File(folderPath);

        if (directory.isDirectory()) {
            File[] fileList = directory.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                /**如果当前是文件夹，进入递归扫描文件夹**/
                if (fileList[i].isDirectory()) {
                    directories.add(fileList[i].getAbsolutePath());
                    /**递归扫描下面的文件夹**/
                    getFilesLocation(fileList[i].getAbsolutePath());
                }
                /**非文件夹**/
                else {
                    scanFiles.add(fileList[i].getAbsolutePath());
                }
            }
        }
        return scanFiles;
    }

    /**
     * TODO:非递归方式扫描指定文件夹下面的所有文件
     *
     * @param folderPath 需要进行文件扫描的文件夹路径
     * @return ArrayList<Object>
     * @time 2017年11月3日
     */
    public static ArrayList<Object> getFilePaths(String folderPath) {
        ArrayList<Object> scanFiles = new ArrayList<>();
        LinkedList<File> queueFiles = new LinkedList<>();
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            log.error("incorrect folder path !!!");
            return null;
        } else {
            //首先将第一层目录扫描一遍
            File[] files = directory.listFiles();
            //遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    queueFiles.add(files[i]);
                } else {
                    //暂时将文件名放入scanFiles中
                    scanFiles.add(files[i].getAbsolutePath());
                }
            }

            //如果linkedList非空遍历linkedList
            while (!queueFiles.isEmpty()) {
                //移出linkedList中的第一个
                File headDirectory = queueFiles.removeFirst();
                File[] currentFiles = headDirectory.listFiles();
                for (int j = 0; j < currentFiles.length; j++) {
                    if (currentFiles[j].isDirectory()) {
                        //如果仍然是文件夹，将其放入linkedList中
                        queueFiles.add(currentFiles[j]);
                    } else {
                        scanFiles.add(currentFiles[j].getAbsolutePath());
                    }
                }
            }
        }
        return scanFiles;
    }


}
