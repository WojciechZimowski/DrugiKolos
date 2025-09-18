package game;

import org.junit.Test;

import static junit.framework.TestCase.*;


public class DuelTest {

    @Test
    public void playersShouldBeInDuelAfterCreation() {
        // given
        Player p1 = new Player();
        Player p2 = new Player();

        // when
        Duel duel = new Duel(p1, p2);

        // then
        assertTrue(p1.isDueling());
        assertTrue(p2.isDueling());
    }
    @Test
    public void testPlayer1Win(){
        Player p1 = new Player();
        Player p2 = new Player();
        Duel duel = new Duel(p1, p2);
        p1.makeGesture(Gesture.ROCK);
        p2.makeGesture(Gesture.SCISSORS);
        Duel.Result result = duel.evaluate();
        assertNotNull(result);
        assertEquals(p1, result.winner());
        assertEquals(p2, result.looser());
    }
    @Test
    public void testEvaluateDraw(){
        Player p1 = new Player();
        Player p2 = new Player();
        Duel duel = new Duel(p1, p2);
        p1.makeGesture(Gesture.ROCK);
        p2.makeGesture(Gesture.ROCK);
        Duel.Result result = duel.evaluate();
        assertNull(result);
    }
}
