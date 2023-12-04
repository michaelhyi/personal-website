import { redirect } from "next/navigation";
import { Container } from "ui";
import type { User } from "types";
import { readUserByEmail } from "services";

const Home = async () => {
  const user: User | null = await readUserByEmail();

  if (!user) {
    redirect("/login");
  }

  return (
    <Container>
      <div>hi</div>
    </Container>
  );
};

export default Home;
