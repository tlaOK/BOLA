#!/usr/bin/env bash

cd src

docker-compose down
docker-compose up -d

echo 'Build complete'