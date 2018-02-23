package com.skcc.rtspgw.utils;

import com.skcc.rtspgw.utils.BytesUtil;
import org.junit.Assert;
import org.junit.Test;

public class BytesUtilTest {
    @Test
    public void test() {
        byte[] bytes = BytesUtil.intToBytes(1);
        int i = BytesUtil.bytesToInt(bytes);

        Assert.assertEquals(1, i);

        bytes = BytesUtil.intToBytes(0);
        i = BytesUtil.bytesToInt(bytes);

        Assert.assertEquals(0, i);

        bytes = BytesUtil.intToBytes(Integer.MAX_VALUE);
        i = BytesUtil.bytesToInt(bytes);

        Assert.assertEquals(Integer.MAX_VALUE, i);

        bytes = BytesUtil.intToBytes(Integer.MIN_VALUE);
        i = BytesUtil.bytesToInt(bytes);

        Assert.assertEquals(Integer.MIN_VALUE, i);
    }
}
