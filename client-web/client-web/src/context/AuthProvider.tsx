import { useEffect, useState } from "react";
import { IUser } from "./authTypes";
import { getUserLocalStorage, setUserLocalStorage } from "../utils/storageUtils";
import { AuthContext } from "./AuthContext";
import { loginRequest } from "../auth/authService";

export const AuthProvider = ({ children }: { children: JSX.Element }) => {
    const [user, setUser] = useState<IUser | null>(null);

    useEffect(() => {
        const user = getUserLocalStorage();
        if (user) setUser(user);
        return () => {
        }
    }, []);

    async function authenticate(email: string, password: string) {
        const data = await loginRequest(email, password);

        const payload = { token: data.token, email };

        setUser(payload);
        setUserLocalStorage(payload);
    }

    async function logout() {
        setUser(null);
        setUserLocalStorage(null);
    }

    return (
        <AuthContext.Provider value={{ ...user, authenticate, logout }}>
            {children}
        </AuthContext.Provider >
    );
}