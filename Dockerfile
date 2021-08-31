FROM 10.1.8.15:5000/paas_platform/nl.taas.jdk:1.8.201_update
RUN groupadd paasopt
RUN useradd -g paasopt -m paasopt -u 2000

ADD Dockerfile /
RUN mkdir -p /usr/local/nldata/logs
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
ADD target/spring-boot-vue*.jar /usr/local/nldata/app.jar
ADD log/*.log /usr/local/nldata/logs/
RUN chown -R paasopt:paasopt /usr/local/nldata
USER 2000
WORKDIR /usr/local/nldata
ENTRYPOINT ["java","-jar","/usr/local/nldata/app.jar"]
EXPOSE 8080