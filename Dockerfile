FROM adoptopenjdk:11-jdk-hotspot as builder

ARG BANKING_RELATIVE_DIR 
ARG IS_DEVELOPMENT_MODE

RUN mkdir -p ${BANKING_RELATIVE_DIR}
WORKDIR ${BANKING_RELATIVE_DIR}

EXPOSE 8080

ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update
RUN apt-get install git wget zip unzip build-essential vim -y
ARG DEBIAN_FRONTEND=interactive

# Install MyBatis Migrations
RUN wget https://github.com/mybatis/migrations/releases/download/mybatis-migrations-3.3.9/mybatis-migrations-3.3.9-bundle.zip
RUN unzip mybatis-migrations-3.3.9-bundle.zip && rm mybatis-migrations-3.3.9-bundle.zip
RUN mv mybatis-migrations-3.3.9/* /usr/local/bin

COPY build.gradle.kts gradlew ./
COPY gradle gradle

COPY . .

RUN if [ "$IS_DEVELOPMENT_MODE" = "false" ] ; then \
    { \
        ./gradlew dependencies; \
        ./gradlew bootJar; \
        cp ${BANKING_RELATIVE_DIR}/build/libs/*.jar app.jar; \
    } \
    fi

ENTRYPOINT if [ "$IS_DEVELOPMENT_MODE" = "false" ] ; then \
        java -jar app.jar; \
    else \
        ./gradlew bootRun; \
    fi

