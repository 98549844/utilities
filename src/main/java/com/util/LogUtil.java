package com.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LogUtil {

    private final static Log logger = LogFactory.getLog(LogUtil.class);
    private final static Logger log = LogManager.getLogger(LogUtil.class.getName());

    public static void main(String[] args) {
        log.traceEntry();
        log.trace("我是trace");
        log.info("我是info信息:{}", "ACE_UTIL");
        log.error("我是error");
        log.fatal("我是fatal");
        log.trace("退出程序.");
        log.traceExit();
    }

    public static void removeRedundantLogExt(String path, List<String> ls) throws Exception {
        int i = 0;
        path = FileUtil.fileToPath(path);
        ArrayList<String> newls = new ArrayList<>();
        for (String fs : ls) {
            if (FileUtil.isFile(path + fs)) {
                String[] fset = fs.split("\\.");
                ++i;
                String newFileName = fset[0] + "." + fset[1];
                if (newls.contains(newFileName)) {
                    newFileName = fset[0] + "_" + i + "." + fset[1];
                }
                newls.add(newFileName);
            }
        }
        FileUtil.renameFilesName(path, newls);
    }

}
