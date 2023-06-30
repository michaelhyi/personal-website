import Image from "next/image";

export default function Home() {
  return (
    <div className="flex w-full items-center justify-center">
      <div className="max-w-[768px]">
        <div className="mt-48 text-3xl font-bold">Michael Yi</div>
        <div className="mt-6">
          Hi! I'm <span className="font-bold">Michael</span>, a software
          engineer based in Los Angeles, CA. I'm a first-year @{" "}
          <a
            className="text-pink-300 cursor-pointer duration-500 hover:opacity-50 font-semibold"
            rel="noopener noreferrer"
            target="_blank"
            href="https://www.gatech.edu/"
          >
            Georgia Tech
          </a>{" "}
          pursuing a B.S. in Computer Science.
        </div>
        <Image
          unoptimized={true}
          src="/Michael.png"
          alt="me"
          height={250}
          width={250}
        />
        <div>
          I'm passionate about the intersection of software, AI, &
          entrepreneurship. I'm currently a SWE Intern @ Ardent Labs, I was the
          Executive Director @{" "}
          <a
            className="text-pink-300 cursor-pointer duration-500 hover:opacity-50 font-semibold"
            rel="noopener noreferrer"
            target="_blank"
            href="https://www.joinhealthhacks.com/"
          >{`health{hacks}`}</a>
          , a healthcare hackathon dedicated to disrupting healthcare with
          engineering & business. I was also a SWE Intern @{" "}
          <a
            className="text-pink-300 cursor-pointer duration-500 hover:opacity-50 font-semibold"
            rel="noopener noreferrer"
            target="_blank"
            href="https://megaevolution.io/"
          >
            MegaEvolution
          </a>
          , a Web3 Influencer Marketing startup.
        </div>
        <div className="text-xs absolute bottom-4 text-center">
          &copy; 2023 Michael Yi. All Rights Reserved.
        </div>
      </div>
    </div>
  );
}
