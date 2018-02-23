package com.skcc.rtspgw.utils;

import org.junit.Assert;
import org.junit.Test;

public class CircularQueueTest {
    @Test
    public void test() {
        CircularQueue<Integer> queue = new CircularQueue<>(10);
        for(int i=1; i<=10; i++) {
            queue.add(i);
        }
        queue.add(11);

        Assert.assertEquals(10, queue.size());
        Assert.assertEquals((Integer)2, queue.poll());
    }
}
