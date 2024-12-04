import { useContext } from "react"
import { AuthContext } from "../context/AuthProvider/AuthContext"

const useAuth = () => {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used in an AuthProvider");
    }

    return context;
}

export default useAuth;