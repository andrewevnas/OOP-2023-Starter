package ie.tudublin;

import java.util.ArrayList;
import processing.core.PApplet;

public class DANI extends PApplet {

     // ArrayList for storing words and their follows
    ArrayList<Word> model = new ArrayList<Word>();

	// Processing setup function to set window size
    public void settings() {
        size(800, 800);
        //fullScreen(SPAN);
    }

	// Processing setup function to set color mode, frame rate, and load the text file
    public void setup() {
        colorMode(HSB);
	
        loadFile("shakespere.txt");
    }

	// Function to load text file into memory and generate the word model
    public void loadFile(String filename) {
        String[] lines = loadStrings(filename);

        for (String line : lines) {
            String[] words = line.split("\\s+");

            for (int i = 0; i < words.length - 1; i++) {
                String word = words[i];
                String nextWord = words[i + 1];

				// Find the word in the model, add it if it doesn't exist

                Word w = findWord(word);
                if (w == null) {
                    w = new Word(word);
                    model.add(w);
                }
				 // Add the follow word to the current word
				
                w.addFollow(nextWord);
            }
        }
    }
	// Function to find a word in the model
    public Word findWord(String word) {
        for (Word w : model) {
            if (w.getWord().equals(word)) {
                return w;
            }
        }
        return null;
    }
	 // Function to find the follows for a given word
    public ArrayList<Follow> findFollow(String word) {
        for (Word w : model) {
            if (w.getWord().equals(word)) {
                return w.getFollow();
            }
        }
        return null;
    }
	
	// Function to generate a sonnet on key press
    public void keyPressed() {
		if (key == ' ') {
		  // Generate a new sonnet
		  sonnet = writeSonnet();
	  
		  // Print the sonnet to the console
		  System.out.println(sonnet);
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
	// Array to hold generated sonnet text
	String[] sonnet;

	// Function to generate a sonnet

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
		
		// Check is sonnet array is null
		if (this.sonnet == null) return;
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
