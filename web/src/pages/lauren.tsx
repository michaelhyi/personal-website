import Image from "next/image";

const Lauren = () => {
  return (
    <div className="flex h-screen justify-center items-center">
      <div className="flex flex-col gap-8 items-center">
        <Image src="/Lauren.png" height={250} width={250} alt="la la" />
        <div className="text-sm font-light">
          good luck on your quizzes today!! don&apos;t stress so much,
          you&apos;ll kill it! i love uuuuuuu
        </div>
      </div>
    </div>
  );
};

export default Lauren;
