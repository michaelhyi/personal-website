FROM nginx:1.19.0
WORKDIR /usr/share/nginx/html
COPY build . 
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]