# Project_Room_Reservation_System (Grand Reserve)

This is a Java 1.8  room/resource reservation system application originally designed to meet real business needs of a big psychotherapy center.
This app provides main users (e.g. therapists) functions such as signing up, logging in, creating new event or series of events,
defining room requirements (size, location, day, time, duration, number of events, cycle length), searching for free rooms,
and making reservation. Further changes and removal of events and series of events are possible ass well.
Grand Reserve contains fully functional admin panel that enables adding, deletion and edition of all objects (Users, Rooms, Sites, Events). 

## Setup

Clone this repo to your IDE.
Create mySql database `grand_reserve` and set database url and login data in `src/main/resources/application.properties`.

## Starting the application

Right-click the project repository in your IDE, choose `Run as --> Spring Boot App` (for Eclipse).
To enter home page of Grand Reserve type `[your server address]/main` in your browser.  ` 

## Built With

* [Spring boot](https://projects.spring.io/spring-boot/) - dependency management
* [Thymaleaf](http://www.thymeleaf.org/) - web templates

## License

To be completed.

## Acknowledgments

* Coderslab team for support in first steps of development, remarks and guidelines.
