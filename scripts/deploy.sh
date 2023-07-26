#!/bin/sh

echo "Deploying via SSH!"

ssh -i "$SSH_KEY" "$SSH_USERNAME@$SSH_HOST" \
    "sudo systemctl restart guinaweb-api.service; exit"

echo "Deployed successfully!!!"
