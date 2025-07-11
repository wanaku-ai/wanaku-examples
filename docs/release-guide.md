## Releasing Wanaku Examples

This guide breaks down the process of releasing your project using the provided Maven commands. It explains each step, from setting version numbers to deploying the final release.

### **1. Setting the Stage: Version Numbers**

Before you begin the release process, you need to define three key versions as environment variables. This practice helps to automate and standardize your release workflow.

```shell
export PREVIOUS_VERSION=0.0.5
export CURRENT_DEVELOPMENT_VERSION=0.0.6
export NEXT_DEVELOPMENT_VERSION=0.0.7
```


### **2. Building the Project**

The first command prepares your project for release by cleaning and packaging it.

```shell
mvn -Pdist clean package
```


### **3. Preparing for Release**

The next set of commands uses the Maven Release Plugin to automate the release process.

```shell
mvn release:clean
```

`mvn --batch-mode -Dtag=wanaku-${CURRENT_DEVELOPMENT_VERSION} release:prepare -DreleaseVersion=${CURRENT_DEVELOPMENT_VERSION} -DdevelopmentVersion=${NEXT_DEVELOPMENT_VERSION}-SNAPSHOT`

---

### **4. Finalizing the Release**

The final command performs the release, which means building from the tagged version and deploying it.

```shell
mvn -Pdist release:perform -Dgoals=install
```
