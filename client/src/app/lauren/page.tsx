import Image from "next/image";

const Lauren = () => {
   return (
      <div className="flex h-screen items-center justify-center">
         <div className="flex flex-col items-center gap-8 sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[640px]">
            <div className="flex items-center gap-6 sm:flex-col md:flex-row ">
               <Image
                  src="/mimi+lala.png"
                  width={250}
                  height={250}
                  alt="mimi and lala"
               />
               🤍
               <Image
                  src="/mimi+lala2.png"
                  width={250}
                  height={250}
                  alt="mimi and lala 2"
               />
            </div>
            <div className="text-sm font-light">
               day 0 to day 229 !! now 8 months old. happy 8 months baby &lt;3
            </div>
         </div>
      </div>
   );
};

export default Lauren;
