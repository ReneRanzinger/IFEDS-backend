#!/bin/sh
  
# This will create ALL volumes
docker volume create postgres-volume

# create the network
docker network create ifeds-network
