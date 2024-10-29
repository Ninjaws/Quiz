FROM openjdk:17-jdk-slim AS base
WORKDIR /app
RUN apt-get update && apt install -y \
    maven \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

#---Development
FROM base AS development
# Switching user so it doesn't use root
# If root adds files, it takes ownership, making them unremovable on the host except by using sudo
# node tranfers ownership to the host user
ENV UID=1000
ENV GID=1000
ENV USER=dev
RUN groupadd -g ${GID} ${USER} \
    && useradd -m -u ${UID} -g ${USER} ${USER}
USER ${USER}
EXPOSE 8080
CMD ["tail","-f","/dev/null"]

#---Building



#---Production