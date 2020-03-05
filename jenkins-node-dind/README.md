# dind-jenkins-node

A Docker image which allows for running Docker in Docker (DinD). This image is meant to be used with the [Jenkins Docker plugin](https://wiki.jenkins-ci.org/display/JENKINS/Docker+Plugin). It allows for Docker containers used as Jenkins build slaves to create and publish their own Docker images in-turn.

This is a mix of "[jenkins/ssh-slave](https://hub.docker.com/r/jenkins/ssh-slave/)" and "[jpetazzo/dind](https://registry.hub.docker.com/u/jpetazzo/dind/)".

## Requirements

* The container must be run with `--privileged` in order for nested-Docker to work. There is a check box to enable this for the image within the Jenkins UI.

## Instructions

Once your Jenkins slave is up and running, you can run `docker` commands like `docker build`, `docker pull`, and `docker push` from within the Docker build slave container.

## Misc References

* https://blog.docker.com/2013/09/docker-can-now-run-within-docker/
