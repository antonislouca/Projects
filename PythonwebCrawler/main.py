from monitor import *
import threading
import collections
import crawler


def main():
    print("Crawler start: ")
    url = input("insert url: ")
    crawlerNum = int(input("Set crawler number: "))
    depth = int(input("Set maximum depth: "))
    lock = threading.RLock()
    q = [webpage(url, 0, url)]
    m = monitor(q, crawlerNum, depth, lock,
                set(), threading.Condition(lock=lock), threading.Condition(lock=lock), False)
    threads = []
    for i in range(0, crawlerNum):
        threads.append(threading.Thread(
            target=crawler.crawlerFunction, args=(m, i, url)))
        threads[i].start()

    for i in range(0, crawlerNum):
        threads[i].join()


if __name__ == "__main__":
    main()
