## Development Setup

#### Pre-requisites:

- Node
- Gradle
- MySQL 
- Redis 
- Docker 

1. Clone the repository and navigate to the project.

```shell
git clone https://github.com/michaelhyi/personal-website.git
cd personal-website
```

2. Set up environment variables.

Set environment variables by pasting the following in `.zshrc` (if you're using zsh) or running the following in your terminal:

```shell
export PERSONAL_WEBSITE_AWS_ACCESS_KEY=<your-aws-access-key>
export PERSONAL_WEBSITE_AWS_SECRET_KEY=<your-aws-secret-key>
export PERSONAL_WEBSITE_AWS_S3_BUCKET=<your-aws-s3-bucket>
export PERSONAL_WEBSITE_SECURITY_AUTH_ADMIN_PW=<your-admin-password>
export PERSONAL_WEBSITE_SECURITY_JWT_SECRET_KEY=<your-jwt-secret-key>
```

- To generate AWS secret keys, log in to your [AWS console](https://aws.amazon.com/). Click on your username, and click `Security credentials` on the dropdown menu. Scroll down to `Access keys`, and click `Create access key`.
- To set up an AWS S3 bucket, refer to Step 4.
- Set `PERSONAL_WEBSITE_SECURITY_AUTH_ADMIN_PW` equal to a secure, hashed password. Generate one using `openssl` or any password manager, and then hash it using Spring Security's BCryptPasswordEncoder.
- To generate a JWT secret key, use `openssl rand -base64 512`.

> For any environment variables that have a `$`, escape it with a backslash.

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
