#!/bin/bash
# Exemplo de envio rápido via comando único
echo '{"userId": "user_123", "category": "GAMES", "points": 5}' | \
docker exec -i kafka kafka-console-producer \
  --bootstrap-server localhost:9092 \
  --topic user-events