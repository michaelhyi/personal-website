import Footer from "@/components/Footer";

const NotFound = () => {
   return (
      <>
         <div
            className="absolute
                        left-1/2
                        top-1/2
                        -translate-x-1/2
                        -translate-y-1/2
                        transform
                        text-[10px]
                        text-neutral-400"
         >
            404, not found.
         </div>
         <Footer absolute />
      </>
   );
};

export default NotFound;
