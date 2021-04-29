/**

@mainpage Simple DES algorithm encryption implementation

@author Antonis Louca 
	Copyright (C) 2021 Antonis Louca 
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
    at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.
    Υou should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.	

	The program implements a simplefied version of DES encryption algorythm
    Given a given string of 8 characters representing 8 bits and a string of 10
    characters representing a 10 bit key.
    Following below steps:
    1)performs an initial permutation of the given plain text:Diffusion

    2)Following the key scheduling process calculates the 8bit key for the
      first round

    3)Then the function process follows below 4 steps to form an output
        a)Expansion of right part of the plain text
        b)expanded text xored with Ki (key of ith round)
        c)S box procedure substitution, Confusion of the text
            also supresses the result back to 4 bits
        d) p4 final permutation Diffusion
        
    4)left side of plaintext is xored with function's result 

    5)left side gets swapped with right side on the first round only. 
	
	Correct Usage:
	Mode 1: Running with no options, the program runs with  default parameters 
           (plaintext:01010001,key:0101001100)

    Mode 2: 2 parameters:
			<8-bit plaintext> <10-bit key>

*/