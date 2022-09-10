#!/usr/bin/env bash

USERNAME=gustavisk
IMAGE=guina-api

read -p 'Insira a versão do git: ' VERSIONGIT

git pull
git add .
git commit -a -m"Nova versão do Docker: $VERSIONGIT"
git tag $VERSIONGIT HEAD
git push

mvn clean package -Dmaven.test.skip

docker build -t $USERNAME/$IMAGE:latest .



docker tag $USERNAME/$IMAGE:latest $USERNAME/$IMAGE:$VERSIONGIT

docker push $USERNAME/$IMAGE:latest

docker push $USERNAME/$IMAGE:$VERSIONGIT