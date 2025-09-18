package server;

import java.util.Map;

public class Database {

    public Database() {
    }

    public boolean authenticate(String login, String password) {
        // Tymczasowa wersja – zawsze zwraca true
        return true;
    }

    public void updateLeaderboard(String winner, String loser) {
        // Tu później będzie logika aktualizacji tabeli wyników
    }

    public Map<String, Integer> getLeaderboard() {
        // Na razie zwraca przykładową mapę
        return Map.of("user", 0);
    }
}