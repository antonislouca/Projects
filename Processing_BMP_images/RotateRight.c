/*
 *
 * Copyright (C) 2019 Andreas Hadjivasili & Antonis Louca
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * Υou should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @file RotateRight.c
 * Implements a Right Rotation of the image and returns the new image rotated.
 * Created on: Nov 20, 2019
 * Author: Andreas Hadjivasili
 *
 */
#include "bmplib.h"

public IMAGE *Rotate_Right(IMAGE *im){

  int width = im->bmpInfo.biWidth;
  int height = im->bmpInfo.biHeight;
  int pad = im->padding;
  int nw = im->bmpInfo.biHeight;
  int nh =im->bmpInfo.biWidth;
  int newpad = (nw*3)%4;
  int i=0;
  int counter = 0;
  int c = 0;
  int size = (height * width * 3);

  if (newpad!=0)
    size+=(4-newpad)*nh;

  unsigned char *temps = calloc(1,size);
  int start = (nh-1)*(nw)*3;

  if (newpad!=0)
    start = start + (nh-1)*(4-newpad);

  int n = start;

  while (i <  im->bmpInfo.biSizeImage  ) {

    if((counter == width) && (pad!=0) ){
      i = i + (4-pad);
      counter = 0;
    }

    counter ++;
    c++;

    if (n<size) {
    *(temps+n) = im->data[i];
    *(temps+n+1) = im->data[i+1];
    *(temps+n+2) = im->data[i+2];
  }

    if (newpad!=0)
      n = n - (4-newpad);

    i = i +3;

    if (c == nh) {
        start = start + 3;
        n = start;
        c = 0;
        continue;
    }

    n = n - nw*3;

  }

  free(im->data);
  im->data = temps;
  im->bmpInfo.biSizeImage=size;
  im->bmpInfo.biHeight=nh;
  im->bmpInfo.biWidth=nw;
  im->bmpFile.bfSize = size + 54;

  return im;

}


#ifdef DEBUG_ROTATERIGHT
int main(int argc, char const *argv[]) {

  IMAGE *im;
  im = LoadBitmapFile("image7.bmp");
  im = Rotate_Right(im);
  WriteBitmapFile(im,"test7.bmp");
  return 0;
}
#endif
