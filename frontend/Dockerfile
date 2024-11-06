FROM node:22-bookworm-slim AS base
WORKDIR /app
ENV NG_CLI_ANALYTICS=ci
RUN npm install -g @angular/cli

#--- Development
FROM base AS development
RUN apt-get update && \
    apt-get install -y git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
# Switching user so it doesn't use root
# If root adds files, it takes ownership, making them unremovable on the host except by using sudo
# node tranfers ownership to the host user
USER node
EXPOSE 4200
CMD ["tail","-f","/dev/null"]

#--- Building
FROM base AS build
COPY package*.json ./
RUN npm install
# Copied here, since the the Development setup uses a bind volume instead
COPY . .
RUN ng build --configuration production

#--- Production
FROM nginx:alpine AS production
COPY --from=build /app/dist/quiz/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]