package com.mossony.framwork.db.uuid;

public class SnowFlakeUuidGenerator implements UuidGenerator<Long> {

    private long vm;

    /**
     * 16组自增id，降低锁竞争频率
     */
    private Long[] ids = {0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L};

    @Override
    public Long uuid() {
        /**
         *  获取纳秒时间
         *      低四位定位锁
         *      高42位作为时间戳
         */
        long nanoTime = System.nanoTime();

        int index = (int) nanoTime & 1111;

        synchronized (ids[index]) {
            ids[index] += 1;
            long l = ids[0] % 1000L;
            return null;
        }
    }
}
