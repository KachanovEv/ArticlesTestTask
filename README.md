
Articles List App

 



The idea of the task is to show an infinite
list of Articles, that come from backend. Side menu is not necessary. 

Design is provided in Adobe XD



Backend:



Url : 
https://www.heikoschrang.de/applications/app/content/list/?category=1&page=0&per_page=10&order=desc



There are three categories with ID’s 1, 2 and
3.



Pages start from 0



Functionality



·       
List should have endless
scrolling, meaning each scroll should load 10 more items and after scrolling to
the bottom next 10 articles should be added.



·       
Clicking on Top bar categories
should load first 10 items of each category. Selected category should change
color. Every category has its own color, please check the design.



·       
Clicking on article, should
open article display screen, on that screen article Title and Content fields
should be shown only (any design you like, doesn’t matter)



No other
functionality needs to be done (sharing or clicking on coins icon etc…)



 



                                                                          Requirements



·       
Tab Bar should be included(as
first item) in one Recyclerview with Articles items – Needed for endless
scroll.



·       
MVVM or MVP should be used



·       
Use some Dependency Injection
lib(Dagger 2 or Coin, either is ok)



·       
Use RxJava



·       
Use Navigation Controller  for changing screens



·       
After returning from Article
Details screen back to Articles list, the screen should be in exactly same
state (position) as user left it.



        Bonus



Not mandatory,
but it would be best if you download articles and store it to Room DB, and get
it from there to Articles List Screen. It would make application usable OFFLNE
