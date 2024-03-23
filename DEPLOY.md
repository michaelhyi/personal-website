## Deployment

# MySQL Server

1. [Launch a new EC2 Instance](https://us-east-2.console.aws.amazon.com/ec2/home?region=us-east-2#LaunchInstances:).
2. Make sure that the OS is set to `Amazon Linux 2023 AMI`. Architecture should be set to `64-bit (x86)`.
3. Click `Create new key pair`. Make sure that `Key pair type` is set to `RSA` and the `Private key file format` is set to `.pem`.
4. Under `Network settings`, set the `VPC` to the default VPC. Set the `Subnet` to `us-east-2a`. Under `Inbound Security Group Rules`, make sure that ports `3306` and `6379` are open to all sources and `22` is set to your IP address only.
5. Only on your first time using the keypair, run the following command.

```shell
chmod 400 /path/to/keypair
```

6. Connect to the EC2 instance.

```shell
ssh -i /path/to/keypair ec2-user@<EC2 Instace IP Address>
```

7. Install MySQL.

```shell
sudo wget https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm 
sudo dnf install mysql80-community-release-el9-1.noarch.rpm -y
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2023
sudo dnf install mysql-community-server -y
sudo systemctl start mysqld
sudo grep 'temporary password' /var/log/mysqld.log
```

8. Use the temporary password output to login to MySQL. Subsequently, update the root password.

```shell
mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY '<NEW PASSWORD>'; 
```

9. Create a new user, database, and grant privileges to the user.

```shell
CREATE USER '<USERNAME>'@'%' IDENTIFIED BY '<PASSWORD>';
CREATE DATABASE personal_website_api_db_prod;
GRANT ALL PRIVILEGES ON personal_website_api_db_prod.* TO '<USERNAME>'@'%';
FLUSH PRIVILEGES;
```

10. Initialize the database with tables.

```shell
USE personal_website_api_db_prod;
# copy the code from V1__Initial_Setup.sql in ./api/main/resources/db/migrations
```

Repeat the above steps to create a test database server for running integration tests.

# Redis Server

1. Connect to the same EC2 instance.

```shell
ssh -i /path/to/keypair ec2-user@<EC2 Instace IP Address>
``` 

2. Install Redis.

```shell
sudo dnf install -y redis6
sudo systemctl start redis6
sudo systemctl enable redis6
```

3. Generate a new Redis password.

```shell
openssl rand -base64 512
```

4. Edit the Redis config file.

```shell
cd /etc
sudo vi redis6
```

5. Select redis6.conf.
6. Find `# requirepass foobared`, remove the `#`, and replace `foobared` with the generated password from Step 3.
7. Find `bind 127.0.0.1 -::1`, and comment it out.
8. Restart the Redis service.

```shell
sudo systemctl restart redis6
```

Repeat the steps above to create a test Redis server for running tests. However, skip Step 6 and be sure to set `protected-mode yes` to `protected-mode no` in `redis6.conf`. 

# Spring Boot API Deployment 

1. [Launch a new EC2 Instance](https://us-east-2.console.aws.amazon.com/ec2/home?region=us-east-2#LaunchInstances:).
2. Make sure that the OS is set to `Amazon Linux 2023 AMI`. Architecture should be set to `64-bit (x86)`.
3. Click `Create new key pair`. Make sure that `Key pair type` is set to `RSA` and the `Private key file format` is set to `.pem`.
4. Under `Network settings`, set the `VPC` to the default VPC. Set the `Subnet` to `us-east-2a`. Under `Inbound Security Group Rules`, make sure that ports `22`, `80`, and `443` are open to all sources.
5. Only on your first time using the keypair, run the following command.

```shell
chmod 400 /path/to/keypair
```

6. Connect to the EC2 instance.

```shell
ssh -i /path/to/keypair ec2-user@<EC2 Instace IP Address>
```

7. Install Docker and start the Docker service.
```shell
sudo yum install -y docker
sudo service docker start
```

8. Login to Docker Hub.

> Navigate to [Docker Hub](https://hub.docker.com/).
> Click on your profile picture and click `My Account`.
> Navigate to `Security`, and click `New Access Token`.
> Write a brief description, and be sure to save or write down your generated token.

```shell
sudo docker login -u michaelyi -p <Docker Hub Token>
```

9. Install, configure, and start Nginx.
```shell
sudo yum update
sudo yum install nginx
sudo vi /etc/yum.repos.d/nginx.repo
>> Add this
[nginx]
name=nginx repo
baseurl=https://nginx.org/packages/mainline/<OS>/<OSRELEASE>/$basearch/
gpgcheck=0
enabled=1 
<<
```

10. Update your Nginx configuration file.

```shell
vi /etc/nginx/conf.d/default.conf
>> Add this
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
<<
sudo service nginx start
```

11. Generate SSL certificates using OpenSSl.

```shell
sudo mkdir /etc/ssl/private
sudo chmod 700 /etc/ssl/private
sudo openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/ssl/private/nginx-selfsigned.key -out /etc/ssl/certs/nginx-selfsigned.crt
```

12. Create a CNAME record in Cloudflare. Set the `name` equal to your subdomain, and the `target` equal to the address listed under `Public IPv4 DNS` for your EC2 instance.

# Setting Github Secrets

1. Prior to merging PR's to the `prod` branch, go through each file ending in `-cd.yml` in `.github/workflows`. Set `jobs.deploy.env.VERSION` to the version of the corresponding app.
2. Navigate to the [GitHub repository](https://github.com/michaelhyi/personal-website), click `Settings`. Under `Security`, click `Secrets and variables` and `Actions`.
3. Set all secrets.

> Set `API_AUTH_WHITELISTED_EMAILS` to a comma separated list of authorized emails for the admin platform.
> Set `API_AWS_ACCESS_KEY` to your AWS Access Key.
> Set `API_AWS_SECRET_KEY` to your AWS Secret Key.
> Set `API_SECURITY_CORS_ALLOWED_ORIGINS` to a comma separated list of CORS allowed origins.
> Set `API_SECURITY_JWT_SECRET_KEY` to your JWT signing key.
> Set `API_SPRING_CACHE_HOST` to the address listed under `Public IPv4 DNS` for your EC2 instance that hosts your Redis server.
> Set `API_SPRING_DATASOURCE_PASSWORD` to the MySQL user password. 
> Set `API_SPRING_DATASOURCE_URL` to the JDBC url, but replace the host with the address listed under `Public IPv4 DNS` for your EC2 instance that hosts your MySQL server.
> Set `API_SPRING_DATASOURCE_USERNAME` to the MySQL user username.
> Set `API_SPRING_DATA_REDIS_PASSWORD` to the Redis server authentication password. 
> Set `API_SPRING_TEST_DATASOURCE_URL` to the JDBC url, but replace the host with the address listed under `Public IPv4 DNS` for your EC2 instance that hosts your MySQL test server. 
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