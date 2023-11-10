<div align="center">
  <img src="https://www.michael-yi.com/michael.png" width="100" height="100" />
  <h1>michael-yi.com</h1>
  © 2023 Michael Yi, All Rights Reserved.
  <br/>
  <br/>
  <a href="https://www.michael-yi.com/">Website</a>
</div>

<hr/>

### Built With 

- [Turborepo](https://turbo.build/)
- [Java](https://www.java.com/en/)
- [Spring Framework](https://spring.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [JUnit](https://junit.org/junit5/)
- [TypeScript](https://www.typescriptlang.org/)
- [Next.js](https://nextjs.org/)
- [TailwindCSS](https://tailwindcss.com/)
- [Docker](https://www.docker.com/)
- [Kubernetes](https://kubernetes.io/)
- [AWS EC2, EBS, RDS](https://aws.amazon.com/)

### Repo Activity

![Alt](https://repobeats.axiom.co/api/embed/0d0e559984591c9b57adbc13a96171939ad77a0f.svg "Repobeats analytics image")

### Getting Started

#### Prerequisites:

- Node
- Maven
- Docker
- Bun

#### Setup:

1. Clone the repo.

```shell
git clone https://github.com/michaelhyi/personal-website.git
```

2. Navigate to the project directory.

```shell
cd personal-website
```

3. Run docker-compose and installation of Maven & Node dependencies/packages.

```shell
bun ci
```

4. Navigate to the website application directory.

```shell
cd apps/web
```

5. Create a file for environment variables.

> Copy `.env.example` into a `.env.local` file in the root of the Next.js project.
> 
> Set `NEXT_PUBLIC_API_URL` equal to your Spring Boot / API url.

6. Navigate back to the root of the project.

```shell
cd ../../
```

1. Run the project, starting both Spring Boot and Next.js applications.

```shell
bun dev
```

8. Format the project using Prettier and ESLint.

```shell
bun format
```

9. Build the project using Maven packaging and Next.js.

```shell
bun run build
```

10. Lint the project using Checkstyle, Prettier, and ESLint.

```shell
bun lint
```

11. Run unit and integration testing using JUnit and E2E testing using Cypress.

```shell
bun run test
```