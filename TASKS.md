# Hour 1
- [x] (2pt) Create a dockerfile for each projects in `settings.gradle`
- [ ] (1pt) Create a gradle task to build all docker, the task could look like `./gradlew clean build buildDocker -x test`
- [ ] (0.5pt) Create a gradle task to build single docker images when you are working in only one module
- [ ] (2pt) Create a docker-compose version 3+ file to orchestrate de booting of all modules

Docker files are done, you can build with `docker build authorization/` for example. I dunno what that does, but it runs.

I tried the second one, create a gradle task, but I dunno what I'm doing. It's in `build.gradle`