To run the program:/
go to the GUI directory: ```cd GUI```/
run the program on mac/linux: ```./mvnw spring-boot:run```/
run the program on Windows: ```mvnw.cmd spring-boot:run```/

We chose to build our frontend GUI with JAVA and Spring Boot, as we have prior experience using it from the course 02324 Advanced Programming. 
The TCP socket communication is implemented in the TcpClientService, which is called by the GameController. 
The controller receives the response from the C backend, parses it, and passes it on to our HTML page. 
We use Thymeleaf to dynamically render the game board based on this data. We also use Bootstrap with our HTML page, to make it
easier for ourselves to design it without having to write too much CSS ourselves.

To make it possible to click a card and drop it without needing to type backend C commands, the HTML page is structured entirely as a form. 
Every playable card acts as a radio button, and the drop zones at the bottom of each pile act as submit buttons. 
When clicked, the form sends both the selected card and the destination pile to the server. 
Every time a new command is sent, the page reloads to allow Thymeleaf to render the updated HTML.

There are probably easier ways to achieve this (fx using JavaScript), but we chose to stick with Spring Boot and Thymeleaf to 
build on our existing experience. A lot of the frontend HTML layout and CSS design was created with the assistance of AI 
to ensure it rendered correctly. The core server logic and game implementation were written entirely by us, with AI utilized 
occasionally for discussing design choices and solving complex bugs.