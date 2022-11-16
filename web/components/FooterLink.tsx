import React from "react";

interface Props {
  href: string;
  text: string;
}

const FooterLink: React.FC<Props> = ({ href, text }) => {
  return (
    <div className="sm:text-xs md:text-lg xl:text-xl hover:cursor-pointer duration-300 hover:opacity-25">
      <a href={href} target="_blank" rel="noreferrer">
        {text}
      </a>
    </div>
  );
};

export default FooterLink;
