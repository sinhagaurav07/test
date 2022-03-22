package org.gs.ratelimiter;

import org.junit.Assert;
import org.junit.Test;

public class RateLimiterTest {

    @Test
    public void testRateLimiter() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter();
        boolean result = rateLimiter.isAllowed("test");
        Assert.assertEquals(result, Boolean.TRUE);
        Thread.sleep(400);
        result = rateLimiter.isAllowed("test");
        Assert.assertEquals(result, Boolean.FALSE);
        Thread.sleep(4000);
        result = rateLimiter.isAllowed("test");
        Assert.assertEquals(result, Boolean.TRUE);
    }
}
