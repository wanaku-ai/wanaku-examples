#!/bin/bash

branch=${1:-main}
manifests=$(cat containers.txt)

for manifest in $manifests ; do
  aarch64Image=$(echo "${manifest}" | sed "s/:[^:]*$/:${branch}-aarch64/")
  x864Image=$(echo "${manifest}" | sed "s/:[^:]*$/:${branch}-x86_64/")

  echo "Pulling ${aarch64Image}"
  podman pull ${aarch64Image}

  echo "Pulling ${x864Image}"
  podman pull ${x864Image}

  echo "Creating the manifest ${manifest}"
  podman manifest create ${manifest} ${aarch64Image} ${x864Image}
  podman manifest push --all ${manifest}

done