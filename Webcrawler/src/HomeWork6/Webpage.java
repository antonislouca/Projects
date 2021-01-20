package HomeWork6;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.*;

/**
 * @author Antonis Louca
 * 	This class represents the webpage in the program that consists of a uri
 * and an integer number that represents the depth of the page.
 * The class also has a static field that is the root page set by main function
 * of the program. Is the same page that the use gives in the beginning.
 * The class contains a constructor that give a uri and and integer creates
 * the webpage. Getter methods for the uri and the depth,  toString and 
 * equals overridden methods and a static method that Crawls the webpage 
 * given and extracts all links found in the htlm file.
 */
public class Webpage {
	private static Webpage rootpage;
	private URI url;
	private int depth;

	/**
	 * The constructor of a webpage
	 * @param u : the uri of the webpage
	 * @param d : the integer that represents the depth of the webpage
	 */
	public Webpage(URI u, int d) {
		this.url = u;
		this.depth = d;
	}

	/**
	 * A getter method of the uri of the webpage
	 * @return : the uri to be returned
	 */
	public URI getUri() {
		return url;
	}

	/**
	 * A getter method for the depth of the webpage
	 * @return : The depth to be returned
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * A setter method that sets the static filed that represents 
	 * the root webpage.
	 * Should only be used once at the start of the program.
	 * @param s : the webpage that will represent the root page during the 
	 * 			crawling
	 */
	public static void setRoot(Webpage s) {
		rootpage = s;
	}

	/**
	 * A static method that given a webpage will crawl inside the htlm file and find 
	 * the webpages that are in the htlm and create new ones with depth+1. This method
	 * returns an array list of consisting of the webpages found.
	 * When a connection is achieved it reads all the htlm into a string and then closes
	 * the connection. If connection could not be established or any other read time our 
	 * error occurs an according message is printed and an empty list is returned.
	 * This method skips .apk links as it can cause the thread to be stuck.
	 * It splits the string and extracts the urls to create the webpage but first it
	 * replaces any spaces with %20 and encodes the query part of the url if it exists.
	 * after resolving and normalizing the uri it creates the webpage of that particular 
	 * url. If the webpage given is null then an empty list is returned.
	 * If the webpage is not in the root page (thsi is checked using the getAuthority method
	 * from the URI class ), an empty list is returned.
	 * NOTE: Read timeout and Connection timeout are set 30000 and 300000 respectively
	 * @param s : The webpage to be crawled
	 * @return : An array list that contains the new webpages found.
	 */
	public static ArrayList<Webpage> Crawl(Webpage s) {
		ArrayList<Webpage> l = new ArrayList<Webpage>();

		if (s == null)
			return l;
		String url = "";
		int index = 0;
		int to = 0;
		BufferedReader br;

		try {
			if (s.getUri().toString().endsWith(".apk"))
				return l;
			if (!s.getUri().getAuthority().equals(rootpage.getUri().getAuthority())) {
				System.out.println("\n" + s.getUri() + " Not in root, so wont be crawled.");
				return l;
			}
			HttpURLConnection connection = (HttpURLConnection) s.getUri().toURL().openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);

			connection.connect();
			if (connection.getResponseCode() != 200) {
				System.out.println("\nSite: " + s.getUri().toString() + " not responding.");
				return l;
			}

			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String page = "";

			while ((url = br.readLine()) != null) {
				page = page.concat(url);
			}
			page = page.trim();
			String Raw = page.toString();
			page = page.toLowerCase().replaceAll("\\s", " ");
			
		//	System.out.println("page:"+page);
			br.close();
			connection.disconnect();
			while ((index = page.indexOf("<a ", index)) != -1) {
				if ((index = page.indexOf("href", index)) == -1)
					// no href found
					break;
				if ((index = page.indexOf("=", index)) == -1)
					// no equal url found
					break;

				if ((to = page.indexOf("\"", index = index + 2)) == -1)
					break;
				url = Raw.substring(index, to);
				url=url.trim();
				// replace spaces
				if (url.contains(" "))
					url = url.replaceAll(" ", "%20");

				// check query
				if (url.contains("?")) {
					int index3 = url.indexOf('?');
					String query = url.substring(index3 + 1);
					try {
						query = URLEncoder.encode(query, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					url = url.substring(0, index3) + '?' + query;

				}

				// check if url ends with /
				if (url.endsWith("/")) {

					int index2 = url.lastIndexOf('/');
					url = url.substring(0, index2);

				}

				URI temp;
				try {
					temp = s.getUri().resolve(url).normalize();
				} catch (Exception e) {
					continue;
				}

				// add webpage to the list
				l.add(new Webpage(temp, s.getDepth() + 1));
			}

			return l;
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ConnectException e) {
			System.out.println(Thread.currentThread().getName() + " Connection Error: This site can't be reached.\n");
			return new ArrayList<Webpage>();
		} catch (SocketException e) {
			System.out.println(Thread.currentThread().getName() + " Read timed out.");
			return new ArrayList<Webpage>();
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " There was a problem with this site.");
			return new ArrayList<Webpage>();
		}
		return new ArrayList<Webpage>();
	}

	/**
	 *Returns a printable form of the webpage
	 */
	public String toString() {
		return this.depth + "						" + this.url;
	}

	/**
	 *A method that compares two webpages
	 *@param Object  to be compared
	 */
	@Override
	public boolean equals(Object temp) {
		if (!(temp instanceof Webpage)) {
			return false;
		}
		Webpage temp2 = (Webpage) temp;

		return this.url.equals(temp2.url);
	}

}
