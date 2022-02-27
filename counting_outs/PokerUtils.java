public class PokerUtils {
	public static boolean getBit(int input, int position)
	{
   		return (((input >> position) & 1) == 1);
	}

}