"use client";

import React from "react";
import Container from "./Container";
import Spinner from "./Spinner";

export default function Loading() {
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
        <Spinner />
      </div>
    </Container>
  );
}
