import Container from "@/components/Container";
import Image from "next/image";

const Lauren = () => {
   return (
      <Container absoluteFooter>
         <div
            className="absolute
                        left-1/2
                        top-1/2
                        -translate-x-1/2
                        -translate-y-1/2"
         >
            <div className="flex flex-col items-center gap-8">
               <Image
                  src="/Lauren.png"
                  width={250}
                  height={250}
                  alt="mimi and lala"
               />
               <div className="text-sm font-light">coming soon...</div>
            </div>
         </div>
      </Container>
   );
};

export default Lauren;
