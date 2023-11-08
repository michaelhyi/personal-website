import Image from "next/image";
import Link from "next/link";
import Container from "@/components/Container";

const Home = () => {
  return (
    <Container absoluteFooter>
      <div
        className="absolute
                   left-1/2
                   top-1/2
                   -translate-x-1/2
                   -translate-y-1/2
                   transform
                   flex
                   flex-col
                   items-center
                   text-center
                   "
      >
        <Image
          alt="michael"
          className="rounded-full"
          height={100}
          src="/michael.png"
          width={100}
        />
        <div className="mt-4 text-2xl font-ligh">Michael Yi</div>
        <div className="mt-1 text-xs font-light text-neutral-400">
          Software Engineer
        </div>
        <div className="flex mt-2 text-[10px] text-neutral-300">
          <Link
            href="/portfolio"
            className="cursor-pointer
                          duration-500
                          hover:opacity-50"
          >
            Portfolio
          </Link>
          &nbsp;&nbsp;&#183;&nbsp;&nbsp;Blog&nbsp;&nbsp;&#183;&nbsp;&nbsp;
          <Link
            href="https://michael-yi.com/Resume.pdf"
            className="cursor-pointer
                          duration-500
                          hover:opacity-50"
          >
            Resume
          </Link>
          &nbsp;&nbsp;&#183;&nbsp;&nbsp;
          <Link
            href="/contact"
            className="cursor-pointer
                          duration-500
                          hover:opacity-50"
          >
            Contact
          </Link>
        </div>
      </div>
    </Container>
  );
};

export default Home;
