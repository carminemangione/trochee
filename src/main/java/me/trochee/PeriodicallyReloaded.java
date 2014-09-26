package me.trochee;

import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public abstract class PeriodicallyReloaded<T> implements Supplier<T> {

    @SuppressWarnings("FieldCanBeLocal") // do not garbage collect timer
    private final Timer reloadTimer;
    private volatile T cachedObject;

    public PeriodicallyReloaded(long periodMs) {
        this.cachedObject = load();
        this.reloadTimer = new Timer(true);
        this.reloadTimer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        cachedObject = load();
                    }
                },
                periodMs,
                periodMs);
    }

    @Override
    public T get() {
        if (cachedObject == null) {
            throw new NoSuchElementException();
        }
        return cachedObject;
    }

    protected T load() {
        T toReturn = null;
        try {
            toReturn = loadUnsafe();
        } catch (Exception e) {
            //its ok
        }
        return toReturn;
    }

    protected abstract T loadUnsafe() throws Exception;
}
