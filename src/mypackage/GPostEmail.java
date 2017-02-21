/*PAUL RYAN
* 15115305*/
package mypackage;
import java.util.*;

/*The GPostEmail class encapsulates the data for each gpost of the user, it contains the information
* such as the emailOwner of the gpost and the wordcount of that emailOwner, it also holds all the words
* for that user so it can easily be added to*/
public class GPostEmail {
	//the person who owns the email
	private String emailOwner;
	//list to hold all the words for the user
	private List<String> words = new ArrayList<String>();
	//this will hold the wordcount for the user - using case insenstive order
	private Map<String, Integer> wordCount = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
	//Constructor for creating gpostemail ---
	//the wordcount will be created later
	public GPostEmail(String emailOwner, List<String> words){
		this.emailOwner = emailOwner;
		//words= new LinkedList<String>(Arrays.asList(text.split(" ")));
		this.words.addAll(words);
	}

	public String getEmailOwner() {
		return emailOwner;
	}

	public List <String> getWords() {
		return words;
	}
	//useful method to add words to the users list using a string parameter
	public void addToWords(String text){
		words.addAll(Arrays.asList(text.split(" ")));
	}
	//add using a list
	public void addToWords(List<String> newWords){
		words.addAll(newWords);
	}

	//method to generate the final report for the user
	//the method calls createWordCount to ensure that the word count will
	//be up to date
	public void generateReport(){
		createWordCount();
		//I pass true to my sortMapByWordCountDescedning so the words will be sorted descending
		List<Map.Entry<String,Integer>> sortedEntries = sortMapByWordCountDescedning(wordCount ,true);
		System.out.print("{" + emailOwner + " -> " );
		int i = 0;
		for (Map.Entry<String, Integer> entry : sortedEntries){
			if(i == 10){
				System.out.println();
				break;
			}
			System.out.print(" { " + entry.getKey() + " , " + entry.getValue() + " } ");

			i++;
			if(i != 10){
				System.out.print(",");
			}
			else{
				System.out.print("}");
			}
		}
	}
	//method to create the word count for the emailOwner
	private void createWordCount(){
		int count;
		for(String word : words){
			//if the word is not in the map then add it
			if(!wordCount.containsKey(word)){
                wordCount.put(word, 1);
            }
			//else increment the count of the word by 1
            else{
                count = wordCount.get(word);
                wordCount.put(word, count + 1);
            }
		}
	}

	//sort the map by descending or ascending
	private static List<Map.Entry<String, Integer>> sortMapByWordCountDescedning(Map<String, Integer> map, boolean desc){
		//put the map into a list so Collections.sort can be used
		List<Map.Entry<String,Integer>> sortedMap = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		if(desc){
			Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
				@Override
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});
		}
		else{
			Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
				@Override
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
			});
		}

		return sortedMap;
	}

}
