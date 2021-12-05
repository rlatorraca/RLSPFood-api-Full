# A nossa Imagem Base /Pai (que tem o debian como base)
FROM openjdk:17.0.1-slim-buster

# DIRECTORY de trabalho, onde iremos criar as imagens e trabalhar
# Se nao existir o DIRECTORY ele sera criado
WORKDIR /app

# ORIGIN (na maquina), para o DESTINO (dentro do docker)
COPY target/rlspfood*.jar /app/rlspfood.jar

# Diz em qual PORTA o Container estara escutando
# Nao existe a publicacao da porta como no comando docker (-p 8080:80), mas e apenas uma documentacao
EXPOSE 8080

# Comando padrao que sera usado na inicializcao do container
# Recomando estar dentro de um ARRAY
# DIRECTORY base sera o espeficicado no WORKDIR=/app
CMD ["java", "-jar", "rlspfood.jar"]


# ####
# Para construir a imagem temos que estar no mesmo diretorio raiz do projeto e digitar
# $ docker image build -t rlspfood:1.0 .
# $ docker container run --rm -p 8080:8080 rlspfood    (--rm = para remover o app apos
# ###