# JobSearchMS

This is a microservice-based application designed to help users seamlessly search for jobs across various companies. Each company features user reviews, enabling users to evaluate and make informed decisions. The application comprises three distinct microservices: Job, Company, and Reviews.

- Communication:
  - Asynchronous: Utilizes RabbitMQ for messaging between services.
  - Synchronous: Employs OpenFeign for RESTful communication.
- Key Features:
  - Each microservice integrates with its own Postgres database and leverages:
    - Eureka Server for service discovery.
    - API Gateway and Config Server for centralized routing and configuration.
    - Actuator and Zipkin for health monitoring and distributed tracing.
- Deployment:
  - The application is fully containerized using Docker Compose and deployed in a scalable environment with Kubernetes.

## Microservices Communication
### 1. Asynchronous Communication:**
- The Reviews and Company microservices use RabbitMQ to enable asynchronous communication for determining the reviews associated with a company.
### How It Works
- The Review microservice acts as the message producer.
- Within the `messaging` package, we have:
  - A RabbitMQ configuration file:
    - Defines several beans, including:
      - Queue: Specifies the messaging queue for review data.
      - MessageConverter: Handles message serialization and deserialization.
      - RabbitTemplate: Facilitates sending and receiving messages.
      - All beans are configured using the `org.springframework.amqp` dependency.
  - A Spring Boot service file:
    - Implements the producer logic.
    - Uses the RabbitTemplate to convert and send review messages asynchronously to the queue whenever a review for a company is available.
- Just like the Review Microservice, the Company microservice acts as the message consumer.
- Within the `messaging` package, we have:
- A RabbitMQ configuration file:
    - Defines several beans, including:
        - Queue: Specifies the messaging queue for review data.
        - MessageConverter: Handles message serialization and deserialization.
        - RabbitTemplate: Facilitates sending and receiving messages.
        - All beans are configured using the `org.springframework.amqp` dependency.
- A Spring Boot service file:
    - Implements the consumer logic.
    - Uses the `@RabbitListener` annotation to listen and receive review messages asynchronously from the queue whenever a review for a company is available.
This design ensures efficient, non-blocking communication between the microservices, leveraging RabbitMQ for messaging.
2. Synchronous Communication Using OpenFeign:
- The Job microservice communicates with the Company and Review microservices synchronously to fetch details about a company and its associated reviews. This is achieved using OpenFeign, a declarative HTTP client provided by Spring Cloud.
- Implementation Details:
  
  Within the Job microservice, a client folder contains two separate interfaces:

  - CompanyClient: Fetches details of a given company by its ID.
  - ReviewClient: Retrieves reviews associated with a job or company.

 These interfaces use the OpenFeign dependency provided by Spring Cloud, which simplifies API integration by enabling declarative REST clients.

## Microservices

### Job Microservice

