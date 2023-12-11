
# Infraestructure: Containers configuration

In this folder you can find the docker files used to create the docker containers for your Final project. 
**You don't need to create them in your local environment, this is just to show you how they were configured.**

## Required software

For defining images and creating docker containers, it is required to install:

- [Docker](https://docs.docker.com/get-docker/).

## Creating Final Project image

To create a docker image run the following command:

```sh
cd project-container
docker build -f Dockerfile.dsmt_2023-2024 -t unipi/dsmt:20232024 .
```

## Creating Final Project container

To create a docker container from the previous created image:

```sh
docker run -d -t -p 9084:8084 -p 9080:8080 -p 5858:4848 --name dsmt-20232024 unipi/dsmt:20232024
```

## Connecting to a running Final Project container

Make sure your container is running:

```sh
docker ps -a
```
In case it is not running, run the command of the previous step. Next:

```sh
docker exec -it dsmt-20232024 /bin/bash
```

**Once you connect to a container, you have to run manually either Apache Tomcat or Glassfish.**

## Applications inside the container

The docker file definition was configured to create a container with the following programs:

- SDKMAN
- Java.net 11.0.12-open (JDK)
- Apache Maven 3.8.6
- Apache Tomcat 10.1.16 (Port: 8084)
- Glassfish 7.0.11 (Port: 8080)

## Admin credentials for Apache Tomcat and Glassfish

To enter to the administration console in both applications, make use of the following credentials:

* username: admin
* password: admin

## Servers URLs

Pay attention to the port mapping defined for the container:

- Container port 4848 -> mapped to -> Host port 5858
- Container port 8084 -> mapped to -> Host port 9084
- Container port 9080 -> mapped to -> Host port 9080

So the URLs of the servers are:

- Apache Tomcat:    http://localhost:9084
- Glassfish:        http://localhost:9080
- Glassfish Administration Console: https://localhost:5858/

