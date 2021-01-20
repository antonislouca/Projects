package HomeWork6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Antonis Louca A class that represents the monitor for the webcrawling
 *         program The class will ensure mutual exclusion for the threads
 *         running and crawling webpages, adding links and removing links from
 *         the todo list and modifying other variables in the monitor. It
 *         consists of the todo list that contains all webpages to be crawled, a
 *         hashset that contains all crawled webpages two condition variables ,
 *         one for when the todo list is empty and one when we need to proceed
 *         to the next depth but not all thread finished so the others have to
 *         wait. An some integers that represent number of webpages is a certain
 *         depth or maxdepth to crawl into, a string to hold the final catalog
 *         to be printed in main, etc.
 *
 */
public class MonitorSingle implements Monitor {
	private final int maxDepth;
	private ArrayList<Webpage> todo;
	private final ReentrantLock lock;
	private final Condition waitinglevel, full;
	private int depthNow;
	private int nextlevel = 0;
	private int out = 0;
	// private ArrayList<String> alreadyCrawled = new ArrayList<String>();
	private HashSet<String> alreadyCrawled = new HashSet<String>();
	private int currentlevel = 1;
	private String log = ""; // the string for the final catalog


	/**
	 * The constructor of the monitor. Creates a monitor given max depth and the
	 * rootpage, to be added to the todo list.
	 * 
	 * @param depth : The max depth the threads can get and crawl into
	 * @param root  : The first webpage that will go into the todo list is the first
	 *              page to crawl and set current depth to zero.
	 */
	public MonitorSingle(int depth, Webpage root) {

		this.maxDepth = depth; // initialize max depth
		todo = new ArrayList<Webpage>();
		todo.add(root);
		this.depthNow = 0;
		this.lock = new ReentrantLock();
		this.waitinglevel = lock.newCondition();
		this.full = lock.newCondition();
	}

	/**
	 * Getter method returns max depth of the monitor.
	 * 
	 * @return : Max depth of the monitor
	 */
	public int getMaxDepth() {
		lock.lock();
		try {
			return maxDepth;
		} finally {
			lock.unlock();
		}

	}

	/**
	 * A getter method returns current depth of the monitor
	 * 
	 * @return current depth of the monitor
	 */
	public int getDepthNow() {
		lock.lock();
		try {
			return depthNow;
		} finally {
			lock.unlock();
		}

	}