- Port: 8082
- Database name: job
- Service name: Job-service
- Image: juliettegodyere/jobms:latest
  - Endpoints:

    - **Get all jobs:**
      -     URL: /api/v1/jobs
      -     Method: GET
      -     Response: 
      - ```json
          [
          {
          "id": 1,
          "title": "Software Engineer",
          "jobDescription": "Responsible for developing and maintaining web applications.",
          "minSalary": "50000",
          "maxSalary": "100000",
          "location": "New York",
          "postingDate": "2024-12-28T14:27:30.450+00:00",
          "salary": "75000",
          "jobType": "PERMANENT",
          "jobReference": "SE-12345",
          "company": {
          "id": 1,
          "name": "Example Company",
          "companyDescription": "This is an example company description."
          },
          "review": [
          {
          "id": 1,
          "title": "Great Workplace",
          "description": "The company has an excellent work culture and supportive management.",
          "rating": 4.5
          }],
          "favorite": false
          }]

    - **Get job by ID:**
      -       URL: /api/v1/jobs/1
      -       Method: GET
      -     Status Code: 200 OK
      -   Response: 
      - ``` json
        {
        "id": 1,
        "title": "Software Engineer",
        "jobDescription": "Responsible for developing and maintaining web applications.",
        "minSalary": "50000",
        "maxSalary": "100000",
        "location": "New York",
        "postingDate": "2024-12-28T14:27:30.450+00:00",
        "salary": "75000",
        "jobType": "PERMANENT",
        "jobReference": "SE-12345",
        "company": {
            "id": 1,
            "name": "Example Company",
            "companyDescription": "This is an example company description."
        },
        "review": [
            {
                "id": 1,
                "title": "Great Workplace",
                "description": "The company has an excellent work culture and supportive management.",
                "rating": 4.5
            }
        ],
        "favorite": false
        }
    - **Create a job:** 
      -       URL: /api/v1/jobs
      -       Method: POST
      -       Status Code: 201 CREATED
      -       Request: 
      - ```json
        {
        "title": "Software Engineer",
        "jobDescription": "Responsible for developing and maintaining web applications.",
        "minSalary": "50000",
        "maxSalary": "100000",
        "location": "New York",
        "isFavorite": false,
        "salary": "75000",
        "jobType": "PERMANENT",
        "jobReference": "SE-12345",
        "companyId": 1
        }
    - **Update a job:** 
      -     URL: /api/v1/jobs/{jobId}
      -     Method: PUT
      -     Status Code: 200 OK
    - **Delete a job:** 
      -     URL: /api/v1/jobs/{jobId}
      -     Method: DELETE
      -     Status Code: 200 OK

### Company Microservice

- Port: 8081
- Database name: Company
- Service name: Company-service
- Image: juliettegodyere/companyms:latest
- Endpoints:
  - **Get all companies:** 
   -     URL: /api/v1/companies
   -     Method: GET
   -     Response: 
     ```json
       [
         {
              "id": 1,
              "name": "Example Company",
              "companyDescription": "This is an example company description.",
              "rating": 4.5
        }
      ]

  - **Get Company by ID:** 
    -     URL: /api/v1/companies/1
    -     Method: GET
    -     Status Code: 200 OK
    -     Response: 
    - ```json 
      {
       "id": 1,
       "name": "Example Company",
       "companyDescription": "This is an example company description.",
       "rating": 4.5
      }
  - **Create a company:** 
    -     URL: /api/v1/companies
    -     Method: POST
    -     Status Code: 201 CREATED
    -     Request: 
    -  ```json 
       {
        "name": "Example Company",
        "companyDescription": "This is an example company description."
        }
  - **Update a Company:** 
    -     URL: /api/v1/companies/{companyId}
    -     Method: PUT
    -     Status Code: 200 OK
  - **Delete a Company:** 
    -     URL: /api/v1/companies/{companyId}
    -     Method: DELETE
    -     Status Code: 200 OK

### Review Microservice

- Port: 8083
- Database name: review
- Service name: review-service
- Image: juliettegodyere/reviewms:latest
- Endpoints:
  - **Get all Reviews for the given company:** 
    -     URL: api/v1/reviews?companyId=1
    -     Method: GET
    -     Status Code: 200 OK
    -     Response: 
    -  ```json
       [
          {
            "id": 1,
            "title": "Great Workplace",
            "description": "The company has an excellent work culture and supportive management.",
            "rating": 4.5,
            "companyId": 1
          }
       ]

  - **Get Reviews by ID:** 
    -     URL: /api/v1/reviews/1
    -     Method: GET
    -     Status Code: 200 OK
    -     Response: 
    - ``` json
      {
         "id": 1,
          "title": "Great Workplace",
          "description": "The company has an excellent work culture and supportive management.",
          "rating": 4.5,
          "companyId": 1
      }
  - Create a Review: 
    -     URL: /api/v1/reviews
    -     Method: POST
    -     Status Code: 201 CREATED
    - Request: 
    - ```json 
      {
         "title": "Great Workplace",
         "description": "The company has an excellent work culture and supportive management.",
         "rating": 4.5,
         "companyId": 1
      }
  - **Update a Reviews:** 
    -     URL: /api/v1/reviews/{reviewId}  
    -     Method: PUT
    -     Status Code: 200 OK
  - **Delete a Reviews:** 
    -     URL: /api/v1/reviews/{reviewId}  
    -     Method: `DELETE`
    -     Status Code: `200` `OK`

## Spring Cloud Services Used

### 1. Config Server
- **Port:** 8080
- **Dependency:**
  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-config-server</artifactId>
  </dependency>

- **Uses:**

    Centralized configuration management for all microservices. It allows externalized configuration using a Git repository to manage application properties and profiles.

- **Used GitHub:**

    Configurations are stored in a GitHub repository. For example:

      https://github.com/<username>/<config-repo>
- **How to Implement:**

  1. Add the dependency above to the pom.xml.
  2. Annotate the main class with @EnableConfigServer.
  3. Add the following to the application.properties of the config server:
     ```properties     
        spring.application.name=config-server
        server.port=8080
        spring.cloud.config.server.git.uri=https://github.com/juliettegodyere/config-server-repo/
  4.  Run the Config Server.
    
