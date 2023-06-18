# budget-app

`budget-app` is a project which consists of backend written in [Java](https://www.java.com/en/) ([Spring Boot](https://spring.io/projects/spring-boot)), database ([PostgreSQL](https://www.postgresql.org/)) and frontend written in [TypeScript](https://www.typescriptlang.org/) ([Angular](https://angular.io/)).

## Features

- creating expenses consisting of up to 5 tags, value and date
- browsing list of all expenses
- editing and deleting expenses

## Running app

In order to run this app, you ought to have installed [Docker](https://www.docker.com/) (and [Docker Compose](https://docs.docker.com/compose/)).
You can start he app with the following command:

```
$ docker compose up
```

It will start all parts ([`backend`](./backend), [`database`](./database/), [`frontend`](./frontend)) inside separate docker containers.
