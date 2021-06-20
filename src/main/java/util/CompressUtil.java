package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

            System.out.println("Transfering bytes from input file to GZIP Format.");
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
            String outFileName = FileUtil.getFileName(inFileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                log.error("Could not write to file. " + outFileName);
                System.exit(1);
            }

            log.info("Transfering bytes from compressed file to the output file.");
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
        path = FileUtil.fileToPath(path);
        System.out.println(path);
        ArrayList<String> filels = FileUtil.getFileNames(path);
        for (String f : filels) {
            f = path + f;
            unCompressGZFile(f);
        }
    }

    public static void unCompressGZFilesAndDeleteGZFile(String path) throws Exception {
        path = FileUtil.fileToPath(path);
        System.out.println(path);
        ArrayList<String> filels = FileUtil.getFileNames(path);
        for (String f : filels) {
            f = path + f;
            unCompressGZFile(f);
            FileUtil.delete(f);
        }
    }

    public static void main(String[] args) throws Exception {
        String f = "/Users/garlam/IdeaProjects/spring-boot/src/main/resources/15927/06";
        //  String f = "/Users/garlam/IdeaProjects/spring-boot/src/main/resources/15927/01/cis2_ui.log.20210427_0054.gz";
        unCompressGZFilesAndDeleteGZFile(f);
    }

}
