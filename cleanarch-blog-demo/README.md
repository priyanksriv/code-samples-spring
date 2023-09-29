# Demo Application : Clean Architecture in Spring

## Modules

#### *Interface Adapters*

- ***blog-api, blog-rabbitmq***
	- Primary (or Driving) Adapters wrap around a port
	- tell the application core what to do
	- controllers, console commands, message-receivers, etc

- ***blog-persistence***
	- Secondary (or Driven) Adapters implement a port, an interface and are then injected into the application core
	- told by the application core what to do
	- repository, search engine, message bus, etc 


&nbsp;
#### *Application Layer* : *blog-application-service*
- Business Use cases
- Drive the application flow control to/from primary adapters, domain and secondary adapeters


&nbsp;
#### *Domain Layer* : *blog-domain*
- ***Domain***
	- data and the logic to manipulate that data that is specific to the domain
	- independent of the business processes that trigger that logic
	- completely unaware of the Application Layer
	- good location for "ports" 

- ***Ports***
	- specific entry point into the application core
	- specification of how a tool (UI, console, etc) can use the application core, or how it (database engine, etc) is used by the application core
	- for all practical purposes these are interfaces (one or several) 


&nbsp;
## Module Dependencies
(refer the gradle build files)

***blog-domain***

- the core module (innermost layer)
- it is a ***compile*** dependency for all other modules: `blog-application-service`, `blog-persistence`, `blog-api`, `blog-rabbitmq`
- it does not have any dependency on outer layers or frameworks
- independently testable

***blog-application-service***

- contains the `Main` class, **Spring Boot** jar is packaged here
- ***compile*** dependency on `blog-domain`
- ***runtime*** dependency on `blog-persistence`, `blog-api`, `blog-rabbitmq` (since these need to be packaged in the jar) 
 
***blog-api, blog-persistence, blog-rabbitmq*** 

- ***compile*** dependency on `blog-domain`


&nbsp;
## Data structures

***DTO (Data Transfer Object)***

  - Basic object with API layer validations (mandatory, length, etc.; probably using javax.Validation)
  - defined in `blog-api`, `blog-rabbitmq` (Along with mappers)

***BO (Business Object)***

  - Object that contains data directly tied to it and encapsulates basic operations. 
  - Follows "Tell, Don't Ask"; must not have "get" methods, the BO works on the command given to it by the Service layer
    Example: in the Service Layer
    
        instead of using    :   `if (person.getGender() == "M")` 
        use something like  :   `if (person.isMale())`
  - So it contains the core business logic around the "data/model"
  - Service layer is more of a falicitator in this scenario and deals with taking actions based on the logic encapsulated in the BO
  - defined in `blog-application-service`
  
***Entity***

  - No suffix as such
  - Persistence object
  - defined in `blog-persistence` (Along with mappers)


***Mappers***

- For converting one form of bean to another:
  
  DTO	<--->  BO
  
  BO	<--->  Entity
