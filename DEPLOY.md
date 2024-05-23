## Deployment

# MySQL Server on Amazon Linux 2023

1. Install MySQL.

```shell
sudo wget https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm 
sudo dnf install mysql80-community-release-el9-1.noarch.rpm -y
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2023
sudo dnf install mysql-community-server -y
sudo systemctl start mysqld
sudo grep 'temporary password' /var/log/mysqld.log
```

2. Use the temporary password output to login to MySQL. Subsequently, update the root password.

```shell
mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY '<NEW PASSWORD>'; 
```

3. Create a new user, database, and grant privileges to the user.

```shell
CREATE USER '<USERNAME>'@'%' IDENTIFIED BY '<PASSWORD>';
CREATE DATABASE personal_website_api;
GRANT ALL PRIVILEGES ON personal_website_api.* TO '<USERNAME>'@'%';
FLUSH PRIVILEGES;
```

4. Initialize the database with tables.

```shell
USE personal_website_api;
# copy the code from V1__Initial_Setup.sql in ./api/main/resources/db/migrations
```

# Redis Server on Amazon Linux 2023

1. Install Redis.

```shell
sudo dnf install -y redis6
```

2. Generate a new Redis password.

```shell
openssl rand -base64 512
```

3. Edit the Redis config file.

```shell
sudo vi /etc/redis6
```

4. Select redis6.conf.
5. Find `# requirepass foobared`, remove the `#`, and replace `foobared` with the generated password from Step 3.
6. Find `bind 127.0.0.1 -::1`, and comment it out.
7. Start the Redis service.

```shell
sudo systemctl start redis6
sudo systemctl enable redis6
```

# Spring Boot API Deployment 

1. Install Docker and start the Docker service.
```shell
sudo yum install -y docker
sudo service docker start
```

2. Login to Docker Hub.

> Navigate to [Docker Hub](https://hub.docker.com/).
> Click on your profile picture and click `My Account`.
> Navigate to `Security`, and click `New Access Token`.
> Write a brief description, and be sure to save or write down your generated token.

```shell
sudo docker login -u michaelyi -p <Docker Hub Token>
```

3. Install, configure, and start Nginx.
```shell
sudo yum install -y nginx
sudo vi /etc/yum.repos.d/nginx.repo
```

4. Paste the following code.
```
[nginx]
name=nginx repo
baseurl=https://nginx.org/packages/mainline/<OS>/<OSRELEASE>/$basearch/
gpgcheck=0
enabled=1 
```

5. Update your Nginx configuration file.
```shell
sudo vi /etc/nginx/conf.d/default.conf
```

6. Paste the following code.
```
upstream server {
  server 127.0.0.1:8080;
}
server {
listen              443 ssl default_server;
listen              [::]:443 ssl default_server;
server_name  localhost;
ssl_certificate /etc/ssl/certs/nginx-selfsigned.crt;
ssl_certificate_key /etc/ssl/private/nginx-selfsigned.key;
 
 location / {
  proxy_pass http://server;
  proxy_set_header Upgrade $http_upgrade;
  proxy_set_header Connection "Upgrade";
  proxy_set_header Host            $host;
  proxy_set_header X-Real-IP       $proxy_protocol_addr;
  proxy_set_header X-Forwarded-For $proxy_protocol_addr;
  proxy_read_timeout 600s;
 }
}
```

7. Generate SSL certificates using OpenSSl.
```shell
sudo mkdir /etc/ssl/private
sudo chmod 700 /etc/ssl/private
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/ssl/private/nginx-selfsigned.key -out /etc/ssl/certs/nginx-selfsigned.crt
sudo service nginx start
```

8. Create a CNAME record in Cloudflare. Set the `name` equal to your subdomain, and the `target` equal to the address listed under `Public IPv4 DNS` for your EC2 instance.

# Setting Github Secrets

1. Prior to merging PR's to the `prod` branch, go through each file ending in `-cd.yml` in `.github/workflows`. Set `jobs.deploy.env.VERSION` to the version of the corresponding app.
2. Navigate to the [GitHub repository](https://github.com/michaelhyi/personal-website), click `Settings`. Under `Security`, click `Secrets and variables` and `Actions`.
3. Set all secrets.

> Set `API_AUTH_WHITELISTED_EMAILS` to a comma separated list of authorized emails for the admin platform.
> Set `API_AWS_ACCESS_KEY` to your AWS Access Key.
> Set `API_AWS_SECRET_KEY` to your AWS Secret Key.
> Set `API_AWS_S3_BUCKET` to your production AWS S3 bucket name.
> Set `API_SECURITY_JWT_SECRET_KEY` to your JWT signing key.
> Set `API_SPRING_DATASOURCE_PASSWORD` to the MySQL user password. 
> Set `API_SPRING_DATASOURCE_URL` to the JDBC url, but replace the host with the address listed under `Public IPv4 DNS` for your EC2 instance that hosts your MySQL server.
> Set `API_SPRING_DATASOURCE_USERNAME` to the MySQL user username.
> Set `API_SPRING_DATA_REDIS_HOST` to the address listed under `Public IPv4 DNS` for your EC2 instance that hosts your Redis server.
> Set `API_SPRING_DATA_REDIS_PASSWORD` to the Redis server authentication password. 
> Set `DOCKERHUB_TOKEN` to your Docker Hub token.
> Set `SSH_HOST` to the IP address of the EC2 instance hosting the Spring Boot app.
> Set `SSH_PRIVATE_KEY` to the content in the keypair that authorizes SSH connections to the EC2 instance hosting the Spring Boot app.

# Deployment to Vercel

1. Login to [Cloudflare](https://www.cloudflare.com/). Click on your domain and click `DNS`.
2. Create `A` records for the main domain as well as any subdomains. Set the `Content` to `76.76.21.21`. Set `Proxy status` to `DNS only`.
3. Create `CNAME` records for `www` and `www.<subdomain>`, and set their `Content` to `cname.vercel-dns.com`. Set `Proxy status` to `DNS only`. 
4. Login to [Vercel](https://vercel.com/).
5. Click `Add new project`. Import your repository, set `Framework Preset` to `Create React App`, and set the `Root Directory` to your React app path.
6. Click `Deploy`.
7. Repeat `Steps 1-3` for all React apps in the repository. 
8. Once deployment has finished, navigate to each project, click `Settings`, and click `Domains`. Type in the corresponding domain name.

Now, simply merge commits to the `prod` branch and you've successfully deployed your code!
