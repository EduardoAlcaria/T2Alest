
package service;

import domain.Tournament;
import java.util.LinkedList;

public class TournamentService {
    private final Tournament tournament = new Tournament();

    public Tournament getTournament() {
        return tournament;
    }

    public void createTournament(LinkedList<String> participants) {
        tournament.buildTournament(participants);
    }

    public boolean registerWinner(String winner) {
        return tournament.registerWinner(winner);
    }
}
