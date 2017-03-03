package bitManipulation;

import java.util.Stack;

public class BitManipulation {

	public BitManipulation() {}
	
	public static String bit2string(long data, int bits)
	{
		String result = "";
		for (int i = bits - 1; i >= 0; --i) {
			result  = result + (((data >>> i) & 0x01) == 1 ? '1' : '0');
		}
		return result;
	}
	
	public static byte left(byte _byte){
		return (byte) ((_byte >>> 4) & 0x0F); 
	}
	
	public static byte right(byte _byte){
		byte b = (byte) (_byte << 4);
		return (byte) ((b >>> 4) & 0x0F); 
	}
	
	public static byte sixbits(byte _byte){
		return (byte) ((_byte >>> 2) & 0x3F); 
	}
	
	public static short lrswap(short _short){
		short left = (short) ((_short & 0x00FF) << 8);
		short right = (short) ((_short & 0xFF00) >>> 8);
		return (short) (left | right);
	}
	
	public static byte extender(byte _byte) {
		byte ms = (byte) ((_byte << 2) & 0xC0);
		byte mid = (byte) (~(_byte) & 0x0C);
		byte mid2 = (byte) ((byte) mid << 2);
		byte ls = (byte) (_byte & 0x03);
		return (byte) (ms | mid | mid2 | ls);
	}
	
	public static byte keyextractor(short _key, int _pos){
		byte result;
		byte ls, rs;
		int help = 16 - _pos;
		byte bin = 0x00, bin2 = 0x00;
		if (_pos <= 8) {result = (byte) ((byte) (_key >>> (8 - _pos)) & 0xFF);}
		else {
			switch (help) {
			 	case 1: bin = 0x01; bin2 = 0x7F; break;
			 	case 2: bin = 0x03; bin2 = 0x3F; break;
			 	case 3: bin = 0x07; bin2 = 0x1F; break;
			 	case 4: bin = 0x0F; bin2 = 0x0F; break;
				case 5: bin = 0x1F; bin2 = 0x07; break;
				case 6: bin = 0X3F; bin2 = 0x03; break;
				case 7: bin = 0X7F; bin2 = 0x01; break;
			default: break;
			}
			ls = (byte) ((_key & bin) << (8 - help));
			rs = (byte) ((_key >>> (8 + help)) & bin2);
			result = (byte) (ls | rs);
		}
		return result;
	}
	
	public static Stack<String> simpleDES(String input){
		
		int inputStrSize;
		String[] inputStrArray;
		byte[] byteArray = input.getBytes();
		int bytePos = 0;
		String binaryStr = "";
		Stack<String> byteStack = new Stack<String>();
		
		if (input.length() % 3 == 0) {inputStrSize = input.length() / 3;}
		else inputStrSize = (input.length() / 3) + 1;
		inputStrArray = new String[inputStrSize];
		
		for(int i = 0; i < inputStrArray.length; i++){
			int lastChar = 3;
			if(input.length() < 3){lastChar = input.length();}
			inputStrArray[i] = input.substring(0, lastChar);
			input = input.substring(lastChar);
			System.out.println("inputStrArray[" + i + "]: " + inputStrArray[i]);

			// temporary bin to store bytes from string array
			byte[] bin = inputStrArray[i].getBytes();
			
			// loop 3 character segment and add bin position to continuous string
			for(int j = 0; j < bin.length; j++){
				byteArray[bytePos] = bin[j];
				System.out.println("byteArray[" + bytePos + "]: " + bit2string(byteArray[bytePos], 8));
				binaryStr += (bit2string(byteArray[bytePos], 8));
				bytePos++;
			}
		}
		
		// scan for 12 character segments
		// if not mod 12, pad end with 0's
		if(binaryStr.length() % 12 != 0){
			binaryStr = binaryStr + (new String(new char[12 - binaryStr.length() % 12]).replace("\0", "0"));
			System.out.println("\npadded binary string: " + binaryStr);
		}
		
		// break continous string into 12-bit segments
		// push 12-bit segments onto stack
		for(int i = 0; i < binaryStr.length(); i++){
			int endChar = 12;
			String stringfinal = (binaryStr.substring(0, endChar));		
			long bytetest = (long) Integer.parseInt(stringfinal, 2);	
			byteStack.push(bit2string(bytetest, 12));
			binaryStr = binaryStr.replace(binaryStr.substring(0, endChar), "");
		}
		
		System.out.println("stack: " + byteStack);
		return byteStack;
	}
	
	public static void decryptDES(Stack<String> inputStack){
		String encryptedString = "";
		String decryptedString = "";
		int charBin = 0;
		
		final int inputSize = inputStack.size();
		
		for(int i = 0; i < inputSize; i++){
			encryptedString = inputStack.pop() + encryptedString;
		}
		
		for(int i = 0; i < encryptedString.length() / 8; i++){
			charBin = Integer.parseInt(encryptedString.substring(i*8, (i+ 1) * 8), 2);
			decryptedString += new Character((char)charBin).toString();
			System.out.println(charBin + " " + decryptedString);	
		}
		
	}

	public static void main(String[] args) {

		Stack<String> cipher = simpleDES("converted");
		decryptDES(cipher);
	}
}