### 2. Gateway
- **Port:** 8084
- **Dependency:**

  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-config-server</artifactId>
  </dependency>

- **Uses:**

    API Gateway acts as a single entry point for all microservices, handling routing, load balancing, and security.

- **How to Implement:**
  1. Add the dependency above to the pom.xml.
  2. Configure routes in the application.properties or application.yml. Example
     ```yaml
     spring:
     cloud:
     gateway:
     routes:
      - id: job-service
     uri: http://localhost:8082
     predicates:
      - Path=/jobs/**
      - id: company-service
      uri: http://localhost:8081
     predicates:
      - Path=/companies/**

   3. Annotate the main class with `@SpringBootApplication`.
   4. Run the Gateway service.

### 3. Service Registry (Eureka)
- **Port: 8761**
- **Dependency:**

    ```xml
    <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
- **Uses:**

    Service Registry for discovering and registering microservices. Each microservice registers itself with Eureka to enable dynamic service discovery.

- **How to Implement:**

  1. Add the dependency above to the pom.xml.
  2. Annotate the main class with @EnableEurekaServer.
  3. Add the following configuration to application.properties:
     ```properties
     spring.application.name=eureka-server
     server.port=8761
     eureka.client.register-with-eureka=false
     eureka.client.fetch-registry=false
  4. Run the Eureka server.

## Additional Dependencies Used

### 1. OpenFeign
- **Dependency:**
  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
  </dependency>
- **Uses:**
OpenFeign is used for synchronous communication between microservices. It simplifies HTTP calls by using declarative REST client interfaces.

- **How to Implement:**

  1. Add the dependency above to the pom.xml.
  2. Enable Feign in the main class by annotating it with @EnableFeignClients.
  3. Create a Feign client interface in the microservice’s client package:
     ```java
     @FeignClient(name = "company-service", url = "http://company-service")
     public interface CompanyClient {
      @GetMapping("/companies/{id}")
      Company getCompanyById(@PathVariable Long id);
     }
  4. Inject the Feign client into the service class and use it to make HTTP calls.
### 2. Resilience4J
  - Dependency:

    ```xml
    <dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot2</artifactId>
    </dependency>

   - **Uses:**
Resilience4J is used for fault tolerance and resilience patterns like circuit breakers, rate limiting, and retries.

   - **How to Implement:**

     1. Add the dependency above to the pom.xml.
     2. Configure Resilience4J in application.properties or application.yml. Example:
     
        ```properties
        resilience4j.circuitbreaker.instances.companyService.slidingWindowSize=5
        resilience4j.circuitbreaker.instances.companyService.failureRateThreshold=50
        
     3. Use the circuit breaker annotation in the service method:
    
         ```java
        @CircuitBreaker(name = "companyService", fallbackMethod = "fallbackGetCompany")
          public Company getCompany(Long id) {
          return companyClient.getCompanyById(id);
        }
    
        public Company fallbackGetCompany(Long id, Throwable t) {
          return new Company("Default Company", "Fallback description");
        }

### 3. Zipkin
   - **Dependency:**

    <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
    </dependency>
 
  - **Uses:**
     Zipkin is used for distributed tracing to monitor and troubleshoot microservice communication.

  - How to Implement:

    1. Add the dependency above to the pom.xml.
    2. Configure Zipkin in application.properties or application.yml. Example:
         ```properties
         spring.zipkin.base-url=http://zipkin-server:9411
         spring.sleuth.sampler.probability=1.0

    3. Start the Zipkin server (can be done locally or in Kubernetes).
    4. Spring Boot applications automatically propagate trace IDs for Zipkin once the dependency is added.

