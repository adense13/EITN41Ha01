
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class B01 {
	
	static class Luhn{
		
		public Luhn(){
			
		}
		
		public static List<Integer> getDigitsArray(String s){
			return s.chars().mapToObj(i -> (char) i)
                   .map(Character::getNumericValue)
                    .collect(Collectors.toList());
		}
		
		public static List<Integer> getDigitsArray(int nbr){
			return getDigitsArray(Integer.toString(nbr));
		}
		
		public static int sumDigits(List<Integer> list){
			int temp = 0;
			for(int i = 0; i < list.size(); i++){
				temp = temp + list.get(i);
			}
			return temp;
		}
		
		public static void run(String[] s){
			for(int i = 0; i<s.length; i++){
				run(s[i]);
			}
		}
		
		
		public static String run(String str){
			List<Integer> l = getDigitsArray(str);
			//System.out.println(l);
			//List<Integer> odd = new ArrayList<Integer>();
			int sum = 0;
			boolean isTimeToDouble = false;
			boolean xWasDoubled = false;
			for(int i = l.size()-1; i >= 0; i--){
				int whatWeTurnTheDigitInto = 0;
				System.out.println("Found '"+l.get(i)+"' at index '"+i+"', and double bool is: "+isTimeToDouble);
				if(l.get(i) == 33){
					xWasDoubled = isTimeToDouble;
				}
				else if (isTimeToDouble){
					int digitWeDoubled = l.get(i)*2;
					if(digitWeDoubled > 9){
						sum = sum + (digitWeDoubled-9);//sumDigits(getDigitsArray(digitToDouble));
					}
					else{
						sum = sum + digitWeDoubled;
					}
					whatWeTurnTheDigitInto = digitWeDoubled;
				}
				else{
					sum = sum + l.get(i);
					whatWeTurnTheDigitInto = l.get(i);
				}
				isTimeToDouble = !isTimeToDouble;
				System.out.println("Turned into '"+whatWeTurnTheDigitInto+"'");
				System.out.println("----------------------------");
			}
			int x = 0;
			int temp = 0;
			for(int i = 0; i < 10; i++){
				x = i;
				if(xWasDoubled){
					temp=i*2;
					if(temp>9){
						temp = temp - 9;
					}
				}
				else{
					temp = i;
				}
				if( (sum+temp)%10 == 0 ){
					break;
				}
			}
//			for(int i = 0; i<10; i++){
//				
//			}
			//System.out.println("Sum without X: "+sum);
			System.out.println(x);
			return Integer.toString(x);
			//System.out.println("------------------");
			
		}
		
		
	}
	
	public static void main(String args[]){
		
		//System.out.println(Luhn.getDigitsArray("12774212857X4109"));
		System.out.println(Luhn.run("36X0002030416468"));
		//System.out.println("------OVER--------------------------");
		Scanner scan = new Scanner(System.in);
		String output = "";
		try {
		
			for (String line : Files.readAllLines(Paths.get("list.txt"))) {
			    output = output + Luhn.run(line);
			}
			System.out.println("Output: "+output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//5496331440914992338434701218071555719657419448245128019889398009532562488698540071959143506293615978 rätt
	//5496331440914992338434701919071999719657419448249198019899398009932562488699940071959143506993619978 igår
	//5776331440914772338437701717071777717657719478277178017879398009732562488697770071957143507773617978 idag

}
