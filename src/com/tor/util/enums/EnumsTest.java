package com.tor.util.enums;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.08.12
 * Time: 14:57
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.javacodegeeks.com/2012/08/java-enums-you-have-grace-elegance-and.html
 */
public class EnumsTest {
    @Test
    public void paper_beats_rock() {
        assertTrue(Gesture.PAPER.beats(Gesture.ROCK));
        assertFalse(Gesture.ROCK.beats(Gesture.PAPER));
    }

    @Test
    public void scissors_beats_paper() {
        assertTrue(Gesture.SCISSORS.beats(Gesture.PAPER));
        assertFalse(Gesture.PAPER.beats(Gesture.SCISSORS));
    }

    @Test
    public void rock_beats_scissors() {
        assertTrue(Gesture.ROCK.beats(Gesture.SCISSORS));
        assertFalse(Gesture.SCISSORS.beats(Gesture.ROCK));
    }

    @Test
    public void notify_users_in_Baku_only() {
        assertTrue(EurovisionNotification.ONLY_IF_IN_MY_CITY.mustNotify("Baku", "BAKU"));
        assertFalse(EurovisionNotification.ONLY_IF_IN_MY_CITY.mustNotify("Baku", "Paris"));
    }

    private boolean DISCOMFORT=true;

    private boolean NO_DISCOMFORT = false;

    @Test
    public void eat_then_sleep_then_poop_and_repeat() {
        assertEquals(BabyState.EAT.next(NO_DISCOMFORT),BabyState.SLEEP);
        assertEquals(BabyState.SLEEP.next(NO_DISCOMFORT),BabyState.POOP);
        assertEquals(BabyState.POOP.next(NO_DISCOMFORT),BabyState.EAT);
    }

    @Test
    public void if_discomfort_then_cry_then_eat() {
        assertEquals(BabyState.SLEEP.next(DISCOMFORT),BabyState.CRY);
        assertEquals(BabyState.CRY.next(NO_DISCOMFORT),BabyState.EAT);
    }

    // Using an EnumMap to represent the votes by authority level
    @Test
    public void votes_with_a_clear_majority() {
     final Map<AuthorityLevel, Integer> votes = new EnumMap(AuthorityLevel.class);
     votes.put(AuthorityLevel.SELL, 1);
     votes.put(AuthorityLevel.ADVISE, 3);
     votes.put(AuthorityLevel.INQUIRE, 2);
     assertEquals(votes.get(AuthorityLevel.ADVISE),Integer.valueOf(3));
    }

}
