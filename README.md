<div align="center">
  <img src="https://michael-yi.com/michael.png" width="100" height="100" />
  <h2>Michael Yi's Personal Website</h2>
  <p>© 2023 Michael Yi, All Rights Reserved.</p>
  <a href="https://michael-yi.com/">Website</a>
</div>

<hr/>

## Project Stats

![Alt](https://repobeats.axiom.co/api/embed/0d0e559984591c9b57adbc13a96171939ad77a0f.svg "Repobeats analytics image")

## Tech Stack

- [Java](https://www.java.com/en/)
- [Spring Framework](https://spring.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [AWS EC2, S3](https://aws.amazon.com/)
- [Docker](https://www.docker.com/)
- [JUnit](https://junit.org/junit5/)
- [TypeScript](https://www.typescriptlang.org/)
- [Next.js](https://nextjs.org/)
- [TailwindCSS](https://tailwindcss.com/)

## Development Setup

#### Pre-requisites:

- Node
- Maven
- Docker
- Yarn

1. Clone the repository using git.

```shell
git clone https://github.com/michaelhyi/personal-website.git
```

2. Navigate to the project directory.

```shell
cd personal-website
```

3. Install all dependencies and packages using npm, Maven, and docker-compose.

```shell
yarn ci
```

4. Create a `.env` file to store environment variables.

> Copy `.env.example` into a `.env` file in the root of the project.

5. Generate all secrets and keys.

> To generate AWS secret keys, log in to your [AWS console](https://aws.amazon.com/). Click on your username, and click `Security credentials` on the dropdown menu. Scroll down to `Access keys`, and click `Create access key`. Paste the Access Key into `API_AWS_ACCESS_KEY` and the Secret Key into `API_AWS_SECRET_KEY` in `.env` below.

> Use `openssl rand -base64 512` to generate a secret key for JWT signing. Paste it into `API_SECURITY_JWT_SECRET_KEY` and `NEXTAUTH_SECRET` in `.env`.

6. Add environment variables to `.env` file.

```shell
API_SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/personal_website_api_db // db url
API_SPRING_DATASOURCE_USERNAME=postgres // db username
API_SPRING_DATASOURCE_PASSWORD=postgres // db password
API_SPRING_JPA_HIBERNATE_DDL_AUTO=update // use create-drop or update for dev, validate for prod

API_AUTH_WHITELISTED_EMAILS="user@mail.com" // whitelisted emails for user authentication

API_AWS_ACCESS_KEY= // aws access key
API_AWS_SECRET_KEY= // aws secret key
API_AWS_REGION=us-east-2 // aws region
API_AWS_S3_BUCKETS_BLOG=personal-website-api-bucket // bucket name of

API_SECURITY_JWT_SECRET_KEY= // secret key, generate using
API_SECURITY_CORS_ALLOWED_ORIGINS="*" // allowed origins for cors

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=

NEXTAUTH_SECRET=
NEXTAUTH_URL=http://localhost:3001 // url of the admin app

NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1 // url of the api
NEXT_PUBLIC_WEB_URL=http://localhost:3000 // url of the main web app
```

6. Run the project, starting both Spring Boot and Next.js applications.

```shell
yarn dev
```

6. Format the project using Prettier and ESLint.

```shell
yarn format
```

7. Build the project using Maven packaging and Next.js.

```shell
yarn build
```

8. Lint the project using Checkstyle, Prettier, and ESLint.

```shell
yarn lint
```

9. Run unit and integration testing using JUnit and E2E testing using Cypress.

```shell
yarn run test
```

## Deployment

### Postgres Server Setup

1. Login to [AWS console](https://aws.amazon.com/).
2. Navigate to the EC2 dashboard, and click `Launch instance`.
3. Provide a name for the server,
