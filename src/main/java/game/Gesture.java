package game;

public enum Gesture {
    ROCK,
    PAPER,
    SCISSORS;

    public static Gesture fromString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
            switch (input.toUpperCase()) {
                case "r":
                    return ROCK;
                case "p":
                    return PAPER;
                case "s":
                    return SCISSORS;
                default:
                    throw new IllegalArgumentException("Unknown gesture: " + input);
            }

        }
        public int compareWith(Gesture other) {
        if(this==other){
            return 0;
        }
        switch (this){
            case ROCK: return (other==SCISSORS)? 1 : -1;
            case PAPER: return (other==ROCK)? 1 : -1;
            case SCISSORS: return (other==PAPER)? 1 : -1;
            default: throw new IllegalArgumentException("Unknown gesture: " + other);
        }
        }
}
