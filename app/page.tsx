import Link from "@/app/components/Link";
import Image from "next/image";

export default function Home() {
  return (
    <div className="flex w-full items-center justify-center">
      <div className="max-w-[768px]">
        <div className="mt-48 text-3xl font-bold">Michael Yi</div>
        <div className="mt-6">
          Hi! I'm <span className="font-bold">Michael</span>, a software
          engineer based in Los Angeles, CA. I'm a first-year{" "}
          <Link href="https://www.gatech.edu/">@ Georgia Tech</Link> pursuing a
          B.S. in Computer Science.
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
          Executive Director{" "}
          <Link href="https://www.joinhealthhacks.com/">
            @ {`health{hacks}`}
          </Link>
          , a healthcare hackathon dedicated to disrupting healthcare with
          engineering & business. I was also a SWE Intern{" "}
          <Link href="https://megaevolution.io/">@ MegaEvolution</Link>, an
          Influencer Marketing startup.
        </div>
        <div className="text-xs absolute bottom-4 text-center left-0 right-0">
          &copy; 2023 Michael Yi. All Rights Reserved.
        </div>
      </div>
    </div>
  );
}
