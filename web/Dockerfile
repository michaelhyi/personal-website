FROM node:18
WORKDIR /usr/src/app
COPY . . 

ENV CI=true

RUN npm ci
RUN npm run build
ENTRYPOINT [ "npm", "start" ]