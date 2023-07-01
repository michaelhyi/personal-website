import HeadLink from "@/app/components/HeadLink";
import Link from "@/app/components/Link";
import { headLinks } from "@/app/data/headLinks";
import Image from "next/image";
import { AiFillHtml5 } from "react-icons/ai";
import {
  BiLogoCPlusPlus,
  BiLogoFigma,
  BiLogoGraphql,
  BiLogoJava,
  BiLogoJavascript,
  BiLogoMongodb,
  BiLogoPostgresql,
  BiLogoPython,
  BiLogoReact,
  BiLogoTailwindCss,
  BiLogoVisualStudio,
} from "react-icons/bi";
import { DiVim } from "react-icons/di";
import { SiPytorch, SiTensorflow } from "react-icons/si";

export default function Home() {
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
          about the intersection of software, AI, & entrepreneurship; I strive
          to deliver my aptitude for leadership, communication, passion to
          create exceptional results & impact. I'm currently a SWE Intern&nbsp;
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
          violin, piano, & guitar.
        </div>
        <br />
        <br />
        <div className="font-bold text-2xl">Experience</div>
        <br />
        <div className="flex justify-between">
          <div>Executive Director @ {`health{hacks}`}</div>
          <div>Apr 2022 - Jun 2023</div>
        </div>

        <div className="font-bold text-2xl">Projects</div>
        <br />
        <div className="font-bold text-2xl">Technologies</div>
        <br />
        <div className="flex gap-3 text-blue-400">
          <BiLogoJavascript size={40} />
          <BiLogoPython size={40} />
          <BiLogoJava size={40} />
          <BiLogoCPlusPlus size={40} />
          <BiLogoPostgresql size={40} />
          <AiFillHtml5 size={40} />
          <BiLogoTailwindCss size={40} />
          <BiLogoReact size={40} />
          <SiTensorflow size={40} />
          <BiLogoGraphql size={40} />
          <BiLogoVisualStudio size={40} />
          <SiPytorch size={40} />
          <DiVim size={40} />
          <BiLogoFigma size={40} />
          <BiLogoMongodb size={40} />
        </div>
        <div className="text-xs absolute bottom-4 text-center left-0 right-0">
          &copy; 2023 Michael Yi, All Rights Reserved.
        </div>
      </div>
    </div>
  );
}
