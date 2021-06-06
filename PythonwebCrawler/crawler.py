from monitor import monitor
import threading
import time
import requests
from bs4 import BeautifulSoup
from monitor import webpage


class crawler(threading.Thread):

    def __init__(self, monitor, ID: int):
        self.ID = ID
        self.monitor = monitor


def crawlerFunction(monit: monitor, ID: str, rootpage: str):
    print("i am crawler with number: ", ID)
    print("monitor current depth", monit.currentDepth)
    # TODO check depth
    # TODO GET FIRST URL
    # TODO CRAWL IN THE SITE
    # TODO ADD FOUND URLS IN MONITORS QUEUE
    while monit.check_finished() == False:
        if monit.depth == int(monit.currentDepth):
            monit.set_finished()
            print("crawler: ", ID, " finshed.")
            return
        url = monit.removeFromQueue(ID)
        foundpages = crawl_Page(url, rootpage)
        monit.addToQueue(foundpages, ID)
        time.sleep(1)
    # repeat loop untill finla depth is reached


# will extract URLs from given webpage
def crawl_Page(url, rootpage):
    webpages_found = []
    page = str(url.url)
    if page.endswith(".apk"):
        return webpages_found
    if page.startswith("http") == False:
        page = str(rootpage)+page
        # print(page)
    reqs = requests.get(url.url)
    soup = BeautifulSoup(reqs.text, "html.parser")
    # print(soup)
    for link in soup.find_all('a'):
        print("\nLINK\n", link)
        webpages_found.append(webpage(link.get('href'), url.depth+1, rootpage))
    return webpages_found