### 5. RabbitMQ
   - **Dependency:**

    <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
  - **Uses:**
    RabbitMQ is used for asynchronous communication between microservices. It allows for message queuing and event-driven architectures.

  - **How to Implement:**

    1. Add the dependency above to the pom.xml.
    2. Configure RabbitMQ in application.properties or application.yml. Example:
       ```properties
       spring.rabbitmq.host=localhost
       spring.rabbitmq.port=5672
       spring.rabbitmq.username=guest
       spring.rabbitmq.password=guest
    3. Define a message producer and consumer:
      - **Producer:**
        ```java
        @Service
        public class ReviewProducer {
          private final RabbitTemplate rabbitTemplate;
        
            public ReviewProducer(RabbitTemplate rabbitTemplate) {
                this.rabbitTemplate = rabbitTemplate;
            }
        
            public void sendReviewMessage(Review review) {
                rabbitTemplate.convertAndSend("reviewQueue", review);
            }
        }
      - **Consumer:**
          ```java
          @Service
          public class ReviewConsumer {
            @RabbitListener(queues = "reviewQueue")
            public void receiveReview(Review review) {
              System.out.println("Received review: " + review);
            }
          }
    4. Ensure RabbitMQ is running locally or in Kubernetes for message queuing.

## Docker

### Building Docker Images with Spring Boot Maven Plugin
Spring Boot provides a convenient way to build Docker images using the spring-boot-maven-plugin. This plugin simplifies the process by leveraging Cloud Native Buildpacks to create production-ready images directly from your source code—no Dockerfile needed. Here's how it works:

#### How Does It Work?
The spring-boot-maven-plugin integrates with Cloud Native Buildpacks to automate the creation of Docker images. When you use this plugin, you're already harnessing the power of Buildpacks under the hood.

1. Packaging
The plugin first packages your application into a JAR or WAR file, just as it would during a normal Maven build.
2. Handing Off to Buildpacks
After packaging, the plugin hands over the artifact to the Buildpacks. Buildpacks then take care of assembling the Docker image, ensuring it’s optimized and ready to deploy.

**To build a Docker image using the spring-boot-maven-plugin, run:**
    
    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=<docker-username>/jobms

During the build process, you may also want to skip running tests to speed up the build. This can be done by adding the -DskipTests flag to your Maven command. Here's an example:

    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=<docker-username>/companyms -DskipTests

By including -DskipTests, Maven will bypass the test execution phase, allowing the build process to focus solely on creating the Docker image. This is particularly useful when you're confident that the application has already passed tests in earlier stages or environments.

By default, Spring Boot uses `application.properties` for configuration. However, when working with Docker, your configuration might differ from the default setup. To handle this, it's advisable to use a separate profile like `application-docker.properties`.
Spring Boot allows you to specify different profiles for different environments. However, there are cases where the application might always default to `application.properties`, even when another profile is intended.
To ensure Spring Boot uses the `application-docker.properties` file during the build, add the following line to `application.properties`:

    spring.profiles.active=docker

This explicitly activates the "docker" profile, ensuring Spring Boot loads the configuration from `application-docker.properties` instead of the default file. This approach is especially useful when building Docker images or running the application in a containerized environment.

You can also specify the active profile during the image build process to ensure the correct configuration is used. This can be achieved by including the -Dspring.profiles.active=docker flag in the Maven command. For example:

    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=<docker-username>/companyms -Dspring.profiles.active=docker

By setting spring.profiles.active=docker, Spring Boot will use the application-docker.properties file for configuration during the image build. This ensures the Docker-specific settings are applied, streamlining the build process for containerized deployments.

### Push to Docker Repository
The following command helps you to push to the docker repository. This is useful for collaboration and also help during deployment. The K8s pulls images from docker repository

    docker push <docker-username>/jobms

### Running the Containers
Ensure you are in the location where the docker compose file resides
    
    docker-compose up -d

To run the microservice, because there where many services involved and which depended on one another, I decided to use docker compose. This is one of the benefits of docker compose. Without it, I had to start up 6 difference applications manually and imagine if the application grows more than 6. Maybe 20 services what happens. This is where docker compose comes in handy. I added the following services in my docker file and i use the command above to start all services
1. Service registry
2. Config Server
3. Gateway
4. Postgres
5. pgadmin
6. Zipkin
7. Rabbitmq
8. jobms
9. reviewms
10. companyms

### Stopping the Containers
Ensure you are in the location where the docker compose file resides
 
    docker-compose down

### Checking Running images
Ensure you are in the location where the docker compose file resides

    docker ps 
    
    docker ps -a

### Checking logs and inspect
Ensure you are in the location where the docker compose file resides

    docker logs <container-name/container-id

    docker inspect <container-name/container-id

### Remove stopped container or images fro local storage 
Ensure you are in the location where the docker compose file resides

    docker rm <container-id/container-name

    docker rmi <image-id/image-name

## Kubernetes

Because we intend to improve this application and we know that it will get to a point where it will become be difficult to manage all these containers manually and that is where K8s came into play. K8s is an open source platform that automates linux container platform. It involves handling the maual processes involved in deploying, scaling and containarization. It is designed to manage the life cycle of containarized applications.

### Benefits
1. Service Discovery and Load balancing
2. Automatic Rollbacks and Rollouts
3. Horizontal Scaling
4. Self-Healing - k8s restarts when stopped independently
5. Secret and Configuration Management

### Kubernetes (K8s) Structure
Kubernetes is an orchestration system that manages containerized applications in a cluster. Here's a breakdown of its main components:

#### Worker Node

Definition: A machine (physical or virtual) that runs your containerized applications.
Example: A server running multiple pods for handling user requests.

#### Pods

Definition: The smallest deployable unit in Kubernetes. Each pod represents a running instance of an application and can contain one or more containers.
Example: A pod running a web server container and a logging container together.

#### Containers

Definition: Lightweight, portable environments that run applications and their dependencies. Pods host these containers.
Example: A container running a Python web app inside a pod.

#### Control Plane

Definition: The brain of the cluster that manages worker nodes and the overall cluster state.

Components:

API Server: Handles communication between users and the cluster.
Controller Manager: Ensures the desired state of the cluster (e.g., scaling apps).
Scheduler: Assigns workloads (pods) to available worker nodes.
etcd: Stores the cluster's configuration and state data.
Example: The control plane ensures that if a pod crashes, a new one is created to maintain the cluster state.

#### Kubelet

Definition: An agent running on each worker node that ensures the pods are running and healthy.
Example: Kubelet restarts a crashed container in a pod.

#### Kube-Proxy

Definition: A networking component that manages routing and load balancing for pods.
Example: Routes traffic from external users to the correct pod.
In Practice
When you deploy an app in Kubernetes, the control plane schedules it to run on a worker node. The app runs inside a pod, which may contain multiple containers. The kubelet ensures the pods stay healthy, while the kube-proxy manages network communication.

![](/private/var/folders/_r/vrqw8dgj6w1494pqhd0ybcq40000gn/T/com.apple.Photos.NSItemProvider/uuid=96D130B1-4401-4BD5-815E-27651067A0F6&library=1&type=1&mode=2&loc=true&cap=true.jpeg/IMG_0143.jpeg)

### Minikube: Local Kubernetes Made Easy

Minikube is a tool that allows you to run and manage Kubernetes clusters locally. It’s designed specifically for local development and testing and mimics the behavior of a live Kubernetes cluster. However, Minikube is not recommended for production use.

For production environments, such as deploying on AWS, you would use a managed Kubernetes service (e.g., Amazon EKS) or provision your own Kubernetes cluster.

You can download Minikube -- https://minikube.sigs.k8s.io/docs/start/?arch=%2Fmacos%2Farm64%2Fstable%2Fbinary+download

For macOS users, I recommend installing Minikube using Homebrew for simplicity:

    brew install minikube

### Minikube Usage

`minikube` - This will work if the installation was successful. 

**Starts local instant**

    minikube start 

You can also run using a different driver if you get `failed to start virtualbox VM` error

    minikube start --driver=docker

Kubectl is a way by which you can interact with Minikube

**This will give you the url where the Kubernetes control pane is running and CoreCDN url is running as well**

    kubectl cluster-info 

### Minikube Dashboard

To access the dashboard use the following command on you terminal

    minikube dashboard

### Pods

To create a Pod

    kubectl apply -f first-pod.yaml

Pods are immutable, meaning you cannot modify an existing Pod once it has been created. If changes are needed, you must delete the current Pod and create a new one with the updated configuration. This ensures consistency and predictability in the Kubernetes environment.

To get the list of all the Pods in the system

    kubectl get pods   

    kubectl get pods -w

`-w` will run the application in a watch mode and you will get a live status update

### Service

Service is a way you can access a Pod.

To create a service 

    kubectl apply -f first-service.yaml

To get the list of services

    kubectl get services

### How to Expose your Application

#### Types of Kubernetes Services
1. ClusterIP (Default):
- Provides the service with its own internal IP address.
- Services are only reachable within the cluster, enabling communication between internal components.
2. NodePort:
- Exposes the service on a specific port of each Node in the cluster.
- This allows the service to be accessed outside the cluster using the NodeIP:NodePort combination.
3. LoadBalancer:
- Exposes the service externally by integrating with a cloud provider's load balancer (e.g., AWS ELB, Azure Load Balancer).
- Automatically routes traffic from the internet to the service.

### ReplicaSet

A ReplicaSet is a Kubernetes object used for managing and scaling a set of identical pos replicas.

#### Why do you need identical Pods
1. High Availability
2. Load Balancing
3. Scaling
4. Rolling Updates 
5. Service Discovery and Load Balancing

**To create a ReplicaSet**

    kubectl apply -f first-replicaset.yaml
    
    kubectl get pods -w
    
    kubectl get replicaset

**Delete Pod**

    kubectl delete pod <pod-name>

### Deployment in K8s

Deployment in K8s is a high level concept that manages the replicaset. It provides updates for Pods and Replicaset. 

**To create a Deployment**

    kubectl apply -f first-deployment.yaml
    
    kubectl get deployments -w
    
    kubectl describe first-deployment
    
    kubectl logs <pod-name>`

