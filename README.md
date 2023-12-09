# Proiect GlobalWaves
### Copyright Carauleanu Valentin Gabriel 321CA

## Structure of the project

* I chose to use the solution ```app```, offered by the team as it gave the project a more cohesive structure than my own
* ```audio```: contains the audio files used for the project, as well as the new ```Album``` class
* ```pagination```: contains the page functionality for the project
* ```player```: contains the newly updated player and it now supports Albums
* ```searchBar```: contains the search functionality for the project and it now supports host and artist searches
* ```user```: contains the 3 types of users and their functionality
  * ```utils```: contains the user support classes such as ```Merch```, ```Announcement``` and ```Event```
* ```utils``` : contains enums used for the project
* ```Admin``` : utility class for admin functionalities
* ```app.CommandRunner``` : utility class for app.command line functionalities

## Implementation

* The pagination of the project is based around the Page abstract class, which allows the updatePage and clearPage methods to be implemented in the child classes
* Each time a user accesses a page, the page is either instantiated or updated. The pages don't exist outside of the user's session and each time a user re-accesses a page, it is re-instantiated
* There are 2 new types of users: ```Artist``` and ```Host``` that allows them to modify the content of the library itself by adding or removing their own content
* The programme now supports the removal of users and all their interactions with the other users.
* In order to successfully implement the ```deleteUser``` functionality, we must first check if anyone is listening to the content of the user that is to be deleted. If so, the user cannot be deleted. Otherwise we remove all the follows and likes the user ever gave, delete the playlists he created and remove the content he uploaded from the library, as well as from the playlists of all other users.
* In order to efficiently print the content of the pages, I chose to overwrite the ```toString``` method of the ```Page``` , ```Album```, ```Song```, ```Merch```, ```Event```, ```Announcement``` and  ```Playlist``` classes
* Regarding the design patterns, I chose to use a singleton with lazy initialization for the ```Admin``` class, and since in the next phase of the project we may get more types of pages and more operations on them, I chose to use the Visitor design pattern for the ```Page``` class, as it allows us to add new operations without modifying the classes themselves.
* I also chose to restructure the ```CommandRunner``` and I created a new abstract class ```Command``` and subclasses for each command, allowing me to implement the command design pattern. Moreover, I used jackson annotations to deserialize the subclasses commands from the json file.
* Since the output of the commands has similar structure, I chose to implement Builder to create the ObjectNode for each command.
