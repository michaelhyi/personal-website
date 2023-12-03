"use client";

import React from "react";
import { Container } from "./Container";

export const NotFound = () => {
  return (
    <Container absoluteFooter>
      <div
        className="absolute
                   left-1/2
                   top-1/2
                   -translate-x-1/2
                   -translate-y-1/2
                   transform
                   text-[10px]
                   text-neutral-300"
      >
        404, not found.
      </div>
    </Container>
  );
};