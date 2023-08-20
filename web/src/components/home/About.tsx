import Image from "next/image";
import { icons } from "../../data/icons";
import Link from "../links/Link";

const About = () => {
  return (
    <div>
      <div className="flex mt-36 gap-8">
        <Image
          unoptimized={true}
          src="/Michael.png"
          alt="me"
          height={115}
          width={115}
          className="rounded-full"
        />
        <div>
          <div className="text-3xl font-bold">Michael Yi</div>
          <div className="mt-2">Software Engineer</div>
          <div>
            B.S. in Computer Science&nbsp;
            <Link href="https://www.gatech.edu/">@ Georgia Tech</Link>
          </div>
          <div className="flex gap-2 mt-2">
            {icons.map(({ icon: Icon, href }, i) => (
              <Link key={i} href={href}>
                <Icon size={20} className="text-pink-300" />
              </Link>
            ))}
          </div>
        </div>
      </div>
      <div className="mt-8">
        I love building. With my aptitude for leadership, communication, and
        passion, I deliver exceptional results in software and entrepreneurship.
      </div>
    </div>
  );
};

export default About;
