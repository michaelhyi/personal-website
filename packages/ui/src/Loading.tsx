"use client";

import { Spinner } from "@chakra-ui/react";
import React from "react";
import { Container } from "./Container";

export const Loading = () => {
  return (
    <Container absoluteFooter>
      <div
        className="absolute
                   left-1/2
                   top-1/2
                   -translate-x-1/2
                   -translate-y-1/2
                   transform
                   text-neutral-300"
      >
        <Spinner size="xs" />
      </div>
    </Container>
  );
};
