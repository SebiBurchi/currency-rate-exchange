Java web application which queries currency exchange rates from publicly
available sources and returns, via a REST API, the parity between two currencies.

It is a Spring Boot back-end application integrated with Thymeleaf and some jQuery for user interface.
Security is ensured by integration with Keycloak based on OAuth2

Prerequisites:
	To run the application, you need java installed, as well as maven, docker and docker-compose.

How to run:
	After clone, run make in the local project. This will run the Spring Boot application together with a docker container for the Keycloak service

How to use:
	Access http://localhost:8080/ for the user interface. The home page is unprotected, while the others will require authentication.
	There are already 2 users defined in Keycloak: john.doe/password and smith.cooper/password. First has permissions only for the exchange rate in Europe. The second one has full access.
	Users can also be viewed and manipulated from the Keycloak administration page: http://localhost:8081/auth with user admin/password.

The application makes calls to https://api.exchangeratesapi.io/latest with fallback to https://www.bnro.ro/nbrfxrates.xml in case of failure.
