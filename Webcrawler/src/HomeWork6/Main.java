package HomeWork6;
import java.net.*;
import java.util.Scanner;

/**
 * @author Antonis Louca
 *  The main class of the program this class contains the main method that will 
 *  read the user's input and on the root webpage, max depth, and how many
 *  threads the program will run. It will also create the monitor and the threads
 *  that represent the crawlers and start crawling from the root page
 *
 */
public class Main {

	/**
	 * The main method that creates the threads and prints the final catalog of the 
	 * program, when it terminates. All arguments are taken at runtime with Scanner.
	 * If any argument is wrong then the program prints the message of error and 
	 * terminates.
	 * @param args no arguments taken 
	 */
	public static void main(String[] args) {
		System.out.println("<Usage> : What input the program will need:\n"
				+ "1.Enter a valid root page\n"
				+ "2.Enter an integer number for depth\n"
				+ "3.Enter an integer number between 5-10 that represent the threads.\n");
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter root page for scanning:");
		Webpage rootPage = null;
		try {
			rootPage = new Webpage(new URI(scan.next()), 0);
		} catch (URISyntaxException e1) {
			System.out.println(e1.getMessage());
			System.out.println("Enter valid URL.");
			scan.close();
			return;

		}

		System.out.println();
		System.out.print("Enter depth:");
		int depth = scan.nextInt();
		System.out.println();
		System.out.print("Enter number of threads between 5 and 10 threads maximum:");
		int Crawlers = scan.nextInt();
		if(Crawlers<5||Crawlers>10) {
			System.out.println("Wrong number of Crawlers");
			scan.close();
			return;
		}
		System.out.println();
		scan.close();
		
		Webpage.setRoot(rootPage);
		MonitorSingle monitor = new MonitorSingle(depth, rootPage);
		monitor.incLog("\n<Depth>        		 <ThreadID>			<URL> \n");
		System.out.println("Depth now is: " + monitor.getDepthNow());

		Thread t[] = new Thread[Crawlers];

		for (int i = 0; i < Crawlers; i++) {
			t[i] = new Thread(new WebCrawler(monitor), " Crawler " + i);
		}
		for (int i = 0; i < t.length; i++) {
			t[i].start();
		}
		for (int i = 0; i < t.length; i++) {
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		monitor.incLog("\nPages found but not Crawled as max depth reached.\n");
		monitor.printRestTodo();
		System.out.println(monitor.getLog());
		

	}
}