run the following command to get the pod-name

    kubectl get pods

### Deploying a Service in Kubernetes
For most services, we created two configuration files:
1. A file to define the Kubernetes Service.
2. A file to define the Kubernetes Deployment configuration.

These configuration files are stored in the K8s folder, except for the Postgres service, which may have a different structure or setup.

#### Zipkin Deployment

Navigate to the directory where zipkin folder that contains both files. In my case, the file is in the following folder

    kubectl apply -f Services/zipkin

#### Rabbitmq Deployment

Navigate to the directory where rabbitmq folder that contains both files. In my case, the file is in the following folder

    kubectl apply -f Services/rabbitmq

#### Postgres Deployment

The Postgres setup includes the following files:
1. ConfigMap:
- Stores configuration data as key-value pairs, such as database credentials or settings.
2. Service:
- Provides a stable and consistent endpoint for applications to connect to the Postgres database.
3. StatefulSet:
- Manages stateful applications to ensure proper data persistence and reliable Pod identity.
4. PersistentVolumeClaim (PVC):
- Ensures persistent storage for the database, crucial for data durability and performance.

    kubectl apply -f Services/postgres

### Creating Databases in Kubernetes

**Once your database is successfully running in Kubernetes, the next step is to create the required databases.**

    kubectl exec -it postgres-0 -- psql -U postgres
    
    \l or \list - List of databases
    
    create database job;
    create database company;
    create database review;

    \l  

