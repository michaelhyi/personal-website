import Link from "next/link";
import Container from "@/components/Container";

const Contact = () => {
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
        <Link
          href="mailto:contact@michael-yi.com"
          className="text-[10px] text-neutral-300
                          duration-500
                          hover:opacity-50"
        >
          contact@michael-yi.com
        </Link>
      </div>
    </Container>
  );
};

export default Contact;
