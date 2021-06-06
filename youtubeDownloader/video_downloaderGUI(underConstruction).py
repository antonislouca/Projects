from tkinter import *
import requests
from pytube import *
from videos import videos
from pathlib import Path
# Youtube Link: https://www.youtube.com/watch?v=E7LAF63Wa0g&t=314s


def displaySetUp():
    vid = videos(Tk(), StringVar())
    vid.window.geometry('600x300')
    vid.window.resizable(0, 0)
    vid.window.title("custom Youtube Downloader")

    Label(vid.window, text='Youtube Downloader', font='calibry 20 bold').pack()
    Label(vid.window, text='Enter link here:',
          font='calibry 15 bold').place(x=10, y=160)

    return vid


def printInfo(url):
    print("Title: ", url.title)
    print("Views: ", url.views)
    print("Video length: ", url.length, " seconds")
    print("Description: ", url.description)
    print("Ratings: ", url.rating)


def Downloader(vid):
    # when running with no GUI
    # vid.link = input("Enter video link: ")
    vid.link = Entry(vid.window, width=70,
                     textvariable=vid.link).place(x=160, y=166)
    yt_url = YouTube(str(vid.link))

    # prints url/video information
    printInfo(yt_url)
    strr = yt_url.streams
    for elem in strr:
        print(elem)

    audio = input("\nonly audio: ")
    only_audio = False
    if audio == "y":
        only_audio = True

    # set download destination
    setDownloadlocation = input(
        "\nChange Download location, default /Downloads: ")

    dire = str(Path().home()) + ""+"\\Downloads"
    # print(dire)

    # to change download location
    # if setDownloadlocation == "y":  # {
    #     dire = input("\nInsert Download location/full path: ")
    # # }

    if only_audio != True:
        res = input("\nSelect resolution from streams: ")
        video = yt_url.streams.get_by_resolution(res)
    else:
        video = yt_url.streams.get_audio_only()
    # video = url.streams.first()
    print("Downloading...")
    video.download(dire)
    print("Download completed!!!")
    # Label(vid.window, text='DOWNLOADED',
    #       font='calibry 15').place(x=180, y=210)


def main():
    # vid = videos(StringVar())
    vid = displaySetUp()
    Button(vid.window, text="Download", font='calibry 15',
           bg='pale violet red', padx=2, command=Downloader(vid)).place(x=160, y=200)
    vid.window.mainloop()
    # Downloader(vid)

    # TODO THESE ARE FOT THE GUI


if __name__ == "__main__":
    main()
