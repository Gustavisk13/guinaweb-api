#!/usr/bin/env bash

USERNAME=gustavisk
IMAGE=guina-api

mvn clean package -Dmaven.test.skip

docker build -t $USERNAME/$IMAGE:latest .

read -p 'Insira a versão da imagem: ' VERSION

docker tag $USERNAME/$IMAGE:latest $USERNAME/$IMAGE:$VERSION

docker push $USERNAME/$IMAGE:latest

docker push $USERNAME/$IMAGE:$VERSION