package ca.unknown.bot.entities.game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RPSTest {
    private RockPaperScissors RPS;

    @BeforeEach
    void setUp() {
        RPS = new RockPaperScissors();
    }

    @Test
    void testRock() {
        String result = RPS.startGame("rock");
        assertTrue(result.equals("The bot chose rock. The game is a tie.") ||
                result.equals("The bot chose paper. You lose.") ||
                result.equals("The bot chose scissors. You win!"));
    }

    @Test
    void testPaper() {
        String result = RPS.startGame("paper");
        assertTrue(result.equals("The bot chose rock. You win!") ||
                result.equals("The bot chose paper. The game is a tie.") ||
                result.equals("The bot chose scissors. You lose."));
    }

    @Test
    void testScissors() {
        String result = RPS.startGame("scissors");
        assertTrue(result.equals("The bot chose rock. You lose.") ||
                result.equals("The bot chose paper. You win!") ||
                result.equals("The bot chose scissors. The game is a tie."));
    }
}