### API Gateway and Eureka Server

In Kubernetes, we typically do not need an API Gateway or Eureka Server because Kubernetes provides these functionalities natively:
1. API Gateway:
- Kubernetes uses the Ingress Controller, which handles load balancing, advanced traffic routing, and external access to services.
2. Service Discovery (Eureka Server):
- Kubernetes internally manages service discovery through its built-in DNS capabilities.

However, for local development or traditional applications, you can still use an API Gateway or Eureka Server. In this setup, we will retain them for our local environment.

### Custom Microservice Deployment

For deploying our custom microservices, we introduced an additional configuration file named application-k8s.properties for each microservice. This file handles the Kubernetes-specific configurations.

**We have three microservices:**
1. Job
2. Company
3. Review
In these application-k8s.properties files:
- Eureka Server and Config Server configurations are disabled since they are not needed in Kubernetes.
- Configurations for Resilience4J, RabbitMQ, Zipkin, Actuator, and Postgres are retained as they are essential.

**Each microservice has:**
- A service configuration file: This defines the Kubernetes service and ensures stable communication within the cluster.
- A deployment configuration file: This handles the deployment of the microservice, including container specifications and scaling.

**These files follow the same structure as the RabbitMQ and Zipkin service deployments.**

    kubectl apply -f bootstrap/job
    
    kubectl apply -f bootstrap/review
    
    kubectl apply -f bootstrap/company

**To access the applications on the browser, use the following command to get the url for each service. Ensure you are inside the microservice directory**

    minikube service job --url
    minikube service review --url
    minikube service company --url

### Testing Application

Ensure you are in the right microservice directory

**kubectl get all**

    kubectl logs <pod-name> eg kubectl logs job-5795c775b6-stblf

#### Test Zipkin

**Get the URL**

    minikube service zipkin --url

#### Test Database

**Access DB**

    kubectl exec -it postgres-0 -- psql -U postgres
    
    \l

**Switch to respective database eg job**

    \c job
    
    SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';
    
    select * from jobs;
    
    \c company
    
    SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';
    
    select * from company;
    
    \c review
    
    SELECT table_name FROM information_schema.tables WHERE table_schema = 'public';
    
    select * from review;






