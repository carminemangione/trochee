package me.trochee;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class PeriodicallyReloadedTest {

    @Test
    public void periodicallyReloads() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger();
        final PeriodicallyReloaded<Integer> periodicallyReloaded = new PeriodicallyReloaded<Integer>(100) {
            @Override
            protected Integer loadUnsafe() throws Exception {
                return atomicInteger.incrementAndGet();
            }
        };
        assertEquals(new Integer(1), periodicallyReloaded.get());
        Thread.sleep(150);
        assertTrue(2 <= periodicallyReloaded.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void nullThrowsNSEE() throws Exception {
        new PeriodicallyReloaded<Integer>(100) {
            @Override
            protected Integer loadUnsafe() throws Exception {
                return null;
            }
        }.get();
    }

    @Test(expected = NoSuchElementException.class)
    public void exceptionOnConstructorReturnsNull() throws Exception {
        final PeriodicallyReloaded<Integer> periodicallyReloaded = new PeriodicallyReloaded<Integer>(100) {
            @Override
            protected Integer loadUnsafe() throws Exception {
                throw new IllegalStateException();
            }
        };
        periodicallyReloaded.get();
    }

    @Test
    public void exceptionOnReload() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger();
        final PeriodicallyReloaded<Integer> periodicallyReloaded = new PeriodicallyReloaded<Integer>(100) {
            @Override
            protected Integer loadUnsafe() throws Exception {
                final int x = atomicInteger.incrementAndGet();
                if (x > 1) {
                    throw new IllegalStateException();
                }
                return x;
            }
        };
        assertEquals(1, periodicallyReloaded.get().intValue());
        Thread.sleep(150);
        try {
            periodicallyReloaded.get();
            fail("Supposed to except here");
        } catch (NoSuchElementException e) {
            //success
        }
    }
}
