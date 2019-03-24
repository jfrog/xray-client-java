# Xray Java Client 

JFrog Xray Client adds JFrog Xray scanning capabilities to your java application.

# Building and Testing the Sources

To build the plugin sources, please follow these steps:
1. Clone the code from git.
2. Build and install the *xray-client-java* dependency in your local maven repository, by running the following Gradle command:
```
gradle clean install
```
4. If you'd like run the *xray-client-java* integration tests, follow these steps:
* Make sure your Xray instance is up and running.
* Set the *CLIENTTESTS_XRAY_URL*, *CLIENTTESTS_XRAY_USERNAME* and *CLIENTTESTS_XRAY_PASSWORD* environment variables with your Xray URL, username and password.
* Run the following command:
```
gradle test
```

# Code Contributions
We welcome community contribution through pull requests.
