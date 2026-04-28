!#/bin/bash

#generic script to start containerized dbs
# Bring down existing containers and don't remove named volumes
docker compose down && docker compose up --build
