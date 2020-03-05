FROM jenkins/ssh-slave

ENV DEBIAN_FRONTEND noninteractive
ENV DOCKER_COMPOSE_VERSION 1.21.2

RUN apt-get update -qq && apt-get install -qqy \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common && \
    rm -rf /var/lib/apt/lists/*

RUN curl -sSL https://get.docker.com/ | sh

ADD wrapdocker /usr/local/bin/wrapdocker
RUN chmod +x /usr/local/bin/wrapdocker
VOLUME /var/lib/docker

# create folder structure same as ifeds server, great explanation here:
# https://gitlab.com/gitlab-org/gitlab-ce/issues/41227
# basically both dockers need to know reference point of folder structures.
RUN export uid=1001 gid=1001 && \
  mkdir -p /home/ifedsuser && \
  chown ${uid}:${gid} -R /home/ifedsuser
RUN usermod -u 1001 jenkins
RUN groupmod -g 1001 jenkins
RUN id -u jenkins
RUN chown -R jenkins:jenkins /home/jenkins
RUN usermod -a -G docker jenkins

RUN curl -L https://github.com/docker/compose/releases/download/$DOCKER_COMPOSE_VERSION/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
RUN chmod +x /usr/local/bin/docker-compose

ADD jenkins-slave-startup.sh /
CMD ["/jenkins-slave-startup.sh"]
