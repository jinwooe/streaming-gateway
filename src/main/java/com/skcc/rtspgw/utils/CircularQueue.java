package com.skcc.rtspgw.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CircularQueue<E> {
    private BlockingQueue<E> queue;
    private int capacity;

    private ReentrantLock lock;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        queue = new ArrayBlockingQueue<>(capacity);
        lock = new ReentrantLock(true);
    }

    public boolean add(E e) {
        lock.lock();
        try {
            if (isFull()) {
                try {
                    queue.take();
                } catch (InterruptedException e1) {
                    return false;
                }
            }
            return queue.add(e);
        }
        finally {
            lock.unlock();
        }
    }

    public E poll() {
        return queue.poll();
    }

    public E poll(long timeout, TimeUnit unit) {
        try {
            return queue.poll(timeout, unit);
        } catch (InterruptedException e) {

        }

        return null;
    }

    public int size() {
        return queue.size();
    }

    private boolean isFull() {
        lock.lock();
        try {
            return queue.size() == capacity;
        }
        finally {
            lock.unlock();
        }
    }
}
