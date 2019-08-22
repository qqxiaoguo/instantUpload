
package com.lpjj.application.entity.retrieval;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Calendar;
@Setter
@Getter
@ToString
public class Timestamp {

    private long gte;
    private long lte;

    public Timestamp() {
        final Calendar cal = Calendar.getInstance();
        //2、取得时间偏移量：
        final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        final int dstOffset = cal.get(Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        this.gte = cal.getTimeInMillis();
        this.lte = cal.getTimeInMillis();
    }
}