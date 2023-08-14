import Image from "next/image";
import { iconLinks } from "../../data/iconLinks";
import IconLink from "../links/IconLink";
import Link from "../links/Link";

const About = () => {
  return (
    <div>
      <div className="mt-36 text-3xl font-bold">Michael Yi</div>
      <br />
      <div>Software Engineer based in Atlanta, GA</div>
      <div>
        B.S. in Computer Science&nbsp;
        <Link href="https://www.gatech.edu/">@ Georgia Tech</Link>
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
        In my free time, I enjoy playing the violin/guitar. I also enjoy going
        on walks and eating açaí bowls, bingsu, and mangoes.
      </div>
      <br />
      <div>
        I&apos;m <span className="font-bold">open to freelance</span>,
        please&nbsp;
        <Link href="mailto:michaelyi@gatech.edu">contact</Link>&nbsp;me if
        interested.
      </div>
    </div>
  );
};

export default About;
