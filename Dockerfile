#Stage 1. Build
FROM gradle:7.5.1-jdk17-alpine as builder
WORKDIR /source
COPY --chown=gradle:gradle build.gradle ./
COPY --chown=gradle:gradle settings.gradle ./
COPY --chown=gradle:gradle gradle.properties ./
COPY --chown=gradle:gradle src ./src/
RUN gradle --no-daemon --console=plain bootJar

#Stage 2. Run
FROM openjdk:17.0.2-jdk-slim
ENV APP_ROOT /kameleoon
RUN groupadd --gid 1111 --system kameleoon \
    && useradd --uid 1111 --system --gid kameleoon kameleoon \
    && mkdir --parents ${APP_ROOT} \
    && chown --recursive kameleoon:kameleoon ${APP_ROOT}
USER kameleoon
WORKDIR ${APP_ROOT}
COPY --chown=kameleoon:kameleoon --from=builder /source/build/libs/kameleoon.jar ./
EXPOSE 8080
CMD ["java" , "-server", "-Xmx4G", "-Xms2G", "-jar", "kameleoon.jar"]
