package util;

/**
 * Created by Alexandra on 5/15/2017.
 */
public class Pair<Long, T> {
    private Long first;
    private T second;

    public Pair() {
    }

    public Pair(Long first, T second) {
        super();
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
    
    public static class PairValue {
        private Integer score;
        private String sense;

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getSense() {
            return sense;
        }

        public void setSense(String sense) {
            this.sense = sense;
        }

        public PairValue() {
        }

        public PairValue(Integer score, String sense) {
            this.score = score;
            this.sense = sense;
        }
    }
}