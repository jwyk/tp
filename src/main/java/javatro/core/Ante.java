package javatro.core;

public class Ante {
    public enum Blind{
        SMALL_BLIND(1, "SMALL BLIND"),
        LARGE_BLIND(1.5, "LARGE BLIND"),
        BOSS_BLIND(2, "BOSS BLIND");

        private final double multiplier;
        private final String name;

        Blind(double multiplier, String name) {
            this.multiplier = multiplier;
            this.name = name;
        }

        public double getMultiplier(){
            return multiplier;
        }
        public String getName(){
            return name;
        }
    }

    private int anteCount;
    private Blind blind;
    private int[] anteScore = {300, 800, 2000, 5000, 11000, 20000, 35000, 50000};

    public Ante(){
        anteCount = 1;
        blind = Blind.SMALL_BLIND;
    }

    public int getRoundScore(){
        return (int) (anteScore[anteCount-1] * blind.multiplier);
    }

    public int getAnteScore(){
        return anteScore[anteCount-1];
    }

    public void resetAnte(){anteCount = 1;}
    public void nextRound(){
        if(blind == Blind.SMALL_BLIND){
            blind = Blind.LARGE_BLIND;
        }
        else if(blind == Blind.LARGE_BLIND){
            blind = Blind.BOSS_BLIND;
        }
        else{
            if(anteCount == 8) return;
            blind = Blind.SMALL_BLIND;
            anteCount++;
        }
    }

    public void setBlind(Blind blind){
        this.blind = blind;
    }

    public Blind getBlind(){
        return blind;
    }

    public int getAnteCount(){
        return anteCount;
    }

}
