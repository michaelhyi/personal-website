import Container from "@/components/Container";
import Image from "next/image";

const Lauren = () => {
   return (
      <Container>
         {" "}
         <div className="flex flex-col items-center gap-8">
            <Image
               src="/maur.jpg"
               width={250}
               height={250}
               alt="mimi and lala"
            />
            <div className="text-sm font-light">
               GOOD LUCK ON CHEMMMMMM. YOU&apos;LL KILL IT IM SO PROUD OF YOU
               💯😘
            </div>
         </div>
      </Container>
   );
};

export default Lauren;
