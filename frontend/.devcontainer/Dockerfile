FROM node:22-bookworm-slim

ARG WORKDIR
WORKDIR ${WORKDIR}

RUN apt-get update && \
    apt-get install -y git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Switching user so it doesn't use root
# If root adds files, it takes ownership, making them unremovable on the host except by using sudo
# node tranfers ownership to the host user
USER node

EXPOSE 4200

CMD ["tail","-f","/dev/null"]