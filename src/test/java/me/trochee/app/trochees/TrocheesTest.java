package me.trochee.app.trochees;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;

public class TrocheesTest {

    private static final ImmutableList<String> LEXICON = ImmutableList.of("monkey", "gouda", "cyber");

    @Test
    public void noTwoCyclesTheSame() throws Exception {
        final Trochees trochees = new Trochees(LEXICON, new Random(15251));
        final Trochees.Cycler cycler0 = trochees.cycler();
        final Trochees.Cycler cycler1 = trochees.cycler();
        assertFalse(cycler0.next(100).equals(cycler1.next(100)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cyclerNextNonPositive() throws Exception {
        new Trochees(LEXICON).cycler().next(0);
    }
}
