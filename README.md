# Overview
The project is a multi-layered architecture Spring Boot RESTful Web Service based on ***Spring Boot Version: 2.2.6.RELEASE***.

Layers include Spring MVC, Spring Service, Data Access Object(DAO), Data Transfer Object(DTO), Global Exception Handling, Unified Return Result Handling, etc. The CRUD functions are included to show how all of the layers interact with each other.

The application provides mature solutions for common development issues and elegant features to improve the ease of development. The basic architecture shows below.

<img width="439" alt="image" src="https://user-images.githubusercontent.com/42689061/170894756-1b5ef583-2753-4bc7-a7ae-bef5568c58d8.png">

# Highlights

## Flexible Dynamic Search Conditions
Searching data with specified conditions is a common feature in web applications.

The simple way is to write the conditions in the backend service. However, such a way is hard-coding. You must rewrite the backend code when there is a need to change the search conditions.

This application provides a flexible way to decouple the search conditions from the backend service. The conditions are specified in the frontend. A query DTO object is to encapsulate the conditions in the backend service. The flexible object-oriented parameter management in Mybatis makes it easy to convert the object attributes into SQL statements.

Here is the simple example about seaching by page. You can trace `com.longporo.dev.modules.user.controller.UserController#read` method for full details.

1) Conditions in JSON format from the frontend

```JSON
{
    "page": 1,
    "limit": 10,
    "usernameAllLike": "user",
}
```
2) Controller encapsulates the data with UserQueryDTO

```java
@GetMapping
@ResponseBody
public Result read(@RequestBody UserQueryDTO userQueryDTO) {
    return new Result()<>.ok(userService.pageList(userQueryDTO));
}
```

3) Details in UserQueryDTO

```java
@Data
public class UserQueryDTO {
  // current page
  private Long page = 1L;

  // page size
  private Long limit = 10L;

  // all like
  private String usernameAllLike;
}
```

4) Details about converting into SQL statements in the XML file

```XML
<if test="user.usernameAllLike != null  and user.usernameAllLike != ''">
  and user.username like concat(concat('%',#{user.usernameAllLike,jdbcType=VARCHAR}),'%')
</if>
```

## RESTful API
The RESTful API is supported within Spring. Spring provides a series of annotations to use the REST API easily. The UserController includes their use.

```java
@GetMapping, @PostMapping, @PutMapping, @DeleteMapping
```

## Intercepting Requests
The Spring MVC Interceptor helps to intercept requests and handle them, which can be useful for logging and authorization checks. The simple implementation is in the class `com.longporo.dev.common.intercepter.MvcInterceptor`.

