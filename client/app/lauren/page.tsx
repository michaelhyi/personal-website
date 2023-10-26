import Image from "next/image";

const Lauren = () => {
   return (
      <div className="flex h-screen items-center justify-center">
         <div className="sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[640px] flex flex-col items-center gap-8">
            <Image
               src="/maur.JPG"
               width={250}
               height={250}
               alt="mimi and lala"
            />
            <div className="text-sm font-light">
               GOOD LUCK ON CHEMMMMMM. YOU&apos;LL KILL IT IM SO PROUD OF YOU
               💯😘
            </div>
         </div>
      </div>
   );
};

export default Lauren;
