@echo off
call mvn clean package
call docker build -t src/cartorio_notas_web .
call docker rm -f cartorio_notas_web
call docker run -d -p 9080:9080 -p 9443:9443 --name cartorio_notas_web src/cartorio_notas_web