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
* ```CommandRunner``` : utility class for command line functionalities

## Implementation

* The pagination of the project is based around the Page abstract class, which allows the updatePage and clearPage methods to be implemented in the child classes
* Each time a user accesses a page, the page is either instantiated or updated. The pages don't exist outside of the user's session and each time a user re-accesses a page, it is re-instantiated
* There are 2 new types of users: ```Artist``` and ```Host``` that allows them to modify the content of the library itself by adding or removing their own content
* The programme now supports the removal of users and all their interactions with the other users.
* In order to successfully implement the ```deleteUser``` functionality, we must first check if anyone is listening to the content of the user that is to be deleted. If so, the user cannot be deleted. Otherwise we remove all the follows and likes the user ever gave, delete the playlists he created and remove the content he uploaded from the library, as well as from the playlists of all other users.

![my project]()
