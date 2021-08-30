FROM 10.1.8.15:5000/paas_platform/nl.taas.jdk:1.8.201_update
ADD Dockerfile /
RUN mkdir /usr/local/nldata
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
ADD target/spring-boot-vue*.jar /usr/local/nldata
ENTRYPOINT ["java","-jar /usr/local/nldata/spring-boot-vue*.jar"]
EXPOSE 8080