	/**
	 * A method the returns the first element of the todo list under some
	 * constrains. Firstly if the to do list is empty thread waits on CV full until
	 * another thread puts new links in the todo list. If the element on the head of
	 * the toto list has a depth not equal to current depth the thread waits on CV
	 * waitinglevel. Note that that depth cant be less than current depth as todo
	 * list is operatated in a FIFO way and so if the depth is not equal to current
	 * depth is automatically a page deeper than current depth which means that we
	 * cant crawl into it until all threads on finish on the same depth. This method
	 * also removes the element and returns it, also adds that list to
	 * alreadycrawled hashset so we won't visit it again. If max depth has been
	 * reached this method returns null, which is a case handled by crawl() method
	 * in Webpage class.
	 * 
	 * @return Webpage at the head of todo list or null if max depth reached
	 */
	public Webpage getlevelLinks() {
		lock.lock();
		try {
			while (todo.isEmpty()) {
				System.out.println("To do list is empty so" + Thread.currentThread().getName() + " waits");

				this.full.await();
				if (this.depthNow > this.maxDepth) {
//					full.signalAll();
//					this.waitinglevel.signalAll();
					return null;
				}

			}

			while (todo.get(0).getDepth() == depthNow + 1) {
				System.out.println("\n"+Thread.currentThread().getName()
						+ " waits for other crawlers to finish and proceed to next level.");
				this.waitinglevel.await();

				if (this.depthNow > this.maxDepth) {
//					full.signalAll();
//					this.waitinglevel.signalAll();
					return null;
				}
				while (todo.isEmpty()) {
					System.out.println("To do list is empty so" + Thread.currentThread().getName() + " waits");
					this.full.await();
					if (this.depthNow > this.maxDepth) {
//						full.signalAll();
//						this.waitinglevel.signalAll();
						return null;
					}
				}

			}
			Webpage temp = todo.remove(0);
			// out++;

			alreadyCrawled.add(temp.getUri().toString());

			System.out.println(Thread.currentThread().getName() + " exploring " + "Depth =  " + temp.getDepth()
					+ " Webpage = " + temp.getUri().toString());

			this.log = this.log + temp.getDepth() + "			" + Thread.currentThread().getName() + "		"
					+ temp.getUri().toString() + "\n";

			return temp;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;

	}

	/**
	 * This method is a method that produces links. When a thread finished crawling
	 * a webpage, adds found links to the todo list with the help of this method.
	 * All links found that are neither in the todo list nor have been visited yet
	 * area added to the todo list. For each link the counter nextlevel is increased
	 * by one. This counter helps keep track of how many links are going to be in
	 * todo of the next level. nextlevel counter is zeroed when next depth starts
	 * and the value goes to currentlevel counter as next depth is now current
	 * depth. Another counter out is increased every time a thread calls this method
	 * this means that a link was taken of the todo list and now this thread comes
	 * with the crawled results of that link. This way if out== currentlevel then
	 * this thread is the one that got the final link of current depth out of todo
	 * list. Thus current depth is finally over and we can signal any thread waiting
	 * on the waitinglevel CV. Out counter becomes zero and current level is now
	 * equal to nextlevel counter which then becomes zero, and current depth is
	 * increased. After each call of this method we signal full CV and wake up all
	 * threads waiting on that variable. If no links were found for the next depth 
	 * then a deadlock can occur as all threads will wait and no thread will actually add 
	 * link for crawling, and all threads will be waiting for someone to signal them
	 * to prevent ,this we increase the depth more than max depth so 
	 * the threads will think they reached max depth and return normally to main.
	 *@param The list with newly found links
	 *
	 */
	public void addLinks(ArrayList<Webpage> l) {
		lock.lock();
		try {

			System.out.println(Thread.currentThread().getName() + " finished exploring.");
			ListIterator<Webpage> elem = l.listIterator();
			while (elem.hasNext()) {
				Webpage temp = elem.next();
				if (!alreadyCrawled.contains(temp.getUri().toString()) && !todo.contains(temp)) {
					todo.add(temp);
					this.nextlevel++;
				}
			}
			out++;
			if (out == currentlevel) {

				System.out.println("\nDepth " + depthNow + " finished moving to next depth.");
				currentlevel = this.nextlevel;
				out = 0;
				this.depthNow++;
				System.out.println("Depth now is: " + this.depthNow+"\n");
				if (this.nextlevel == 0) {
					System.out.println("No webpages found for crawling in depth "+depthNow+" program terminates.\n" );
					this.depthNow = this.maxDepth + 1;
				}
				this.nextlevel = 0;
				this.waitinglevel.signalAll();
			}
			full.signalAll();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Thsi method should only be executed after all threads finished and returned
	 * to main. What this method does is print the rest of the todo list with the
	 * pages that are one depth deeper than max depth these webpages wont be crawled
	 * but were found during crawling so we will print those pages at the end of the
	 * program after the normal catalog is over.
	 */
	public void printRestTodo() {
		lock.lock();
		try {
			if (!todo.isEmpty()) {
				// System.out.println("Found URLs but not crawled.");
				while (!todo.isEmpty()) {
					this.log = this.log + todo.remove(0).toString() + "\n";
				}
			}else {
				this.log = this.log + "todo list is empty no links were found for next depth.\n";
			}
		} finally {
			lock.unlock();

		}

	}

	/**
	 * Returns the log string field of the monitor which basically stores the
	 * catalog that will be printed at the end of the program
	 *
	 * @return the log string that represents the catalog
	 */
	public String getLog() {
		lock.lock();
		try {
			return log;
		} finally {
			lock.unlock();

		}
	}

	/**
	 * Appends on the string that represents the catalog the given string a new line
	 * is added automatically at the end of the string given.
	 * @param String the string to append at the end of log string
	 */
	public void incLog(String logi) {
		lock.lock();
		try {
			log = log + logi + "\n";
		} finally {
			lock.unlock();

		}
	}
}
