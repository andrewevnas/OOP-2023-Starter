package ie.tudublin;

import java.util.ArrayList;

public class Word {
    private String word;
    private ArrayList<Follow> follows;
    
    public Word(String word) {
        this.word = word;
        this.follows = new ArrayList<Follow>();
    }
    
    public String getWord() {
        return word;
    }
    
    public ArrayList<Follow> getFollow() {
        return follows;
    }
    
    public void setWord(String word) {
        this.word = word;
    }
    
    public void addFollow(String word) {
        for (Follow follow : follows) {
            if (follow.getWord().equals(word)) {
                follow.setCount(follow.getCount() + 1);
                return;
            }
        }
        follows.add(new Follow(word, 1));
    }
    
    public String toString() {
        String result = word + ": ";
        for (Follow follow : follows) {
            result += follow.toString() + " ";
        }
        return result;
    }
}