## Mybatis-Plus
[MyBatis-Plus](https://github.com/baomidou/mybatis-plus) is a powerful enhanced toolkit of MyBatis for simplifying development. This toolkit provides some efficient, useful, out-of-the-box features for [MyBatis](https://blog.mybatis.org/), which effectively save the development time.

MyBatis-Plus offers elegant interfaces for writing SQL conditions in the OOP way(Object-Oriented Programming). The class `com.longporo.dev.modules.user.service.impl.UserServiceImpl` provides the simple use of Mybatis-Plus.

```java
@Override
public void deleteByEmail(String email) {
    if (StringUtils.isEmpty(email)) {
        throw new ErpException(ErrorCode.PARAMETER_ERROR);
    }
    QueryWrapper<UserEntity> queryWrapper = this.getQueryWrapper();
    queryWrapper.eq("email", email);
    boolean success = this.delete(queryWrapper);
    if (!success) {
        throw new ErpException(ErrorCode.DELETE_ERROR);
    }
}
```

## Global Exception Handling
Exceptions are common in the Controller Layer since the Controller calls the service interfaces directly. Exceptions might be thrown consciously or occur accidentally. Trying and catching exceptions in each method in the Controller might lead to verbose code.

Handling exceptions in the Controller Layer globally helps to avoid repetitive work. It is implemented with Spring AOP (Aspect Oriented Programming). With the use of AOP, we can declare the new code and the new behaviors separately. The class `com.longporo.dev.common.aop.WebLogAspect` is to enhance all the controller methods. All the controller methods proceed in the `com.longporo.dev.common.aop.WebLogAspect#doAround` method which handles all the exceptions in the controller methods.

## Full Log Trace
The full details of every request and response will be recorded in JSON string format into the specified logging file, which helps trace bugs, errors, and vicious attacks on the application. The full log trace feature is also implemented with the use of Spring AOP, see `com.longporo.dev.common.aop.WebLogAspect` for details.

Tips: You can use [Kibana](https://www.elastic.co/guide/en/kibana/current/index.html) (not in this project) to search and analyze the log data.

## Unified Response Result
Using a uniform return result helps standardize the result handling in the frontend. In this case, all the response results are encapsulated in a Result object (`com.longporo.dev.common.utils.Result`). The Result object includes three attributes: code (int), msg (String), and data (T). The code represents the result state where 0 stands for success and others for failure. By the agreed code, the frontend can handle the result globally so as in the backend. Here is a sample of the response result.
```JSON
{
    "code": 800,
    "msg": "Wrong parameter",
    "data": null
}
```

## i18n Support
Internationalization can be easily added to a Spring Boot project. The config class (`com.longporo.dev.common.config.LocaleConfig`) specifies the default locale and an interceptor which enables the user to change the locale dynamically. This project simply supports Chinese and English locales. It is used with the Result object, the code, and msg attribute corresponding to the key and value in the properties file.

<img width="427" alt="image" src="https://user-images.githubusercontent.com/42689061/171522201-9e1c481a-f49a-4cc5-8c08-b2a77e04313d.png">

## Multiple Spring Profiles Support
Profiles are a useful feature in the Spring framework. We can activate different profiles in different environments. This project provides the dev (default) and prod profiles. The profiles are integrated with the Maven configuration file (pom.xml). You can activate the profile you want while building this project with a Maven command. See build.sh for details.

<img width="397" alt="image" src="https://user-images.githubusercontent.com/42689061/171534473-0edfb82a-73e8-4542-8f85-e2e3f651b95d.png">

## High Cohesive Class Structure
In the development process, we can always find the commonly used attributes or methods among a number of business interfaces or objects, thus it is necessary to abstract them which improves the reusability and cohesion of our code. Many inheritances or realizations are included in this project.

<img width="464" alt="image" src="https://user-images.githubusercontent.com/42689061/171525536-b95084a8-1d19-4934-b5dd-d27de5db902c.png">

<img width="599" alt="image" src="https://user-images.githubusercontent.com/42689061/171525609-317f16cc-c519-416e-be38-e3017c78ac26.png">

## Extensible Module Structure
The module structure in this project is well-designed and organized. This project contains two main modules: the business module (longporo-business) and the common module (longporo-common). The common module abstracts all the common things from the business module, which consists of all the common configurations, abstract class, util objects, etc. So you can add any number of business modules you want in such a structure. All the necessary configurations have been done in the common module. You can even extend this project to a Spring Cloud project by simply adding the business module.

<img width="423" alt="image" src="https://user-images.githubusercontent.com/42689061/171534038-175e6655-aa86-4d94-984f-bdb248faf1af.png">

# Run The Project
### 1) Create a Table
Create the “USER” table in your MySql database with the use of the user.sql file (in the root path of the project).

<img width="287" alt="image" src="https://user-images.githubusercontent.com/42689061/171529388-87729002-cf33-458c-8172-9f7b2ef6e6a6.png">

### 2) Change Database Connection Information
Change to your own MySql database connection information in application-*.yml.

<img width="920" alt="image" src="https://user-images.githubusercontent.com/42689061/171528940-9844b34f-fea8-48ae-8a44-f0e033d7ae55.png">

### 3) Change The Logging Directory
Such a directory is used for saving the logging files. Change to your own logging directory in the pom.xml (in the root path of the project).

<img width="1258" alt="image" src="https://user-images.githubusercontent.com/42689061/171530546-e4cc7c72-446d-451c-8dcb-074a7c886508.png">

### 4) Change Directory for Packaging
Such a directory is used for saving the packaged JAR file. Change to your own packaging directory in the pom.xml (in the root path of the project).

<img width="1192" alt="image" src="https://user-images.githubusercontent.com/42689061/171531387-1ae85caa-5938-4302-832a-8c6c74542e1f.png">

### 5) Then RUN!!!

# Package The Project
Simply run the build.sh file (in the root path) in the console panel. The project will automatically be packaged into an executable JAR file. The JAR file is saved in the directory that is configured in the root path pom.xml (jar.package.dir).

For example, package the project using the 'prod' environment (the application-prod.yml will be activated).

```shell
sh build.sh prod
```
