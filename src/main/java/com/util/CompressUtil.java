package com.util;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class CompressUtil {
    static private final Log log = LogFactory.getLog(CompressUtil.class);

    public static void compressGZFile(String inFileName) {
        try {
            System.out.println("Creating the GZIP output stream.");
            String outFileName = inFileName + ".gz";
            GZIPOutputStream out = null;
            try {
                out = new GZIPOutputStream(new FileOutputStream(outFileName));
            } catch (FileNotFoundException e) {
                System.err.println("Could not create file: " + outFileName);
                System.exit(1);
            }


            System.out.println("Opening the input file.");
            FileInputStream in = null;
            try {
                in = new FileInputStream(inFileName);
            } catch (FileNotFoundException e) {
                System.err.println("File not found. " + inFileName);
                System.exit(1);
            }

            System.out.println("Transferring bytes from input file to GZIP Format.");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();

            System.out.println("Completing the GZIP file");
            out.finish();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void unCompressGZFile(String inFileName) {
        try {
            if (!FileUtil.getExtension(inFileName).equalsIgnoreCase("gz")) {
                log.error("File name must have extension of \".gz\"");
                System.exit(1);
            }

            log.info("Opening the compressed file.");
            GZIPInputStream in = null;
            try {
                in = new GZIPInputStream(new FileInputStream(inFileName));
            } catch (FileNotFoundException e) {
                log.error("File not found. " + inFileName);
                System.exit(1);
            }

            log.info("Open the output file.");
            String outFileName = FileUtil.getName(inFileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                log.error("Could not write to file. " + outFileName);
                System.exit(1);
            }

            log.info("Transferring bytes from compressed file to the output file.");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            log.info("Closing the file and stream");
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public static void unCompressGZFiles(String path) throws Exception {
        path = FileUtil.getParent(path);
        System.out.println(path);
        ArrayList<String> files = FileUtil.getFileNamesWithExt(path);
        for (String f : files) {
            f = path + f;
            unCompressGZFile(f);
        }
    }

    public static void unCompressGZFilesAndDeleteGZFile(String path) throws Exception {
        path = FileUtil.getParent(path);
        System.out.println(path);
        ArrayList<String> files = FileUtil.getFileNamesWithExt(path);
        for (String f : files) {
            f = path + f;
            unCompressGZFile(f);
            FileUtil.delete(f);
        }
    }

    public static void unRAR(File rarFile, String outDir) throws Exception {
        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }
        Archive archive = new Archive(new FileInputStream(rarFile));
        FileHeader fileHeader = archive.nextFileHeader();
        while (fileHeader != null) {
            if (fileHeader.isDirectory()) {
                fileHeader = archive.nextFileHeader();
                continue;
            }
            File out = new File(outDir + fileHeader.getFileNameString());
            if (!out.exists()) {
                if (!out.getParentFile().exists()) {
                    out.getParentFile().mkdirs();
                }
                out.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(out);
            archive.extractFile(fileHeader, os);

            os.close();

            fileHeader = archive.nextFileHeader();
        }
        archive.close();
    }

    public static void unZip(File zipFile, String outDir) throws IOException {

        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                System.out.println("创建压缩目录成功");
            }
        }

        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration<ZipEntry> enumeration = zip.getEntries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = enumeration.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);

            if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir + zipEntryName);
                fileDir.mkdir();
                continue;
            }

            File file = new File(outDir, zipEntryName);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public static void un7z(String in7ZFilePath, String outDir) {
        try (SevenZFile sevenZFile = new SevenZFile(new File(in7ZFilePath))) {
            ArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                }

                File outputFile = new File(outDir, entry.getName());
                File outputDirFile = outputFile.getParentFile();
                if (!outputDirFile.exists()) {
                    outputDirFile.mkdirs();
                }

                byte[] content = new byte[(int) entry.getSize()];
                sevenZFile.read(content, 0, content.length);

                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    fos.write(content);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 7Z 压缩
     *
     * @param outPut7zDir 压缩后的文件路径（如 D:\SevenZip\test.7z）
     * @param inFiles     需要压缩的文件
     */
    public static void compressTo7z(String outPut7zDir, File... inFiles) {
        try (SevenZOutputFile out = new SevenZOutputFile(new File(outPut7zDir))) {
            for (File file : inFiles) {
                addToArchived7zCompression(out, file, ".");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToArchived7zCompression(SevenZOutputFile out, File file, String dir) {
        String name = dir + File.separator + file.getName();
        if (dir.equals(".")) {
            name = file.getName();
        }
        if (file.isFile()) {
            SevenZArchiveEntry entry;
            FileInputStream in = null;
            try {
                entry = out.createArchiveEntry(file, name);
                out.putArchiveEntry(entry);
                in = new FileInputStream(file);
                byte[] b = new byte[1024];
                int count = 0;
                while ((count = in.read(b)) > 0) {
                    out.write(b, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.closeArchiveEntry();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addToArchived7zCompression(out, child, name);
                }
            }
        } else {
            System.out.println(file.getName() + " is not supported");
        }
    }

    public static void main(String[] args) throws Exception {
        String in = "/Users/garlam/Downloads/ip2region-master.zip";
        String out = "/Users/garlam/Downloads/ip2region-master";
        unZip(new File(in), out);
    }

}
