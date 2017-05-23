package resequencing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RunIt {			
	public static String testStuff() {
		try {
			File file = new File("C:\\Users\\arthu\\workspace\\Genome Resequencing\\src\\resequencing\\rna1.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
			String contents = stringBuffer.toString();
			System.out.println(contents);
			
			return contents;
			
		} catch (IOException e) {
			e.printStackTrace();
			return "SOMETHING WENT WRONG!!";
		}
	}
	public static void main(String[] args) {
		
	}
}
