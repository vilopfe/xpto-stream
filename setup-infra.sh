#!/bin/bash

echo "🚀 Subindo containers..."
docker-compose up -d

echo "⏳ Aguardando DynamoDB estabilizar..."
sleep 5

echo "🏗️ Criando tabela user_interests..."
aws dynamodb create-table \
    --table-name user_interests \
    --attribute-definitions \
        AttributeName=userId,AttributeType=S \
        AttributeName=category,AttributeType=S \
    --key-schema \
        AttributeName=userId,KeyType=HASH \
        AttributeName=category,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --endpoint-url http://localhost:8000 \
    --region us-east-1

echo "✅ Infraestrutura pronta!"