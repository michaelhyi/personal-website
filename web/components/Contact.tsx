import { useForm, ValidationError } from "@formspree/react";
import { useState } from "react";
import Input from "./Input";
import { useToast } from "@chakra-ui/react";

const Contact = () => {
  const [state, handleSubmit] = useForm(process.env.NEXT_PUBLIC_FORM!);

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");

  const toast = useToast();

  return (
    <div id="contact" className="pt-8 flex flex-col items-center">
      <div className="font-bold text-5xl mt-24 mb-4">Contact</div>
      <form
        onSubmit={async (e) => {
          e.preventDefault();

          const response = await handleSubmit(e);

          if (response.body.ok) {
            setName("");
            setEmail("");
            setMessage("");

            toast({
              title: "Submitted!",
              description:
                "You have successfully submitted to my contact form. I will respond within 1-2 business days.",
              status: "success",
              duration: 10000,
              isClosable: true,
            });
          }
        }}
        className="mt-12 flex-col space-y-6 sm:w-[300px] md:w-[512px]"
      >
        <Input
          type="text"
          name="name"
          label="Name"
          value={name}
          setValue={setName}
        />
        <div className="font-poppins font-semibold text-red-300 text-sm">
          <ValidationError prefix="Name" field="name" errors={state.errors} />
        </div>
        <Input
          type="email"
          name="email"
          label="Email"
          value={email}
          setValue={setEmail}
        />
        <div className="font-poppins font-semibold text-red-300 text-sm">
          <ValidationError prefix="Email" field="email" errors={state.errors} />
        </div>
        <Input
          name="message"
          label="Message"
          value={message}
          setValue={setMessage}
        />
        <div className="font-poppins font-semibold text-red-300 text-sm">
          <ValidationError
            prefix="Message"
            field="message"
            errors={state.errors}
          />
        </div>
        <button
          disabled={state.submitting}
          type="submit"
          className="bg-[#1E1E1E] duration-300 hover:opacity-25 hover:cursor-pointer font-poppins text-white border-[1px] border-white text-center p-4 rounded-xl text-semibold text-xl mt-12 w-full"
        >
          Submit
        </button>
      </form>
    </div>
  );
};

export default Contact;
