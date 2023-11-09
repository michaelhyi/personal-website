"use client";

import { Spinner } from "@chakra-ui/react";
import Container from "./Container";

const Loading = () => {
  return (
    <Container absoluteFooter>
      <Spinner size="xs" color="white" />
    </Container>
  );
};

export default Loading;
