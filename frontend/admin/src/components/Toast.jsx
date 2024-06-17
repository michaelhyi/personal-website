export default function Toast({ animation, message, success }) {
    return (
        <div
            className={`
                  ${animation}
                  ${success ? "bg-green-400" : "bg-red-400"}
                    absolute 
                left-1/2
top-8
-translate-x-1/2
transform
                  shadow-lg
                  rounded-lg
                  w-48
text-center
                `}
        >
            <div className="px-5 py-3 font-semibold text-sm">{message}</div>
        </div>
    );
}
