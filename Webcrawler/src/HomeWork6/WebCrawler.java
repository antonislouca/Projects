package HomeWork6;

/**
 * @author Antonis Louca
 * This class contains the runnable object Webcrawler
 * each crawler has a monitor and a variable that is static
 * that helps the crawlers return to main when max depth is reached.
 *
 */
public class WebCrawler implements Runnable {
	private MonitorSingle monitor;

	private static boolean finished = false;
//	private MonitorInner m;

	/**
	 * Constructor of a webcrawler. Creates a webcrawler that can use the monitor given
	 * @param monitor : the monitor given to the crawlers
	 */
	public WebCrawler(MonitorSingle monitor) {
		this.monitor = monitor;

	}

	/**
	 *The runn method that gets a link from the monitor crawls the page and adds the found links
	 *on the todo list using the monitor.addLinks() method
	 *when max depth is reached then the thread exit and return to main.
	 *After each repetition the thread sleeps for 1000 ms.
	 */
	@Override
	public void run() {

		while (!finished) {
			if (monitor.getDepthNow() > monitor.getMaxDepth()) {
				finished = true;
				break;
			}

			Webpage temp = monitor.getlevelLinks();
		
			// System.out.println(Thread.currentThread().getName()+" started Crawling.");
			monitor.addLinks(Webpage.Crawl(temp));
			// System.out.println(Thread.currentThread().getName()+" Finished Crawling.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
		System.out.println(Thread.currentThread().getName() + " done executing, returning to main.");

	}

}
