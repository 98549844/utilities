package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.core.io.FileSystemResource;
import util.constant.Constant;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.*;

@SuppressWarnings("unchecked")
public class FileUtil {
    private static final Logger log = LogManager.getLogger(FileUtil.class.getName());

    public static Map getSubFolderList(String path) {
        File file = new File(path);
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
        map.put("folderList", folderList);
        map.put("fileList", fileList);
        return map;
    }

    /**
     * check slash exist
     */
    private static boolean hasSlash(String path) {
        boolean isSlash = false;
        if (isFile(path)) {
            log.info("This is File path");
            return false;
        }
        String end = path.substring(path.length() - 1);
        if (end.equals("/") || end.equals("\\")) {
            isSlash = true;
        }
        return isSlash;
    }

    /**
     * add backslash if missing
     *
     * @param path
     * @return
     */
    private static String slash(String path) {
        if (!hasSlash(path)) {
            String osType = getOsType(path);
            if (Constant.MAC_OS.equals(osType)) {
                path = path + "/";
            } else if (Constant.WINDOWS.equals(osType)) {
                path = path + "\\\\";
            } else {
                log.error("Path incorrect, please check");
            }
        }
        return path;
    }

    /**
     * ?????????????????????????????????????????????
     */
    public static void CopyFileWithNewExt(String path, List<String> fileNames, String ext, Boolean delFile) {

        FileUtil fileUtil = new FileUtil();
        path = fileToPath(path);
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
                fr = new FileReader(path + fileName);//???
                fw = new FileWriter(path + newFileName);//???
                char[] buf = new char[1024];//?????????
                int len;
                while ((len = fr.read(buf)) != -1) {
                    fw.write(buf, 0, len);//??????????????????
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

    public static void renameFilesName(String path, List<String> newNameList) throws Exception {
        log.info("Start rename file");

        FileUtil fileUtil = new FileUtil();
        path = fileToPath(path);
        ArrayList<String> fileList = FileUtil.getFileNames(path);
        if (fileList.size() != newNameList.size()) {
            log.error("List size not equal");
            return;
        }
        for (int i = 0; i < newNameList.size(); i++) {
            path = slash(path);
            File oldFile = new File(path + fileList.get(i));
            File newFile = new File(path + newNameList.get(i));
            if (newFile.exists()) {
                newFile = new File(path + "_" + newNameList.get(i));
            }
            oldFile.renameTo(newFile);
        }
        log.info("Finish rename file");
    }

    public static void renameFilesExt(String path, String newExt) throws Exception {
        log.info("Start rename ext");
        FileUtil fileUtil = new FileUtil();
        path = fileToPath(path);
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


    public static String fileToPath(String file) {
        if (NullUtil.isNull(file)) {
            log.error("File path is null");
        }
        String path = "";
        String osType = getOsType(file);
        if (Constant.WINDOWS.equals(osType)) {
            if (isFile(file)) {
                String[] fileSet = file.split("\\\\");
                path = file.replace(fileSet[fileSet.length - 1], "");
            } else {
                path = file;
            }
            log.info("File path: " + path);
        } else if (Constant.MAC_OS.equals(osType)) {
            if (isFile(file)) {
                String[] fileSet = file.split("/");
                path = file.replace(fileSet[fileSet.length - 1], "");
            } else {
                path = file;
            }
        }
        path = slash(path);
        return path;
    }

    public static ArrayList<String> getFileNames(String path) throws Exception {
        FileUtil fileUtil = new FileUtil();
        path = fileToPath(path);
        log.info("Directory: " + path);

        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempLists = file.listFiles();
        for (int i = 0; i < tempLists.length; i++) {
            if (tempLists[i].isFile() && !tempLists[i].getName().equals(".DS_Store")) {
                files.add(tempLists[i].getName());//file name
                // files.add(tempLists[i].toString());//full path
            }
        }
        return files;
    }


    public static Map<String, Object> read(String path) throws IOException {
        FileUtil fileUtil = new FileUtil();
        path = fileToPath(path);
        File f = new File(path);
        // ???????????????????????????reader
        InputStreamReader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
        // ????????????????????????????????????????????????????????????????????????
        BufferedReader br = new BufferedReader(reader);
        String line;
        line = br.readLine();
        int i = 1;

        StringBuilder content1 = new StringBuilder();
        StringBuilder content2 = new StringBuilder();
        List<StringBuilder> builderList = new LinkedList<>();
        while (NullUtil.isNotNull(line)) {

            // ????????????????????????,???????????????
            //content.append(i + "*: ");
            content1.append(line + System.getProperty("line.separator"));
            content2.append(line);
            builderList.add(new StringBuilder(line));
            i++;
            // ??????????????????????????????
            line = br.readLine();
        }
        br.close();
        reader.close();

        Map<String, Object> map = new HashMap();
        map.put(Constant.ORIGINAL, content1);
        map.put(Constant.ONE_LINE, content2);
        map.put(Constant.LIST, builderList);

        return map;
    }

    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    /**
     * ??????????????????????????????
     *
     * @param path
     * @return
     */
    LinkedList<String> list = new LinkedList<>();

    public LinkedList<String> getFileNameAndDirectory(String path) throws Exception {
        FileUtil fileUtil = new FileUtil();
        path = fileToPath(path);

        //??????????????????jar????????? new File()??????????????????FileSystemResource
        File file = new FileSystemResource(path).getFile();
        // ??????????????????????????????????????????
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                // ????????????????????? ??????????????????????????? ?????????
                //add dir into list
                list.add(files[i].getPath());
                list.addAll(getFileNameAndDirectory(files[i].getPath()));
            }
        }
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                // add file into list
                list.add(files[i].getPath());
            }
        }
        return list;
    }

    /**
     * ??????line 0 ??? lineNum?????????
     * ????????????log??????, ????????????log??????
     *
     * @param fullPath
     * @param lineNum
     * @return
     * @throws IOException
     */
    public List<String> removeLinesByLineNum(String fullPath, int lineNum) throws IOException {
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
    public void mkDirs(String fullPath) {
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
        String osType = "";
        if (s.startsWith("/")) {
            osType = Constant.MAC_OS;
        } else if (s.charAt(1) == ':') {
            osType = Constant.WINDOWS;
        } else {
            osType = Constant.UNKNOWN;
        }
        return osType;
    }

    public void write(String filePath, String fileName, Object obj) {
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
//                        contentInBytes = contentList.get(i).toString().getBytes();
//                        fop.write(contentInBytes);
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
        FileUtil fileUtil = new FileUtil();
        path = fileToPath(path);
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
     * ?????????????????????encoding
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
        MessageDigest digest = null;
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
        String fname = "";
        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {
            fname = f.substring(0, i);
        }
        return fname;
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


    private static final ArrayList<Object> scanFiles = new ArrayList<Object>();


    /**
     * linkedList??????
     **/
    private static final LinkedList<File> queueFiles = new LinkedList<File>();


    /**
     * TODO:????????????????????????????????????????????????
     *
     * @return ArrayList<Object>
     * @author ??????????????????LQ???
     * @time 2017???11???3???
     */
    public static ArrayList<Object> getFilesLocation(String folderPath) {
        ArrayList<String> dirctorys = new ArrayList<String>();
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            folderPath = fileToPath(folderPath);
        }
        if (directory.isDirectory()) {
            File[] filelist = directory.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                /**??????????????????????????????????????????????????????**/
                if (filelist[i].isDirectory()) {
                    dirctorys.add(filelist[i].getAbsolutePath());
                    /**??????????????????????????????**/
                    getFilesLocation(filelist[i].getAbsolutePath());
                }
                /**????????????**/
                else {
                    scanFiles.add(filelist[i].getAbsolutePath());
                }
            }
        }
        return scanFiles;
    }

    /**
     * TODO:?????????????????????????????????????????????????????????
     *
     * @param folderPath ??????????????????????????????????????????
     * @return ArrayList<Object>
     * @author ??????????????????LQ???
     * @time 2017???11???3???
     */
    public static ArrayList<Object> getFilePaths(String folderPath) {
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            folderPath = fileToPath(folderPath);
        } else {
            //????????????????????????????????????
            File[] files = directory.listFiles();
            //??????????????????????????????????????????????????????????????????linkedList???????????????
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    queueFiles.add(files[i]);
                } else {
                    //????????????????????????scanFiles???
                    scanFiles.add(files[i].getAbsolutePath());
                }
            }

            //??????linkedList????????????linkedList
            while (!queueFiles.isEmpty()) {
                //??????linkedList???????????????
                File headDirectory = queueFiles.removeFirst();
                File[] currentFiles = headDirectory.listFiles();
                for (int j = 0; j < currentFiles.length; j++) {
                    if (currentFiles[j].isDirectory()) {
                        //???????????????????????????????????????linkedList???
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
