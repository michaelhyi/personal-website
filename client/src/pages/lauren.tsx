import Image from "next/image";

const Lauren = () => {
  return (
    <div className="flex h-screen justify-center items-center">
      <div className="flex flex-col items-center gap-8 sm:w-[320px] md:w-[480px] lg:w-[512px] xl:w-[640px]">
        <Image src="/Lauren.png" width={250} height={250} alt="la la" />
        <div className="text-light font-sm">i love my gf!!!!</div>
      </div>
    </div>
  );
};

export default Lauren;
