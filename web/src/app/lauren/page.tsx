"use client";

import Center from "@/components/Center";
import Container from "@/components/Container";
import Hoverable from "@/components/Hoverable";
import Image from "next/image";
import { useCallback } from "react";
import { Toaster, toast } from "react-hot-toast";

export default function Lauren() {
  const valentine = useCallback(() => {
    toast.custom(({ visible }) => (
      <div
        className={`${visible ? "animate-enter" : "animate-leave"}
                  bg-green-400
                  flex
                  justify-center
                  shadow-lg
                  rounded-lg
                `}
      >
        <Image
          src="/funny-bunny.png"
          height={200}
          width={200}
          alt="funny-bunny"
        />
      </div>
    ));
  }, []);

  const sad = useCallback(() => {
    toast.custom(({ visible }) => (
      <div
        className={`${visible ? "animate-enter" : "animate-leave"}
                  bg-red-400
                  flex
                  justify-center
                  shadow-lg
                  rounded-lg
                `}
      >
        <div className="px-5 py-3 font-semibold text-sm">
          RAHHHHHHHHHHHHHH 😡😡😡😡😡 🧨🧨🧨🧨🧨 💣💣💣💣💣💣 🔥🔥🔥🔥🔥🔥🔥
        </div>
      </div>
    ));
  }, []);

  return (
    <Container absoluteFooter>
      <Center>
        <div className="flex flex-col items-center gap-4">
          <Image
            src="/lauren.png"
            width={250}
            height={250}
            alt="mimi and lala"
          />
          <div className="text-sm font-light">Will you be my valentine?</div>
          <div className="flex space-x-6 mt-5">
            <Hoverable>
              <button
                onClick={valentine}
                className="font-medium bg-green-400 px-4 py-2 rounded-lg shadow-sm"
              >
                Yes !!!!
              </button>
            </Hoverable>
            <Hoverable>
              <button
                onClick={sad}
                className="font-medium bg-red-400 px-4 py-2 rounded-lg shadow-sm"
              >
                No...
              </button>
            </Hoverable>
          </div>
        </div>
      </Center>
      <Toaster position="bottom-center" />
    </Container>
  );
}
