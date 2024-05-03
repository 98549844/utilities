package com.ace.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;


/**
 * @Classname: ByteUtil
 * @Date: 24/2/2024 6:37 pm
 * @Author: garlam
 * @Description:
 */


public class ByteUtil {
    private static final Logger log = LogManager.getLogger(ByteUtil.class.getName());


    /** byte[]转换成object
     * @param bytes
     * @param type
     * @return
     * @throws Exception
     */
    public static Object toObject(byte[] bytes, Class<T> type) throws Exception {
        return FastJson2Util.BytesArrayToObject(bytes, type);
    }

}

