export default function Toast({ visible, message, success }) {
    return (
        <div
            className={`${visible ? "animate-enter" : "animate-leave"}
                  ${success ? "bg-green-400" : "bg-red-400"}
                  flex
                  justify-center
                  shadow-lg
                  rounded-lg
                `}
        >
            <div className="px-5 py-3 font-semibold text-sm">{message}</div>
        </div>
    );
}
