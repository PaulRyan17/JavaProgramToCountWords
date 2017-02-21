/*PAUL RYAN
* 15115305*/
package mypackage;

public class MainDriver {
	//path to the gpost file
	private static final String emailPath = "C:/Users/Paul/Desktop/gPostMessages.txt";
	//path to the noise words
	private static final String noiseWordsPath = "C:/Users/Paul/Desktop/noisewords.txt";

	public static void main(String [] args){
		GPostIO.analyse(emailPath, noiseWordsPath);
	}
}
