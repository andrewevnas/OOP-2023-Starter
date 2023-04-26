package ie.tudublin;

import java.util.ArrayList;
import processing.core.PApplet;

public class DANI extends PApplet {
    
    ArrayList<Word> model = new ArrayList<Word>();

    public void settings() {
        size(500, 500);
        //fullScreen(SPAN);
    }


    public void setup() {
        colorMode(HSB);
		frameRate(1);
        loadFile("shakespere.txt");
    }

    public void loadFile(String filename) {
        String[] lines = loadStrings(filename);

        for (String line : lines) {
            String[] words = line.split("\\s+");

            for (int i = 0; i < words.length - 1; i++) {
                String word = words[i];
                String nextWord = words[i + 1];

                Word w = findWord(word);
                if (w == null) {
                    w = new Word(word);
                    model.add(w);
                }
                w.addFollow(nextWord);
            }
        }
    }

    public Word findWord(String word) {
        for (Word w : model) {
            if (w.getWord().equals(word)) {
                return w;
            }
        }
        return null;
    }

    public ArrayList<Follow> findFollow(String word) {
        for (Word w : model) {
            if (w.getWord().equals(word)) {
                return w.getFollow();
            }
        }
        return null;
    }

    public void keyPressed() {
		if (key == ' ') {
		  // Generate a new sonnet
		  String[] sonnetText = writeSonnet();
	  
		  // Print the sonnet to the console
		  System.out.println(sonnetText);
		}
	  }

    float off = 0;

	public void printModel() {
		for (Word w : model) {
			System.out.print(w.getWord() + ": ");
			for (Follow f : w.getFollow()) {
				System.out.print(f.getWord() + "," + f.getCount() + " ");
			}
			System.out.println();
		}
	}

	String[] sonnet;

	public String[] writeSonnet() {
		StringBuilder sonnet = new StringBuilder();
		Word currentWord = model.get((int) random(model.size()));
	
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 8; j++) {
				if (currentWord != null) {
					sonnet.append(currentWord.getWord()).append(" ");
				} else {
					currentWord = model.get((int) random(model.size()));
					sonnet.append(currentWord.getWord()).append(" ");
				}
				
				ArrayList<Follow> follows = currentWord.getFollow();
				if (follows != null && !follows.isEmpty()) {
					int index = (int) random(follows.size());
					currentWord = findWord(follows.get(index).getWord());
				} else {
					break;
				}
			}
			sonnet.append("\n");
			currentWord = model.get((int) random(model.size()));
		}
	
		return sonnet.toString().split("\n");
	}

	public void draw() {
		background(0);
		fill(255);
		noStroke();
		textSize(20);
		textAlign(CENTER, CENTER);
		
		// Generate the sonnet
		String[] sonnet = writeSonnet();
		
		// Calculate the height of each line of text
		float lineHeight = height / (sonnet.length + 2);
		
		// Display the sonnet
		float y = lineHeight * 2;
		for (String line : sonnet) {
			text(line, width / 2, y);
			y += lineHeight;
		}
	}
	
	
	
	
}
