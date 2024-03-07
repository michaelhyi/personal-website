## Development Setup

#### Pre-requisites:

- Node
- Maven
- MySQL 
- Redis 

1. Clone the repository and navigate to the project.

```shell
git clone https://github.com/michaelhyi/personal-website.git
cd personal-website
```

2. Install all dependencies.

```shell
sh install-deps.sh
```

> Alternatively, you can run `sh clean-install-deps.sh` to verify npm cache and clean the apps prior to installing dependencies.

3. In `api/src/main/resources`, create a file named `application.properties`. In `api/src/test/resources`, create a file named `application-it.resources`. Copy the following into both files:

```shell
auth.whitelisted-emails=

aws.access-key=
aws.secret-key=
aws.s3.bucket=personal-website-api-bucket-dev

security.cors.allowed-origins=http://localhost:3000,http://localhost:3001
security.jwt.secret-key=

spring.application.name=personal-website-api

spring.datasource.url=jdbc:mysql://localhost:3306/personal_website_api_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

spring.cache.type=redis
spring.cache.redis.time-to-live=60000
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

- In `application.properties`, set `auth.whitelisted-emails` equal to all authorized emails to the admin platform. Separate emails with commas. In `application-it.properties`, set `auth.whitelisted-emails` equal to `test@mail.com`.

> To generate AWS secret keys, log in to your [AWS console](https://aws.amazon.com/). Click on your username, and click `Security credentials` on the dropdown menu. Scroll down to `Access keys`, and click `Create access key`.

- Set `aws.access-key` equal to your AWS Access Key in both files.
- Set `aws.secret-key` equal to your AWS Secret Key in both files.
- In `application-it.properties`, change `aws.s3.bucket` to `personal-website-api-bucket-test`.

> Use `openssl rand -base64 512` to generate a secret key for JWT signing.

- Set `security.jwt.secret-key` equal to your generated secret key in both files.

> Using the instructions [here](https://github.com/michaelhyi/personal-website/blob/prod/DEPLOY.md) on how to create a MySQL database server using AWS EC2 instances, create a test MySQL server. 
- Update `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` to reflect the configuration to your test server.
- Add the following property to `application-it.properties`: `spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`.

4. Create a new MySQL database. 

> Create a new database called `personal_website_api_db`. Make sure that your MySQL master username and password are both set to `root`.

5. Create an AWS S3 bucket.

> Go to your [AWS S3 console](https://s3.console.aws.amazon.com/s3/home?region=us-east-2#). Create two buckets named `personal-website-api-bucket-dev` and `personal-website-api-bucket-test`. Make sure that you are using region `us-east-2`.

6. Run the projects.

> Open three terminal instances. Follow the below code blocks to run each app.

```shell
cd admin
npm start
```

```shell
cd api
mvn spring-boot:run
```

```shell
cd web
npm start
```

7. Fix linting errors using Prettier and ESLint.

```shell
# cd admin or web
npm run lint:fix
```

8. Run unit/integration tests on and build a production bundle for your Spring Boot application.
```shell
cd api
mvn -ntp verify
```