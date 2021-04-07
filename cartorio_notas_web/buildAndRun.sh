#!/bin/sh
mvn clean package && docker build -t src/cartorio_notas_web .
docker rm -f cartorio_notas_web || true && docker run -d -p 9080:9080 -p 9443:9443 --name cartorio_notas_web src/cartorio_notas_web