"use client";

import HeadLink from "@/app/components/links/HeadLink";
import Link from "@/app/components/links/Link";
import { headLinks } from "@/app/data/headLinks";
import Image from "next/image";

const About = () => {
  return (
    <div>
      <div className="mt-36 text-3xl font-bold">Michael Yi</div>
      <br />
      <div>
        Hi! I&apos;m <span className="font-bold">Michael</span>, a software
        engineer based in Atlanta, GA. I&apos;m a first-year&nbsp;
        <Link href="https://www.gatech.edu/">@ Georgia Tech</Link> pursuing a
        B.S. in Computer Science.
      </div>
      <br />
      <div className="flex sm:flex-col md:flex-row sm:gap-8 md:gap-16 items-center">
        <Image
          unoptimized={true}
          src="/Michael.png"
          alt="me"
          height={225}
          width={225}
        />
        <div className="flex flex-col gap-2">
          {headLinks.map((v) => (
            <HeadLink key={v.text} icon={v.icon} href={v.href} text={v.text} />
          ))}
        </div>
      </div>
      <br />
      <div>
        I <span className="font-bold">love</span> building. I&apos;m interested
        in the intersection of software, AI, and entrepreneurship where I apply
        my aptitude for leadership, communication, and passion to deliver
        exceptional results. I&apos;m currently a SWE Intern&nbsp;
        <Link href="https://www.ardentlabs.io/">@ Ardent Labs</Link>, but I was
        previously the Executive Director&nbsp;
        <Link href="https://www.joinhealthhacks.com/">@ {`health{hacks}`}</Link>
        , a SWE Intern&nbsp;
        <Link href="https://megaevolution.io/">@ MegaEvolution</Link>, and a
        Research Intern&nbsp;
        <Link href="https://inklab.usc.edu/">@ USC INK Lab</Link>.
      </div>
      <br />
      <div>
        Apart from engineering, I love music. I&apos;ve played the violin for 9
        years, and I&apos;m currently learning the piano and guitar. My favorite
        composers are Chopin, Mendelssohn, and Tchaikovsky; my favorite bands
        are The Beatles and The Velvet Underground.
      </div>
    </div>
  );
};

export default About;
