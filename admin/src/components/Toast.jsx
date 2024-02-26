import { AnimatePresence, motion } from "framer-motion";

export default function Toast({ visible, message, success }) {
    return (
        <AnimatePresence>
            {visible && (
                <motion.div
                    initial={{ opacity: 0, sclae: 0.9 }}
                    animate={{ opacity: 1, scale: 1 }}
                    exit={{ opacity: 0, scale: 0.9 }}
                    className={`
                  ${success ? "bg-green-400" : "bg-red-400"}
                  flex
                  justify-center
                  shadow-lg
                  rounded-lg
                `}
                >
                    <div className="px-5 py-3 font-semibold text-sm">
                        {message}
                    </div>
                </motion.div>
            )}
        </AnimatePresence>
    );
}
