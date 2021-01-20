/* Author:Antonis Louca
 * Written:27/10/2018
 * Last updated:28/10/2018
 * Compilation:javac PlayerMaterMind
 * Execution: java PlayerMasterMind <max tries><max games>
 * Description:Its the class that connects and organizes the game.It takes as inputs the limited tries and
 * the games that are about to be played between  the user and the computer.Each time it calls a class 
 * either(PlayerComputerMM or PlayerUserMM) a new game begins between the user and the computer where 
 * either the computer or the user have to guess a certain combination.The result from each game is returned 
 * as a win or loss.The wins for the computer and the user are counted and the one who has the most takes the full game.
 * 
 * */
public class PlayMasterMind{
public static boolean WIN = false;
public static void main(String[] args){
 boolean userToPlay = Math.random() < 0.5;
 int tries = Integer.parseInt(args[0]);
 int times = Integer.parseInt(args[1]);
 int userWins = 0, computerWins = 0;
 System.out.println("\n\n***** WELCOME TO MASTER MIND *****");
 System.out.println("\nNumber of Games = " + times);
 System.out.println("\nFor each game the maximum number of guesses is ");
 System.out.print(tries + "\n");
 for (int i = 1; i <= times; i++){
 WIN = false;
 if (userToPlay) {
 PlayerUserMM.Play(tries);
 if (WIN) {userWins++;
 System.out.println("\nThis GAME goes to the User");}
 else {computerWins++;
 System.out.println("\nThis GAME goes to the Computer");}
 userToPlay = false;
 }
 else {
 PlayerComputerMM.Play(tries);
 if (WIN) {computerWins++;
 System.out.println("This GAME goes to the Computer");}
 else {userWins++;
 System.out.println("This GAME goes to the User");}
 userToPlay = true;
 }
 }
 System.out.println("\n\nThe User has won " + userWins);
 System.out.print(" games and the Computer " + computerWins + "\n\n");
}
}