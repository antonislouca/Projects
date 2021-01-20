package HomeWork6;

import java.util.ArrayList;

/**
 * @author Antonis Louca 
 * The interface of the monitor of the multithreaded webcrawler program
 *
 */
public interface Monitor {
	public int getMaxDepth();

	public int getDepthNow();

	public Webpage getlevelLinks();

	public void addLinks(ArrayList<Webpage> l);

	public void printRestTodo();

	public String getLog();

	public void incLog(String logi);
}
