#!/usr/bin/env bash

GREEN="\033[32m"
NORMAL="\033[0m"

PREVIOUSVERSION=$(sed 's:</span>:\n:g' src/main/resources/templates/teste.html | sed -n 's/.*>//p' | sed '/^ *$/d')
echo -e "${GREEN}Previous version: ${PREVIOUSVERSION} ${NORMAL}"

USERNAME=gustavisk
IMAGE=guina-api


read -p 'Insira a versão do git: ' VERSIONGIT

sed -i -e "s/\(<span>\).*\(<\/span>\)/<span>$VERSIONGIT<\/span>/g" src/main/resources/templates/teste.html

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