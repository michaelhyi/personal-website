import Center from "@/components/Center";
import Container from "@/components/Container";
import Image from "next/image";

export default function Lauren() {
  return (
    <Container absoluteFooter>
      <Center>
        <div className="flex flex-col items-center gap-8">
          <Image
            src="/lauren.png"
            width={250}
            height={250}
            alt="mimi and lala"
          />
          <div className="text-sm font-light">coming soon...</div>
        </div>
      </Center>
    </Container>
  );
}
