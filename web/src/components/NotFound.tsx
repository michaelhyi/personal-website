"use client";

import Center from "./Center";
import Container from "./Container";

export default function NotFound() {
  return (
    <Container absoluteFooter>
      <Center>
        <div className="text-[10px] text-neutral-300">404, not found.</div>
      </Center>
    </Container>
  );
}
