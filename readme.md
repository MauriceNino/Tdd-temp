# Prerequisites
- This example has no usage and all the classes do not interact with each other
- To do the exercises, check it out and follow the guidelines below
- If you want your tutor to have an easy time when checking your solution, consider creating a fork
- Exercises have checkboxes ( [ ] ). To check them, write an 'x' inside them: [x]

# Installation
## Setup IntelliJ

- Install Lombok Plugin for IntelliJ
- Install some Markdown Plugin for IntelliJ or open it in VS Code (or just read it uncompiled)

## Run DB

1. start postgres
    1. `docker container run --name postgres_tdd -it -p 5432:5432 \
            -e POSTGRES_USER=tdd_it \
            -e POSTGRES_PASSWORD=tdd_it \
            -e POSTGRES_DB=tdd_it postgres:11`
    1. Or if you have already started once: 
        `docker container start postgres_tdd -i`
1. Create datasource in IntelliJ with the following Data:
    1. Url: jdbc:postgresql://localhost:5432/tdd_it
    1. Username: tdd_it
    1. Password: tdd_it
1. For the DB connection, enable the tdd_it Schema

# Exercises

## DBUnit

- [ ] Exercise 1) Go to the class [BookDaoTest](src/test/java/daos/BookDaoTest.java)
- [ ] Exercise 2) Go to the class [BookDaoTest](src/test/java/daos/BookDaoTest.java)
- [ ] Exercise 3) Go to the class [BookDaoTest](src/test/java/daos/BookDaoTest.java)
- [ ] Exercise 4) Explain, why the DB stays consistent between all the tests

> **Answer Exercise 4 here**
> 

## RESTEasy

- [ ] Exercise 5) Go to the class [WorldApiTest](src/test/java/rest/WorldApiTest.java) and do all 3 exercises