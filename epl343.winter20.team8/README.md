# epl343.winter20.team8

/**

ATOS HelpDesk System

Project Status:
- This is the first prototype of the program and we plan on further expanding
  its capability it.

Description:
- This application is meant to automate the ticketing handling procedure of 
  ATOS Company. It also provides additional features like viewing the 
  FAQ, Policies and editing submitted tickets.
  Specifically, the system allows an admin to handle and edit tickets,
  and also modify FAQs and Policies. Furthermore, let's an admin add,
  remove, and set a user as admin. The program can also generate a report
  at any time in excel form, that includes the handled tickets of each
  admin. A non-admin user can create a ticket submit it, view the FAQs and
  view the Policies of the company. 

Execution:
You can run the application by running the main method in the class main.
BUT it will not run because you propably dont have the java fx library, jdbc  and poi library in your classpath.
And you have not connected to correct Database.

All that you will need are in the recources folder.
Firstly you extract the recources to a location on your computer.
Then you go to the projects classpath and click on it. 
Press the button that says add library and click on the user defined libraries.
From there you need to click on user Libraries button.

Now you need to create the libraries we need:
Click on the new and add a name and create.
Then when it appears click on add external jars and add all the jars that apear on the recourses. 
When you are done , press apply and close.
Check the library you just created and press finished.

The final step you nedd to make is to go to the main function, right click and click on the run configurations.
From there go to arguents and add the text  ' --module-path "[path you extracted]\javafx-sdk-15\lib" --add-modules ' javafx.controls,javafx.fxml"

And click apply.

Hopefully you can run the program now.


In addtion you will need to change the connection in the authentication model to be the connection to the DataBase you want.
Lastly, that DataBase needs to have the tables that are in the Database package. There you will find scripts to create an identical DB to fully ustilizes (and firstly log in of cource).

You can also whenever a function is called from the models (DB connectors) replace the result with a dummy result you want in the format of the item it return and it will work just as fine.


@authors 	
			Andreas Hadjivasili
			Antonis Louca
			Christodoulos Costi
			Stylianos Herodotou
			Konstantinos Christou
			Panikos Christou

Copyright (C) 2020

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

*/
