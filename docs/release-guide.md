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
mvn versions:set -DnewVersion=${CURRENT_DEVELOPMENT_VERSION} && mvn versions:commit && git commit -m "Prepare for release" .
```

```shell
mvn -Pdist clean package
```

### **3. Tag the Project**

```shell
git tag wanaku-${CURRENT_DEVELOPMENT_VERSION} && git push origin wanaku-${CURRENT_DEVELOPMENT_VERSION}
```

### **4. Release the Project**

```shell
jreleaser full-release -Djreleaser.project.version=${CURRENT_DEVELOPMENT_VERSION}
```

### **5. Version Bump**

```shell
mvn versions:set -DnewVersion=${NEXT_DEVELOPMENT_VERSION} && mvn versions:commit
```