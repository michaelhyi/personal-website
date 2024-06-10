## Development Setup

#### Pre-requisites:

- Node
- Maven
- MySQL 
- Redis 
- Docker 

1. Clone the repository and navigate to the project.

```shell
git clone https://github.com/michaelhyi/personal-website.git
cd personal-website
```

2. In `backend/src/main/resources`, create a file named `application.properties`. Copy the following into the file:

```shell
api.version-path=/v1

auth.admin-pw=

aws.access-key=
aws.secret-key=
aws.s3.bucket=

security.cors.allowed-origins=http://localhost:3000,http://localhost:3001
security.jwt.secret-key=

spring.application.name=personal-website-api

spring.datasource.url=jdbc:mysql://localhost:3306/personal_website_api
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

spring.data.redis.host=localhost
spring.data.redis.port=6379

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

- In `application.properties`, set `auth.admin-pw` equal to a secure, hashed password. Generate one using `openssl` or any password manager.

> To generate AWS secret keys, log in to your [AWS console](https://aws.amazon.com/). Click on your username, and click `Security credentials` on the dropdown menu. Scroll down to `Access keys`, and click `Create access key`.

- Set `aws.access-key` equal to your AWS Access Key in both files.
- Set `aws.secret-key` equal to your AWS Secret Key in both files.

> Use `openssl rand -base64 512` to generate a secret key for JWT signing.

- Set `security.jwt.secret-key` equal to your generated secret key in both files.

> Using the instructions [here](https://github.com/michaelhyi/personal-website/blob/prod/DEPLOY.md) on how to create a MySQL database server using AWS EC2 instances, create a test MySQL server. 
- Update `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` to reflect the configuration to your test server.
- Add the following property to `application-it.properties`: `spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`.

3. Create a new MySQL database. 

> Create a new database called `personal_website_api`. Make sure that your MySQL master username and password are both set to `root`.

4. Create an AWS S3 bucket.

> Go to your [AWS S3 console](https://s3.console.aws.amazon.com/s3/home?region=us-west-2#). Create two buckets for storing blog images, one for dev and one for prod. Use a UUID generator to ensure uniqueness of bucket name. Make sure that you are using region `us-west-2`.

5. Install dependencies and run the projects.

> Alternatively, you can run `sh clean-setup.sh` to verify npm cache and clean the apps prior to installing dependencies.
> Open three terminal instances. Follow the below code blocks to run each app.

```shell
cd frontend/admin 
npm i
npm start
```

```shell
cd backend 
./gradlew bootRun
```

```shell
cd frontend/web 
npm i
npm start
```

6. Fix linting errors using Prettier and ESLint.

```shell
# cd frontend/admin or frontend/web
npm run lint:fix
```
