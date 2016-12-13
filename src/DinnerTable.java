import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.Scanner;

public class DinnerTable {

	public String name;
	public ArrayList<int[]> secrets;

	public DinnerTable(String name) {
		this.name = name;
		this.secrets = new ArrayList();
	}

	public String toString() {
		return name;
	}

	public void receiveSecret(int[] bits) {
		secrets.add(bits);
	}

	public int xorSecrets(int index, boolean theBool) {
		int i1 = secrets.get(0)[index];
		int i2 = secrets.get(0)[index];

		int result = i1 ^ i2;
		if (theBool) {
			if (result == 1) {
				result = 0;
			} else {
				result = 1;
			}
		}
		return result;
	}

	public int xorSecretsEx(int index, int i1, int i2, boolean theBool) {

		int result = i1 ^ i2;
		if (theBool) {
			if (result == 1) {
				result = 0;
			} else {
				result = 1;
			}
		}
		return result;
	}

	public int[] hexStringToBits(String hex) {
		System.out.println("HEX STRING TO BITS");
		int i = Integer.parseInt(hex, 16);
		String bin = Integer.toBinaryString(i);
		System.out.println("Bin: " + bin);
		char[] charArray = bin.toCharArray();
		if (charArray.length < 16) {
			int lengthDiff = 16 - charArray.length;
			char[] charArrayN = new char[16];
			for (int j = 0; j < lengthDiff; j++) {
				charArrayN[j] = '0';
			}
			for (int k = 0; k < (16 - lengthDiff); k++) {
				charArrayN[k + lengthDiff] = charArray[k];
			}
			charArray = charArrayN;
		}
		System.out.println(charArray.length);
		int[] intArray = new int[16];
		for (int j = 0; j < 16; j++) {
			int x = Character.getNumericValue(charArray[j]);
			System.out.println("x: " + x);
			System.out.println("char: " + charArray[j]);
			intArray[j] = x;
		}
		return intArray;
	}

	public int[] create16BitSecret(DinnerTable dt) {
		Random rand = new Random();
		int[] bits = new int[16];
		for (int i = 0; i < 16; i++) {
			if (rand.nextInt(2) == 0) {
				bits[i] = 1;
			} else {
				bits[i] = 0;
			}
		}
		System.out.println(name + " creates secret with " + dt);
		for (int i = 0; i < 16; i++) {
			System.out.print(bits[i]);
		}
		dt.receiveSecret(bits);
		System.out.println("------");
		return bits;
	}

	public static void main(String[] args) {
		DinnerTable you = new DinnerTable("You");
		DinnerTable alice = new DinnerTable("Alice");
		DinnerTable bob = new DinnerTable("Bob");
		you.create16BitSecret(alice);
		you.create16BitSecret(bob);

		alice.create16BitSecret(you);
		alice.create16BitSecret(bob);

		bob.create16BitSecret(you);
		bob.create16BitSecret(bob);

		Scanner scan = new Scanner(System.in);
		System.out.print("SA: ");
		String SA = scan.next();
		int[] saArr = you.hexStringToBits(SA);

		System.out.print("SB: ");
		String SB = scan.next();
		int[] sbArr = you.hexStringToBits(SB);

		System.out.print("DA: ");
		String DA = scan.next();
		int[] daArr = you.hexStringToBits(DA);

		System.out.print("DB: ");
		String DB = scan.next();
		int[] dbArr = you.hexStringToBits(DB);

		System.out.print("M: ");
		String M = scan.next();
		int[] mArr = you.hexStringToBits(M);

		System.out.print("b: ");
		int b = scan.nextInt();

		int[] message = new int[] { 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 0 };
		int[] reconstructedMessage = new int[16];
		int youBroadcast;
		int[] myTotalBroadcast = new int[16];

		if (b == 1) {
			for (int i = 0; i < 16; i++) {
				int aliceBroadcast = daArr[i];
				int bobBroadcast = dbArr[i];
				if (mArr[i] == 0) {
					youBroadcast = you.xorSecretsEx(i, saArr[i], sbArr[i],
							false);
				} else {
					youBroadcast = you
							.xorSecretsEx(i, saArr[i], sbArr[i], true);
				}
				int step1 = aliceBroadcast ^ bobBroadcast;
				int step2 = step1 ^ youBroadcast;
				reconstructedMessage[i] = step2;
				myTotalBroadcast[i] = youBroadcast;
			}
		} else {
			for (int i = 0; i < 16; i++) {
				int aliceBroadcast = daArr[i];
				int bobBroadcast = dbArr[i];
				youBroadcast = you.xorSecretsEx(i, saArr[i], sbArr[i], false);
				System.out.println("a:" + aliceBroadcast + ", b: "
						+ bobBroadcast + ", you: " + youBroadcast);
				int step1 = aliceBroadcast ^ bobBroadcast;
				int step2 = step1 ^ youBroadcast;
				reconstructedMessage[i] = step2;
				myTotalBroadcast[i] = youBroadcast;
			}
		}

		// for(int i = 0; i<16; i++){
		// int aliceBroadcast = alice.xorSecrets(i, false);
		// int bobBroadcast = bob.xorSecrets(i, false);
		// int youBroadcast;
		// if(message[i] == 0){
		// youBroadcast = you.xorSecrets(i, false);
		// }
		// else{
		// youBroadcast = you.xorSecrets(i, true);
		// }
		// int step1 = aliceBroadcast ^ bobBroadcast;
		// int step2 = step1 ^ youBroadcast;
		// reconstructedMessage[i] = step2;
		// }
		System.out.print("My broadcast: ");
		String strMyBroadcastBin = "";
		for (int i = 0; i < 16; i++) {
			System.out.print(myTotalBroadcast[i]);
			strMyBroadcastBin = strMyBroadcastBin + myTotalBroadcast[i];
		}
		String strMessage = "";
		System.out.println("");
		System.out.print("Message: ");
		for (int i = 0; i < 16; i++) {
			System.out.print(reconstructedMessage[i]);
			strMessage = strMessage + reconstructedMessage[i];
		}

		System.out.println("");
		System.out.print("My hex broadcast: ");
		System.out.print(Integer.toHexString(Integer.parseInt(
				strMyBroadcastBin, 2)));
		String hexBroadcast = Integer.toHexString(Integer.parseInt(
				strMyBroadcastBin, 2));
		
		while (hexBroadcast.length() < 4) {
			hexBroadcast = "0" + hexBroadcast;
		}

		System.out.println("");
		System.out.print("My hex message: ");
		String hexMessage = Integer
				.toHexString(Integer.parseInt(strMessage, 2));

		// One single zero should really be FOUR zeroes
		// because it ignores least significant bits
		while (hexMessage.length() < 4) {
			hexMessage = "0" + hexMessage;
		}

		System.out.print(hexMessage);

		String theAnswer = hexBroadcast;
		if (b == 0) {
			theAnswer = theAnswer + hexMessage;
		}

		System.out.println("");
		System.out.println("Answer this: " + theAnswer);
		//
		// int[] arr = you.hexStringToBits("75F5");
		// System.out.print("Array: ");
		// for(int i = 0; i<16; i++){
		// System.out.print(arr[i]);
		// }
	}

}
