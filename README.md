# PWCBHandler_project

This project implements a custom `PasswordCallbackHandler` for securing WSO2 Micro Integrator web services with Rampart WS-Security policies. It includes the Java source code and a sample MI integration project.

## What's inside

| Path | Description |
| --- | --- |
| `src/main/java/org/veneerac/PWCBHandler.java` | Standalone handler. Handles `USERNAME_TOKEN` (user `admin`) and `SIGNATURE` (key alias `wso2carbon`) callbacks. |
| `pom.xml` | Maven build for the standalone handler (`PWCBHandler-1.0-SNAPSHOT.jar`). |
| `Class_Pwd/` | WSO2 MI for VS Code integration project with a variant of the handler (`com.veneerac.pwd.PWCBHandler`, `USERNAME_TOKEN` only) and Docker deployment files. |

## Prerequisites

- JDK 8 to build the standalone handler. The root `pom.xml` compiles with `source`/`target` 1.6, which JDK 11 and later no longer support. To build with a newer JDK, change both values to `1.8`.
- Maven 3.x
- Access to the WSO2 Maven repository (`https://maven.wso2.org/nexus/content/repositories/releases/`) for `wss4j 1.5.11-wso2v17`.

## Build

```bash
mvn clean package
```

The JAR is created at `target/PWCBHandler-1.0-SNAPSHOT.jar`.

## Use it in WSO2 Micro Integrator

1. Copy the JAR to `<MI_HOME>/lib` and restart the server.
2. In the Rampart policy of your secured service, point the password callback to the handler class:

   ```xml
   <rampart:RampartConfig xmlns:rampart="http://ws.apache.org/rampart/policy">
       <rampart:passwordCallbackClass>org.veneerac.PWCBHandler</rampart:passwordCallbackClass>
       <!-- other Rampart settings -->
   </rampart:RampartConfig>
   ```

3. Call the service with a WS-Security `UsernameToken` for the user `admin`.

To use the variant in `Class_Pwd`, open that folder in VS Code with the WSO2 Integrator: MI extension, build the project, and use `com.veneerac.pwd.PWCBHandler` as the callback class.

## Adapting the handler

The usernames, passwords and key alias are hard-coded for demonstration. Before using this pattern beyond a test environment, read the credentials from a secure source such as Secure Vault or a user store instead of the source code.

## License

Licensed under the [Apache License 2.0](LICENSE).
