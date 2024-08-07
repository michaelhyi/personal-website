FROM nginx:alpine
WORKDIR /usr/share/nginx/html

RUN rm -rf /etc/nginx/conf.d
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY build .

EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]