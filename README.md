# AutocompletingLibrary

A simple autocompleting engine based on a prefix tree.

Example CSV file is built in the project (airports.csv) and you can search rows, having previously chosen by you column starting with a specific value.

A column number is fixed during the start of the application (you can choose from 1 to 14).

## Steps for launching the application

1) Clone the project or download ZIP using GitHub
2) Open the terminal
3) Go to the project folder ("AutocompletingLibrary-master"), rename it if you desire
4) Run "mvn clean package" to form .jar

![image](https://user-images.githubusercontent.com/72615475/185341545-aca2ff1b-4118-476a-a010-0800b7f91b27.png)

5) Change directory to "target" (relative to the project folder)

![image](https://user-images.githubusercontent.com/72615475/185341732-d7b5aebc-5e8b-41f7-8db9-fe6282182fcd.png)

6) Run the application by typing "java -jar airports-search-*.jar" and add a correct column number. Otherwise the application will give you an error.

![image](https://user-images.githubusercontent.com/72615475/185341936-b98fa1ee-cb6a-4e7f-833a-1ff42f9943ba.png)

7) Type in the terminal the prefix, for example "Bo", and then press ENTER. 


You will get results in such form: <search-result>[<whole-row-from-the-file>]. After you will see the quantity of results and the time of search in ms.

![image](https://user-images.githubusercontent.com/72615475/185342645-0267e025-996a-4956-81aa-036158f0bbbc.png)

Pay attention that you cannot search results with empty prefix (empty or containing only spaces)!

![image](https://user-images.githubusercontent.com/72615475/185343490-d7f8d58e-2c46-4049-903d-2b978f35b44c.png)

8) Either type in "!quit" and press ENTER to quit the application or just press ENTER to begin the search again (step 7).

