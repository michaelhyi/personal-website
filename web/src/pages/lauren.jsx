import Center from "../components/Center";
import Container from "../components/Container";

export default function Lauren() {
  return (
    <Container absoluteFooter>
      <Center>
        <div className="flex flex-col items-center gap-4">
          <img
            src={require("../assets/lauren.png")}
            width={250}
            height={250}
            alt="mimi and lala"
          />
          <div className="text-sm font-light">soon...</div>
        </div>
      </Center>
    </Container>
  );
}
