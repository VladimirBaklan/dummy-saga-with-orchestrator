# dummy saga with orchestrator

Successful saga order flow:
- create product
- create order

Rollback saga order flow:
- rollback order
- rollback product

Repository structure:
- orchestrator - microservice aimed at managing events in different topics and responsible for the logical distribution of events, depending on the business logic
- orders service - microservice focused on order management
- products service - microservice focused on products management
- shared - library containing common code used in the microservices


Necessary improvements (not implemented yet):
- error handling
- logging
- metrics
- tests
- dev/stage/prod configs
- Dockerfile(s)
- DB migrations
- _testing_