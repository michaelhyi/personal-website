import Image from "next/image";
import { iconLinks } from "../../data/iconLinks";
import IconLink from "../links/IconLink";
import Link from "../links/Link";

const About = () => {
  return (
    <div>
      <div className="mt-36 text-3xl font-bold">Michael Yi</div>
      <br />
      <div>
        Hi! I&apos;m <span className="font-bold">Michael</span>, a software
        engineer based in Los Angeles, CA. I&apos;m pursuing a B.S. in Computer
        Science&nbsp;<Link href="https://www.gatech.edu/">@ Georgia Tech</Link>
        &nbsp;with concentrations in&nbsp;
        <Link href="https://www.cc.gatech.edu/academics/threads/intelligence">
          Intelligence
        </Link>
        &nbsp;&amp;&nbsp;
        <Link href="https://www.cc.gatech.edu/academics/threads/information-internetworks">
          Information Internetworks
        </Link>
        .
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
          {iconLinks.map((v) => (
            <IconLink key={v.text} icon={v.icon} href={v.href} text={v.text} />
          ))}
        </div>
      </div>
      <br />
      <div>
        I <span className="font-bold">love</span> building. I&apos;m interested
        in the intersection of software, AI, and entrepreneurship where I apply
        my aptitude for leadership, communication, and passion to deliver
        exceptional results.
      </div>
      <br />
      <div>
        I also love music. I&apos;ve played the violin for 9 years and the
        guitar for a year. My favorite composers are Tchaikovsky, Chopin, and
        Mendelssohn; my favorite bands are The Beatles and The Velvet
        Underground. I also enjoy going on walks and eating açaí bowls, bingsu,
        and mangoes.
      </div>
      <br />
      <div>
        I'm <span className="font-bold">open to freelance</span>, please&nbsp;
        <Link href="mailto:michaelyi@gatech.edu">contact</Link>&nbsp;me if
        interested.
      </div>
    </div>
  );
};

export default About;
