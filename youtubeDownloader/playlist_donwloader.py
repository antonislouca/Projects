from pytube.contrib.playlist import Playlist
from pytube import YouTube
from pytube.cli import on_progress
from pathlib import Path
import os
# print(Path.home())
# url = input("Enter Playlist URL: ")
result_dir = f'{str(Path.home())}/Desktop/videos/'

if not os.path.isdir(result_dir):
    os.mkdir(result_dir)
playlists = ['https://www.youtube.com/watch?v=2Y7eyyFi6KQ&list=PL_EFQ32UJ6ZuoNOREZTajBBU_zErhzAi9',
             'https://www.youtube.com/watch?v=g4Z353jBwsc&list=PLMJ78NDkO-sMS7aSBg-X_NoVIDQmDVyIb', 'https://www.youtube.com/watch?v=BrPv9hRhlQg&list=PLEJ8Ygunw55eSvFfppUBAv87DJS6tPdA1', '']
directories = ['Thapse', 'Cooking', 'Broscar', 'Standup']

for i, pl in enumerate(playlists):
    playlist = Playlist(pl)
    print("Total Videos: ", len(playlist.video_urls),
          'for playlist: ', directories[i])
    if not os.path.isdir(f'{result_dir}/{directories[i]}'):
        os.mkdir(f'{result_dir}/{directories[i]}')

    for video_url in playlist.video_urls:
        yt = YouTube(video_url, on_progress_callback=on_progress)
        stream = yt.streams.get_highest_resolution()
        print(yt.title)
        filedest = f'{result_dir}/{directories[i]}/{yt.title}.mp4'
        stream.download(
            filename=filedest)
