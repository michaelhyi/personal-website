FROM node:18
WORKDIR /usr/src/app
COPY . . 

RUN npm ci
RUN npm run lint
RUN npm run build
ENTRYPOINT [ "npm", "start" ]