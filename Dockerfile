FROM openjdk:17

RUN mkdir /var/rinha/

WORKDIR /var/rinha/

COPY ./interpretador.jar /var/rinha/

ENTRYPOINT ["java","-jar","/var/rinha/interpretador.jar"]
