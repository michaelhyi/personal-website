## Development Setup

#### Pre-requisites:

- Node
- Maven
- PostgreSQL

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

3. Create properties files for Spring Boot.

```shell
cd api/src/main/resources
cp example.properties application.properties
```
- Set `auth.whitelisted-emails` equal to all authorized emails to the admin platform. Separate emails with commas.

> To generate AWS secret keys, log in to your [AWS console](https://aws.amazon.com/). Click on your username, and click `Security credentials` on the dropdown menu. Scroll down to `Access keys`, and click `Create access key`.

- Set `aws.access-key` equal to your AWS Access Key.
- Set `aws.secret-key` equal to your AWS Secret Key.

> Use `openssl rand -base64 512` to generate a secret key for JWT signing.

- Set `security.jwt.secret-key` equal to your generated secret key.

4. Create a new PostgreSQL database. 

> Create a new database called `personal_website_api_db`. Make sure that your Postgres master username and password are both set to `postgres`.

5. Create an AWS S3 bucket.

> Go to your [AWS S3 console](https://s3.console.aws.amazon.com/s3/home?region=us-east-2#). Create a bucket named `personal-website-api-bucket-dev`. Make sure that you are using region `us-east-2`.

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

5. Fix linting errors using Prettier and ESLint.

```shell
npm run lint:fix
```