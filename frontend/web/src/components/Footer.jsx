import { AiFillGithub, AiFillLinkedin } from "react-icons/ai";
import Hoverable from "./Hoverable";
import NewTabLink from "./NewTabLink";

const LINKS = [
    {
        href: "https://github.com/michaelhyi/",
        icon: AiFillGithub,
    },
    {
        href: "https://www.linkedin.com/in/michaelhyi/",
        icon: AiFillLinkedin,
    },
];

export default function Footer({ absolute }) {
    return (
        <footer
            className={`flex 
                  flex-col
                  items-center
                  gap-3
                  text-[10px]
                  ${
                      absolute
                          ? "absolute bottom-4 left-0 right-0"
                          : "mt-16 pb-4"
                  }
                `}
        >
            <section className="flex gap-2">
                {LINKS.map(({ href, icon: Icon }) => (
                    <Hoverable key={href}>
                        <NewTabLink href={href}>
                            <Icon size={15} />
                        </NewTabLink>
                    </Hoverable>
                ))}
            </section>
            <p>&copy; 2023 Michael Yi, All Rights Reserved.</p>
        </footer>
    );
}
