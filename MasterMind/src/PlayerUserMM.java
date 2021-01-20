
/* Author:Antonis Louca  
 * Written:27/10/2018
 * Last updated:28/10/2018
 * Compilation:javac PlayerUserMM
 * Execution: java PlayerUserMM <max tries>
 * Description:It randomly calculates a 4 digit number between 0000-9999 then the game starts .
 * The computer asks from  the user to guess the number that it randomly generated .Asking the user a 
 * 4 digit combination it answers  back with a fullmatch if the user guessed a number in a correct place 
 * and with a partial match if the user found a number from the combination.The game goes to the user if he manages to guess
 * the combination before his tries run out if, not the game  goes to the computer.
 * 
 * */
import java.util.Scanner;

public class PlayerUserMM {
	private static int[][] GUESSES = new int[30][4];
	private static int GCount = 0;

	public static void Play(int tries) {
		System.out.println("\n\nA GAME BEGINS: The User does the guessing!!\n");
		GCount = 0;
		Scanner scan = new Scanner(System.in);
		String hnumber = "";
		for (int i = 0; i < 4; i++) {
			hnumber = hnumber + (int) (Math.random() * 10);
		}
		int fullmatch = 0, partialm = 0;
		while ((GCount < tries) && (fullmatch != 4)) {
			fullmatch = 0;
			partialm = 0;
			if (GCount > 0) {
				boolean check = true;
				do {
					check = true;
					System.out.print("Enter your guess:");
					for (int i = 0; i < 4; i++) {
						GUESSES[GCount][i] = scan.nextInt();
					}
					for (int j = GCount - 1; j >= 0; j--) {
						int pl = 0;
						if (GUESSES[GCount][0] == GUESSES[j][0]) {
							for (int c = 0; c < 4; c++) {
								if (GUESSES[GCount][c] == GUESSES[j][c]) {
									pl++;
								} else {
									break;
								}
							}
						}
						if (pl == 4) {
							check = false;
							break;
						}

					}
				} while (!check);
			} else {
				System.out.print("Enter your guess:");
				for (int i = 0; i < 4; i++) {
					GUESSES[GCount][i] = scan.nextInt();
				}
			}
			int pl = 0, c = 0;
			boolean exists[] = new boolean[4];
			for (int i = 0; i < 4; i++) {
				String s = "" + hnumber.charAt(c);
				if (GUESSES[GCount][i] == Integer.parseInt(s)) {
					fullmatch++;
					pl++;
					exists[i] = true;
				}
				c++;
			}
			if (fullmatch != 4) {
				int table[] = new int[4 - pl];
				c = 0;
				for (int i = 0; i < 4; i++) {
					if (!exists[i]) {
						table[c] = GUESSES[GCount][i];
						c++;
					}
				}
				c = 0;
				for (int i = 0; i < exists.length; i++) {
					String s = "" + hnumber.charAt(c);
					if (!exists[i]) {
						for (int j = 0; j < table.length; j++) {
							if (table[j] == Integer.parseInt(s)) {
								partialm++;
							}
						}
					}
					c++;
				}
			}
			System.out.println("Full match=" + " " + fullmatch + " " + "Partial match=" + " " + partialm);
			GCount++;
		}
		if ((fullmatch == 4) && (GCount < tries)) {
			System.out.println("Well done!You WON the game!!!");
			PlayMasterMind.WIN = true;
		} else {
			System.out.println("You lost the game!" + "" + "The hidden number was:" + "  " + hnumber);
			PlayMasterMind.WIN = false;
		}
	}

	public static void main(String[] args) {
		GCount = 0;
		System.out.println("***** WELCOME TO MASTER MIND *****");
		System.out.println("\n\nThis is a standalone game where ");
		System.out.print("the User is guessing\n");
		Play(Integer.parseInt(args[0]));
	}
}
