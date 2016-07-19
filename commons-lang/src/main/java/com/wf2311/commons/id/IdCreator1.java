package com.wf2311.commons.id;

//import com.wf2311.commons.math.NumberUtils;

/**
 * @author wf2311
 * @date 2016/01/14 9:39
 */
public class IdCreator1 {
//    protected static final Logger logger = Logger.getLogger(IdCreator1.class);

    private long workerId;
    private long sequence = 0L;

    private long twePoCh = 1361753741828L;

    //机器标识位数
    private long workerIdBits = 4L;
    //机器ID最大值
    private long maxWorkerId = ~(-1L << workerIdBits);//-1L ^ (-1L << workerIdBits)
    //毫秒内自增位
    private long sequenceBits = 10L;
    //机器ID偏左移12位
    private long workerIdShift = sequenceBits;
    //时间毫秒左移22位
    private long timestampLeftShift = sequenceBits + workerIdBits;
    private long sequenceMask = ~(-1L << sequenceBits);//-1L ^ (-1L << sequenceBits)
    private long lastTimestamp = -1L;

    public IdCreator1(long workerId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
//        logger.info(String.format("worker starting. timestamp left shift %d, worker id bits %d, sequence bits %d, workerId %d", timestampLeftShift, workerIdBits, sequenceBits, workerId));
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (lastTimestamp == timestamp) {
            //当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                //当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        //时间错误
        if (timestamp < lastTimestamp) {
//            logger.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        lastTimestamp = timestamp;

        //ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - twePoCh) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
    }

//    public synchronized String getUid() {
//        return NumberUtils.i10To64(nextId());
//    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
