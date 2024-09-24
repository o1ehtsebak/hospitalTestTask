# What is this?

This is a test app for small hospital.
It exposes API for creating/updating hospital departments along with rooms, API for doctor/patient adding.
Doctors are being notified via email when new patient arrives or old one leaves hospital.

Note - app was build very quickly within not full 2 days so testing wasn't performed thoroughly. Also, amount of unit
and integration tests is really low because of that.

# Quick start

Postman collection can be found under **postman** dir

## Prerequisties

* Git
* Java 17
* Docker or Rancher or Colima
* MySQL 8
* Postman (optional)

## Development Configuration

### Local MySQL

* Setup MyDB for local usage
  You need to have MySQL running on local machine in order to start Spring Boot app from IDE.
  However, if you only need to start project you should consider using this command

```shell 
  ./gradlew clean bootJar && docker compose build && docker compose up
```
