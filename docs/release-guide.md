## Releasing Wanaku Examples

This guide describes how to release the project. The release is automated via the `release` GitHub Actions workflow, which creates a release branch, builds container images, and publishes artifacts through JReleaser.

### **Automated Release (GitHub Actions)**

Via the GitHub UI:

1. Go to **Actions** > **release** > **Run workflow**.
2. Enter the release version (e.g., `0.1.0`).
3. Click **Run workflow**.

Via the CLI:

```shell
export RELEASE_VERSION=0.1.0
gh workflow run release -f releaseVersion=${RELEASE_VERSION}
```

The workflow will:

- Create a branch named after the version (e.g., `0.1.0`).
- Build the project with the `dist` profile.
- Build and push container images for x86 and arm64 to Quay.io.
- Run JReleaser to publish the release artifacts on GitHub.
- Create and push multi-arch container manifests.

### **Manual Release**

If you need to release manually, follow these steps:

#### **1. Set the version**

```shell
export RELEASE_VERSION=0.1.0
```

#### **2. Create the release branch**

```shell
git checkout -b ${RELEASE_VERSION}
git push origin ${RELEASE_VERSION}
```

#### **3. Build the project**

```shell
mvn -DskipTests -Pdist clean package
```

#### **4. Release the project**

```shell
jreleaser full-release -Djreleaser.project.version=${RELEASE_VERSION}
```