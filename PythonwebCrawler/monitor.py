import threading
import dataclasses


@dataclasses.dataclass(init=True, eq=True, frozen=True)
class webpage:
    url: str
    depth: int
    rootpage: str


"""
represents the monitor clas that will hold queue with webpages to
be crawled
"""


class monitor:
    def __init__(self, queue, numofcrawlers: int, depth: int, lock, alreadyCrawled, waiting_level: threading.Condition, full: threading.Condition, finished: bool):
        self.queue = queue
        self.numofcrawlers = numofcrawlers
        self.depth = depth
        self.lock = lock
        self.alreadyCrawled = alreadyCrawled
        self.waiting_level = waiting_level
        self.full = full
        self.finished = finished
        # will be used to implement a pseudo barrier
        self.currentDepth = 0
        self.pagesInCurrentDepth = 1
        self.nextDepth = 0
        self.outDepth = 0

    def removeFromQueue(self, ID):
        self.lock.acquire()
        try:
            # print("Crawler: ", ID,
            #       "QUEUE: ", self.queue, '\n')

            while self.queue.__len__() == 0:
                print("To do list is empty thread",
                      ID, "waits\n")
                self.full.wait()
                if int(self.currentDepth) > self.depth:
                    exit()  # thread exits when queue is empty and max depth <current depth

            # wait on a barrier when current depth is not finished but next page in qeueu
            # belongs to next depth
            print(self.queue[0].depth)
            while self.queue[0].depth == (int(self.currentDepth)+1):
                print("Crawler: ", ID,
                      "waits for other crawlers to finish current depth links.\n")
                self.waiting_level.wait()

                while self.queue.__len__() == 0:
                    print("To do list is empty thread",
                          ID, "waits\n")
                    self.full.wait()
                    if int(self.currentDepth) > self.depth:
                        exit()

            elem = self.queue.pop(0)
            print("Crawler: ", ID,
                  "exploring webpage: ", elem.url, "in depth: ", elem.depth, '\n')

            return elem
        finally:
            self.lock.release()

    def addToQueue(self, foundpages, ID):
        self.lock.acquire()
        try:
            print("Crawler: ", ID, "finished exploring\n")
            for page in foundpages:
                # if already crawled skip page
                if page in self.alreadyCrawled:
                    continue
                # add new page and increament nextDepth
                self.queue.append(page)
                self.nextDepth += 1
            self.outDepth += 1

            if self.outDepth == self.pagesInCurrentDepth:
                print("Depth: ", self.currentDepth,
                      "finished moving to next depth\n")

                self.pagesInCurrentDepth = self.nextDepth
                self.currentDepth += 1
                print("Current depth", self.currentDepth)

                if self.nextDepth == 0:  # now pages found for next depth need to quit all threads
                    print("No pages found for next depth.\n")
                    self.currentDepth = self.depth+1

                self.nextDepth = 0
                self.waiting_level.notifyAll()

            self.full.notifyAll()
        finally:
            self.lock.release()

    def check_finished(self):
        self.lock.acquire()
        try:
            return self.finished
        finally:
            self.lock.release()

    def set_finished(self):
        self.lock.acquire()
        try:
            self.finished = True
        finally:
            self.lock.release()
