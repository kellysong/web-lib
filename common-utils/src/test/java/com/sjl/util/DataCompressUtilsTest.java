package com.sjl.util;

import org.junit.Test;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename DataCompressUtilsTest
 * @time 2022/11/20 15:38
 */
public class DataCompressUtilsTest {
    @Test
    public void testCompress(){
        String data="中国队12225555";
        try {
            byte[] compress = DataCompressUtils.compress(data.getBytes());
            byte[] uncompress = DataCompressUtils.uncompress(compress);
            System.out.println(new String(uncompress));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
