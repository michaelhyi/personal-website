import Experience from "@/app/components/Experience";
import Footer from "@/app/components/Footer";
import HeadLink from "@/app/components/HeadLink";
import Link from "@/app/components/Link";
import { headLinks } from "@/app/data/headLinks";
import Image from "next/image";

const Home = () => {
  return (
    <div className="flex w-full items-center justify-center">
      <div className="w-[768px]">
        <div className="mt-48 text-3xl font-bold">Michael Yi</div>
        <br />
        <div>
          Hi! I'm <span className="font-bold">Michael</span>, a software
          engineer based in Atlanta, GA. I'm a first-year&nbsp;
          <Link href="https://www.gatech.edu/">@ Georgia Tech</Link> pursuing a
          B.S. in Computer Science.
        </div>
        <br />
        <div className="flex gap-16 items-center">
          <Image
            unoptimized={true}
            src="/Michael.png"
            alt="me"
            height={225}
            width={225}
          />
          <div className="flex flex-col gap-2">
            {headLinks.map((v) => (
              <HeadLink
                key={v.text}
                icon={v.icon}
                href={v.href}
                text={v.text}
              />
            ))}
          </div>
        </div>
        <br />
        <div>
          I <span className="font-bold">love</span> building. I'm passionate
          about the intersection of software, AI, & entrepreneurship where I
          apply my aptitude for leadership, communication, & passion to deliver
          exceptional results. I'm currently a SWE Intern&nbsp;
          <Link href="https://www.ardentlabs.io/">@ Ardent Labs</Link>.
          Previously, I was Executive Director&nbsp;
          <Link href="https://www.joinhealthhacks.com/">
            @ {`health{hacks}`}
          </Link>
          , SWE Intern&nbsp;
          <Link href="https://megaevolution.io/">@ MegaEvolution</Link>, &
          Research Intern&nbsp;
          <Link href="https://inklab.usc.edu/">@ USC INK Lab</Link>.
        </div>
        <br />
        <div>
          In my free time, I love listening to & performing music. I play the
          violin, piano, & guitar. My favorite composers are Chopin,
          Mendelssohn, & Tchaikovsky, & my favorite bands are The Beatles & The
          Velvet Underground.
        </div>
        <br />
        <br />
        <Experience />
        <br />
        <div className="font-bold text-2xl">Projects</div>
        <br />
        <Footer />
      </div>
    </div>
  );
};

export default Home;
