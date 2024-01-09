import Link from "next/link";
import BackButton from "@/components/BackButton";
import Hoverable from "@/components/Hoverable";
import Footer from "@/components/Footer";

export default function About() {
  return (
    <div className="h-screen bg-black text-white">
      <div
        className="absolute
                   left-1/2
                   top-1/2
                   -translate-x-1/2
                   -translate-y-1/2
                   transform
                   flex
                   flex-col
                   text-left
                   text-[13px]
                   gap-4
                   sm:w-[360px]
                   md:w-[576px]
                   "
      >
        <BackButton href="/" text="Home" />
        <div>
          Michael Yi is a software engineer based in Atlanta, GA. He&apos;s
          currently studying computer science at Georgia Tech with
          concentrations in Artificial Intelligence and Information
          Internetworks. Previously, he interned at Ardent Labs and
          MegaEvolution.
        </div>
        <div>
          Michael loves building. When he&apos;s not creating software, he loves
          watching cinema.
        </div>
        <div className="flex">
          Reach him at&nbsp;
          <Hoverable>
            <Link
              href="mailto:contact@michael-yi.com"
              className="underline text-neutral-400"
            >
              contact@michael-yi.com
            </Link>
          </Hoverable>
          .
        </div>
      </div>
      <Footer absolute />
    </div>
  );
}
